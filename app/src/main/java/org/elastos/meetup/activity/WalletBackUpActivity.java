package org.elastos.meetup.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.elastos.meetup.R;
import org.elastos.meetup.config.SystemConfig;
import org.elastos.meetup.dialog.NormalDialog;

import java.util.ArrayList;


/**
 * Created by 备份钱包提示
 */
public class WalletBackUpActivity extends Activity implements View.OnClickListener {

    View topView;
    LinearLayout topbar_back,lin_wallet_backup,lin_wallet_words;

    TextView tv_wallat_backup,topbar_title,tv_wallat_words,tv_wallat_backup_title,tv_wallat_backup_content;


    String address="",words="";
    private int walletaction=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_backup);
        Intent intent=getIntent();
        words=intent.getStringExtra("words");
        walletaction=intent.getIntExtra("action",0);

        topbar_back=(LinearLayout)findViewById(R.id.topbar_back);
        topbar_title=findViewById(R.id.topbar_title);
        lin_wallet_backup=(LinearLayout)findViewById(R.id.lin_wallet_backup);
        lin_wallet_words=(LinearLayout)findViewById(R.id.lin_wallet_words);

        tv_wallat_backup=(TextView)findViewById(R.id.tv_wallat_backup);
        tv_wallat_words=(TextView)findViewById(R.id.tv_wallat_words);
        tv_wallat_backup_title=(TextView)findViewById(R.id.tv_wallat_backup_title);
        tv_wallat_backup_content=(TextView)findViewById(R.id.tv_wallat_backup_content);
        tv_wallat_words.setText(words);
        topbar_back.setOnClickListener(this);
        tv_wallat_backup.setOnClickListener(this);
        setContent();
    }
    private void setContent(){
        if(walletaction>2){

            lin_wallet_backup.setVisibility(View.GONE);
            lin_wallet_words.setVisibility(View.VISIBLE);
            topbar_title.setText("创建DID成功");
            tv_wallat_words.setText(words);
            if(walletaction==3){
                tv_wallat_backup_title.setText("恭喜你已经创建成功，将来您可以在钱包中进行导入导出和备份");
                tv_wallat_backup_content.setText("您的DID地址如下");
            }else if(walletaction==4){
                tv_wallat_backup_title.setText(getString(R.string.copy_your_keystore));
                tv_wallat_backup_content.setText(getString(R.string.keystore_important));
            }

        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_wallat_backup:
                lin_wallet_backup.setVisibility(View.GONE);
                lin_wallet_words.setVisibility(View.VISIBLE);
                topbar_title.setText(getString(R.string.backup_mnemonic));
                topbar_back.setVisibility(View.VISIBLE);
                break;
            case R.id.topbar_back:
                Intent intent=new Intent(WalletBackUpActivity.this,MainActivity.class);
                startActivity(intent);
                finish();

                break;
        }
    }
    private void showBackDialog(){
        ArrayList<String> buttonlist=new ArrayList<String>();
        String versiontitle="免责声明";
        String versionmsg="请确保已备份钱包至安全的地方，DMA app不承担任何钱包丢失、被盗、忘记密码等产生的资产损失！";
        buttonlist.add(getString(R.string.wallet_confirm));
        buttonlist.add(getString(R.string.wallet_cancel));

        final NormalDialog confirmDialog=new NormalDialog(WalletBackUpActivity.this,versiontitle,versionmsg,buttonlist);
        confirmDialog.show();
        confirmDialog.setClicklistener(new NormalDialog.ClickListenerInterface() {
            @Override
            public void doConfirm() {
                confirmDialog.dismiss();
                Intent intent;
                if(!SystemConfig.haswallet){
                    intent=new Intent(WalletBackUpActivity.this,MainActivity.class);
                    startActivity(intent);
                }
                finish();
            }

            @Override
            public void doCancel() {
                // TODO Auto-generated method stub
                confirmDialog.dismiss();

            }
        });
    }
}
