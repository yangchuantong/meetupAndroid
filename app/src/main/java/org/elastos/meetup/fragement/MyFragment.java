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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.elastos.meetup.R;
import org.elastos.meetup.activity.MyTokenDetailActivity;
import org.elastos.meetup.activity.OrderListActivity;
import org.elastos.meetup.activity.WalletAddressActivity;
import org.elastos.meetup.adapter.WalletHomeAdapter;
import org.elastos.meetup.config.SystemConfig;
import org.elastos.meetup.tools.NodeClient;
import org.elastos.meetup.view.SwipeListView;
import org.elastos.meetuplib.base.DIDChain;
import org.elastos.meetuplib.base.EthChain;
import org.elastos.meetuplib.service.tokenizedAsset.service.AssetService;
import org.elastos.meetuplib.tool.entity.Apply;
import org.elastos.meetuplib.tool.entity.ApplyDetail;
import org.elastos.meetuplib.tool.menum.ApplyStates;
import org.elastos.meetuplib.tool.util.JsonResult;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xianxian on 2018/11/24.
 */

public class MyFragment extends Fragment implements View.OnClickListener {

    private LinearLayout topbar_back,lin_wallet_ela;
    private TextView topbar_save,tv_wallet_address,tv_wallet_address_ela,tv_metrowallet_blance,tv_metrowallet_blance_eth;
    ImageView img_address_qrcode_ela,img_address_qrcode;
    private Activity mainActivity;

