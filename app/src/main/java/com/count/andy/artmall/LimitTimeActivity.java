//市集->Slider->专场列表->单品|市集->市集精选->单品|市集->限时抢购->单品
package com.count.andy.artmall;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
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
import java.util.ArrayList;

/**
 * Created by andy on 15-11-30.
 */
public class LimitTimeActivity extends Activity{
    private static String URL1 = "http://www.artmall.com/app/findGoodDetailsInfo?goodsId=";
    private String name_v,desc_v = null;
    private ArrayList<String> urls = new ArrayList<String>();
    private TextView name;
    private WebView desc;
    private SliderLayout sliderLayout = null;
    private static String ID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.ID = getIntent().getStringExtra("ID");
        name_v = getIntent().getStringExtra("Name");
        setContentView(R.layout.activity_limit_time);
        findViewById();
        new downloaddata().execute();
    }

    private void findViewById() {
        name = (TextView) findViewById(R.id.activity_limit_time_name);
        desc = (WebView) findViewById(R.id.activity_limit_time_detail);
        sliderLayout = (SliderLayout) findViewById(R.id.activity_limit_time_slider);
    }

    private class downloaddata extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            String URL2 = URL1 + ID;
            //从服务端获取数据，并且解析
            HttpUtil httpUtil = new HttpUtil(URL2);
            String str = null;
            try {
                str = httpUtil.downloaddata();
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
                TextSliderView textSliderView = new TextSliderView(LimitTimeActivity.this);
                textSliderView.image(urls.get(i)).setScaleType(BaseSliderView.ScaleType.FitCenterCrop);
                sliderLayout.addSlider(textSliderView);
            }
            name.setText(name_v);
            desc.loadDataWithBaseURL(null, desc_v, "text/html", "utf-8", null);
        }
    }

    private void parseJson(String str) {
        try {
            JSONObject jsonObjs = new JSONObject(str).getJSONObject("data");
            JSONObject jsonObj1 = jsonObjs.getJSONObject("goodsDetails");
            JSONArray jsonArray = jsonObjs.getJSONArray("picList");

            desc_v = jsonObj1.getString("descs");
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
