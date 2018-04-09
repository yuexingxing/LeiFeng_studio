package com.tajiang.leifeng.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.adapter.ActionListAdapter;
import com.tajiang.leifeng.model.StallsFullcut;
import com.tajiang.leifeng.model.StoreStalls;
import com.tajiang.leifeng.utils.NumberUtils;

import java.util.List;

/**
 * Created by Admins on 2016/10/29.
 */
public class NotifyDialog extends Dialog implements View.OnClickListener {

    private View contentView;
    private EditText et_store_stalls_notice;
    private ImageView iv_hide_view;

    private Context mContext;
    private RecyclerView rv_action_list;
    private ActionListAdapter mActionListAdapter;

    public NotifyDialog(Context context, StoreStalls storeStalls) {
        super(context, R.style.dialog_operate);
        this.mContext = context;
        setCancelable(true);
        setCanceledOnTouchOutside(true);

        contentView = getLayoutInflater().inflate(R.layout.dialog_food_fragment_notify, null);
        iv_hide_view = (ImageView) contentView.findViewById(R.id.iv_hide_view);
        et_store_stalls_notice = (EditText) contentView.findViewById(R.id.et_store_stalls_notice);
        rv_action_list = (RecyclerView) contentView.findViewById(R.id.rv_action_list);

        iv_hide_view.setOnClickListener(this);
        initData(storeStalls);

        setContentView(contentView);
    }

    private void initData(StoreStalls storeStalls) {
        mActionListAdapter = new ActionListAdapter(mContext, storeStalls.getActivityList());
        rv_action_list.setLayoutManager(new LinearLayoutManager(mContext));
        rv_action_list.setAdapter(mActionListAdapter);

        if (storeStalls.getNotice() == null || storeStalls.getNotice().equals("")) {
            et_store_stalls_notice.setText("暂无商家公告。");
        } else {
            et_store_stalls_notice.setText("        " + storeStalls.getNotice());
        }
    }

//    /**
//     * 新增优惠活动 View (重用)
//     * @param stallsFullcut
//     */
//    public void addNewActionTextView(@Nullable StallsFullcut stallsFullcut) {
//        TextView textView;
//        textView = new TextView(mContext);
//        Drawable drawable= mContext.getResources().getDrawable(R.drawable.icon_user_invite);
//        drawable.setBounds(0, 0,
//                drawable.getMinimumWidth(),
//                drawable.getMinimumHeight());
//        textView.setLayoutParams(new LinearLayout.LayoutParams(
//                ViewGroup.LayoutParams.WRAP_CONTENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT));
//        textView.setMaxLines(1);
//        textView.setTextSize(12);
//        textView.setTextColor(Color.WHITE);
//        textView.setEllipsize(TextUtils.TruncateAt.END);
//        textView.setGravity(Gravity.CENTER_VERTICAL);
//        textView.setCompoundDrawables(stallsFullcut == null ? null : drawable, null, null, null);
//        textView.setCompoundDrawablePadding(18);
//        textView.setPadding(0,0,-18,0);  //此处抵消 setCompoundDrawablePadding 方法造成的文本距右边界的距离数值
//        if (stallsFullcut == null) {
//            textView.setText("暂无商家活动。");
//        } else {
//            textView.setText("满" + NumberUtils.clearTailZero(stallsFullcut.getPrice()) + "减"
//                    + NumberUtils.clearTailZero(stallsFullcut.getLimitPrice()) + "  ");
//        }
//
//        ll_activity_root.addView(textView);
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_hide_view:
                dismiss();
                break;
        }
    }

}
