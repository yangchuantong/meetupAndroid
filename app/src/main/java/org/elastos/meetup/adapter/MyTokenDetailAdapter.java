package org.elastos.meetup.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.elastos.meetup.R;
import org.elastos.meetup.entity.UserDetail;

import java.util.ArrayList;

/**
 * Created by xianxian on 2018/11/24.
 */

public class MyTokenDetailAdapter extends BaseAdapter{

    Context context;
   public ArrayList<UserDetail> list;
    private LayoutInflater inflater;
    private  ItemClass itemClass;
    public MyTokenDetailAdapter(Context context, ArrayList<UserDetail> lists){
        this.context=context;
        this.list=list;
        this.inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return list==null?0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list==null?null : list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(R.layout.item_token_detail, null);
            itemClass = new ItemClass();
            itemClass.tv_name =  convertView.findViewById(R.id.tv_name);
            itemClass.tv_company =  convertView.findViewById(R.id.tv_company);
            itemClass.tv_job = convertView.findViewById(R.id.tv_job);
            itemClass.tv_mobile = convertView.findViewById(R.id.tv_mobile);
            itemClass.tv_email = convertView.findViewById(R.id.tv_email);
            itemClass.tv_remark = convertView.findViewById(R.id.tv_remark);

            convertView.setTag(itemClass);
        }else{
            itemClass = (ItemClass) convertView.getTag();
        }
        UserDetail obj=this.list.get(position);

        itemClass.tv_name.setText(obj.getName());
        itemClass. tv_company.setText(obj.getCompany());
        itemClass.tv_job.setText(obj.getJob());
        itemClass.tv_mobile.setText(obj.getMobile());
        itemClass.tv_email.setText(obj.getEmail());
        itemClass.tv_remark.setText(obj.getRemark());
        return convertView;
    }



    class ItemClass{
        TextView tv_name, tv_company, tv_job,tv_mobile,tv_email,tv_remark;
    }
}
