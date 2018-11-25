package org.elastos.meetup.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.elastos.meetup.R;
import org.elastos.meetup.tools.Waiter;
import org.elastos.meetuplib.tool.entity.ApplyDetail;
import org.elastos.meetuplib.tool.menum.ApplyStates;

import java.util.ArrayList;

/**
 * Created by xianxian on 2018/11/24.
 */

public class OrderAdapter extends BaseAdapter {

    Context context;
   public ArrayList<ApplyDetail> list;
    private LayoutInflater inflater;
    private  ItemClass itemClass;
    public OrderAdapter(Context context, ArrayList<ApplyDetail> list){
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
            convertView = inflater.inflate(R.layout.item_order, null);
            itemClass = new ItemClass();
            itemClass.img_assets=(ImageView) convertView.findViewById(R.id.img_assets);
            itemClass.tv_order_name=(TextView)convertView.findViewById(R.id.tv_order_name);
            itemClass.tv_order_no=(TextView)convertView.findViewById(R.id.tv_order_no);
            itemClass.tv_order_status=(TextView)convertView.findViewById(R.id.tv_order_status);
            itemClass.tv_token_name=(TextView)convertView.findViewById(R.id.tv_token_name);
            itemClass.tv_order_time=(TextView)convertView.findViewById(R.id.tv_order_time);

            convertView.setTag(itemClass);
        }else{
            itemClass = (ItemClass) convertView.getTag();
        }
        ApplyDetail obj=list.get(position);
        itemClass.tv_order_name.setText(obj.getContractName());
        itemClass.tv_order_no.setText(obj.getApplyNo());
        itemClass.tv_token_name.setText(obj.getName());
        if(obj.getCreateTime()!=null){
            long timest=obj.getCreateTime()*1000;
            itemClass.tv_order_time.setText(Waiter.timestamp2StringForDate(timest,""));
        }
        String status="";
        if(obj.getStatus().intValue()== ApplyStates.APPLIED.intValue()){
            status="待审核";
        }else if(obj.getStatus().intValue()== ApplyStates.AUDIT_NO_PASS.intValue()){
            status="审核未通过";
        }else if(obj.getStatus().intValue()== ApplyStates.AUDIT_PASS.intValue()){
            status="审核通过";
        }
        itemClass.tv_order_status.setText(status);
        String url=obj.getContractImgUrl();
        itemClass.img_assets.setTag(null);//需要清空tag，否则报错
        if(url!=null&&url.length()>0){
            Glide.with(context).load(url).into(itemClass.img_assets);
        }
        return convertView;
    }

    class ItemClass{
        ImageView img_assets;
        TextView tv_order_name,tv_order_no,tv_order_status,tv_token_name,tv_order_time;
    }
}
