package com.tajiang.leifeng.adapter;

import android.content.Context;
import android.net.Uri;

import com.facebook.drawee.view.SimpleDraweeView;
import com.tajiang.leifeng.R;
import com.tajiang.leifeng.commonadapter.CommonAdapter;
import com.tajiang.leifeng.commonadapter.ViewHolder;
import com.tajiang.leifeng.model.Adv;
import com.tajiang.leifeng.model.Order;

import java.util.List;

/**
 * Created by 12154 on 2016/1/28.
 */
public class HomeAdvAdapter extends CommonAdapter<Adv> {

    public HomeAdvAdapter(Context context, List<Adv> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, Adv item) {

        Uri uri = Uri.parse(item.getImgUrl());
        SimpleDraweeView draweeView = helper.getView(R.id.ivBannerImage);
        draweeView.setImageURI(uri);
    }
}
