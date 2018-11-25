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
import org.elastos.meetuplib.tool.entity.ApplyDetail;

import java.util.ArrayList;

/**
 * Created by xianxian on 2018/11/24.
 */

public class TicketAdapter extends BaseAdapter{

    Context context;
   public ArrayList<ApplyDetail> list;
    private LayoutInflater inflater;
    private  ItemClass itemClass;
    private Boolean isClick;
    public TicketAdapter(Context context, ArrayList<ApplyDetail> lists, Boolean isClick){
        this.context=context;
        this.list=list;
        this.inflater = LayoutInflater.from(context);
        this.isClick=isClick;
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
            convertView = inflater.inflate(R.layout.item_ticket_detail, null);
            itemClass = new ItemClass();
            itemClass.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            itemClass.tv_token_name = (TextView) convertView.findViewById(R.id.tv_token_name);
            itemClass.img_ewm = (ImageView) convertView.findViewById(R.id.img_ewm);

            convertView.setTag(itemClass);
        }else{
            itemClass = (ItemClass) convertView.getTag();
        }
        ApplyDetail obj=this.list.get(position);

        itemClass.tv_name.setText(obj.getContractName());
        itemClass.tv_token_name.setText(obj.getName());
        String urlcode="http://www.starryplaza.com/common/util/qrcode?data="+obj.getContractAddress();
        Glide.with(context).load(urlcode).into( itemClass.img_ewm);
        return convertView;
    }



    class ItemClass{
        ImageView img_ewm;
        TextView tv_name,tv_token_name;
    }
}