    List<ApplyDetail> list;
    WalletHomeAdapter adapter;
    Handler handler;
    String elabalance="",ethbalance="";
    SwipeListView swipeListView;
    SwipeRefreshLayout swipeRefreshLayout;
    private Dialog dialog;
    private int lastVisibleItem;
    private LinearLayoutManager linearLayoutManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_wallet, container, false);
        mainActivity = getActivity();
        topbar_back=v.findViewById(R.id.topbar_back);
        TextView topbar_title=v.findViewById(R.id.topbar_title);
        topbar_title.setText("我的");
        topbar_back.setVisibility(View.GONE);
        lin_wallet_ela=v.findViewById(R.id.lin_wallet_ela);
        lin_wallet_ela.setOnClickListener(this);
        topbar_save=v.findViewById(R.id.topbar_save);
        topbar_save.setText("我的报名");
        topbar_save.setVisibility(View.VISIBLE);
        topbar_save.setOnClickListener(this);
        tv_wallet_address=v.findViewById(R.id.tv_wallet_address);
        tv_wallet_address_ela=v.findViewById(R.id.tv_wallet_address_ela);
        img_address_qrcode_ela=v.findViewById(R.id.img_address_qrcode_ela);
        img_address_qrcode=v.findViewById(R.id.img_address_qrcode);

        tv_metrowallet_blance=v.findViewById(R.id.tv_metrowallet_blance);
        tv_metrowallet_blance_eth=v.findViewById(R.id.tv_metrowallet_blance_eth);

        tv_wallet_address.setText(SystemConfig.ethAddress);
        tv_wallet_address_ela.setText(SystemConfig.address);
        img_address_qrcode_ela.setOnClickListener(this);
        img_address_qrcode.setOnClickListener(this);
        tv_metrowallet_blance.setOnClickListener(this);

        swipeListView =(SwipeListView) v.findViewById(R.id.swipeListView) ;
        swipeRefreshLayout=(SwipeRefreshLayout)v.findViewById(R.id.swipe_refresh);
        linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        adapter = new WalletHomeAdapter(mainActivity, null);

        swipeListView.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getElaBalance();
                        getEthBalance();
                        getApplyInfoList();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                },2000);
            }
        });
        swipeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ApplyDetail obj= (ApplyDetail) parent.getItemAtPosition(position);
                if(obj.getStatus()!=null&&obj.getStatus().intValue()== ApplyStates.AUDIT_PASS.intValue()){

                    Intent intent=new Intent(mainActivity,MyTokenDetailActivity.class);
                    Bundle b=new Bundle();
                    b.putSerializable("obj",obj);
                    intent.putExtras(b);
                    mainActivity.startActivity(intent);
                }

            }
        });
        getElaBalance();
        getEthBalance();
        getApplyInfoList();
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage(msg);

                switch(msg.what)
                {
                    case 0:
//                        setETHbalance();
                        tv_metrowallet_blance_eth.setText(ethbalance);
                        break;
                        case 1:
                            tv_metrowallet_blance.setText(elabalance);
                        break;
                        case 2:

                            adapter.list= (ArrayList<ApplyDetail>) list;
                            adapter.zichanPosition=-1;
                            adapter.notifyDataSetChanged();
                        break;
                }
            }
        };
        return v;
    }
    private void getEthBalance(){
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... params) {
                try {
                    if(SystemConfig.ethAddress!=null&&SystemConfig.ethAddress.length()>0){
                        ethbalance= EthChain.balance(SystemConfig.ethAddress);
                        BigDecimal b = new BigDecimal(ethbalance);
                        ethbalance = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue()+"";
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }


                return 0;
            }

            protected void onPostExecute(Integer result) {
                if(result==0&&handler!=null){
                    handler.sendEmptyMessage(0);
                }
            }

            ;
        }.execute();
    }
    private void getElaBalance(){
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... params) {
                elabalance= DIDChain.balance(SystemConfig.address);
                if(elabalance!=null&&elabalance.length()>0&&handler!=null){
                    handler.sendEmptyMessage(1);
                }
                return 0;
            }

            protected void onPostExecute(Integer result) {

            }
            ;
        }.execute();
    }
    private void getApplyInfoList(){
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... params) {

                AssetService service=NodeClient.getAssetServiceClient();
                Apply apply=new Apply();
                apply.setStatus(ApplyStates.AUDIT_PASS.intValue());
                apply.setOwner(SystemConfig.ethAddress);
                JsonResult<List<ApplyDetail>> jsonResult= service.ownerApplyDetailList(apply);
                if(jsonResult!=null&&jsonResult.getSuccess()!=null&&jsonResult.getSuccess()){
                    list=jsonResult.getData();
                    handler.sendEmptyMessage(2);
                }
                return 0;
            }

            protected void onPostExecute(Integer result) {

            }
            ;
        }.execute();
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.topbar_save:{
                Intent intent=new Intent(mainActivity,OrderListActivity.class);
                mainActivity.startActivity(intent);
            }
            break;
            case R.id.img_address_qrcode:
                {
                        String qrcodeAddress=String.valueOf(v.getTag());
                        Intent intent=new Intent(mainActivity,WalletAddressActivity.class);
                        intent.putExtra("address",SystemConfig.ethAddress);
                        SystemConfig.linkType="ETH";
                        mainActivity.startActivity(intent);


            }
            break;
            case R.id.tv_wallet_address:
                {
                        String qrcodeAddress=String.valueOf(v.getTag());
                        Intent intent=new Intent(mainActivity,WalletAddressActivity.class);
                        intent.putExtra("address",SystemConfig.ethAddress);
                        SystemConfig.linkType="ETH";
                        mainActivity.startActivity(intent);


            }
            break;
            case R.id.img_address_qrcode_ela:
                {
                        String qrcodeAddress=String.valueOf(v.getTag());
                        Intent intent=new Intent(mainActivity,WalletAddressActivity.class);
                        intent.putExtra("address",SystemConfig.address);
                        SystemConfig.linkType="ELA";
                        mainActivity.startActivity(intent);
            }
                break;
            case R.id.tv_wallet_address_ela:
                {

                        String qrcodeAddress=String.valueOf(v.getTag());
                        Intent intent=new Intent(mainActivity,WalletAddressActivity.class);
                        intent.putExtra("address",SystemConfig.address);
                        SystemConfig.linkType="ELA";
                        mainActivity.startActivity(intent);


            }
                break;

        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}