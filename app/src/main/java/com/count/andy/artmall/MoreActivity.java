//拍卖->更多专场
package com.count.andy.artmall;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import android.widget.TextView;

/**
 * Created by andy on 15-11-20.
 */
public class MoreActivity extends FragmentActivity {
    private FragmentTabHost tabHost = null;
    private View view = null;
    private android.app.ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar = this.getActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setCustomView(R.layout.title_more);
        setContentView(R.layout.activity_more);

        tabHost = (FragmentTabHost) findViewById(R.id.more_tabhost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.more_tabcontent);

        view = getIdicatorView("正在竞拍", R.layout.tab_auctioning);
        tabHost.addTab(tabHost.newTabSpec("autioning").setIndicator(view), AuctioningFragment.class, null);

        view = getIdicatorView("往期回顾", R.layout.tab_auctioned);
        tabHost.addTab(tabHost.newTabSpec("autioned").setIndicator(view), AuctionedFragment.class, null);
    }

    private View getIdicatorView(String name, int layoutId) {
        View v = getLayoutInflater().inflate(layoutId, null);
        TextView tv = (TextView) v.findViewById(R.id.tab_text);
        tv.setText(name);
        return v;
    }
}
