//拍卖->拍卖专场->专场列表
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

import com.count.andy.adapter.PersonAdapter;
import com.count.andy.network.HttpUtil;
import com.count.andy.network.GetBitMap;
import com.count.andy.structure.Person;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by andy on 15-11-18.
 */
public class PersonActivity extends Activity implements AdapterView.OnItemClickListener {
    private static String URL = "http://www.artmall.com/app/findSpecialAuctions?specialTopicsId=";
    private Boolean flag = false, flag1 = true;
    private int Date_Numeber = 0;
    private static int page = 1;
    private static String ID = "";
    private ArrayList<Person> persons = new ArrayList<Person>();
    private android.app.ActionBar actionBar;
    private ListView listView = null;
    private View loadMore;
    private PersonAdapter personAdapter = null;

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

        setContentView(R.layout.activity_person);
        loadMore = LayoutInflater.from(this).inflate(R.layout.widget_loadmore, null);
        listView = (ListView) findViewById(R.id.person_listView);
        listView.addFooterView(loadMore);
        new downloaddata().execute();
        listView.setOnItemClickListener(this);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                if (flag && scrollState == SCROLL_STATE_IDLE) {
                    if (Date_Numeber != personAdapter.getCount()) {
                        loadMore.setVisibility(View.VISIBLE);
                        Date_Numeber = personAdapter.getCount();
                        new downloaddata().execute();
                        personAdapter.notifyDataSetChanged();
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
        Intent intent = new Intent(PersonActivity.this, MerchandiseActivity.class);
        intent.putExtra("ID", persons.get(i).id);
        startActivity(intent);
    }

    private class downloaddata extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            //从服务端获取数据，并且解析
            if (personAdapter == null) {
                String URL1 = URL + ID + "&page=" + page;
                page++;
                HttpUtil httpUtil = new HttpUtil(URL1, PersonActivity.this);
                String str = null;
                try {
                    httpUtil.downloaddata();
                    while (str == null) {
                        while (str == null) {
                            str = httpUtil.getString();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d(" ", "Unable to retrieve web page. URL may be invalid.");
                }
                parseJson(str);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (flag1) {
                flag1 = false;
                personAdapter = new PersonAdapter(PersonActivity.this, persons);
                listView.setAdapter(personAdapter);
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
                String endAt = jsonObj.getString("endAt");
                Double currentPrice = jsonObj.getDouble("currentPrice");
                Integer auctionTimes = jsonObj.getInt("auctionTimes");
                String pictureUrl = jsonObj.getString("pictureUrl");
                String id = jsonObj.getString("id");
                GetBitMap getpic = new GetBitMap();
                getpic.getBitmap(pictureUrl, PersonActivity.this);
                Bitmap bitmap = null;
                while (bitmap == null) {
                    bitmap = getpic.getpicture();
                }
                Person person = new Person(bitmap, name, auctionTimes, currentPrice, endAt, id);
                persons.add(person);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
