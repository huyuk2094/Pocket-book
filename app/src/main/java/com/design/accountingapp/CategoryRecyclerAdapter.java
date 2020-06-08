package com.design.accountingapp;
//区分选择图标与未选图标
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.LinkedList;

public class CategoryRecyclerAdapter extends RecyclerView.Adapter<CategoryViewHolder> {

    private LayoutInflater mInflater;
    private Context mContext;
    private String selected="";//保存用户选择的图标

    private LinkedList<CategoryResBean> cellList = GlobalUtil.getInstance().costRes;//资源图标列表


    public String getSelected() {
        return selected;
    }

    public void setOnCategoryClickListener(OnCategoryClickListener onCategoryClickListener) {
        this.onCategoryClickListener = onCategoryClickListener;
    }

    private OnCategoryClickListener onCategoryClickListener;

    public CategoryRecyclerAdapter(Context context){
//        GlobalUtil.getInstance().setContext(context);
        this.mContext = context;
        mInflater = LayoutInflater.from(mContext);
        selected = cellList.get(0).title;//初始化selected默认选择第0个图标
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.cell_category,parent,false);
        CategoryViewHolder myViewHolder = new CategoryViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        final CategoryResBean res = cellList.get(position);
        holder.imageView.setImageResource(res.resBlack);
        holder.textView.setText(res.title);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected = res.title;
                notifyDataSetChanged();

                if (onCategoryClickListener!=null){
                    onCategoryClickListener.onClick(res.title);
                }

            }
        });

        //对选择的图标加背景，用以区分未选择的其他图标
        if (holder.textView.getText().toString().equals(selected)){
            holder.background.setBackgroundResource(R.drawable.bg_edit_text);
        }
        else {
            holder.background.setBackgroundResource(R.color.colorPrimary);
        }

    }

    //点击转换图标后将支出图标变换为收入图标
    public void changeType(RecordBean.RecordType type){
        if (type == RecordBean.RecordType.RECORD_TYPE_EXPENSE){
            cellList = GlobalUtil.getInstance().costRes;
        }else {
            cellList = GlobalUtil.getInstance().earnRes;
        }

        selected = cellList.get(0).title;
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return cellList.size();
    }

    public interface OnCategoryClickListener{
        void onClick(String category);
    }

}

//cell_category中的控件
class CategoryViewHolder extends RecyclerView.ViewHolder{

    RelativeLayout background;
    ImageView imageView;
    TextView textView;

    public CategoryViewHolder(View itemView) {
        super(itemView);
        background = itemView.findViewById(R.id.cell_background);
        imageView = itemView.findViewById(R.id.imageView_category);
        textView = itemView.findViewById(R.id.textView_category);
    }
}
