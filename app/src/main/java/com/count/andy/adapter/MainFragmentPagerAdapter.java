package com.count.andy.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
/**
 * Created by andy on 15-12-3.
 */


public class MainFragmentPagerAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> fragments;
    public MainFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
// TODO Auto-generated constructor stub
    }
    public MainFragmentPagerAdapter(FragmentManager fm,ArrayList<Fragment> fragments){
        super(fm);
        this.fragments = fragments;
    }
    /* (non-Javadoc)
     * @see android.support.v4.app.FragmentPagerAdapter#getItem(int)
     */
    @Override
    public Fragment getItem(int arg0) {
        return fragments.get(arg0);
    }
    /* (non-Javadoc)
     * @see android.support.v4.view.PagerAdapter#getCount()
     */
    @Override
    public int getCount() {
        return fragments.size();
    }
    @Override
    public int getItemPosition(Object object) {
// TODO Auto-generated method stub
        return super.getItemPosition(object);
    }
}
