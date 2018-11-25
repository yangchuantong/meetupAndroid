package org.elastos.meetup.dialog;


import android.app.Dialog;
import android.content.Context;
import org.elastos.meetup.R;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by xianxian on 2018/11/24.
 */
public class NormalDialog extends Dialog {

    private Context context;
    private ClickListenerInterface clickListenerInterface;

    LinearLayout dialog_lin_button,dialog_lin_view;
    TextView dialog_content, dialog_title;
    Button dialog_onesure, dialog_cancel, dialog_sure;
    boolean ispost = false;
    private String title = "";
    private String content = "";
    private ArrayList<String> buttonlist;

    public interface ClickListenerInterface {

        public void doConfirm();

        public void doCancel();
    }
    public NormalDialog(Context context, String title, String content,ArrayList<String> buttonlist) {
        super(context, R.style.checkinDialog);
        this.context = context;
        this.title = title;
        this.content = content;
        this.buttonlist=buttonlist;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_normal, null);
        setContentView(view);
        LinearLayout lin_border = (LinearLayout) view.findViewById(R.id.lin_border);
        dialog_lin_button = (LinearLayout) view.findViewById(R.id.dialog_lin_button);

        dialog_title = (TextView) view.findViewById(R.id.dialog_title);
        dialog_content = (TextView) view.findViewById(R.id.dialog_content);

        dialog_onesure = (Button) view.findViewById(R.id.dialog_onesure);
        dialog_cancel = (Button) view.findViewById(R.id.dialog_cancel);
        dialog_sure = (Button) view.findViewById(R.id.dialog_sure);
//
        dialog_onesure.setOnClickListener(new clickListener());
        dialog_cancel.setOnClickListener(new clickListener());
        dialog_sure.setOnClickListener(new clickListener());
        dialog_title.setText(title);
        dialog_content.setText(Html.fromHtml(content));
        if(buttonlist.size()==1){
            dialog_lin_button.setVisibility(View.GONE);
            dialog_onesure.setVisibility(View.VISIBLE);
            dialog_onesure.setText(buttonlist.get(0));
        }else if(buttonlist.size()==2){
            dialog_lin_button.setVisibility(View.VISIBLE);
            dialog_onesure.setVisibility(View.GONE);
            if(buttonlist.get(0).length()>0){
                dialog_sure.setText(buttonlist.get(0));
            }
            if(buttonlist.get(1).length()>0){
                dialog_cancel.setText(buttonlist.get(1));
            }
        }

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels); // 高度设置为屏幕的0.6
        dialogWindow.setAttributes(lp);

        GradientDrawable myGrad1 = (GradientDrawable)lin_border.getBackground();
        myGrad1.setColor(context.getResources().getColor(R.color.white));
    }

    public void setClicklistener(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }

    private class clickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            int id = v.getId();
            switch (id) {
                case R.id.dialog_sure:
                    clickListenerInterface.doConfirm();

                    break;
                case R.id.dialog_cancel:
                    clickListenerInterface.doCancel();
                    break;
                case R.id.dialog_onesure:
                    clickListenerInterface.doCancel();
                    break;
            }
        }


    }
}