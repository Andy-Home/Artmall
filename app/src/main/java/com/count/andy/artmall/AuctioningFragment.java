//拍卖->更多专场->正在竞拍
package com.count.andy.artmall;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.count.andy.adapter.AuctioningAdapter;
import com.count.andy.network.HttpUtil;
import com.count.andy.network.GetBitMap;
import com.count.andy.structure.Auctioning;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by andy on 15-11-20.
 */
public class AuctioningFragment extends Fragment implements AbsListView.OnScrollListener {
    private View view;
    private ListView listView;
    private View loadMore;
    private AuctioningAdapter auctioningAdapter = null;

    private ArrayList<Auctioning> auctionings = new ArrayList<Auctioning>();
    private static final String URL = "http://www.artmall.com/app/findspecialList?auctionType=1&page=";
    private static int page = 1;
    private Boolean flag = false, flag1 = true;
    private int Date_Numeber = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_auctioning, container, false);
        loadMore = LayoutInflater.from(this.getActivity()).inflate(R.layout.widget_loadmore, null);
        listView = (ListView) view.findViewById(R.id.fragment_auctioning_listView);
        listView.addFooterView(loadMore);
        new downloaddata().execute();
        listView.setOnScrollListener(this);
        return view;
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
        if (flag && scrollState == SCROLL_STATE_IDLE) {
            if (Date_Numeber != auctioningAdapter.getCount()) {
                loadMore.setVisibility(View.VISIBLE);
                Date_Numeber = auctioningAdapter.getCount();
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

    private class downloaddata extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            if (auctioningAdapter == null) {
                //从服务端获取数据，并且解析
                String URL1 = URL + page;
                page++;
                HttpUtil httpUtil = new HttpUtil(URL1, getActivity());
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
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (auctioningAdapter == null) {
                auctioningAdapter = new AuctioningAdapter(AuctioningFragment.this.getActivity(), auctionings);
                listView.setAdapter(auctioningAdapter);
            } else {
                auctioningAdapter.notifyDataSetChanged();
            }
        }
    }

    private void parseJson(String str) {
        try {
            JSONArray jsonObjs = new JSONObject(str).getJSONArray("data");
            for (int i = 0; i < jsonObjs.length(); i++) {
                JSONObject jsonObj = (JSONObject) jsonObjs.get(i);
                String name = jsonObj.getString("name");
                String description = jsonObj.getString("description");
                String endAt = jsonObj.getString("endAt");
                String specialAdPictureUrl = jsonObj.getString("specialAdPictureUrl");

                Auctioning auctioning = new Auctioning(specialAdPictureUrl, name, endAt, description);
                auctionings.add(auctioning);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
