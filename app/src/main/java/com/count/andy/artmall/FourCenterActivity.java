//市集->中间四格->商品列表
package com.count.andy.artmall;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;

import com.count.andy.adapter.MainFragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by andy on 15-11-25.
 */
public class FourCenterActivity extends FragmentActivity {
    private ViewPager viewPager;
    private ArrayList<Fragment> listViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four);
        viewPager = (ViewPager) findViewById(R.id.activity_four_viewPager);
        listViews = new ArrayList<Fragment>();
        listViews.add(new FourFragment());
        listViews.add(new FourListFragment());
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        //通过fragment适配器把fragment添加入viewpager中
        viewPager.setAdapter(new MainFragmentPagerAdapter(fragmentManager, listViews));
        viewPager.setCurrentItem(0);
    }
}
