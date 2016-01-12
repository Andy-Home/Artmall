//艺站
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
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.ListView;

import com.count.andy.adapter.ArtStageAdapter;
import com.count.andy.network.HttpUtil;
import com.count.andy.network.GetBitMap;
import com.count.andy.structure.ArtStage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by andy on 15-11-16.
 */
public class ArtStageFragment extends Fragment {
    private static final String URL = "http://www.artmall.com/app/artsStory?page=";
    private ListView listView;
    private View loadMore;
    private View view;
    private android.app.ActionBar actionBar;
    private ArtStageAdapter artStageAdapter = null;
    private static int page = 1;
    private ArrayList<ArtStage> artStages = new ArrayList<ArtStage>();

    private Boolean flag = false, flag1 = true;
    private int Date_Numeber = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        actionBar = getActivity().getActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setCustomView(R.layout.title_art_stage);
        view = inflater.inflate(R.layout.fragment_art_stage, container, false);
        loadMore = LayoutInflater.from(this.getActivity()).inflate(R.layout.widget_loadmore, null);
        listView = (ListView) view.findViewById(R.id.art_stage_listView);
        listView.addFooterView(loadMore);
        new downloaddata().execute();
        listView.setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                if (flag && scrollState == SCROLL_STATE_IDLE) {
                    if (Date_Numeber != artStageAdapter.getCount()) {
                        loadMore.setVisibility(View.VISIBLE);
                        Date_Numeber = artStageAdapter.getCount();
                        new downloaddata().execute();
                        artStageAdapter.notifyDataSetChanged();
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ContentActivity.class);
                intent.putExtra("ID", artStages.get(i).id);
                startActivity(intent);
            }
        });
        return view;
    }

    private class downloaddata extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
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
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (flag1) {
                flag1 = false;
                artStageAdapter = new ArtStageAdapter(ArtStageFragment.this.getActivity(), artStages);
                listView.setAdapter(artStageAdapter);
            }
        }
    }

    private void parseJson(String str) {
        try {
            //艺站数据
            JSONArray jsonObjs = new JSONObject(str).getJSONArray("data");
            for (int i = 0; i < jsonObjs.length(); i++) {
                JSONObject jsonObj = (JSONObject) jsonObjs.get(i);
                String id = jsonObj.getString("id");
                String name = jsonObj.getString("currentTitle");
                String createAt = jsonObj.getString("createAt");
                String arts = jsonObj.getString("arts");
                String storyTitle = jsonObj.getString("storyTitle");
                String storyPicUrl = jsonObj.getString("storyPicUrl");
                GetBitMap getpic = new GetBitMap();
                getpic.getBitmap(storyPicUrl, getActivity());
                Bitmap bitmap = null;
                while (bitmap == null) {
                    bitmap = getpic.getpicture();
                }
                createAt = dateChange(createAt);
                ArtStage artStage = new ArtStage(createAt, name, storyTitle, arts, bitmap, id);
                artStages.add(artStage);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String dateChange(String string) {
        String date = "";
        String[] dates = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sept", "Oct", "Nov", "Dec"};
        String month_string = "";
        String day = "";
        int flag2 = 0;
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == '-') {
                flag2++;
            } else if (string.charAt(i) == ' ') {
                break;
            } else if (flag2 == 1) {
                month_string += string.charAt(i);
            } else if (flag2 == 2) {
                day += string.charAt(i);
            }
        }
        int month = Integer.parseInt(month_string);
        date = dates[month - 1] + "." + day;
        return date;
    }
}
