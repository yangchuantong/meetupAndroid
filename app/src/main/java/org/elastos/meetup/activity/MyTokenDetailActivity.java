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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;

import org.elastos.meetup.R;
import org.elastos.meetup.config.SystemConfig;
import org.elastos.meetup.entity.UserDetail;
import org.elastos.meetup.tools.NodeClient;
import org.elastos.meetup.tools.Waiter;
import org.elastos.meetuplib.service.tokenizedAsset.service.AssetService;
import org.elastos.meetuplib.tool.entity.Apply;
import org.elastos.meetuplib.tool.entity.ApplyDetail;
import org.elastos.meetuplib.tool.menum.ApplyStates;
import org.elastos.meetuplib.tool.util.JsonResult;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by xianxian on 2018/11/24.
 */
public class MyTokenDetailActivity extends Activity implements View.OnClickListener {

    boolean ispost = false;
    AssetService service;
    private MyTokenDetailActivity mContext;
    String contractAddress = "";
    ImageView img_head,img_used;
    TextView  tv_contract_name,tv_desc,tv_use,tv_audit_no,tv_audit_yes;
    TextView  tv_name,tv_company, tv_job,tv_mobile,tv_email,tv_remark;
    LinearLayout lin_assets_bottom,lin_assets_audit;
    ArrayList<UserDetail> list;
    public static Handler handler;
    public static String checkpostions="";
    LayoutInflater inflater;
    ApplyDetail obj;
    ScrollView scroll_mytoken;
    private Dialog dialog;
    private boolean isPost=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mytoken_detail);
        mContext = MyTokenDetailActivity.this;
        this.inflater = LayoutInflater.from(mContext);
        Intent intent = getIntent();
         obj = (ApplyDetail) intent.getExtras().getSerializable("obj");
        LinearLayout topbar_back = findViewById(R.id.topbar_back);
        scroll_mytoken=findViewById(R.id.scroll_mytoken);
        lin_assets_bottom = findViewById(R.id.lin_assets_bottom);
        lin_assets_audit = findViewById(R.id.lin_assets_audit);
        img_used=findViewById(R.id.img_used);
        topbar_back.setOnClickListener(this);
        if(obj.getOwner()!=null){

            if(obj.getOwner().equals(SystemConfig.ethAddress)){
                lin_assets_bottom.setVisibility(View.VISIBLE);
                lin_assets_audit.setVisibility(View.GONE);
                if(obj.getStatus().intValue()==ApplyStates.BURN.intValue()){
                    img_used.setVisibility(View.VISIBLE);
                    lin_assets_bottom.setVisibility(View.GONE);
                }else {
                    img_used.setVisibility(View.GONE);
                }
            }else{
                lin_assets_bottom.setVisibility(View.GONE);
                lin_assets_audit.setVisibility(View.VISIBLE);
                if(obj.getStatus().intValue()!=ApplyStates.APPLIED.intValue()){
                    lin_assets_audit.setVisibility(View.GONE);
                    Waiter.alertErrorMessage("此订单已审核",mContext);
                }
            }
        }
        img_head = (ImageView) findViewById(R.id.img_head);
        tv_contract_name = (TextView) findViewById(R.id.tv_contract_name);
        tv_desc = (TextView) findViewById(R.id.tv_desc);

        tv_use = (TextView) findViewById(R.id.tv_use);
        tv_use.setOnClickListener(this);
        tv_audit_no = (TextView) findViewById(R.id.tv_audit_no);
        tv_audit_no.setOnClickListener(this);
        tv_audit_yes= (TextView) findViewById(R.id.tv_audit_yes);
        tv_audit_yes.setOnClickListener(this);


   
        tv_name =  findViewById(R.id.tv_name);
       tv_company =  findViewById(R.id.tv_company);
       tv_job = findViewById(R.id.tv_job);
       tv_mobile = findViewById(R.id.tv_mobile);
       tv_email = findViewById(R.id.tv_email);
       tv_remark = findViewById(R.id.tv_remark);
        if (obj!=null&&obj.getId()!= null) {
            contractAddress = obj.getContractAddress();
            tv_contract_name.setText(obj.getContractName());
            tv_desc.setText(obj.getContractInfo());
            String url = obj.getContractImgUrl();
            if (url != null && url.length() > 0) {
                Glide.with(mContext).load(url).into(img_head);
            }
            if(obj.getJson()!=null){
                UserDetail userDetail=JSONObject.parseObject(obj.getJson(),UserDetail.class);
                tv_name.setText("姓名："+userDetail.getName());
                 tv_company.setText("公司："+userDetail.getCompany());
                tv_job.setText("职务："+userDetail.getJob());
                tv_mobile.setText("手机："+userDetail.getMobile());
                tv_email.setText("Email："+userDetail.getEmail());
                tv_remark.setText("建议："+userDetail.getRemark());
            }
        }

        
        
        service = NodeClient.getAssetServiceClient();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage(msg);

                switch (msg.what) {
                    case 0:
                        break;
                    case 1:
                        Waiter.alertErrorMessage("审核成功",mContext);
                        finish();
                        break;
                    case 2:
                        Waiter.alertErrorMessage("审核失败",mContext);
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

            case R.id.tv_use:
                Intent intent=new Intent(mContext,TicketActivity.class);
                Bundle b=new Bundle();
                b.putSerializable("obj", (Serializable) obj);
                intent.putExtras(b);
                mContext.startActivity(intent);
                break;
            case R.id.tv_audit_yes:
                dealOrder(ApplyStates.AUDIT_PASS.intValue());
                break;
            case R.id.tv_audit_no:
                dealOrder(ApplyStates.AUDIT_NO_PASS.intValue());
                break;
        }
    }

    private void dealOrder(int state){
       final Apply apply=new Apply();
        apply.setStatus(state);
        apply.setApplyNo(obj.getApplyNo());
        contractAddress=obj.getContractAddress();
        if(isPost){
            return;
        }
        isPost=true;
        new AsyncTask<Void, Void, Integer>(){
            @Override
            protected  Integer doInBackground(Void... params) {

                try {
                    JsonResult<ApplyDetail> jsonResult= service.auditApply(apply,contractAddress,obj.getOwner(),SystemConfig.privatekey,SystemConfig.gasPrice,SystemConfig.gasLimit,obj.getOwner());
                    if(jsonResult!=null&&jsonResult.getSuccess()!=null&&jsonResult.getSuccess()){

                        return 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ispost=false;
                return -1;
            }
            protected void onPostExecute( Integer  result) {
                if(result==0){
                    handler.sendEmptyMessage(1);
                }else{
                    handler.sendEmptyMessage(2);
                }


            };
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
}
