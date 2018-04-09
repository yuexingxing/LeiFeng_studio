package com.tajiang.leifeng.push;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.igexin.sdk.PushConsts;
import com.igexin.sdk.PushManager;
import com.tajiang.leifeng.R;
import com.tajiang.leifeng.activity.HomeActivity;
import com.tajiang.leifeng.activity.UserCouponActivity;
import com.tajiang.leifeng.application.TJApp;
import com.tajiang.leifeng.model.Notify;
import com.tajiang.leifeng.utils.AppUtils;
import com.tajiang.leifeng.utils.LogUtils;
import com.tajiang.leifeng.utils.SharedPreferencesUtils;
import com.tajiang.leifeng.utils.ToastUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/26.
 */
public class PushLeiFengReceiver extends BroadcastReceiver {

    public static final int NOTIFICATION_ID = 0;
    public static final String NEW_ORDER_MESSAGE = "new_order_message";
    public static final String NEW_MESSAGE = "new_message";

    /**
     * 应用未启动, 个推 service已经被唤醒,保存在该时间段内离线消息(此时 GetuiSdkDemoActivity.tLogView == null)
     */
    public static StringBuilder payloadData = new StringBuilder();

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        LogUtils.e("GetuiSdkDemo", "onReceive() action=" + bundle.getInt("action"));

        switch (bundle.getInt(PushConsts.CMD_ACTION)) {
            case PushConsts.GET_MSG_DATA:
                // 获取透传数据
                // String appid = bundle.getString("appid");
                byte[] payload = bundle.getByteArray("payload");
                String taskid = bundle.getString("taskid");
                String messageid = bundle.getString("messageid");
                // smartPush第三方回执调用接口，actionid范围为90000-90999，可根据业务场景执行
                boolean result = PushManager.getInstance().sendFeedbackMessage(context, taskid, messageid, 90001);
                LogUtils.d("第三方回执接口调用" + (result ? "成功" : "失败"));

                ParseMessage(new String(payload));

                break;

            case PushConsts.GET_CLIENTID:
                // 获取ClientID(CID)
                // 第三方应用需要将CID上传到第三方服务器，并且将当前用户帐号和CID进行关联，以便日后通过用户帐号查找CID进行消息推送
                String cid = bundle.getString("clientid");
                SharedPreferencesUtils.put(TJApp.getContext(), SharedPreferencesUtils.CHANNEL_ID, cid);
//                if (GetuiSdkDemoActivity.tView != null) {
//                    GetuiSdkDemoActivity.tView.setText(cid);
//                }
                LogUtils.d(cid);
                break;
            case PushConsts.GET_SDKONLINESTATE:
                boolean online = bundle.getBoolean("onlineState");
                Log.d("GetuiSdkDemo", "online = " + online);
                break;

            case PushConsts.SET_TAG_RESULT:
                String sn = bundle.getString("sn");
                String code = bundle.getString("code");

                String text = "设置标签失败, 未知异常";
                switch (Integer.valueOf(code)) {
                    case PushConsts.SETTAG_SUCCESS:
                        text = "设置标签成功";
                        break;

                    case PushConsts.SETTAG_ERROR_COUNT:
                        text = "设置标签失败, tag数量过大, 最大不能超过200个";
                        break;

                    case PushConsts.SETTAG_ERROR_FREQUENCY:
                        text = "设置标签失败, 频率过快, 两次间隔应大于1s";
                        break;

                    case PushConsts.SETTAG_ERROR_REPEAT:
                        text = "设置标签失败, 标签重复";
                        break;

                    case PushConsts.SETTAG_ERROR_UNBIND:
                        text = "设置标签失败, 服务未初始化成功";
                        break;

                    case PushConsts.SETTAG_ERROR_EXCEPTION:
                        text = "设置标签失败, 未知异常";
                        break;

                    case PushConsts.SETTAG_ERROR_NULL:
                        text = "设置标签失败, tag 为空";
                        break;

                    case PushConsts.SETTAG_NOTONLINE:
                        text = "还未登陆成功";
                        break;

                    case PushConsts.SETTAG_IN_BLACKLIST:
                        text = "该应用已经在黑名单中,请联系售后支持!";
                        break;

                    case PushConsts.SETTAG_NUM_EXCEED:
                        text = "已存 tag 超过限制";
                        break;

                    default:
                        break;
                }

                Log.d("GetuiSdkDemo", "settag result sn = " + sn + ", code = " + code);
                Log.d("GetuiSdkDemo", "settag result sn = " + text);
                break;
            case PushConsts.THIRDPART_FEEDBACK:
                /*
                 * String appid = bundle.getString("appid"); String taskid =
                 * bundle.getString("taskid"); String actionid = bundle.getString("actionid");
                 * String result = bundle.getString("result"); long timestamp =
                 * bundle.getLong("timestamp");
                 *
                 * Log.d("GetuiSdkDemo", "appid = " + appid); Log.d("GetuiSdkDemo", "taskid = " +
                 * taskid); Log.d("GetuiSdkDemo", "actionid = " + actionid); Log.d("GetuiSdkDemo",
                 * "result = " + result); Log.d("GetuiSdkDemo", "timestamp = " + timestamp);
                 */
                break;

            default:
                break;
        }
    }

    private void ParseMessage(String strJson) {
        Notify notify = parseJson2Notify(strJson);
        if (notify != null) {
            Notification2User(TJApp.getContext(), notify);  //弹出通知
        }
    }

    /**
     * 判断不同的通知类型
     * @param context
     * @param notify
     */
    private void Notification2User(Context context, Notify notify) {
        PackageManager manager = context.getPackageManager();
        Intent intent = manager.getLaunchIntentForPackage(TJApp.MY_PKG_NAME);
        switch (notify.getType()) {
            case Notify.TYPE_RED_PACKAGE:  //优惠卷
                //判断APP是否存活
                if (AppUtils.instance(context).isAppAlive()) {
                    intent = new Intent(context, UserCouponActivity.class);
                }
                Notify2User(context, intent,  notify);
                break;
            default:  //默认通知
                //判断APP是否存活
                if (AppUtils.instance(context).isAppAlive()) {
                    intent = new Intent(context, HomeActivity.class);
                }
                Notify2User(context, intent,  notify);
                break;
        }

    }

    private void Notify2User(Context context, Intent intent,  Notify notify) {
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);//PendingIntent点击通知后所跳转的页面

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(
                context).setContentTitle(notify.getDescription())
                .setContentText(notify.getContent())
                .setTicker("全民雷锋")
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentIntent(pendingIntent);//执行intent

        notificationBuilder.setAutoCancel(true);//自己维护通知的消失

        Notification notification = notificationBuilder.build();

        notification.flags = Notification.FLAG_ONGOING_EVENT; // 设置常驻 Flag
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.defaults = Notification.DEFAULT_SOUND;
        notification.vibrate = new long[]{500L, 200L, 200L, 500L};
        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(NOTIFICATION_ID, notification);
    }


    /**
     * 解析来自推送的订单
     */
    private Notify parseJson2Notify(String json) {
        Notify notify = null;
        Gson gson = new Gson();
        try {
            Type type = new TypeToken<Notify>() {}.getType();
            notify = gson.fromJson(json, type);
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
        }
        return notify;
    }

}
