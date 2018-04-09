package com.tajiang.leifeng.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.tajiang.leifeng.R;
import com.tajiang.leifeng.adapter.UserAdressAddApartmentAdapter;
import com.tajiang.leifeng.adapter.UserAdressAddSchoolAdapter;
import com.tajiang.leifeng.base.BaseActivity;
import com.tajiang.leifeng.common.http.HttpResponseListener;
import com.tajiang.leifeng.common.http.TJHttpUtil;
import com.tajiang.leifeng.common.http.TJRequestTag;
import com.tajiang.leifeng.model.Apartment;
import com.tajiang.leifeng.model.School;
import com.tajiang.leifeng.model.User;
import com.tajiang.leifeng.utils.QiniuUtils;
import com.tajiang.leifeng.utils.BitmapUtils;
import com.tajiang.leifeng.utils.LogUtils;
import com.tajiang.leifeng.utils.ToastUtils;
import com.tajiang.leifeng.view.dialog.ActionSheetDialog;
import com.tajiang.leifeng.view.dialog.ActionSheetDialog.OnSheetItemClickListener;
import com.tajiang.leifeng.view.dialog.ActionSheetDialog.SheetItemColor;
import com.tajiang.leifeng.view.dialog.CDialog;
import com.tajiang.leifeng.view.dialog.HintDialog;

public class UserPassVerifyActivity extends BaseActivity implements HttpResponseListener{
	
	private final String TAG = this.getClass().getSimpleName();
	
	
	private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
	
	private int sex = User.NONE;
	
	private String token;
	
	private String certificate;
	
	private String nameUser;
	private String phoneUser;
	private String roomUser;

	private File tempPicFile;
	
	private Apartment userApartment;
	private School userSchool;
	
	private View rect_retakePhoto;
	
	private ImageView iv_takePhotoUserVerify;
	private ImageView iv_picCardUserVerify;
	
	private EditText et_nameUserApprove;
	private TextView et_sexUserApprove;
	private EditText et_phoneUserApprove;
	private TextView et_apartmentUserApprove;
	private EditText et_roomUserApprove;

	private Button btn_submitUserApprove;
	
	private ListView lv_schoolAddressAdd;
	private ListView lv_apartmentAddressAdd;

	private View rect_takePhoneUserVerify;
	
	private CDialog cDialog;
	
	private List<School> schoolList = new ArrayList<School>();
	private List<Apartment> apartmentList = new ArrayList<Apartment>();
	
	private UserAdressAddSchoolAdapter userAdressAddSchoolAdapter;
	private UserAdressAddApartmentAdapter userAdressAddApartmentAdapter;
	
	@Override
	protected void initTopBar() {
		setTitle("完善个人信息");
		enableNavLeftImage();
	}

	@Override
	protected void initLayout() {
		setContentView(R.layout.activity_pass_user_infor);
	}

	@Override
	protected void initView() {
		
		rect_takePhoneUserVerify = findViewById(R.id.rect_takePhoneUserVerify);
		rect_retakePhoto = findViewById(R.id.rect_retakePhoto);

		et_nameUserApprove = (EditText) findViewById(R.id.et_nameUserApprove);
		et_sexUserApprove = (TextView) findViewById(R.id.et_sexUserApprove);
		et_phoneUserApprove = (EditText) findViewById(R.id.et_phoneUserApprove);
		et_apartmentUserApprove = (TextView) findViewById(R.id.et_apartmentUserApprove);
		et_roomUserApprove = (EditText) findViewById(R.id.et_roomUserApprove);
		
		iv_takePhotoUserVerify = (ImageView) findViewById(R.id.iv_takePhotoUserVerify);
		iv_picCardUserVerify = (ImageView) findViewById(R.id.iv_picCardUserVerify);
		btn_submitUserApprove = (Button) findViewById(R.id.btn_submitUserApprove);
		
	}
	
	@Override
	protected void initListener() {
		super.initListener();
		
		rect_retakePhoto.setOnClickListener(this);
		iv_takePhotoUserVerify.setOnClickListener(this);
		btn_submitUserApprove.setOnClickListener(this);
		
		et_sexUserApprove.setOnClickListener(this);
		et_apartmentUserApprove.setOnClickListener(this);
		
	}
	
