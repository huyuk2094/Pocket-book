package com.design.accountingapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.LinkedList;

/**
 *FragmentPagerAdapter可以用做许多Fragment的容器，维护各种Fragment*/
public class MainViewPagerAdapter extends FragmentPagerAdapter {

    LinkedList<MainFragment> fragments = new LinkedList<>();//Fragment列表
    LinkedList<String> dates = new LinkedList<>();//日期列表，通过日期初始化Fragment

    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);
        initFragments();
    }

    //初始化Fragment,有多少dates就初始化多少fragment
    private void initFragments(){
        dates = GlobalUtil.getInstance().databaseHelper.getAvaliableDate();//获取日期

        if (!dates.contains(DateUtil.getFormattedDate())){
            dates.addLast(DateUtil.getFormattedDate());
        }

        for (String date:dates){
            MainFragment fragment = new MainFragment(date);
            fragments.add(fragment);//加入到fragments列表中
        }
    }

    public void reload(){
        for (MainFragment fragment :
                fragments) {
            fragment.reload();
        }
    }

    //用于返回fragments列表的最后一个日期，以便主函数中setCurrentItem()方法获取它，实现用户打开，
    //软件后显示为最近的一天的消费日期
    public int getLatsIndex(){
        return fragments.size()-1;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }//返回fragments数量

    public String getDateStr(int index){
        return dates.get(index);
    }

    public int getTotalCost(int index){
        return fragments.get(index).getTotalCost();
    }
}
