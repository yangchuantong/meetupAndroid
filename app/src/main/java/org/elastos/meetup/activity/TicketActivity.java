package org.elastos.meetup.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.elastos.meetup.R;
import org.elastos.meetup.config.SystemConfig;
import org.elastos.meetuplib.tool.entity.ApplyDetail;

/**
 * Created by xianxian on 2018/11/24.
 */
public class TicketActivity extends Activity implements View.OnClickListener {

    boolean ispost = false;
    private TicketActivity mContext;
    String contractAddress = "";
    public static Handler handler;
    LayoutInflater inflater;
    ImageView img_ewm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_detail);
        mContext = TicketActivity.this;
        this.inflater = LayoutInflater.from(mContext);
        Intent intent = getIntent();
        ApplyDetail obj= (ApplyDetail) intent.getExtras().getSerializable("obj");

        LinearLayout topbar_back = findViewById(R.id.topbar_back);
        topbar_back.setOnClickListener(this);
        TextView topbar_title=(TextView)findViewById(R.id.topbar_title);
        TextView tv_contact_name=findViewById(R.id.tv_contact_name);
        TextView tv_name=findViewById(R.id.tv_name);
        img_ewm=findViewById(R.id.img_ewm);
        topbar_title.setText("我的邀请函");
        tv_contact_name.setText(obj.getContractName());
        tv_name.setText(obj.getName());
        String urlcode="http://www.starryplaza.com/common/util/qrcode?data="+obj.getContractAddress()+"_"+SystemConfig.ethAddress+"_"+obj.getTokenId();
        Glide.with(mContext).load(urlcode).into(img_ewm);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.topbar_back:
                finish();
                break;

        }
    }


}
