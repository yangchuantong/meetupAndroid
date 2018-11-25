package org.elastos.meetup.fragement;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.elastos.meetup.R;
import org.elastos.meetup.activity.ApplyListActivity;
import org.elastos.meetup.activity.AssetsCreateActivity;
import org.elastos.meetup.activity.CustomScanActivity;
import org.elastos.meetup.adapter.AssetsAdapter;
import org.elastos.meetup.config.SystemConfig;
import org.elastos.meetup.tools.NodeClient;
import org.elastos.meetup.tools.Waiter;
import org.elastos.meetup.view.SwipeListView;
import org.elastos.meetuplib.service.NativeDataService;
import org.elastos.meetuplib.service.tokenizedAsset.service.AssetService;
import org.elastos.meetuplib.tool.entity.Apply;
import org.elastos.meetuplib.tool.entity.ContractGroupDetail;
import org.elastos.meetuplib.tool.util.JsonResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by xianxian on 2018/11/24.
 */

public class AssetsFragment extends Fragment implements View.OnClickListener {

    TextView tv_create,tv_token_create;
    private Activity mainActivity;
    private AssetService service;
    private String address;
    List<ContractGroupDetail> list;
    AssetsAdapter adapter;
    SwipeListView swipeListView;
    SwipeRefreshLayout swipeRefreshLayout;
    private Dialog dialog;
    private int lastVisibleItem;
    private LinearLayoutManager linearLayoutManager;
    public static Handler handler;
    private boolean ispost=false;
    TextView tv_null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_assets, container, false);
        mainActivity = getActivity();
        tv_create=v.findViewById(R.id.tv_create);
        tv_null=v.findViewById(R.id.tv_null);
        tv_create.setOnClickListener(this);
        service=NodeClient.getAssetServiceClient();
        swipeListView =(SwipeListView) v.findViewById(R.id.swipeListView) ;
        swipeRefreshLayout=(SwipeRefreshLayout)v.findViewById(R.id.swipe_refresh);
        linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
       list=new ArrayList<>();
        adapter=new AssetsAdapter(mainActivity,null);
        swipeListView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getAssets();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },2000);
            }
        });
        if(SystemConfig.ethAddress!=null&&SystemConfig.ethAddress.length()>0){
            address=SystemConfig.ethAddress;
            getAssets();
        }

        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage(msg);

                switch(msg.what)
                {
                    case 0:
                        adapter.list= (ArrayList<ContractGroupDetail>) list;
                        adapter.notifyDataSetChanged();
                        if(adapter.list==null||adapter.list.size()==0){
                            swipeRefreshLayout.setVisibility(View.GONE);
                            tv_null.setVisibility(View.VISIBLE);
                        }else{
                            tv_null.setVisibility(View.GONE);
                            swipeRefreshLayout.setVisibility(View.VISIBLE);
                        }
                        break;
                        case 1:
                        {
                            int position=msg.arg1;
                            if(list!=null&&list.size()>position){
//                                ContractGroupDetail obj=list.get(position);
//                                assetsOnsale(obj.getAddress(),SystemConfig.ethAddress);

                                IntentIntegrator intentIntegrator = new IntentIntegrator(mainActivity);
                                intentIntegrator
                                        .setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
                                        .setPrompt(getString(R.string.title_sys_qrcode))//写那句提示的话
                                        .setOrientationLocked(false)//扫描方向固定
                                        .setCaptureActivity(CustomScanActivity.class) // 设置自定义的activity是CustomActivity
                                        .initiateScan(); // 初始化扫描

                            }
                        }
                        break;
                    case 2:
                        //发布交易成功
                        if (dialog!=null&&dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        Waiter.alertErrorMessage("操作成功！",mainActivity);
                        break;
                    case 3:
                        //发布交易失败
                        if (dialog!=null&&dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        Waiter.alertErrorMessage("操作失败！",mainActivity);
                        break;
                    case 4:
                        dialog= Waiter.initProgressDialog(mainActivity,getString(R.string.title_loading));
                        dialog.show();
                        break;
                    case 6:
                    {
                        int position=msg.arg1;
                        if(list!=null&&list.size()>position){
                            ContractGroupDetail obj=list.get(position);
                           //批量处理订单
//                            dealOrder(obj);
                            Intent intent=new Intent(mainActivity,ApplyListActivity.class);
                            Bundle b=new Bundle();
                            b.putSerializable("obj",obj);

                            intent.putExtras(b);
                            mainActivity.startActivity(intent);
                        }
                    }
                        break;
                    case 7:
                        //核销结果为真
                        Waiter.alertErrorMessage("有效邀请函！",mainActivity);
                        break;
                    case 8:
                        //核销结果为假
                        Waiter.alertErrorMessage("无效邀请函！",mainActivity);
                        break;
                }
            }
        };
        return v;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case 3:
                if(data!=null){
                    ContractGroupDetail obj= (ContractGroupDetail) data.getExtras().getSerializable("obj");
                    if(obj!=null&&obj.getName()!=null){
                        list.add(obj);
                        handler.sendEmptyMessage(0);
                    }
                }
                break;
            case 49374:
                IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
                if(intentResult != null) {
                    if(intentResult.getContents() == null) {
                        Toast.makeText(mainActivity,getString(R.string.message_notnull),Toast.LENGTH_LONG).show();
                    } else {

                        // ScanResult 为 获取到的字符串
                        String scanResult = intentResult.getContents();
                       //获取二维码信息提交验证
                        String[] results=scanResult.split("_");
                        if(results.length!=3){
                            Waiter.alertErrorMessage("无效二维码",mainActivity);
                        }else{
                            verifyAssets(results);
                        }
                    }
                }
                break;
            case 2:
                break;

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_create:
            {
                Intent intent=new Intent(mainActivity,AssetsCreateActivity.class);
                startActivityForResult(intent,1);
                getAssets();
            }
                break;


        }
    }
    private void getAssets(){

        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... params) {
                try {
                    JsonResult<List<ContractGroupDetail>> jsonResult= service.ownerContractListDetailByWalletAddress(SystemConfig.ethAddress);
                    if(jsonResult.getSuccess()!=null&&jsonResult.getSuccess()){
                        list=jsonResult.getData();
                        handler.sendEmptyMessage(0);
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
    private void verifyAssets(String[]results){


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
                    if(burnResult!=null&&burnResult){
                     return 0;
                    }
                } catch (Exception e) {
                    return -1;
                }
                return -1;
            }

            protected void onPostExecute(Integer result) {
                if(result!=null&&result.intValue()==0){
                    handler.sendEmptyMessage(7);
                }else{
                    handler.sendEmptyMessage(8);
                }
            }

            ;
        }.execute();

    }
    private String getPub(){
        Map<String,String> walletMap= NativeDataService.getInstance().loadWallet(mainActivity);
        if(walletMap!=null&&walletMap.get("publicKey")!=null&&walletMap.get("publicKey").length()>0) {
           return walletMap.get("publicKey");
        }
        return "";
    }
    @Override
    public void onResume() {
        super.onResume();

    }
}