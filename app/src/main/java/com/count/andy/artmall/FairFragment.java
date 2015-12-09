//市集
package com.count.andy.artmall;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;

import com.count.andy.adapter.ChoicenessAdapter;
import com.count.andy.adapter.LimitTimeAdapter;
import com.count.andy.network.HttpUtil;
import com.count.andy.network.GetBitMap;
import com.count.andy.structure.Choiceness;
import com.count.andy.structure.FairSlider;
import com.count.andy.structure.LimitTime;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by andy on 15-11-13.
 */
public class FairFragment extends Fragment implements View.OnClickListener {
    //SliderLayout在导入的library中
    private SliderLayout sliderLayout;
    private ListView listView;
    private GridView gridView;
    private Button button1, button2, button3, button4;
    private static LimitTimeAdapter limitTimeAdapter = null;
    private static ChoicenessAdapter choicenessAdapter = null;

    private static final String URL = "http://www.artmall.com/app/listGoodsIndex";
    private ArrayList<FairSlider> fairSliders = new ArrayList<FairSlider>();
    private ArrayList<LimitTime> limitTimes = new ArrayList<LimitTime>();
    private ArrayList<Choiceness> choicenesses = new ArrayList<Choiceness>();
    private View view;
    private android.app.ActionBar actionBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        actionBar = getActivity().getActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setCustomView(R.layout.title_auction);
        view = inflater.inflate(R.layout.fragment_fair, container, false);
        findViewById();
        onClickListener();
        new downloaddata().execute();
        return view;
    }

    private void onClickListener() {
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), LimitTimeActivity.class);
                intent.putExtra("ID", limitTimes.get(i).id);
                intent.putExtra("Name", limitTimes.get(i).name);
                startActivity(intent);
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), LimitTimeActivity.class);
                intent.putExtra("ID", choicenesses.get(i).id);
                intent.putExtra("Name", choicenesses.get(i).name);
                startActivity(intent);
            }
        });
    }

    private void findViewById() {
        sliderLayout = (SliderLayout) view.findViewById(R.id.fair1);
        listView = (ListView) view.findViewById(R.id.fair_listView);
        gridView = (GridView) view.findViewById(R.id.fair_gridView);
        button1 = (Button) view.findViewById(R.id.fair_button1);
        button2 = (Button) view.findViewById(R.id.fair_button2);
        button3 = (Button) view.findViewById(R.id.fair_button3);
        button4 = (Button) view.findViewById(R.id.fair_button4);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getActivity(), FourCenterActivity.class);
        switch (view.getId()) {
            case R.id.fair_button1:
                intent.putExtra("ID", "2");
                intent.putExtra("Name", "当代艺术");
                startActivity(intent);
                break;
            case R.id.fair_button2:
                intent.putExtra("ID", "3");
                intent.putExtra("Name", "匠人手作");
                startActivity(intent);
                break;
            case R.id.fair_button3:
                intent.putExtra("ID", "4");
                intent.putExtra("Name", "海外古董");
                startActivity(intent);
                break;
            case R.id.fair_button4:
                intent.putExtra("ID", "5");
                intent.putExtra("Name", "艺术衍生");
                startActivity(intent);
                break;
        }
    }

    //异步操作
    private class downloaddata extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            HttpUtil httpUtil = new HttpUtil(URL);
            String str = null;
            try {
                str = httpUtil.downloaddata();
            } catch (IOException e) {
                e.printStackTrace();
            }
            parseJson(str);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            for (int i = 0; i < fairSliders.size(); i++) {
                TextSliderView textSliderView = new TextSliderView(FairFragment.this.getActivity());
                textSliderView.image(fairSliders.get(i).path).setScaleType(BaseSliderView.ScaleType.Fit);
                textSliderView.getBundle().putString("ID", fairSliders.get(i).id);
                textSliderView.getBundle().putString("Name", fairSliders.get(i).name);
                textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                    @Override
                    public void onSliderClick(BaseSliderView slider) {
                        Intent intent = new Intent(getActivity(), FairHeadActivity.class);
                        intent.putExtra("ID", slider.getBundle().getString("ID"));
                        intent.putExtra("Name", slider.getBundle().getString("Name"));
                        startActivity(intent);
                    }
                });
                sliderLayout.addSlider(textSliderView);
            }
            limitTimeAdapter = new LimitTimeAdapter(FairFragment.this.getActivity(), limitTimes);
            choicenessAdapter = new ChoicenessAdapter(FairFragment.this.getActivity(), choicenesses);
            setListViewHeightBasedOnChildren(listView);
            setGridViewHeightBasedOnChildren(gridView);
            listView.setAdapter(limitTimeAdapter);
            gridView.setAdapter(choicenessAdapter);
        }
    }

    //Json解析
    private void parseJson(String str) {
        try {
            //SliderLayout数据
            JSONObject jsonObjs = new JSONObject(str).getJSONObject("data");
            String strs = jsonObjs.toString();
            JSONArray jsonObjs1 = new JSONObject(strs).getJSONArray("specialBannerList");
            for (int i = 0; i < jsonObjs1.length(); i++) {
                JSONObject jsonObj = (JSONObject) jsonObjs1.get(i);
                String bannerUrl = jsonObj.getString("bannerUrl");
                String linkUrl = jsonObj.getString("linkUrl");
                String subject = jsonObj.getString("subject");
                String id = linkUrl.substring(27, linkUrl.length());
                FairSlider fairSlider = new FairSlider(bannerUrl, id, subject);
                fairSliders.add(fairSlider);
            }
            //限时抢购数据
            JSONArray jsonObjs2 = new JSONObject(strs).getJSONArray("goodsLimitHostList");
            for (int i = 0; i < jsonObjs2.length(); i++) {
                JSONObject jsonObj = (JSONObject) jsonObjs2.get(i);
                Double originalPrice = jsonObj.getDouble("originalPrice");
                String name = jsonObj.getString("name");
                String author = jsonObj.getString("author");
                String id = jsonObj.getString("id");
                String pictureUrl = jsonObj.getString("pictureUrl");
                Bitmap bitmap = GetBitMap.getBitmap(pictureUrl);
                LimitTime limitTime = new LimitTime(bitmap, originalPrice, name, author, id);
                limitTimes.add(limitTime);
            }
            //市集精选数据
            JSONArray jsonObjs3 = new JSONObject(strs).getJSONArray("goodsList");
            for (int i = 0; i < jsonObjs3.length(); i++) {
                JSONObject jsonObj = (JSONObject) jsonObjs3.get(i);
                Double sellingPrice = jsonObj.getDouble("sellingPrice");
                String name = jsonObj.getString("name");
                String id = jsonObj.getString("id");
                String pictureUrl = jsonObj.getString("pictureUrl");
                Bitmap bitmap = GetBitMap.getBitmap(pictureUrl);
                Choiceness choiceness = new Choiceness(sellingPrice, name, bitmap, id);
                choicenesses.add(choiceness);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //ListView自适应
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        if (limitTimeAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < limitTimeAdapter.getCount(); i++) {
            View listItem = limitTimeAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight
                + (listView.getDividerHeight() * (limitTimeAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    //GridView自适应
    public static void setGridViewHeightBasedOnChildren(GridView gridView) {
        if (choicenessAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < choicenessAdapter.getCount(); i += 2) {
            View listItem = choicenessAdapter.getView(i, null, gridView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight;
        gridView.setLayoutParams(params);
    }
}
