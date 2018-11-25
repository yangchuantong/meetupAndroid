package org.elastos.meetup.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.elastos.meetup.R;
import org.elastos.meetuplib.tool.entity.ApplyDetail;
import org.elastos.meetuplib.tool.menum.ApplyStates;

import java.util.ArrayList;

/**
 * Created by xianxian on 2018/11/24.
 */

public class WalletHomeAdapter extends BaseAdapter {

    Context context;
   public ArrayList<ApplyDetail> list;
    private LayoutInflater inflater;
    private  ItemClass itemClass;
    public int zichanPosition=-1;
    public WalletHomeAdapter(Context context, ArrayList<ApplyDetail> list){
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
            convertView = inflater.inflate(R.layout.item_wallet_home, null);
            itemClass = new ItemClass();
            itemClass.tv_metrowallet_name=(TextView)convertView.findViewById(R.id.tv_metrowallet_name);
            itemClass.img_used=convertView.findViewById(R.id.img_used);

            convertView.setTag(itemClass);
        }else{
            itemClass = (ItemClass) convertView.getTag();
        }
        ApplyDetail obj=this.list.get(position);
        itemClass.tv_metrowallet_name.setText(obj.getContractName());
        if(obj.getStatus().intValue()==ApplyStates.BURN.intValue()){
            itemClass.img_used.setVisibility(View.VISIBLE);
        }else{
            itemClass.img_used.setVisibility(View.GONE);
        }



        return convertView;
    }
    class ItemClass{
        ImageView img_used;
        TextView tv_metrowallet_name;

    }
}
