package com.tajiang.leifeng.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.tajiang.leifeng.R;
import com.tajiang.leifeng.base.BaseActivity;
import com.tajiang.leifeng.common.http.TJHttpUtil;
import com.tajiang.leifeng.common.http.TJRequestTag;
import com.tajiang.leifeng.common.http.HttpResponseListener;
import com.tajiang.leifeng.model.User;
import com.tajiang.leifeng.utils.QiniuUtils;
import com.tajiang.leifeng.utils.ToastUtils;
import com.tajiang.leifeng.utils.UserUtils;
import com.tajiang.leifeng.view.dialog.ActionSheetDialog;
import com.tajiang.leifeng.view.dialog.ActionSheetDialog.OnSheetItemClickListener;
import com.tajiang.leifeng.view.dialog.ActionSheetDialog.SheetItemColor;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UserInforActivity extends BaseActivity  implements HttpResponseListener {

	private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
	private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
	private static final int PHOTO_REQUEST_CUT = 3;// 结果

	private View view_iconChangeUserInfor;
	private View view_usernameChangeUserInfor;
	private View view_sexChangeUserInfor;
	private View view_pswChangeUserInfor;

	private TextView tv_sexUserInfor;
	private TextView tv_nameUserInfor;

	private SimpleDraweeView iv_iconUserInfor;
	private Bitmap bitmap;
	private String token;

	private int sexChoose = 0;

	/* 头像名称 */
	private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
	private File tempFile;

	@Override
	protected void initTopBar() {
		setTitle("个人中心");
		enableNavLeftImage();
	}

	@Override
	protected void initLayout() {
		setContentView(R.layout.activity_user_infor);
	}

	@Override
	protected void initView() {
		view_iconChangeUserInfor = findViewById(R.id.view_iconChangeUserInfor);
		view_usernameChangeUserInfor = findViewById(R.id.view_usernameChangeUserInfor);
		view_sexChangeUserInfor = findViewById(R.id.view_sexChangeUserInfor);
		view_pswChangeUserInfor = findViewById(R.id.view_pswChangeUserInfor);

		iv_iconUserInfor = (SimpleDraweeView) findViewById(R.id.iv_iconUserInfor);

		tv_sexUserInfor = (TextView) findViewById(R.id.tv_sexUserInfor);
		tv_nameUserInfor = (TextView) findViewById(R.id.tv_nameUserInfor);

	}

	@Override
	protected void initDates() {
		super.initDates();

		User user = UserUtils.getInstance().getUser();

//		PicassoUtils.loadImage(this, user.getAvatar(), R.drawable.icon_hander_defaut, iv_iconUserInfor);
		loadImage(iv_iconUserInfor,  user.getAvatar(), R.drawable.icon_hander_defaut);


		tv_nameUserInfor.setText(user.getNikeName());
		tv_sexUserInfor.setText(UserUtils.getInstance().getUserSex());

	}

	@Override
	protected void initListener() {
		super.initListener();

		view_iconChangeUserInfor.setOnClickListener(this);
		view_usernameChangeUserInfor.setOnClickListener(this);
		view_sexChangeUserInfor.setOnClickListener(this);
		view_pswChangeUserInfor.setOnClickListener(this);

	}

	@Override
	public void onResume() {
		super.onResume();

		initDates();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		/**
		 * resultCode == Activity.RESULT_CANCELED
		 * 按返回键，退出拍照
		 */
		if (resultCode != Activity.RESULT_CANCELED) {
			if (requestCode == PHOTO_REQUEST_GALLERY) {
				if (data != null) {
					// 得到图片的全路径
					Uri uri = data.getData();
					File file = new File(uri.getPath());//访问手机端的文件资源，保证手机端sdcdrd中必须有这个文件
					//构建body
					RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
							.addFormDataPart("avatar", file.getName(), RequestBody.create(MediaType.parse("image/jpg"), file))
							.build();

					TJHttpUtil.getInstance(this).upLoadUserIcon(requestBody);
//					crop(uri);
				}

			} else if (requestCode == PHOTO_REQUEST_CAMERA) {
				if (hasSdcard()) {
					tempFile = new File(Environment.getExternalStorageDirectory(), PHOTO_FILE_NAME);
					RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
							.addFormDataPart("avatar", tempFile.getName(), RequestBody.create(MediaType.parse("image/jpg"), tempFile))
							.build();
					TJHttpUtil.getInstance(this).upLoadUserIcon(requestBody);
//					crop(Uri.fromFile(tempFile));
				} else {
					Toast.makeText(this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
				}
			} else if (requestCode == PHOTO_REQUEST_CUT) {
				try {
					bitmap = data.getParcelableExtra("data");
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
					QiniuUtils.upload(token, baos.toByteArray(), new UpCompletionHandler() {
						@Override
						public void complete(String arg0, ResponseInfo arg1, JSONObject arg2) {
//							try {
////								TJHttpUtil.getInstance(UserInforActivity.this).doModifyAvatar(null, arg2.getString("key"));
//							} catch (JSONException e) {
//								e.printStackTrace();
//							}
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onClick(View view) {
		super.onClick(view);

		switch (view.getId()) {
		case R.id.view_iconChangeUserInfor:
			initChangeIconDialog();
			break;
		case R.id.view_usernameChangeUserInfor:
			intent2Activity(UserInforChangeUsernameActivity.class);
			break;
		case R.id.view_sexChangeUserInfor:
			initChangeSexDialog();
			break;
		case R.id.view_pswChangeUserInfor:
			intent2Activity(UserInforChangePasswordActivity.class);
			break;
		}
	}

	/**
	 * 剪切图片
	 *
	 * @function:
	 * @author:Jerry
	 * @date:2013-12-30
	 * @param uri
	 */
	private void crop(Uri uri) {
		// 裁剪图片意图
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		// 裁剪框的比例，1：1
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// 裁剪后输出图片的尺寸大小
		intent.putExtra("outputX", 250);
		intent.putExtra("outputY", 250);
		// 图片格式
		intent.putExtra("outputFormat", "JPEG");
		intent.putExtra("noFaceDetection", true);// 取消人脸识别
		intent.putExtra("return-data", true);// true:不返回uri，false：返回uri
		startActivityForResult(intent, PHOTO_REQUEST_CUT);
	}

	private boolean hasSdcard() {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	private void initChangeIconDialog() {
		// 请求七牛的Token
//		TJHttpUtil.getInstance(UserInforActivity.this).getQiNiuTokenRequest();

		new ActionSheetDialog(this)
				.builder()
				.setCanceledOnTouchOutside(true)
				.setTitle("您要更换头像吗？")
				// 点击弹出框之外的地方，弹出框消失
				.setCanceledOnTouchOutside(false)
				.addSheetItem("相册", SheetItemColor.Green,
						new OnSheetItemClickListener() {
							@Override
							public void onSheetItemClick(int which) {
								// 激活系统图库，选择一张图片
								Intent intent = new Intent(Intent.ACTION_PICK);
								intent.setType("image/*");
								startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
							}
						})
				.addSheetItem("照相", SheetItemColor.Green,
						new OnSheetItemClickListener() {
							@Override
							public void onSheetItemClick(int which) {
								Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
								// 判断存储卡是否可以用，可用进行存储
								if (hasSdcard()) {
									intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment .getExternalStorageDirectory(), PHOTO_FILE_NAME)));
								}
								startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
							}
						})
						.show();
	}

	private void initChangeSexDialog() {
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
						tv_sexUserInfor.setText("男");
						sexChoose = User.MAN;
						TJHttpUtil.getInstance(UserInforActivity.this).doModifySex(User.MAN + "");
						UserUtils.getInstance().modifySex(sexChoose);
					}
				})
		.addSheetItem("女", SheetItemColor.Green,
				new OnSheetItemClickListener() {
					@Override
					public void onSheetItemClick(int which) {
						tv_sexUserInfor.setText("女");
						sexChoose = User.WOMAN;
						TJHttpUtil.getInstance(UserInforActivity.this).doModifySex(User.WOMAN + "");
						UserUtils.getInstance().modifySex(sexChoose);
					}
				}).show();
	}


	@Override
	public void onStart(int requestTag) {
		switch (requestTag) {
			case TJRequestTag.TAG_USER_MODIFY_AVATAR:
				break;
		}
	}

	@Override
	public void onSuccess(com.tajiang.leifeng.common.http.BaseResponse response, int requestTag) {
		switch (requestTag) {
			case TJRequestTag.TAG_USER_MODIFY_SEX:
				if((Boolean) response.getData()){
					ToastUtils.showShort("修改成功");
				}
				break;
			case TJRequestTag.TAG_QINIU_TOKEN_REQUEST:
				token = (String) response.getData();
				break;
			case TJRequestTag.TAG_USER_MODIFY_AVATAR:

				loadImage( iv_iconUserInfor, (String)response.getData(), R.drawable.icon_hander_defaut);
				showToast("修改头像成功");

				UserUtils.getInstance().modifyAvatar((String)response.getData());

				break;
		}
	}

	@Override
	public void onFailed(com.tajiang.leifeng.common.http.BaseResponse response, int requestTag) {

	}

	@Override
	public void onFinish(int requestTag) {

	}
}
