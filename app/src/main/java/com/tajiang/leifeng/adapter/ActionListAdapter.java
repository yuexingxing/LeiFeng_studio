package com.tajiang.leifeng.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.tajiang.leifeng.R;
import com.tajiang.leifeng.model.StallsFullcut;
import com.tajiang.leifeng.utils.GlideRoundTransform;
import com.tajiang.leifeng.utils.NumberUtils;

import java.util.List;

/**
 * Created by Admins on 2016/11/15.
 */
public class ActionListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private boolean haveNotice;

    private List<StallsFullcut> mData;

    public ActionListAdapter(Context context, List<StallsFullcut> list) {
        this.mContext = context;
        this.mData = list;
        this.haveNotice = true;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stalls_action, null);
        RecyclerView.ViewHolder myViewHolder = new ItemViewHolder(mItemView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder viewHolder = (ItemViewHolder) holder;
        if (haveNotice) {
            StallsFullcut stallsFullcut = mData.get(position);
            viewHolder.imageView.setVisibility(View.VISIBLE);
            //初始化头部档口展示图片
            Glide.with(mContext)
                    .load(stallsFullcut.getUrl())
                    .transform(new CenterCrop(mContext)   //此方式解决CenterCrop与Transformer的共存问题
                            ,new GlideRoundTransform(mContext, 2))
                    .placeholder(R.drawable.icon_order_action_empty)
                    .error(R.drawable.icon_order_action_empty)
                    .crossFade()
                    .into(viewHolder.imageView);
            viewHolder.textView.setText(stallsFullcut.getActivityName());
        } else {
            viewHolder.imageView.setVisibility(View.GONE);
            viewHolder.textView.setText("暂无商家活动");
        }
    }

    @Override
    public int getItemCount() {
        if (mData == null || mData.size() == 0) {
            haveNotice = false;
            return 1;
        } else {
            haveNotice = true;
            return mData.size();
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.iv_action);
            this.textView = (TextView) itemView.findViewById(R.id.tv_action);
        }
    }
}
