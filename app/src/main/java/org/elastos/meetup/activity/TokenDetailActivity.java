package org.elastos.meetup.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.elastos.meetup.R;
import org.elastos.meetuplib.service.tokenizedAsset.service.AssetService;
import org.elastos.meetuplib.tool.entity.ContractGroupDetail;
import org.elastos.meetuplib.tool.util.JsonResult;

/**
 * Created by xianxian on 2018/11/24.
 */
public class TokenDetailActivity extends Activity implements View.OnClickListener {

    boolean ispost = false;
    AssetService service;
    private TokenDetailActivity mContext;
    String contractAddress = "";
    ListView list_token;
    ImageView img_head;
    TextView tv_name, tv_desc, tv_num, tv_sale;
    public static Handler handler;
    public static String checkpostions="";
    LayoutInflater inflater;
    ContractGroupDetail contract;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token_detail);
        mContext = TokenDetailActivity.this;
        this.inflater = LayoutInflater.from(mContext);
        Intent intent = getIntent();
        contract = (ContractGroupDetail) intent.getExtras().getSerializable("obj");
        LinearLayout topbar_back = findViewById(R.id.topbar_back);
        topbar_back.setOnClickListener(this);
        img_head = (ImageView) findViewById(R.id.img_head);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_desc = (TextView) findViewById(R.id.tv_desc);
        tv_num = (TextView) findViewById(R.id.tv_num);
        tv_sale = (TextView) findViewById(R.id.tv_sale);
        list_token = (ListView) findViewById(R.id.list_token);
        tv_sale.setOnClickListener(this);

        if (contract.getAddress() != null) {
            contractAddress = contract.getAddress();
            tv_name.setText(contract.getName());

        }
        if(contract.getDescription()!=null){
            tv_desc.setText(contract.getDescription());
        }
        if(contract.getImgUrl()!=null){
            String url = contract.getImgUrl();
            if (url != null && url.length() > 0) {
                Glide.with(mContext).load(url).into(img_head);
            }
        }

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage(msg);

                switch (msg.what) {
                    case 0:
                        Intent intent=new Intent(mContext,OrderListActivity.class);
                        mContext.startActivity(intent);
                        finish();
                        break;

                    case 5:
                        if (contract!=null&&contract.getAddress() != null) {
                            tv_desc.setText(contract.getDescription());

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
            case R.id.tv_sale:
              //进入活动申请表单
            {
                Intent intent=new Intent(mContext,ApplyActivity.class);
                Bundle b=new Bundle();
                b.putSerializable("obj",contract);
                intent.putExtras(b);
                startActivity(intent);


            }
                break;
        }
    }

    private void getContract(final String contractAddress) {
        if (contractAddress!=null&&contractAddress.length()>0) {
            return;
        }
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... params) {
               JsonResult<ContractGroupDetail> jsonResult = null;
                try {
                    jsonResult = service.findByContractAddress(contractAddress);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (jsonResult != null && jsonResult.getSuccess() != null && jsonResult.getSuccess()) {
                    if(jsonResult.getData()!=null){
                        contract=jsonResult.getData();
                        handler.sendEmptyMessage(5);
                    }

                }
                return 0;
            }

            protected void onPostExecute(Integer result) {
            }

            ;
        }.execute();


    }
}
