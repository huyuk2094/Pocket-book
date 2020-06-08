package com.design.accountingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;

/*
 * 管理一天的账目*/
public class ListViewAdapter extends BaseAdapter {

    private LinkedList<RecordBean> records = new LinkedList<>();//使用列表存储多笔账目
    private LayoutInflater mInflater;//初始化界面
    private Context mContext;

    //需要传入上下文才能进行资源文件的初始化
    public ListViewAdapter(Context context){
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    //为record赋值
    public void setData(LinkedList<RecordBean> records){
        this.records = records;
        notifyDataSetChanged();//有数据改变时，从新加载
    }

    @Override
    public int getCount() {
        return records.size();
    }

    @Override
    public Object getItem(int position) {
        return records.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        //当view为空时，新建一个viewHolder
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.cell_list_view,null);

            RecordBean recordBean = (RecordBean) getItem(position);
            holder = new ViewHolder(convertView, recordBean);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }
}

/*当账目较多时，只加载一定数量账目，*/
class ViewHolder{
    TextView remarkTV;
    TextView amountTV;
    TextView timeTV;
    ImageView categoryIcon;

    public ViewHolder(View itemView, RecordBean record){
        remarkTV = itemView.findViewById(R.id.textView_remark);
        amountTV = itemView.findViewById(R.id.textView_amount);
        timeTV = itemView.findViewById(R.id.textView_time);
        categoryIcon = itemView.findViewById(R.id.imageView_category);

        remarkTV.setText(record.getRemark());

        //判断是支出还是收入，支出显示“-”，收入显示“+”
        if (record.getType() == 1){
            amountTV.setText("- "+record.getAmount());
        } else {
            amountTV.setText("+ "+record.getAmount());
        }

        timeTV.setText(DateUtil.getFormattedTime(record.getTimeStamp()));
        categoryIcon.setImageResource(GlobalUtil.getInstance().getResourceIcon(record.getCategory()));
    }

}