package org.elastos.meetup.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.elastos.meetup.R;
import org.elastos.meetup.dialog.NormalDialog;
import org.elastos.meetup.fragement.AssetsFragment;
import org.elastos.meetup.fragement.MeetupFragment;
import org.elastos.meetup.fragement.MyFragment;
import org.elastos.meetup.tools.NodeClient;
import org.elastos.meetup.tools.PermisionUtils;
import org.elastos.meetup.tools.Waiter;
import org.elastos.meetuplib.service.NativeDataService;
import org.elastos.meetuplib.service.tokenizedAsset.service.AssetService;
import org.elastos.meetuplib.tool.entity.Apply;

import java.io.File;
import java.util.Map;

public class MainActivity extends BaseFragmentActivity implements View.OnClickListener{
    long totalminite = 0;
    String  offtime="";
    int size = 0;
    ProgressBar mProgress;
    /* 下载中 */
    private static final int DOWNLOAD = 11;
    /* 下载结束 */
    private static final int DOWNLOAD_FINISH = 12;
    /*不能直接下载*/
    private static final int DOWNLOAD_NULL = 13;
    /*检测服务器APP版本*/
    private static final int DOWNLOAD_VERSION = 15;
    private static final int AD = 16;

    /* 下载保存路径 */
    private String mSavePath;
    private File apkfile;
    private Dialog mDownloadDialog;
    TextView tv_progress;
     NormalDialog confirmDialog;
    String versionName="",desc="",downloadUrl="",campaignID="";
    int tversionCode=0,force=0;
    int progress=0;
    public static MainActivity instance;
    private LinearLayout mTabFaqi;
    private LinearLayout mTabMeetup;

    private LinearLayout mTabMy;

    private ImageView mFaqiImg;
    private ImageView mMeetupImg;
    private ImageView mMyImg;

    private MeetupFragment meetupFragment;
    private AssetsFragment assetsFragment;
    private MyFragment walletFragment;
    public static int tabnum=-1;
    long lastMills;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
        setSelect(1);  //显示第一个Tab
        instance=MainActivity.this;

