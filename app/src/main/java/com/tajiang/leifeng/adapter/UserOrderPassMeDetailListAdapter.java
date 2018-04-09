package com.tajiang.leifeng.adapter;

import java.util.List;

import android.content.Context;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.activity.UserOrderDetialActivity;
import com.tajiang.leifeng.commonadapter.CommonAdapter;
import com.tajiang.leifeng.commonadapter.ViewHolder;
import com.tajiang.leifeng.model.Order;
import com.tajiang.leifeng.model.OrderGoods;
import com.tajiang.leifeng.view.EvaluateView;
import com.tajiang.leifeng.view.EvaluateView.OnEvaluateListener;

public class UserOrderPassMeDetailListAdapter extends CommonAdapter<OrderGoods> implements OnEvaluateListener {

    private Order order;

    private UserOrderDetialActivity userOrderDetialActivity;

    public UserOrderPassMeDetailListAdapter(Context context, List<OrderGoods> mDatas, int itemLayoutId, Order order) {
        super(context, mDatas, itemLayoutId);

        this.order = order;
        userOrderDetialActivity = (UserOrderDetialActivity) context;

    }

    @Override
    public void convert(ViewHolder helper, OrderGoods item) {

        helper.setFoodImageByUrl(R.id.iv_picGoodOrderDetailPassMe, item.getGoodsImage());

        helper.setText(R.id.iv_priceOrderDetailPassMe, item.getGoodsPayPrice() + "");
//        helper.setText(R.id.tv_timeUserOrderDetailPassMe, order.getFinishedTime());

        EvaluateView evaluateView = (EvaluateView) helper.getConvertView().findViewById(R.id.ev_userOrderPassMeGoodDetail);
        evaluateView.setOnEvaluateListener(this);
        evaluateView.setTag(item.getId());

        if (item.getEvaluateGoods() != null) {
            evaluateView.setEvaluateStart(item.getEvaluateGoods().getScores());
        }

    }

    @Override
    public void onEvaluate(EvaluateView evaluateView, int num) {
        String goodId = (String) evaluateView.getTag();
        userOrderDetialActivity.doEvaluateGood(goodId, num);
    }

}