	@Override
	public void onClick(View view) {
		super.onClick(view);
		
		switch (view.getId()) {
		case R.id.btn_submitUserApprove:
			
			if(checkUserInfor()){
				TJHttpUtil.getInstance(this).rapDoUserJoin(nameUser, phoneUser, certificate, userApartment.getApartmentId(), roomUser);
			}
		
			break;
		case R.id.iv_takePhotoUserVerify:
			
			// 请求七牛的Token
			TJHttpUtil.getInstance(this).getQiNiuTokenRequest();
			
			final HintDialog hintDialog = new HintDialog(this);
			hintDialog.setMessage("是否打开相机拍照");
			hintDialog.setPositiveButtonListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					
					tempPicFile = new File(Environment.getExternalStorageDirectory().getPath(), "tempPic.jpg");
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempPicFile));
					startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
					hintDialog.dismiss();
					
				}
			});
			
			hintDialog.show();
			
			break;
			
		case R.id.et_sexUserApprove:
			showSelectSexDialog();
			break;
		case R.id.et_apartmentUserApprove:
			// 学校：宿舍列表
//			 TJHttpUtils.getInstance(this).getSchoolAndApartment();
			break;
			
		case R.id.rect_retakePhoto:
			// 请求七牛的Token
						TJHttpUtil.getInstance(this).getQiNiuTokenRequest();
						
						final HintDialog hintRetakeDialog = new HintDialog(this);
						hintRetakeDialog.setMessage("是否打开相机拍照");
						hintRetakeDialog.setPositiveButtonListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								
								tempPicFile = new File(Environment.getExternalStorageDirectory().getPath(), "tempPic.jpg");
								Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
								intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempPicFile));
								startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
								hintRetakeDialog.dismiss();
								
							}
						});
						
						hintRetakeDialog.show();
			break;
		}
		
	}
	
	private boolean checkUserInfor() {

		nameUser = et_nameUserApprove.getText().toString().trim();
		phoneUser = et_phoneUserApprove.getText().toString().trim();
		roomUser = et_roomUserApprove.getText().toString().trim();
		
		if(TextUtils.isEmpty(certificate)){
			ToastUtils.showShort("请上传照片");
			return false;
		}
		
		else if (TextUtils.isEmpty(nameUser)) {
			ToastUtils.showShort("用户昵称不能为空");
			return false;
		}

		else if (sex == User.NONE) {
			ToastUtils.showShort("请选择您的性别");
			return false;
		}
		
		else if(userApartment == null || userSchool == null){
			ToastUtils.showShort("请选择您的宿舍和学校");
			return false;
		}
		
		else if(TextUtils.isEmpty(roomUser)){
			ToastUtils.showShort("请填写详细的寝室号");
			return false;
		}

		return true;
		
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		LogUtils.d(TAG, "onActivityResult");

		 if (requestCode == PHOTO_REQUEST_CAMERA) {
			 
			if (tempPicFile!= null && tempPicFile.length() != 0 ) {
				 Bitmap bitmap = BitmapUtils.decodeBitmapFromPath(tempPicFile.getAbsolutePath(), 600, 600);
				 bitmap = BitmapUtils.rotateBitmapByDegree(bitmap,  BitmapUtils.getBitmapDegree(tempPicFile.getAbsolutePath()));
				 iv_picCardUserVerify.setImageBitmap(bitmap);
				 rect_takePhoneUserVerify.setVisibility(View.GONE);
				 new Task().execute(bitmap);
			}

		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onStart(int requestTag) {

	}

	@Override
	public void onSuccess(com.tajiang.leifeng.common.http.BaseResponse response, int requestTag) {
		switch (requestTag) {
			case TJRequestTag.TAG_ORDER_RAP_USER_JOIN:

				if ((Boolean) response.getData()) {
					Intent intent = new Intent(this, UserPassVerifySuccessActivity.class);
					startActivity(intent);
					onBackPressed();
				} else {
					ToastUtils.showShort("提交失败");
				}

				break;

			case TJRequestTag.TAG_GET_SCHOOL_APARTMENT_LIST:

				List<School> schoolListResult = (List<School>) response.getData();
				schoolList.clear();
				schoolList.addAll(schoolListResult);

//			showSeleteApartmentDialog();

				break;

			case TJRequestTag.TAG_QINIU_TOKEN_REQUEST:
				token = (String) response.getData();
				break;
		}
	}

	@Override
	public void onFailed(com.tajiang.leifeng.common.http.BaseResponse response, int requestTag) {

	}

	@Override
	public void onFinish(int requestTag) {

	}

	class Task extends AsyncTask<Bitmap, Void, ByteArrayOutputStream> {

		@Override
		protected ByteArrayOutputStream doInBackground(Bitmap... params) {
			 if(tempPicFile.delete()){
					LogUtils.d(TAG, "照片文件删除成功");
				 }else{
					 LogUtils.d(TAG, "照片文件删除失败");
				 }
				 
				 ByteArrayOutputStream baos = new ByteArrayOutputStream();
				 params[0].compress(Bitmap.CompressFormat.PNG, 100, baos);
				 
			return baos;
		}
		
		@Override
		protected void onPostExecute(ByteArrayOutputStream result) {
			QiniuUtils.upload(token, result.toByteArray(), new UpCompletionHandler() {
				@Override
				public void complete(String arg0, ResponseInfo arg1, JSONObject arg2) {
					try {
						certificate = arg2.getString("key");
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});
			super.onPostExecute(result);
		}
		
	}
	
	private void showSelectSexDialog() {

		new ActionSheetDialog(this)
				.builder()
				.setCanceledOnTouchOutside(true)
				.setTitle("请选择您的性别")
				// 点击弹出框之外的地方，弹出框消失
				.setCanceledOnTouchOutside(false)
				.addSheetItem("男", SheetItemColor.Green,
						new OnSheetItemClickListener() {
							@Override
							public void onSheetItemClick(int which) {
								et_sexUserApprove.setText("男");
								sex = User.MAN;
							}
						})
				.addSheetItem("女", SheetItemColor.Green,
						new OnSheetItemClickListener() {
							@Override
							public void onSheetItemClick(int which) {
								et_sexUserApprove.setText("女");
								sex = User.WOMAN;
							}
						}).show();
	}
	
	private void showSeleteApartmentDialog() {

//		cDialog = new CDialog(this, R.layout.dialog_user_adress_add_select_apartment);
//		lv_schoolAddressAdd = cDialog .findChildViewById(R.id.lv_schoolAddressAdd);
//		lv_apartmentAddressAdd = cDialog .findChildViewById(R.id.lv_apartmentAddressAdd);
//		final View ll_apartmentAddressAdd  = cDialog.findChildViewById(R.id.ll_apartmentAddressAdd);
//		userAdressAddSchoolAdapter = new UserAdressAddSchoolAdapter(this, schoolList, R.layout.item_list_user_adress_add_school);
//		userAdressAddApartmentAdapter = new UserAdressAddApartmentAdapter(this, apartmentList, R.layout.item_list_user_adress_add_store);
//		lv_schoolAddressAdd.setAdapter(userAdressAddSchoolAdapter);
//		lv_apartmentAddressAdd.setAdapter(userAdressAddApartmentAdapter);
//		cDialog.show();
//
//		lv_schoolAddressAdd.setOnItemClickListener(new OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//				userSchool = (School) parent.getItemAtPosition(position);
//
//				apartmentList.clear();
//				apartmentList.addAll(userSchool.getApartmentList());
//
//				userAdressAddApartmentAdapter.notifyDataSetChanged();
//
//				 TranslateAnimation animation = new TranslateAnimation(lv_apartmentAddressAdd.getX() +  450, lv_apartmentAddressAdd.getX() ,lv_apartmentAddressAdd.getY(), lv_apartmentAddressAdd.getY());
//				 animation.setDuration(300);//设置动画持续时间
//
//				 ll_apartmentAddressAdd.startAnimation(animation);
//
//				 for(int i = 0; i < parent.getChildCount(); i++){
//					 View itemView = parent.getChildAt(i);
//					 TextView textView  = (TextView) itemView.findViewById(R.id.tv_nameSchoolAdressAdd);
//					 textView.setTextColor(getResources().getColor(R.color.text_black_1));
//				 }
//
//				 TextView currTextView  = (TextView) view.findViewById(R.id.tv_nameSchoolAdressAdd);
//				 currTextView.setTextColor(getResources().getColor(R.color.com_green));
//
//			}
//		});
//
//		lv_apartmentAddressAdd.setOnItemClickListener(new OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				userApartment = (Apartment) parent.getItemAtPosition(position);
//				cDialog.dismiss();
//				et_apartmentUserApprove.setText(userSchool.getName() + "  —  " + userApartment.getName());
//			}
//		});

	}

}
