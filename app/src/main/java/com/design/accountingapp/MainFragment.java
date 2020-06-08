package com.design.accountingapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.LinkedList;

@SuppressLint("ValidFragment")
public class MainFragment extends Fragment implements AdapterView.OnItemLongClickListener{

    private View rootView;//定义根视图寻找TextView和ListView
    private TextView textView;
    private ListView listView;
    private ListViewAdapter listViewAdapter;

    private LinkedList<RecordBean> records = new LinkedList<>();

    private String date = "";

    @SuppressLint("ValidFragment")
    public MainFragment(String date){
        this.date = date;
        records = GlobalUtil.getInstance().databaseHelper.readRecords(date);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main,container,false);
        initView();
        return rootView;
    }

    public void reload(){

        records = GlobalUtil.getInstance().databaseHelper.readRecords(date);
        if (listViewAdapter==null){
            listViewAdapter = new ListViewAdapter(getActivity().getApplicationContext());
        }

        listViewAdapter.setData(records);
        listView.setAdapter(listViewAdapter);

        if (listViewAdapter.getCount()>0){
            rootView.findViewById(R.id.no_record_layout).setVisibility(View.INVISIBLE);
        }
    }

    private void initView(){
        textView = (TextView) rootView.findViewById(R.id.day_text);
        listView = (ListView) rootView.findViewById(R.id.listView);
        textView.setText(date);
        listViewAdapter = new ListViewAdapter(getContext());
        listViewAdapter.setData(records);
        listView.setAdapter(listViewAdapter);

        //当listViewAdapter中有内容时，隐藏no_record_layout控件
        if (listViewAdapter.getCount()>0){
            rootView.findViewById(R.id.no_record_layout).setVisibility(View.INVISIBLE);
        }

        textView.setText(DateUtil.getDateTitle(date));

        listView.setOnItemLongClickListener(this);
    }
//计算页面上半部分的总金额显示
    public int getTotalCost(){
        double totalCost = 0;
        for (RecordBean record: records){
            if (record.getType()==1){
                totalCost-= record.getAmount();
            }else {
                totalCost+= record.getAmount();
            }
        }
        return (int)totalCost;
    }

    //长按点击事件，id获取点击内容的uuid，从数据库中找到方便编辑，position为消费记录在列表中的位置
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        showDialog(position);//执行弹出对话框的操作
        return false;
    }

    //弹出对话框方法
    private void showDialog(int index){
        final String[] options={"删除","编辑"};//对话框中的内容
        final RecordBean selectedRecord = records.get(index);//获取点击账目在数据库中的数据
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());//创建弹出对话框
        builder.create();
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which==0){ //点击“删除”按钮时触发的事件
                    String uuid = selectedRecord.getUuid();
                    GlobalUtil.getInstance().databaseHelper.removeRecord(uuid);
                    reload();
                    GlobalUtil.getInstance().mainActivity.updateHeader();
                }else if (which==1){//点击“编辑”按钮时触发的事件
                    Intent intent = new Intent(getActivity(),AddRecordActivity.class);//打开账目编辑界面
                    Bundle extra = new Bundle();
                    extra.putSerializable("record",selectedRecord);
                    intent.putExtras(extra);
                    startActivityForResult(intent,1);
                }
            }
        });
        builder.setNegativeButton("取消",null);
        builder.create().show();
    }
}
