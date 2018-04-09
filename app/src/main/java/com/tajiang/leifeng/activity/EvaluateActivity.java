package com.tajiang.leifeng.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Rating;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tajiang.leifeng.R;
import com.tajiang.leifeng.application.TJApp;
import com.tajiang.leifeng.base.BaseActivity;
import com.tajiang.leifeng.common.http.BaseResponse;
import com.tajiang.leifeng.common.http.TJHttpUtil;
import com.tajiang.leifeng.common.http.TJRequestTag;
import com.tajiang.leifeng.utils.AppUtils;
import com.tajiang.leifeng.utils.FileUtils;
import com.tajiang.leifeng.utils.ImageTool;
import com.tajiang.leifeng.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EvaluateActivity extends BaseActivity implements View.OnClickListener{

	private RatingBar flavor_grade;
	private RatingBar service_grade;
	private RatingBar speed_grade;
	private Button bt_evaluate_submit;
	private EditText et_evaluate_content;

	private GridView gridView;
	private GVAdapter adapter;
	private static final String IMG_ADD_TAG = "a";
	private List<String> photoList = new ArrayList<String>();
	private RelativeLayout evaluate_layout;

	private static final int TAKE_PICTURE = 0x000001;
	private static final int TAKE_FOLDER = 0x000002;

	private String mOrderId;

	@Override
	protected void initTopBar() {
		setTitle("评价");
		enableNavLeftImage();
	}

	@Override
	protected void initLayout() {
		setContentView(R.layout.activity_user_evaluate);
	}

	@Override
	protected void initView() {
		flavor_grade = (RatingBar) findViewById(R.id.flavor_grade);
		service_grade = (RatingBar) findViewById(R.id.service_grade);
		speed_grade = (RatingBar) findViewById(R.id.speed_grade);
		bt_evaluate_submit = (Button) findViewById(R.id.bt_evaluate_submit);
		et_evaluate_content = (EditText) findViewById(R.id.et_evaluate_content);
		gridView = (GridView) findViewById(R.id.gridview);
		evaluate_layout = (RelativeLayout) findViewById(R.id.evaluate_layout);

		bt_evaluate_submit.setOnClickListener(this);
	}

	@Override
	protected void initDates() {
		super.initDates();
		mOrderId = getIntent().getStringExtra("ORDER_ID");

		photoList.add(IMG_ADD_TAG);
		adapter = new GVAdapter();
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (photoList.get(position).equals(IMG_ADD_TAG)) {
					if (photoList.size() < 4) {
						new PopupWindows(getContext(), evaluate_layout);
					} else
						Toast.makeText(EvaluateActivity.this, "最多只能选择3张照片！", Toast.LENGTH_SHORT).show();
				}
			}
		});
		refreshAdapter();
	}

	@Override
	public void onClick(View view) {
		super.onClick(view);

		switch (view.getId()) {
			case R.id.bt_evaluate_submit:
				String content = et_evaluate_content.getText().toString();
				int service = (int)service_grade.getRating();
				int speed = (int)speed_grade.getRating();
				int taste = (int)flavor_grade.getRating();
				TJHttpUtil.getInstance(this).evaluate(content, mOrderId, service, speed, taste);
				break;
		}
	}

	@Override
	public void onSuccess(BaseResponse response, int requestTag) {
		super.onSuccess(response, requestTag);
		switch (requestTag) {
			case TJRequestTag.TAG_GOOD_EVALUATE:
				showToast("评价成功");
				finish();
				break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data == null) {
			System.out.println("data null");
			return;
		}
		String path = "";
		if (requestCode == TAKE_FOLDER) {
			final Uri uri = data.getData();
			path = ImageTool.getImageAbsolutePath(this, uri);
			System.out.println(path);
		} else {
			if (requestCode == TAKE_PICTURE) {
				if (resultCode == RESULT_OK) {
					Date date = new Date();
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
					String fileName = dateFormat.format(date).toString();
					Bitmap bm = (Bitmap) data.getExtras().get("data");
					path = FileUtils.saveBitmap2(bm, fileName);
				}
			}
		}
		if (photoList.size() == 4) {
			removeItem();
			refreshAdapter();
			return;
		}
		removeItem();
		photoList.add(path);
		photoList.add(IMG_ADD_TAG);
		refreshAdapter();
	}

	private void removeItem() {
		if (photoList.size() != 4) {
			if (photoList.size() != 0) {
				photoList.remove(photoList.size() - 1);
			}
		}
	}

	public class PopupWindows extends PopupWindow {
		public PopupWindows(Context mContext, View parent) {
			super(mContext);
			View view = View.inflate(mContext, R.layout.item_popupwindows, null);
			LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
			getBackground().setAlpha(80);
			setAnimationStyle(R.style.AnimationFade);
			setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
			setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
			setFocusable(true);
			setOutsideTouchable(false);
			setContentView(view);
			showAtLocation(parent, Gravity.BOTTOM, 0, 0);
			update();

			RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.layout_pop_window);
			Button bt1 = (Button) view.findViewById(R.id.item_popupwindows_camera);
			Button bt2 = (Button) view.findViewById(R.id.item_popupwindows_Photo);
			Button bt3 = (Button) view.findViewById(R.id.item_popupwindows_cancel);

			layout.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					dismiss();
				}
			});

			bt1.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					getFromCamera();
					dismiss();
				}
			});

			bt2.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					getFromFolder();
					dismiss();
				}
			});

			bt3.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					dismiss();
				}
			});
		}
	}

	public void getFromCamera() {
		if(FileUtils.existSDCard() == false){
			showToast("没有SD卡");
			return;
		}

		Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(openCameraIntent, TAKE_PICTURE);
	}

	public void getFromFolder() {
		Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(i, TAKE_FOLDER);
	}

	private class GVAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return photoList.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			final ViewHolder holder;
			if (convertView == null) {
				convertView = LayoutInflater.from(getApplication()).inflate(R.layout.activity_add_photo_gv_items, parent, false);
				holder = new ViewHolder();
				holder.imageView = (ImageView) convertView.findViewById(R.id.main_gridView_item_photo);
				holder.checkBox = (CheckBox) convertView.findViewById(R.id.main_gridView_item_cb);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			String s = photoList.get(position);
			if (!s.equals(IMG_ADD_TAG)) {
				holder.checkBox.setVisibility(View.VISIBLE);
				holder.imageView.setImageBitmap(ImageTool.createImageThumbnail(s));
			} else {
				holder.checkBox.setVisibility(View.GONE);
				holder.imageView.setImageResource(R.drawable.order_evaluation_camera);
			}
			holder.checkBox.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					photoList.remove(position);
					refreshAdapter();
				}
			});
			return convertView;
		}

		private class ViewHolder {
			ImageView imageView;
			CheckBox checkBox;
		}

	}

	private void refreshAdapter() {
		if (photoList == null) {
			photoList = new ArrayList<String>();
		}
		if (adapter == null) {
			adapter = new GVAdapter();
		}
		adapter.notifyDataSetChanged();
	}
}