        PermisionUtils.verifyStoragePermissions(this);
    }
    private void initEvent() {
        mTabFaqi.setOnClickListener(this);
        mTabMeetup.setOnClickListener(this);
        mTabMy.setOnClickListener(this);
    }

    private void initView() {
        mTabFaqi = (LinearLayout) findViewById(R.id.tab_faqi);
        mTabMeetup = (LinearLayout) findViewById(R.id.tab_meetup);
        mTabMy = (LinearLayout) findViewById(R.id.tab_my);

        mFaqiImg = (ImageView) findViewById(R.id.tab_faqi_img);
        mMeetupImg = (ImageView) findViewById(R.id.tab_meetup_img);
        mMyImg = (ImageView) findViewById(R.id.tab_my_img);
    }

    @Override
    public void onClick(View v) {
        resetImg();
        switch (v.getId()){
            case R.id.tab_faqi:
                setSelect(0);
                break;
            case R.id.tab_meetup:
                setSelect(1);
                break;
                case R.id.tab_my:
                setSelect(3);
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (data == null || data.equals(""))
            return;

        switch (requestCode) {

            case 49374:
                IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
                if(intentResult != null) {
                    if(intentResult.getContents() == null) {
                        Toast.makeText(MainActivity.instance,getString(R.string.message_notnull),Toast.LENGTH_LONG).show();
                    } else {

                        // ScanResult 为 获取到的字符串
                        String scanResult = intentResult.getContents();
                        //获取二维码信息提交验证
                        String[] results=scanResult.split("_");
                        if(results.length!=3){
                            Waiter.alertErrorMessage("无效二维码",MainActivity.instance);
                        }else{
                            verifyAssets(results);
                        }
                    }
                }
                break;


        }
    }
    /**
     * 显示指定Tab，并将对应的图片设置为亮色
     * @param i
     */
    private void setSelect(int i) {
        FragmentManager fm = getSupportFragmentManager();  //获得FragmentManager
        FragmentTransaction transaction = fm.beginTransaction(); //开启事务
        hideFragment(transaction);
        switch (i){
            case 0:
                if (assetsFragment==null){
                    assetsFragment = new AssetsFragment();
                    transaction.add(R.id.content, assetsFragment);
                }
                //显示指定Fragment
                transaction.show(assetsFragment);
                //将图片设置为亮色
                mFaqiImg.setImageResource(R.drawable.tabbar_faqi);
//                mTabHome.setBackgroundResource(R.color.white);

             
                break;

            case 1:
                if (meetupFragment==null){
                    meetupFragment = new MeetupFragment();
                    transaction.add(R.id.content, meetupFragment);
                }
                //显示指定Fragment
                transaction.show(meetupFragment);
               
                //将图片设置为亮色
                mMeetupImg.setImageResource(R.drawable.tabbar_meetup);
//                mTabMeetup.setBackgroundResource(R.color.white);

                break;
            case 3:
                if (walletFragment==null){
                    walletFragment = new MyFragment();
                    transaction.add(R.id.content, walletFragment);
                }  
                //显示指定Fragment
                transaction.show(walletFragment);
                //将图片设置为亮色
                mMyImg.setImageResource(R.drawable.tabbar_my);
//                mTabMy.setBackgroundResource(R.color.white);
             
                break;
        }
        transaction.commit();  //提交事务
    }

    /**
     * 将所有Fragment隐藏起来
     * @param transaction
     */
    private void hideFragment(FragmentTransaction transaction) {
        if (meetupFragment!=null){
            transaction.hide(meetupFragment);
        }
         if(assetsFragment!=null){
            transaction.hide(assetsFragment);
        }

         if(walletFragment!=null){
            transaction.hide(walletFragment);
        }
    }

    /**
     * 将所有图片切换成暗色
     */
    private void resetImg() {
        mFaqiImg.setImageResource(R.drawable.tabbar_faqi1);
        mMeetupImg.setImageResource(R.drawable.tabbar_meetup1);

        mMyImg.setImageResource(R.drawable.tabbar_my1);
    }



    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_BACK){
            isExit();
            return false;
        }
        return super.dispatchKeyEvent(event);
    }

    private void isExit(){
            Waiter.alertErrorMessage(getString(R.string.home_error_exit_press),MainActivity.this);
            long   currentTimes=System.currentTimeMillis();
            long spaceTimes = (currentTimes - lastMills)/1000;
            if (spaceTimes <= 2.5){
                exit();
            }else {
                lastMills = currentTimes;
            }


    }

    private void verifyAssets(String[]results){

        AssetService service=NodeClient.getAssetServiceClient();
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... params) {
                try {
                    String contractAddress=results[0];
                    String address=results[1];
                    String tokenID=results[2];
                    Apply apply=new Apply();
                    apply.setOwner(address);
                    apply.setTokenId(tokenID);
                    Boolean burnResult= service.burn(apply,contractAddress,getPub());
                    if(burnResult!=null&&burnResult.booleanValue()){
                        return 0;
                    }
                } catch (Exception e) {
                    return -1;
                }
                return -1;
            }

            protected void onPostExecute(Integer result) {
                if(result!=null&&result.intValue()==0){
                    Waiter.alertErrorMessage("有效邀请函！",MainActivity.this);
                }else{
                    Waiter.alertErrorMessage("无效邀请函！",MainActivity.this);
                }
            }

            ;
        }.execute();

    }
    private String getPub(){
        Map<String,String> walletMap= NativeDataService.getInstance().loadWallet(MainActivity.this);
        if(walletMap!=null&&walletMap.get("publicKey")!=null&&walletMap.get("publicKey").length()>0) {
            return walletMap.get("publicKey");
        }
        return "";
    }
    private void exit(){
        this.getApplication().onTerminate();
        this.finish();
    }
    @Override
    protected void onStop() {
        super.onStop();

        if (confirmDialog!=null&&confirmDialog.isShowing())
            confirmDialog.dismiss();

    }
    @Override
    protected void onResume() {
        super.onResume();
        if(tabnum>-1){
            resetImg();
            setSelect(tabnum);
            tabnum=-1;
        }

    }
}
