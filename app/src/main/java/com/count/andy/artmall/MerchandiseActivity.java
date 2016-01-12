//拍卖->单品推荐->单品|拍卖->拍卖专场->专场列表->单品
package com.count.andy.artmall;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;

import com.count.andy.network.HttpUtil;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by andy on 15-11-18.
 */
public class MerchandiseActivity extends Activity {
    private static String URL1 = "http://www.artmall.com/app/findAuctionInfo?auId=";
    private String name_v, endAt_v, desc_v = null;
    private Double startPrice_v, currentPrice_v;
    private ArrayList<String> urls = new ArrayList<String>();
    private TextView name, endAt, startPrice, currentPrice;
    private WebView desc;
    private SliderLayout sliderLayout;
    private static String ID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.ID = getIntent().getStringExtra("ID");
        setContentView(R.layout.activity_merchandise);
        findViewById();
        new downloaddata().execute();
    }

    private void findViewById() {
        name = (TextView) findViewById(R.id.activity_merchandise_name);
        endAt = (TextView) findViewById(R.id.activity_merchandise_endtime);
        desc = (WebView) findViewById(R.id.activity_merchandise_detail);
        startPrice = (TextView) findViewById(R.id.activity_merchandise_start_price);
        currentPrice = (TextView) findViewById(R.id.activity_merchandise_current_price);
        sliderLayout = (SliderLayout) findViewById(R.id.activity_merchandise_slider);
    }

    private class downloaddata extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            String URL2 = URL1 + ID;
            //从服务端获取数据，并且解析
            HttpUtil httpUtil = new HttpUtil(URL2, MerchandiseActivity.this);
            String str = null;
            try {
                httpUtil.downloaddata();
                while (str == null) {
                    str = httpUtil.getString();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(" ", "Unable to retrieve web page. URL may be invalid.");
            }
            parseJson(str);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            for (int i = 0; i < urls.size(); i++) {
                TextSliderView textSliderView = new TextSliderView(MerchandiseActivity.this);
                textSliderView.image(urls.get(i)).setScaleType(BaseSliderView.ScaleType.FitCenterCrop);
                sliderLayout.addSlider(textSliderView);
            }
            name.setText(name_v);
            endAt.setText(endAt_v);
            desc.loadDataWithBaseURL(null, desc_v, "text/html", "utf-8", null);
            startPrice.setText("" + startPrice_v);
            currentPrice.setText("" + currentPrice_v);
        }
    }

    private void parseJson(String str) {
        try {
            JSONObject jsonObjs = new JSONObject(str).getJSONObject("data");
            name_v = jsonObjs.getString("name");
            endAt_v = jsonObjs.getString("endAt");
            desc_v = jsonObjs.getString("desc");
            currentPrice_v = jsonObjs.getDouble("currentPrice");
            startPrice_v = jsonObjs.getDouble("startPrice");
            JSONArray jsonArray = jsonObjs.getJSONArray("auctionPicList");
            for (int i = 0; i < jsonObjs.length(); i++) {
                JSONObject jsonObj = (JSONObject) jsonArray.get(i);
                String path = jsonObj.getString("pricturepath");
                urls.add(path);
            }
        } catch (JSONException e1) {
            e1.printStackTrace();
        }

    }
}
