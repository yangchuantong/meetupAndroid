package org.elastos.meetup.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.elastos.meetup.R;
import org.elastos.meetup.tools.Waiter;
import org.elastos.meetuplib.tool.entity.ApplyDetail;
import org.elastos.meetuplib.tool.menum.ApplyStates;

import java.util.ArrayList;

/**
 * Created by xianxian on 2018/11/24.
 */

public class ApplyAdapter extends BaseAdapter {

    Context context;
   public ArrayList<ApplyDetail> list;
    private LayoutInflater inflater;
    private  ItemClass itemClass;
    public ApplyAdapter(Context context, ArrayList<ApplyDetail> list){
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
            convertView = inflater.inflate(R.layout.item_apply, null);
            itemClass = new ItemClass();
            itemClass.tv_name=convertView.findViewById(R.id.tv_name);
            itemClass.tv_status=convertView.findViewById(R.id.tv_status);
            itemClass.tv_createtime=convertView.findViewById(R.id.tv_createtime);

            convertView.setTag(itemClass);
        }else{
            itemClass = (ItemClass) convertView.getTag();
        }
        ApplyDetail obj=list.get(position);
        itemClass.tv_name.setText(obj.getName());
        if(obj.getCreateTime()!=null){
            long timest=obj.getCreateTime()*1000;
            itemClass.tv_createtime.setText(Waiter.timestamp2StringForDate(timest,""));
        }
        String status="";
        if(obj.getStatus().intValue()== ApplyStates.APPLIED.intValue()){
            status="待审核";
        }else if(obj.getStatus().intValue()== ApplyStates.AUDIT_NO_PASS.intValue()){
            status="审核未通过";
        }else if(obj.getStatus().intValue()== ApplyStates.AUDIT_PASS.intValue()){
            status="审核通过";
        }
        itemClass.tv_status.setText(status);
        return convertView;
    }

    class ItemClass{
        TextView tv_name,tv_status,tv_createtime;
    }
}
