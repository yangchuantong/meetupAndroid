package org.elastos.meetup.adapter;

import android.content.Context;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.elastos.meetup.R;
import org.elastos.meetup.fragement.AssetsFragment;
import org.elastos.meetuplib.tool.entity.Contract;
import org.elastos.meetuplib.tool.entity.ContractGroupDetail;

import java.util.ArrayList;

/**
 * Created by xianxian on 2018/11/24.
 */

public class AssetsAdapter extends BaseAdapter implements View.OnClickListener {

    Context context;
   public ArrayList<ContractGroupDetail> list;
    private LayoutInflater inflater;
    private  ItemClass itemClass;
    public AssetsAdapter(Context context, ArrayList<ContractGroupDetail> lists){
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
            convertView = inflater.inflate(R.layout.item_assets, null);
            itemClass = new ItemClass();
            itemClass.img_assets=(ImageView) convertView.findViewById(R.id.img_assets);
            itemClass.tv_assets_title=(TextView)convertView.findViewById(R.id.tv_assets_title);
            itemClass.tv_num=(TextView)convertView.findViewById(R.id.tv_num);
            itemClass.tv_scan=(TextView)convertView.findViewById(R.id.tv_scan);
            itemClass.tv_orders=convertView.findViewById(R.id.tv_orders);

            convertView.setTag(itemClass);
        }else{
            itemClass = (ItemClass) convertView.getTag();
        }
        ContractGroupDetail obj=list.get(position);
        itemClass.tv_assets_title.setText(obj.getName());
        if(obj.getQuantity()!=null){
            itemClass.tv_num.setText("报名数："+obj.getQuantity());
        }

        itemClass.tv_scan.setOnClickListener(this);
        itemClass.tv_orders.setOnClickListener(this);
        itemClass.img_assets.setOnClickListener(this);
        itemClass.tv_scan.setTag(position);
        itemClass.tv_orders.setTag(position);
        String url=obj.getImgUrl();
        itemClass.img_assets.setTag(null);//需要清空tag，否则报错
        if(url!=null&&url.length()>0){
            Glide.with(context).load(url).into(itemClass.img_assets);
        }
        itemClass.img_assets.setTag(position);


        return convertView;
    }

    @Override
    public void onClick(View v) {
        int position = Integer.parseInt(String.valueOf(v.getTag()));
        Contract obj=list.get(position);
        switch (v.getId()){
            case R.id.tv_scan:
            {
                Message msg=new Message();
                msg.what=1;
                msg.arg1=position;
                AssetsFragment.handler.sendMessage(msg);
            }
                break;

                case R.id.tv_orders:
                {
                    Message msg=new Message();
                    msg.what=6;
                    msg.arg1=position;
                    AssetsFragment.handler.sendMessage(msg);
                }
                break;
        }
    }

    class ItemClass{
        ImageView img_assets;
        TextView tv_assets_title,tv_num,tv_scan,tv_orders;
    }
}
