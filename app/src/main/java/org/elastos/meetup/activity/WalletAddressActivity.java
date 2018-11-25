package org.elastos.meetup.activity;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.elastos.meetup.R;
import org.elastos.meetup.config.SystemConfig;
import org.elastos.meetup.tools.Waiter;


/**
 * Created by xianxian on 2018/11/24.
 */
public class WalletAddressActivity extends Activity implements View.OnClickListener {

    View topView;
    LinearLayout topbar_back,btn_wallet_copy;
    TextView tv_wallet_address,tv_nickname,topbar_title;
    EditText edt_balance;
    ImageView img_address_qrcode;
    String address="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_wallet_address);
        topbar_back=(LinearLayout)findViewById(R.id.topbar_back);
        btn_wallet_copy=(LinearLayout)findViewById(R.id.btn_wallet_copy);
        tv_wallet_address=(TextView) findViewById(R.id.tv_wallet_address);
        tv_nickname=(TextView) findViewById(R.id.tv_nickname);
        edt_balance=(EditText) findViewById(R.id.edt_balance);
        topbar_title= findViewById(R.id.topbar_title);
        topbar_title.setText(getString(R.string.receive_qr_code));
        edt_balance.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 输入的内容变化的监听

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // 输入前的监听

            }

            @Override
            public void afterTextChanged(Editable s) {
                // 输入后的监听
                String balance=edt_balance.getText().toString();
                String urlcode="http://www.starryplaza.com/common/util/qrcode?data="+SystemConfig.ethAddress+"_"+balance;
                Glide.with(getApplicationContext()).load(urlcode).into(img_address_qrcode);


            }
        });
        Intent intent=getIntent();
        address=intent.getStringExtra("address");
        img_address_qrcode=(ImageView) findViewById(R.id.img_address_qrcode);
        tv_wallet_address.setText(address);
        tv_nickname.setText(SystemConfig.walletName);
        btn_wallet_copy.setOnClickListener(this);
        topbar_back.setOnClickListener(this);

        if(address!=null&&address.length()>0){
            String urlcode="http://www.starryplaza.com/common/util/qrcode?data="+address;
            Glide.with(getApplicationContext()).load(urlcode).into(img_address_qrcode);
        }



        GradientDrawable myGrad = (GradientDrawable)btn_wallet_copy.getBackground();
        myGrad.setColor(Color.parseColor("#33cc66"));

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_wallet_copy:
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(address);
                Waiter.alertErrorMessage(getString(R.string.copy_success),getApplicationContext());
                break;
            case R.id.topbar_back:
                finish();
                break;
        }
    }

}
