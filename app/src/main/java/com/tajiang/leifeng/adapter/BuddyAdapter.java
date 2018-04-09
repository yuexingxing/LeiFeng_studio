package com.tajiang.leifeng.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.model.StallsFullcut;

import java.util.List;

/**
 * Created by hexiuhui on 2017/7/26.
 */
public class BuddyAdapter extends BaseExpandableListAdapter{
    private String group;
    private List<StallsFullcut> buddy;
    private Context context;
    private LayoutInflater inflater;

    public BuddyAdapter(String group, List<StallsFullcut> buddy, Context context) {
        super();
        this.group = group;
        this.buddy = buddy;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return 1;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return buddy.size() - 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return group;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return buddy.get(childPosition + 1).getActivityName();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.group, null);
        TextView groupNameTextView = (TextView) convertView.findViewById(R.id.tv_group);
        ImageView ivSelector = (ImageView) convertView.findViewById(R.id.iv_selector);

        groupNameTextView.setText(getGroup(groupPosition).toString());
        ivSelector.setImageResource(R.drawable.home_icon_drop_down2);

        // 更换展开分组图片
        if (!isExpanded) {
            ivSelector.setImageResource(R.drawable.home_icon_drop_down);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.child, null);
        TextView nickTextView = (TextView) convertView.findViewById(R.id.tv_child);

        nickTextView.setText(getChild(groupPosition, childPosition).toString());

        return convertView;
    }

    // 子选项是否可以选择
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
