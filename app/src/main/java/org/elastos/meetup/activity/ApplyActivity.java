package org.elastos.meetup.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.alibaba.fastjson.JSONObject;

import org.elastos.meetup.R;
import org.elastos.meetup.adapter.MyTokenDetailAdapter;
import org.elastos.meetup.config.SystemConfig;
import org.elastos.meetup.entity.UserDetail;
import org.elastos.meetup.tools.NodeClient;
import org.elastos.meetup.tools.Waiter;
import org.elastos.meetuplib.service.tokenizedAsset.service.AssetService;
import org.elastos.meetuplib.tool.entity.Apply;
import org.elastos.meetuplib.tool.entity.ContractGroupDetail;
import org.elastos.meetuplib.tool.util.JsonResult;

/**
 * Created by xianxian on 2018/11/24.
 */
public class ApplyActivity extends Activity implements View.OnClickListener {

    boolean ispost = false;
    AssetService service;
    private ApplyActivity mContext;
    String contractAddress = "";
    EditText edt_name, edt_campany, edt_job,edt_mobile,edt_email,edt_remark;
    Button btn_appley;
    public static Handler handler;
    public static String checkpostions="";
    LayoutInflater inflater;
    MyTokenDetailAdapter adapter;
    ContractGroupDetail contract;
    ScrollView scroll_mytoken;
    private Dialog dialog;
    private boolean isPost=false;
    String errormsg="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);
        mContext = ApplyActivity.this;
        this.inflater = LayoutInflater.from(mContext);
        Intent intent = getIntent();
         contract = (ContractGroupDetail) intent.getExtras().getSerializable("obj");
        LinearLayout topbar_back = findViewById(R.id.topbar_back);

        topbar_back.setOnClickListener(this);


        edt_name =  findViewById(R.id.edt_name);
        edt_campany =  findViewById(R.id.edt_campany);

        edt_job = findViewById(R.id.edt_job);
        edt_mobile = findViewById(R.id.edt_mobile);
        edt_email = findViewById(R.id.edt_email);
        edt_remark = findViewById(R.id.edt_remark);
        btn_appley = findViewById(R.id.btn_appley);
        btn_appley.setOnClickListener(this);


        service = NodeClient.getAssetServiceClient();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage(msg);

                switch (msg.what) {
                    case 1:
                        Waiter.alertErrorMessage("提交成功，等待主办方审核",mContext);
                        Intent intent=new Intent(mContext,OrderListActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    case 2:
                        if(errormsg!=null&&errormsg.length()>0){
                            Waiter.alertErrorMessage(errormsg,mContext);
                        }

                        break;
                }
            }
        };

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.topbar_back:
                finish();
                break;
            case R.id.btn_appley:
                createOrder();
                break;

        }
    }

    private void createOrder(){
        if(!Waiter.CheckEditText(edt_name)||!Waiter.CheckEditText(edt_campany)||!Waiter.CheckEditText(edt_mobile)){
            return;
        }
        Apply info=new Apply();
        info.setOwner(SystemConfig.ethAddress);
        String name=edt_name.getText().toString();
        String company=edt_campany.getText().toString();
        String mobile=edt_mobile.getText().toString();
        String email=edt_email.getText().toString();
        String job=edt_job.getText().toString();
        String remark=edt_remark.getText().toString();
        UserDetail userDetail=new UserDetail();
        userDetail.setCompany(company);
        userDetail.setName(name);
        userDetail.setEmail(email);
        userDetail.setJob(job);
        userDetail.setMobile(mobile);
        userDetail.setRemark(remark);
        String json=JSONObject.toJSONString(userDetail);
        info.setName(name);
        info.setJson(json);
        info.setDid(SystemConfig.did);
        dealOrder(info,contract.getAddress());

    }
    private void dealOrder(Apply apply, String contractAddress){
        if(isPost){
            return;
        }
        isPost=true;
        new AsyncTask<Void, Void, Integer>(){
            @Override
            protected  Integer doInBackground(Void... params) {

                try {
                    JsonResult<Apply> jsonResult= service.createApply(apply,contractAddress);
                    if(jsonResult!=null&&jsonResult.getSuccess()!=null&&jsonResult.getSuccess()){

                        return 0;
                    }else{
                        isPost=false;
                        if(jsonResult!=null&&jsonResult.getMessage()!=null){
                            errormsg=jsonResult.getMessage();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return -1;
            }
            protected void onPostExecute( Integer  result) {
                if(result==0){
                    handler.sendEmptyMessage(1);
                }else{
                    ispost=false;
                    handler.sendEmptyMessage(2);
                }


            };
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }


}
