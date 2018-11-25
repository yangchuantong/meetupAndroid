package org.elastos.meetup.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.elastos.meetup.R;
import org.elastos.meetup.config.SystemConfig;
import org.elastos.meetup.tools.DES;
import org.elastos.meetup.tools.Waiter;
import org.elastos.meetuplib.base.DIDChain;
import org.elastos.meetuplib.base.entity.DIDAccount;
import org.elastos.meetuplib.service.NativeDataService;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    EditText edt_password;
    Button btn_login;
    TextView tv_did;
    public static LoginActivity instance;
    private Dialog dialog;
    private boolean ispost=false;
    String words="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance=LoginActivity.this;
        setContentView(R.layout.activity_login);
        edt_password=(EditText)findViewById(R.id.edt_password);
        btn_login=(Button) findViewById(R.id.btn_login);
        tv_did=findViewById(R.id.tv_did);
        TextView tv_login_title=findViewById(R.id.tv_login_title);
        btn_login.setOnClickListener(this);
        Map<String,String> walletMap= NativeDataService.getInstance().loadWallet(this);
        if(walletMap!=null&&walletMap.get("did")!=null&&walletMap.get("did").length()>0) {
            tv_did.setText("DID编号："+walletMap.get("did"));
            btn_login.setText("登录");
            tv_login_title.setText("登录");
        }else{
            btn_login.setText("创建DID");
            tv_login_title.setText("创建");
        }
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);

            switch(msg.what)
            {
                case 0:
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                    break;
                case 1:
                    dialog= Waiter.initProgressDialog(LoginActivity.this,getString(R.string.title_loading));
                    dialog.show();
                    break;
                case 2:
                {
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }

                    break;
                case 3:
                {
                    Intent intent=new Intent(LoginActivity.this,WalletBackUpActivity.class);
                    intent.putExtra("words",words);
                    intent.putExtra("action",3);
                    startActivity(intent);
                    finish();
                }

            }
        }
    };
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:
                if(!Waiter.CheckEditText(edt_password)){
                    return;
                }
                String password=edt_password.getText().toString();
                if(password.length()<8){
                    Waiter.alertErrorMessage("密码错误",LoginActivity.this);
                    return;
                }

                verfryWallet(password);

                break;

        }
    }
    private void verfryWallet(final String pwd){
        Map<String,String> walletMap= NativeDataService.getInstance().loadWallet(this);
        if(walletMap!=null&&walletMap.get("privateKeyPwd")!=null&&walletMap.get("privateKeyPwd").length()>0){
            if(!pwd.equals(DES.decryptDES(walletMap.get("privateKeyPwd")))){
                Waiter.alertErrorMessage("密码错误",LoginActivity.this);
                return;
            }
            SystemConfig.ethAddress=walletMap.get("ethAddress");
            SystemConfig.address=walletMap.get("address");
            SystemConfig.privatekey=walletMap.get("privateKey");
            SystemConfig.did=walletMap.get("did");
            handler.sendEmptyMessage(2);

        }else{
            handler.sendEmptyMessage(1);
           if(ispost){
               return;
           }
            ispost=true;

            new AsyncTask<Void, Void, DIDAccount>(){
                @Override
                protected  DIDAccount doInBackground(Void... params) {
                    DIDAccount didAccount= DIDChain.create();
                    return didAccount;
                }
                protected void onPostExecute(DIDAccount didAccount) {
                    ispost=false;
                    handler.sendEmptyMessage(0);
                    if(didAccount!=null&&didAccount.getDid()!=null){
                        Map<String,String> walletMap=new HashMap<>();

                        String ethAddress=didAccount.getEthAddress();
                        String publicKey=didAccount.getPublicKey();
                        String address=didAccount.getAddress();
                        String privateKey=didAccount.getPrivateKey();
                        String did=didAccount.getDid();
                        SystemConfig.address=address;
                        SystemConfig.ethAddress=ethAddress;
                        SystemConfig.privatekey=privateKey;
                        SystemConfig.did=did;
                        walletMap.put("privateKey",privateKey);
                        walletMap.put("publicKey",publicKey);
                        walletMap.put("did",did);
                        walletMap.put("address",address);
                        walletMap.put("ethAddress",ethAddress);
                        walletMap.put("privateKeyPwd",DES.encryptDES(pwd));
                        words=did;
                        NativeDataService.getInstance().saveWallet(LoginActivity.this,walletMap);
                        handler.sendEmptyMessage(3);
                    }


                };
            }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        }
    }

}
