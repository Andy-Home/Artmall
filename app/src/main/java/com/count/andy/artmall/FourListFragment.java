package com.count.andy.artmall;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;

import com.count.andy.adapter.AuctionAdapter;
import com.count.andy.adapter.FourListAdapter;
import com.count.andy.adapter.SingleAdapter;
import com.count.andy.network.GetBitMap;
import com.count.andy.network.HttpUtil;
import com.count.andy.structure.Auction;
import com.count.andy.structure.FourGrid;
import com.count.andy.structure.FourList;
import com.count.andy.structure.Single;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by andy on 15-12-3.
 */
public class FourListFragment extends Fragment {
    private static final String URL = "http://www.artmall.com/app/showGoodsCategories";
    private ArrayList<FourList> fourLists = new ArrayList<FourList>();
    private ListView listView;
    private static FourListAdapter mAdapter = null;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.viewpaget_four_list, container, false);
        findViewById();
        new downloaddata().execute();
        return view;
    }

    private void findViewById() {
        listView = (ListView) view.findViewById(R.id.viewpager_list);
    }

    private class downloaddata extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            //从服务端获取数据，并且解析
            if (mAdapter == null) {
                HttpUtil httpUtil = new HttpUtil(URL, getActivity());
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
            mAdapter = new FourListAdapter(FourListFragment.this.getActivity(), fourLists);
            setListViewHeightBasedOnChildren(listView);
            listView.setAdapter(mAdapter);
        }
    }

    private void parseJson(String str) {
        try {
            JSONArray jsonObjs = new JSONObject(str).getJSONArray("data");
            for (int i = 0; i < jsonObjs.length(); i++) {
                JSONObject jsonObjs1 = (JSONObject) jsonObjs.get(i);
                String parentsName = jsonObjs1.getString("name");
                JSONArray jsonObj1 = jsonObjs1.getJSONArray("goodsCategoriesList");
                ArrayList<FourGrid> fourGrids = new ArrayList<FourGrid>();
                for (int j = 0; j < jsonObj1.length(); j++) {
                    JSONObject jsonObj2 = (JSONObject) jsonObj1.get(i);
                    String id = jsonObj2.getString("id");
                    String name = jsonObj2.getString("name");
                    FourGrid fourGrid = new FourGrid(name, id);
                    fourGrids.add(fourGrid);
                }
                FourList fourList = new FourList(parentsName, fourGrids);
                fourLists.add(fourList);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        if (mAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < mAdapter.getCount(); i++) {
            View listItem = mAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight
                + (listView.getDividerHeight() * (mAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    //GridView自适应
    public static void setGridViewHeightBasedOnChildren(GridView gridView) {
        if (mAdapter == null) {
            return;
        }
        int totalHeight = 10;
        for (int i = 0; i < mAdapter.getCount(); i += 2) {
            View listItem = mAdapter.getView(i, null, gridView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight;
        gridView.setLayoutParams(params);
    }
}
