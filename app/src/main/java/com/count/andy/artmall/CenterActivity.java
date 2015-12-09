//首页
package com.count.andy.artmall;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabWidget;
import android.widget.TextView;

/**
 * Created by andy on 15-11-17.
 */
public class CenterActivity extends FragmentActivity {
    private FragmentTabHost tabHost = null;
    private View view = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center);

        tabHost = (FragmentTabHost) findViewById(R.id.center_tabhost);
        tabHost.setup(this, getSupportFragmentManager(), R.id.center_tabcontent);

        view = getIdicatorView("拍卖", R.layout.tab_auction);
        tabHost.addTab(tabHost.newTabSpec("aution").setIndicator(view), AuctionFragment.class, null);

        view = getIdicatorView("市集", R.layout.tab_fair);
        tabHost.addTab(tabHost.newTabSpec("fair").setIndicator(view), FairFragment.class, null);

        view = getIdicatorView("艺站", R.layout.tab_art_stage);
        tabHost.addTab(tabHost.newTabSpec("art_stage").setIndicator(view), ArtStageFragment.class, null);

        view = getIdicatorView("我的", R.layout.tab_me);
        tabHost.addTab(tabHost.newTabSpec("me").setIndicator(view), MeFragment.class, null);
    }


    private View getIdicatorView(String name, int layoutId) {
        View v = getLayoutInflater().inflate(layoutId, null);
        TextView tv = (TextView) v.findViewById(R.id.tab_text);
        tv.setText(name);
        return v;
    }
}
