package org.elastos.meetup.fragement;

import android.app.Activity;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import org.elastos.meetup.R;
import org.elastos.meetup.activity.TokenDetailActivity;
import org.elastos.meetup.adapter.HomeAdapter;
import org.elastos.meetup.tools.NodeClient;
import org.elastos.meetup.view.SwipeGridView;
import org.elastos.meetuplib.service.tokenizedAsset.service.AssetService;
import org.elastos.meetuplib.tool.entity.ContractGroupDetail;
import org.elastos.meetuplib.tool.util.JsonResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xianxian on 2018/11/24.
 */

public class MeetupFragment extends Fragment implements View.OnClickListener{


    SwipeGridView swipeListView;
    SwipeRefreshLayout swipe_refresh;
    private LinearLayoutManager linearLayoutManager;
    private AssetService service;
    public static Handler handler;
    ArrayList<ContractGroupDetail> list;
    HomeAdapter adapter;
    private Activity mainActivity;
    int radioType=1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        service=NodeClient.getAssetServiceClient();
        LinearLayout topbar_back=v.findViewById(R.id.topbar_back);
        topbar_back.setVisibility(View.GONE);
        TextView topbar_title=v.findViewById(R.id.topbar_title);
        topbar_title.setText("MEETUP");
        mainActivity = getActivity();
        swipeListView =v.findViewById(R.id.swipeListView) ;
        linearLayoutManager=new LinearLayoutManager(getActivity());

        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        swipe_refresh = (SwipeRefreshLayout) v.findViewById(R.id.swipe_refresh);
        swipe_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe_refresh.postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        list=new ArrayList<>();
                        getHomeMerchant(radioType,"");
                        swipe_refresh.setRefreshing(false);
                    }
                },2000);
            }
        });
        swipeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ContractGroupDetail obj= (ContractGroupDetail) parent.getItemAtPosition(position);
                Intent intent=new Intent(mainActivity,TokenDetailActivity.class);
                Bundle b=new Bundle();
                b.putSerializable("obj",obj);
                intent.putExtras(b);
                mainActivity.startActivity(intent);
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
                        adapter.list=(ArrayList<ContractGroupDetail>) list;
                        adapter.notifyDataSetChanged();
                        break;
                }
            }
        };
        adapter=new HomeAdapter(mainActivity,null);
        swipeListView.setAdapter(adapter);
        getHomeMerchant(radioType,"");
        return v;
    }
    private void getHomeMerchant(int type,String words){

        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... params) {
                try {
                    //keywords
                    JsonResult<List<ContractGroupDetail>> jsonResult= service.homePage();
                    if(jsonResult.getSuccess()!=null&&jsonResult.getSuccess()){
                        list= (ArrayList<ContractGroupDetail>) jsonResult.getData();
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
    @Override
    public void onClick(View v) {
        switch (v.getId()){


        }
    }

}
