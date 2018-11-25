package org.elastos.meetup.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;

import org.elastos.meetup.R;
import org.elastos.meetup.adapter.OrderAdapter;
import org.elastos.meetup.config.SystemConfig;
import org.elastos.meetup.tools.NodeClient;
import org.elastos.meetup.view.SwipeGridView;
import org.elastos.meetuplib.service.tokenizedAsset.service.AssetService;
import org.elastos.meetuplib.tool.entity.ApplyDetail;
import org.elastos.meetuplib.tool.util.JsonResult;

import java.util.ArrayList;
import java.util.List;

public class OrderListActivity extends Activity implements View.OnClickListener {
    private LinearLayout topbar_back;
    private TextView topbar_title;
    Handler handler;
    OrderListActivity mContext;
    OrderAdapter adapter;
    List<ApplyDetail> list;
    AssetService service;
    SwipeGridView swipeListView;
    SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayoutManager linearLayoutManager;
    private boolean isPost=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderlist);
        mContext = OrderListActivity.this;
        adapter=new OrderAdapter(mContext,null);
        topbar_back=findViewById(R.id.topbar_back);
        topbar_back.setOnClickListener(this);
        topbar_title=findViewById(R.id.topbar_title);
        topbar_title.setText("我的报名");
        topbar_title.setVisibility(View.VISIBLE);
        service=NodeClient.getAssetServiceClient();
        ApplyDetail applyDetail=new ApplyDetail();
        applyDetail.setOwner(SystemConfig.ethAddress);
        getOrders(applyDetail);
        swipeListView =findViewById(R.id.swipeListView) ;
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipe_refresh);
        linearLayoutManager=new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        swipeListView.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ApplyDetail applyDetail=new ApplyDetail();
                        applyDetail.setOwner(SystemConfig.ethAddress);
                        applyDetail.setStatus(null);
                        getOrders(applyDetail);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },2000);
            }
        });
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage(msg);

                switch(msg.what)
                {
                    case 0:
                        adapter.list= (ArrayList<ApplyDetail>) list;
                        adapter.notifyDataSetChanged();
                        break;
                }
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.topbar_back:
                MainActivity.instance.tabnum=3;
               finish();
                break;

        }
    }
    private void getOrders(final ApplyDetail detail){
        if(isPost){
            return;
        }
        isPost=true;
        new AsyncTask<Void, Void, JSONObject>(){
            @Override
            protected  JSONObject doInBackground(Void... params) {
                try {
                    JsonResult<List<ApplyDetail>> jsonResult= service.ownerApplyDetailList(detail);
                    isPost=false;
                    if(jsonResult!=null&&jsonResult.getSuccess()){
                        list=jsonResult.getData();
                      handler.sendEmptyMessage(0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
            protected void onPostExecute( JSONObject  returnJson) {


            };
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
}
