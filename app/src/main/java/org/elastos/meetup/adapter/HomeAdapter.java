package org.elastos.meetup.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.elastos.meetup.R;
import org.elastos.meetup.view.rounded.RoundedImageView;
import org.elastos.meetuplib.tool.entity.Contract;
import org.elastos.meetuplib.tool.entity.ContractGroupDetail;

import java.util.ArrayList;

/**
 * Created by xianxian on 2018/11/24.
 */

public class HomeAdapter extends BaseAdapter {

    Context context;
   public ArrayList<ContractGroupDetail> list;
    private LayoutInflater inflater;
    private  ItemClass itemClass;
    public HomeAdapter(Context context, ArrayList<ContractGroupDetail> lists){
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
            convertView = inflater.inflate(R.layout.item_home, null);
            itemClass = new ItemClass();
            itemClass.img_assets= convertView.findViewById(R.id.img_assets);
            itemClass.tv_assets_title=(TextView)convertView.findViewById(R.id.tv_assets_title);
            itemClass.tv_num=(TextView)convertView.findViewById(R.id.tv_num);



            convertView.setTag(itemClass);
        }else{
            itemClass = (ItemClass) convertView.getTag();
        }
        Contract obj=this.list.get(position);
        itemClass.tv_assets_title.setText(obj.getName());

//        itemClass.img_assets
        String url=obj.getImgUrl();
        if(url!=null&&url.length()>0){
            Glide.with(context).load(url).into(itemClass.img_assets);
        }



        return convertView;
    }


    class ItemClass{
        RoundedImageView img_assets;
        TextView tv_assets_title,tv_num,tv_price;
    }
}
