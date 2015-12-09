//市集->Slider->专场列表
package com.count.andy.artmall;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.count.andy.adapter.FairHeadAdapter;
import com.count.andy.network.HttpUtil;
import com.count.andy.network.GetBitMap;
import com.count.andy.structure.Person;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by andy on 15-11-27.
 */
public class FairHeadActivity extends Activity implements AdapterView.OnItemClickListener{
    private static String URL = "http://www.artmall.com/app/findSpecialGoods?specialTopicsId=";
    private Boolean flag = false;
    private int Date_Numeber = 0;
    private static int page = 1;
    private static String ID = "";
    private ArrayList<Person> persons = new ArrayList<Person>();
    private android.app.ActionBar actionBar;
    private ListView listView = null;
    private View loadMore;
    private FairHeadAdapter fairHeadAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取上级传值
        this.ID = getIntent().getStringExtra("ID");
        String name = getIntent().getStringExtra("Name");
        //设置ActionBar
        actionBar = this.getActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setCustomView(R.layout.title_person);
        TextView textView;
        textView = (TextView) findViewById(R.id.title_person);
        textView.setText(name);

        setContentView(R.layout.activity_fair_head);
        loadMore = LayoutInflater.from(this).inflate(R.layout.widget_loadmore, null);
        listView = (ListView) findViewById(R.id.fair_head_listView);
        listView.addFooterView(loadMore);
        new downloaddata().execute();
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                if (flag && scrollState == SCROLL_STATE_IDLE) {
                    if (Date_Numeber == fairHeadAdapter.getCount()) {
                        loadMore.setVisibility(View.VISIBLE);
                        Date_Numeber = fairHeadAdapter.getCount();
                        new downloaddata().execute();
                        loadMore.setVisibility(View.GONE);
                        loadMore.setPadding(0, -loadMore.getHeight(), 0, 0);
                    }

                }
            }

            @Override
            public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (totalItemCount != 0) {
                    if (firstVisibleItem + visibleItemCount == totalItemCount) {
                        flag = true;
                    } else {
                        flag = false;
                    }
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(FairHeadActivity.this , LimitTimeActivity.class);
        intent.putExtra("ID", persons.get(i).id);
        intent.putExtra("Name", persons.get(i).name);
        startActivity(intent);
    }

    private class downloaddata extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            //从服务端获取数据，并且解析
            String URL1 = URL + ID + "&page=" + page;
            page++;
            HttpUtil httpUtil = new HttpUtil(URL1);
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
            if (fairHeadAdapter == null) {
                fairHeadAdapter = new FairHeadAdapter(FairHeadActivity.this, persons);
                listView.setAdapter(fairHeadAdapter);
            } else {
                fairHeadAdapter.notifyDataSetChanged();
            }
        }
    }


    private void parseJson(String str) {
        try {
            //艺站数据
            JSONArray jsonObjs = new JSONObject(str).getJSONArray("data");
            for (int i = 0; i < jsonObjs.length(); i++) {
                JSONObject jsonObj = (JSONObject) jsonObjs.get(i);
                String name = jsonObj.getString("name");
                String id = jsonObj.getString("id");
                Double sellingPrice = jsonObj.getDouble("sellingPrice");
                String pictureUrl = jsonObj.getString("pictureUrl");
                Bitmap bitmap = GetBitMap.getBitmap(pictureUrl);
                Person person = new Person(bitmap, name, null, sellingPrice, null, id);
                persons.add(person);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
