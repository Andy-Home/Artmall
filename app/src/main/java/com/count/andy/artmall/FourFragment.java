package com.count.andy.artmall;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.count.andy.adapter.FourAdapter;
import com.count.andy.network.GetBitMap;
import com.count.andy.network.HttpUtil;
import com.count.andy.structure.Choiceness;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by andy on 15-12-3.
 */
public class FourFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private View loadMore;
    private FourAdapter fourAdapter = null;
    private ArrayList<Choiceness> fours = new ArrayList<Choiceness>();
    private android.app.ActionBar actionBar;
    private static int page = 1;
    private String URL = "http://www.artmall.com/app/listGoodsByChildCategories?goodsType=";
    private String ID = "";
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Looper looper = Looper.getMainLooper();
        Handler handler = new Handler(looper);
        handler.post(new Runnable() {
            public void run() {
                try {
                    Class.forName("android.os.AsyncTask");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //setActionBar();
        this.ID = getActivity().getIntent().getStringExtra("ID");
        view = inflater.inflate(R.layout.viewpager_four, container, false);
        loadMore = LayoutInflater.from(getActivity()).inflate(R.layout.widget_loadmore, null);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.activity_four_recyclerView);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        new downloaddata().execute();
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            Boolean isSlidtoLast = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int[] a = null;
                StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
                if (newState == RecyclerView.SCROLL_STATE_SETTLING) {
                    int[] lastItem = manager.findLastCompletelyVisibleItemPositions(a);
                    int totalItem = manager.getItemCount();
                    if (lastItem[0] == totalItem - 1 && isSlidtoLast) {
                        new downloaddata().execute();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    isSlidtoLast = true;
                } else {
                    isSlidtoLast = false;
                }
            }
        });
        return view;
    }

    private void setActionBar() {
        this.ID = getActivity().getIntent().getStringExtra("ID");
        String name = getActivity().getIntent().getStringExtra("Name");
        actionBar = getActivity().getActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setCustomView(R.layout.title_person);
        TextView textView;
        textView = (TextView) view.findViewById(R.id.title_person);
        textView.setText(name);
    }

    private class downloaddata extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            //从服务端获取数据，并且解析
            if (fourAdapter == null) {
                String URL1 = URL + ID + "&page=" + page;
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
            if (fourAdapter == null) {
                fourAdapter = new FourAdapter(getActivity(), fours);
                mRecyclerView.setAdapter(fourAdapter);
            } else {
                fourAdapter.notifyDataSetChanged();
            }
            fourAdapter.SetOnItemClickListener(new FourAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    Intent intent = new Intent(getActivity(), LimitTimeActivity.class);
                    intent.putExtra("ID", fours.get(position).id);
                    intent.putExtra("Name", fours.get(position).name);
                    startActivity(intent);
                }
            });
        }
    }


    private void parseJson(String str) {
        try {
            JSONArray jsonObjs = new JSONObject(str).getJSONArray("data");
            for (int i = 0; i < jsonObjs.length(); i++) {
                JSONObject jsonObj = (JSONObject) jsonObjs.get(i);
                String name = jsonObj.getString("name");
                Double originalPrice = jsonObj.getDouble("originalPrice");
                String pictureUrl = jsonObj.getString("pictureUrl");
                String id = jsonObj.getString("id");
                GetBitMap getpic = new GetBitMap();
                getpic.getBitmap(pictureUrl, getActivity());
                Bitmap bitmap = null;
                while (bitmap == null) {
                    bitmap = getpic.getpicture();
                }
                Choiceness four = new Choiceness(originalPrice, name, bitmap, id);
                fours.add(four);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
