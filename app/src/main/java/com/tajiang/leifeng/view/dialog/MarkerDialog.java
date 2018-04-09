package com.tajiang.leifeng.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.utils.BuyCarMapUtils;
import com.tajiang.leifeng.utils.BuyCarUtils;
import com.tajiang.leifeng.view.FlowlayoutTags;

import java.util.ArrayList;
import java.util.List;

public class MarkerDialog extends Dialog implements View.OnClickListener {

    private View tv_negetiveHintDialog;
    private View tv_positiveHintDialog;

    private List<String> list = new ArrayList<String>();

    private EditText et_tag;
    private FlowlayoutTags flred2;

    private TextView tv_markerOrder;

    public MarkerDialog(Context context, List<String> list, TextView tv_markerOrder, List<Integer> tagListCheck, String markInput) {
        super(context, R.style.dialog_operate);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        setContentView(R.layout.dialog_add_marker);
        this.tv_markerOrder = tv_markerOrder;
        this.list = list;

        tv_negetiveHintDialog = findViewById(R.id.tv_negetiveHintDialog);
        tv_positiveHintDialog = findViewById(R.id.tv_positiveHintDialog);
        tv_negetiveHintDialog.setOnClickListener(this);
        tv_positiveHintDialog.setOnClickListener(this);

        flred2 = (FlowlayoutTags) findViewById(R.id.fl_red2);
        et_tag = (EditText) findViewById(R.id.et_tag);
        refreshCategorys(flred2, list);

        /*初始化文字和选中标签*/
        et_tag.setText(markInput);
        flred2.setCheckedTagsIndexArrayList(tagListCheck);

    }

    public void refreshCategorys(FlowlayoutTags flowlayoutTags, List<String> list) {
        flowlayoutTags.removeAllViews();
        flowlayoutTags.setTags(list);
        flowlayoutTags.setTagsUncheckedColorAnimal(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_negetiveHintDialog:
                dismiss();
                break;
            case R.id.tv_positiveHintDialog:

                ArrayList<Integer> tagList = flred2.getCheckedTagsIndexArrayList();
                String inputMarker = et_tag.getText().toString().trim();

//                StringBuffer markerString = new StringBuffer();
//                for (Integer position : tagList) {
//                    markerString.append(list.get(position) + "，");
//                }

//                if (TextUtils.isEmpty(inputMarker)) {
//                    if(tagList.size() != 0){
//                        markerString.delete(markerString.length() - 1, markerString.length());
//                    }
//                } else {
//                    markerString.append(inputMarker);
//                }

                BuyCarMapUtils.getCurBuyCarUtils().setMarkInput(inputMarker);
                BuyCarMapUtils.getCurBuyCarUtils().setMarkList(tagList);

                tv_markerOrder.setText(BuyCarMapUtils.getCurBuyCarUtils().getMarkerText());

                dismiss();
                break;
        }
    }


}
