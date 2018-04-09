package com.tajiang.leifeng.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.adapter.ApartmentListAdapter;
import com.tajiang.leifeng.adapter.ApartmentZoneListAdapter;
import com.tajiang.leifeng.model.Apartment;
import com.tajiang.leifeng.model.ApartmentZone;
import com.tajiang.leifeng.model.SchoolApartment;

import java.util.List;

public class SelectApartmentDialog extends Dialog implements View.OnClickListener {

    private Context context;

    private View contentView;
    private View rectBgSelectApartmentDialog;

    private ListView lvRectApartment;
    private ListView lvApartment;

    private ApartmentZoneListAdapter apartmentZoneListAdapter;
    private ApartmentListAdapter apartmentListAdapter;

    private ApartmentZone apartmentZoneSelected;
    private Apartment apartmentSelected;

    private OnSelectApartmentListener onSelectApartmentListener;

    private SchoolApartment schoolApartment;

    public SelectApartmentDialog( Context context, SchoolApartment schoolApartment) {
        super(context, R.style.dialog_psw_pay);
        this.context = context;
        this.schoolApartment = schoolApartment;

        contentView = getLayoutInflater().inflate(R.layout.dialog_user_adress_add_select_apartment, null);
        rectBgSelectApartmentDialog = contentView.findViewById(R.id.rectBgSelectApartmentDialog);

        lvRectApartment = (ListView) contentView.findViewById(R.id.lvRectApartment);
        lvApartment = (ListView) contentView.findViewById(R.id.lvApartment);

        TextView tvNameSchoolApartmentDialog = (TextView) contentView.findViewById(R.id.tvNameSchoolApartmentDialog);
        tvNameSchoolApartmentDialog.setText(schoolApartment.getName());

        if(schoolApartment != null && schoolApartment.getZonesList().size() >=1){
            apartmentZoneListAdapter = new ApartmentZoneListAdapter(context, schoolApartment.getZonesList(), R.layout.layout_title_list_content);
            lvRectApartment.setAdapter(apartmentZoneListAdapter);

            lvRectApartment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    apartmentZoneSelected = (ApartmentZone) parent.getItemAtPosition(position);

                    apartmentZoneListAdapter.setSelected(position);
                    selectApartmentZone(SelectApartmentDialog.this.context, SelectApartmentDialog.this.schoolApartment.getZonesList().get(position).getApartmentList());

                    apartmentListAdapter.cannelSelect();
                }
            });
            // 默认选中第一个寝室区
            apartmentZoneSelected = schoolApartment.getZonesList().get(0);
            selectApartmentZone(context, schoolApartment.getZonesList().get(0).getApartmentList());
        }

        rectBgSelectApartmentDialog.setOnClickListener(this);
        setContentView(contentView);

    }

    // 选中一个寝室区，刷新右边的寝室楼信息
    private void selectApartmentZone(Context context, List<Apartment> apartmentList) {
        apartmentListAdapter = new ApartmentListAdapter(context, apartmentList, R.layout.layout_title_list_content);
        lvApartment.setAdapter(apartmentListAdapter);
        lvApartment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                apartmentSelected = (Apartment) parent.getItemAtPosition(position);

                if (onSelectApartmentListener != null) {
                    onSelectApartmentListener.onSelectApartment(schoolApartment , apartmentZoneSelected , apartmentSelected);
                    dismiss();
                }

                apartmentListAdapter.setSelect(position);

            }
        });
    }

    public OnSelectApartmentListener getOnSelectApartmentListener() {
        return onSelectApartmentListener;
    }

    public void setOnSelectApartmentListener(OnSelectApartmentListener onSelectApartmentListener) {
        this.onSelectApartmentListener = onSelectApartmentListener;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rectBgSelectApartmentDialog:
                dismiss();
                break;
        }
    }

    public interface OnSelectApartmentListener{
        public void onSelectApartment(SchoolApartment schoolApartment , ApartmentZone apartmentZone , Apartment apartment);
    }

}
