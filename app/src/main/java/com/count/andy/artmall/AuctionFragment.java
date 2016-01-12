//拍卖
package com.count.andy.artmall;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;

import com.count.andy.adapter.AuctionAdapter;
import com.count.andy.adapter.SingleAdapter;
import com.count.andy.network.HttpUtil;
import com.count.andy.network.GetBitMap;
import com.count.andy.structure.Auction;
import com.count.andy.structure.Single;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class AuctionFragment extends Fragment implements View.OnClickListener {
    private static final String URL = "http://www.artmall.com/app/listAuctionIndex";
    private ArrayList<Auction> auctions = new ArrayList<Auction>();
    private ArrayList<Single> singles = new ArrayList<Single>();
    private Button moreAuction;
    private GridView gridView;
    private ListView listView;
    private static SingleAdapter mAdapter = null;
    private static AuctionAdapter adapter = null;
    private View view;
    private android.app.ActionBar actionBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        actionBar = getActivity().getActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setCustomView(R.layout.title_auction);
        view = inflater.inflate(R.layout.fragment_auction, container, false);
        findViewById();
        onClickListener();
        new downloaddata().execute();
        return view;
    }

    private void onClickListener() {
        moreAuction.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), PersonActivity.class);
                intent.putExtra("ID", auctions.get(i).id);
                intent.putExtra("Name", auctions.get(i).name);
                startActivity(intent);
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), MerchandiseActivity.class);
                intent.putExtra("ID", singles.get(i).id);
                startActivity(intent);
            }
        });
    }

    private void findViewById() {
        listView = (ListView) view.findViewById(R.id.auction_listView);
        gridView = (GridView) view.findViewById(R.id.auction4);
        moreAuction = (Button) view.findViewById(R.id.auction_button);
    }

    //更多专场监听
    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.auction_button:
                intent = new Intent(getActivity(), MoreActivity.class);
                startActivity(intent);
                break;
        }
    }


    private class downloaddata extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            //从服务端获取数据，并且解析
            // if (adapter == null && mAdapter == null) {
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
            //}
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            adapter = new AuctionAdapter(AuctionFragment.this.getActivity(), auctions);
            mAdapter = new SingleAdapter(AuctionFragment.this.getActivity(), singles);
            setListViewHeightBasedOnChildren(listView);
            setGridViewHeightBasedOnChildren(gridView);
            gridView.setAdapter(mAdapter);
            listView.setAdapter(adapter);
        }
    }


    private void parseJson(String str) {
        try {
            //单品推荐数据
            JSONObject jsonObjs = new JSONObject(str).getJSONObject("data");
            String strs = jsonObjs.toString();
            JSONArray jsonObjs1 = new JSONObject(strs).getJSONArray("auctionList");
            for (int i = 0; i < jsonObjs1.length(); i++) {
                JSONObject jsonObj = (JSONObject) jsonObjs1.get(i);
                Double currentPrice = jsonObj.getDouble("currentPrice");
                String id = jsonObj.getString("id");
                int auctionTimes = jsonObj.getInt("auctionTimes");
                String name = jsonObj.getString("name");
                String endAt = jsonObj.getString("endAt");
                String pictureUrl = jsonObj.getString("pictureUrl");
                GetBitMap getpic = new GetBitMap();
                getpic.getBitmap(pictureUrl, getActivity());
                Bitmap bitmap = null;
                while (bitmap == null) {
                    bitmap = getpic.getpicture();
                }

                Single single = new Single(currentPrice, endAt, auctionTimes, name, bitmap, id);
                singles.add(single);
            }
            //拍卖专场数据
            JSONArray jsonObjs2 = new JSONObject(strs).getJSONArray("auctionSpecialList");
            for (int i = 0; i < jsonObjs2.length(); i++) {
                JSONObject jsonObj = (JSONObject) jsonObjs2.get(i);
                String id = jsonObj.getString("id");
                int specialBidNum = jsonObj.getInt("specialBidNum");
                String name = jsonObj.getString("name");
                String endAt = jsonObj.getString("endAt");
                String specialAdPictureUrl = jsonObj.getString("specialAdPictureUrl");
                GetBitMap getpic = new GetBitMap();
                getpic.getBitmap(specialAdPictureUrl, getActivity());
                Bitmap bitmap = null;
                while (bitmap == null) {
                    bitmap = getpic.getpicture();
                }
                Auction auction = new Auction(bitmap, specialBidNum, name, endAt, id);
                auctions.add(auction);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        if (adapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight
                + (listView.getDividerHeight() * (adapter.getCount() - 1));
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
