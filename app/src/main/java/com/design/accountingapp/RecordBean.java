package com.design.accountingapp;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by admin on 2019/6/10.
 */
public class RecordBean implements Serializable{
    /**
     *定义消费类别{支出、收入}
     */

    public static String TAG = "RecordBean";
    public enum RecordType{                     //消费类别(支出、收入)
        RECORD_TYPE_EXPENSE,RECORD_TYPE_INCOME
    }

    private double amount;//花费金额
    private RecordType type;//消费类别
    private String category;//消费类型
    private String remark;//备注
    private String date;//日期，格式如2019-06-01
    private long timeStamp;//时间
    private String uuid;//识别码

    /*构造方法
     * 分配UUID
     */
    public RecordBean(){
        uuid = UUID.randomUUID().toString();
        timeStamp = System.currentTimeMillis();//获取unix时间
        date = DateUtil.getFormattedDate();
    }

    public static String getTAG() {
        return TAG;
    }

    public static void setTAG(String TAG) {
        RecordBean.TAG = TAG;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getType() {
        if (this.type == RecordType.RECORD_TYPE_EXPENSE){
            return 1;
        }else {
            return 2;
        }
    }

    public void setType(int type) {
        if (type==1){
            this.type = RecordType.RECORD_TYPE_EXPENSE;
        }
        else {
            this.type = RecordType.RECORD_TYPE_INCOME;
        }
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
