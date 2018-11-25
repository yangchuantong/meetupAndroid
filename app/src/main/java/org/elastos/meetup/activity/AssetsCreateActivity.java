package org.elastos.meetup.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.elastos.meetup.R;
import org.elastos.meetup.config.SystemConfig;
import org.elastos.meetup.tools.LQRPhotoSelectUtils;
import org.elastos.meetup.tools.NodeClient;
import org.elastos.meetup.tools.Waiter;
import org.elastos.meetuplib.service.tokenizedAsset.service.AssetService;
import org.elastos.meetuplib.tool.entity.ContractGroupDetail;
import org.elastos.meetuplib.tool.util.JsonResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigInteger;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * Created by xianxian on 2018/11/24.
 */
public class AssetsCreateActivity extends Activity implements View.OnClickListener {

    TextView topbar_title, tv_upload_img;
    EditText edt_name, edt_symbol, edt_limit, edt_contractInfo;
    Button btn_create;
    ImageView img_assets;
    boolean ispost = false;
    String imgURL="";
    AssetService service;
    private String[] items = new String[]{"选择本地图片", "拍照"};
    private LQRPhotoSelectUtils mLqrPhotoSelectUtils;
    /* 头像名称 */
    private static final String IMAGE_FILE_NAME = "faceImage.jpg";
    /* 请求码 */
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int SELECT_PIC_KITKAT = 3;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESULT_REQUEST_CODE = 2;
    private InputStream file;
    String privateKey ="";
    private AssetsCreateActivity mContext;
    private Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assets_create);
        mContext=AssetsCreateActivity.this;
        topbar_title = (TextView) findViewById(R.id.topbar_title);
        tv_upload_img = (TextView) findViewById(R.id.tv_upload_img);
        LinearLayout topbar_back = findViewById(R.id.topbar_back);
        topbar_back.setOnClickListener(this);
        topbar_title.setText("发起活动");
        edt_name = (EditText) findViewById(R.id.edt_name);
        edt_symbol = (EditText) findViewById(R.id.edt_symbol);
        edt_limit = (EditText) findViewById(R.id.edt_limit);
        edt_contractInfo = (EditText) findViewById(R.id.edt_contractInfo);
        btn_create = (Button) findViewById(R.id.btn_create);
        img_assets = (ImageView) findViewById(R.id.img_assets);

        btn_create.setOnClickListener(this);
        tv_upload_img.setOnClickListener(this);
        service = NodeClient.getAssetServiceClient();
        if( SystemConfig.privatekey !=null){
            privateKey = SystemConfig.privatekey ;
        }
        // 1、创建LQRPhotoSelectUtils（一个Activity对应一个LQRPhotoSelectUtils）
        mLqrPhotoSelectUtils = new LQRPhotoSelectUtils(this, new LQRPhotoSelectUtils.PhotoSelectListener() {
            @Override
            public void onFinish(File outputFile, Uri outputUri) {
                // 4、当拍照或从图库选取图片成功后回调
                Glide.with(AssetsCreateActivity.this).load(outputUri).into(img_assets);
                try {
                    file=new FileInputStream(outputFile);
                    uploadImg(outputFile);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }, true);//true裁剪，false不裁剪
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);

            switch(msg.what)
            {
                case 0:
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    break;
                case 1:
                    dialog= Waiter.initProgressDialog(AssetsCreateActivity.this,getString(R.string.title_loading));
                    dialog.show();
                    break;

            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.topbar_back:
                finish();
                break;
            case R.id.btn_create:

                assets_create();
                break;
            case R.id.tv_upload_img:

                showSettingFaceDialog();

                break;


        }
    }


    @PermissionSuccess(requestCode = LQRPhotoSelectUtils.REQ_TAKE_PHOTO)
    private void takePhoto() {
        mLqrPhotoSelectUtils.takePhoto();
    }

    @PermissionSuccess(requestCode = LQRPhotoSelectUtils.REQ_SELECT_PHOTO)
    private void selectPhoto() {
        mLqrPhotoSelectUtils.selectPhoto();
    }

    @PermissionFail(requestCode = LQRPhotoSelectUtils.REQ_TAKE_PHOTO)
    private void showTip1() {
        //        Toast.makeText(getApplicationContext(), "不给我权限是吧，那就别玩了", Toast.LENGTH_SHORT).show();
        showDialog();
    }

    @PermissionFail(requestCode = LQRPhotoSelectUtils.REQ_SELECT_PHOTO)
    private void showTip2() {
        //        Toast.makeText(getApplicationContext(), "不给我权限是吧，那就别玩了", Toast.LENGTH_SHORT).show();
        showDialog();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 2、在Activity中的onActivityResult()方法里与LQRPhotoSelectUtils关联
        mLqrPhotoSelectUtils.attachToActivityForResult(requestCode, resultCode, data);
    }
    public void showDialog() {
        //创建对话框创建器
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //设置对话框显示小图标
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        //设置标题
        builder.setTitle("权限申请");
        //设置正文
        builder.setMessage("在设置-应用-虎嗅-权限 中开启相机、存储权限，才能正常使用拍照或图片选择功能");

        //添加确定按钮点击事件
        builder.setPositiveButton("去设置", new DialogInterface.OnClickListener() {//点击完确定后，触发这个事件

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //这里用来跳到手机设置页，方便用户开启权限
                Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + mContext.getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        //添加取消按钮点击事件
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        //使用构建器创建出对话框对象
        AlertDialog dialog = builder.create();
        dialog.show();//显示对话框
    }

    private void showSettingFaceDialog() {

        new AlertDialog.Builder(this)
                .setTitle("图片来源")
                .setCancelable(true)
                .setItems(items, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:// Local Image
                                // 3、调用从图库选取图片方法
                                PermissionGen.needPermission(AssetsCreateActivity.this,
                                        LQRPhotoSelectUtils.REQ_SELECT_PHOTO,
                                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                                Manifest.permission.WRITE_EXTERNAL_STORAGE}
                                );
                                break;
                            case 1:// Take Picture
                                // 3、调用拍照方法
                                PermissionGen.with(AssetsCreateActivity.this)
                                        .addRequestCode(LQRPhotoSelectUtils.REQ_TAKE_PHOTO)
                                        .permissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                                Manifest.permission.CAMERA
                                        ).request();
                                break;
                        }
                    }
                })
                .setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                            }
                        }).show();

    }




    protected void uploadImg(final File outputFile) {

        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... params) {
                try {
                   JsonResult<String> jsonResult=  service.uploadFile(outputFile);
                    if(jsonResult!=null&&jsonResult.getSuccess()){
                        imgURL=jsonResult.getData();
                    }

                } catch (Exception e) {

                }
                return 0;
            }

            protected void onPostExecute(Integer result) {


            }

            ;
        }.execute();
    }
    protected void assets_create() {
        if(privateKey==null||privateKey.length()==0){
            Waiter.alertErrorMessage("账号获取失败",mContext);
            return;
        }
        if (!Waiter.CheckEditText(edt_name) || !Waiter.CheckEditText(edt_symbol) || !Waiter.CheckEditText(edt_limit)) {
            return;
        }
      final  String _name = edt_name.getText().toString();
        final   String _symbol = edt_symbol.getText().toString();
        String limit = edt_limit.getText().toString();
        int limit_int = Integer.parseInt(limit);
        final  BigInteger _supplyLimit = BigInteger.valueOf(limit_int);
        final  String _contractInfo = edt_contractInfo.getText().toString();
        if(ispost){
            return;
        }
        ispost=true;
        handler.sendEmptyMessage(1);
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... params) {
                BigInteger gasPrice =BigInteger.valueOf(6000000000L);//SystemConfig.gasPrice
                BigInteger gasLimit = SystemConfig.gasLimit;

                String _owner = SystemConfig.ethAddress;

                try {
                    service.createContractAsync(privateKey, gasPrice, gasLimit, _name, _symbol, _supplyLimit, _contractInfo, _owner,imgURL);
                    handler.sendEmptyMessage(0);
                    Waiter.alertErrorMessage("合约已提交，待上链，请稍候刷新查看",mContext);
                    ContractGroupDetail contractGroupDetail=new ContractGroupDetail();
                    contractGroupDetail.setName(_name);
                    contractGroupDetail.setQuantity(0);
                    contractGroupDetail.setImgUrl(service.getFileUrl(imgURL));
                    contractGroupDetail.setOwner(SystemConfig.ethAddress);
                    Intent intent = new Intent();
                    Bundle b = new Bundle();
                    b.putSerializable("obj", contractGroupDetail);
                    intent.putExtras(b);
                    setResult(3, intent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }

            protected void onPostExecute(Integer result) {


            }

            ;
        }.execute();


    }
    @Override
    protected void onDestroy() {
        if(dialog != null) {
            dialog.dismiss();
        }
        super.onDestroy();
    }

}
