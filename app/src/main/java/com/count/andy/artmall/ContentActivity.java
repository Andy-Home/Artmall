//艺站->艺站列表->详细内容
package com.count.andy.artmall;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.count.andy.network.HttpUtil;
import com.count.andy.network.GetBitMap;
import com.count.andy.structure.Content;
import com.count.andy.widget.ContentWidget;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by andy on 15-11-30.
 */
public class ContentActivity extends Activity {
    private static String URL1 = "http://www.artmall.com/app/showArtsStoryInfo?id=";
    private Content content;
    private ContentWidget contentWidget;
    private ImageView imageView;
    private static String ID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.ID = getIntent().getStringExtra("ID");
        contentWidget = new ContentWidget(ContentActivity.this);
        setContentView(R.layout.activity_content);
        findViewById();
        new downloaddata().execute();
    }

    private void findViewById() {
        imageView = (ImageView) findViewById(R.id.activity_content_image);
        contentWidget = (ContentWidget) findViewById(R.id.activity_content);
    }

    private class downloaddata extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            String URL2 = URL1 + ID;
            //从服务端获取数据，并且解析
            HttpUtil httpUtil = new HttpUtil(URL2, ContentActivity.this);
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
            imageView.setImageBitmap(content.background_url);
            contentWidget.setImageView(content.artBitmap);
            contentWidget.setArtName(content.artname);
            contentWidget.setContent(content.content_url);
            contentWidget.setName(content.name);
            contentWidget.setViewerNum("浏览量：" + content.viewerNum + "次");
        }
    }

    private void parseJson(String str) {
        try {
            JSONObject jsonObjs = new JSONObject(str).getJSONObject("data");
            String artsUrl = jsonObjs.getString("artsUrl");
            String storyContentUrl = jsonObjs.getString("storyContentUrl");
            String storyPicUrl = jsonObjs.getString("storyPicUrl");
            String storyTitle = jsonObjs.getString("storyTitle");
            String arts = jsonObjs.getString("arts");
            String viewerNum = jsonObjs.getString("viewerNum");
            GetBitMap getpic = new GetBitMap();
            getpic.getBitmap(artsUrl, ContentActivity.this);
            Bitmap artBitmap = null;
            while (artBitmap == null) {
                getpic.getpicture();
            }
            getpic.getBitmap(storyPicUrl, ContentActivity.this);
            Bitmap backBitmap = null;
            while (backBitmap == null) {
                getpic.getpicture();
            }
            content = new Content(storyTitle, arts, viewerNum, artBitmap, backBitmap, storyContentUrl);
        } catch (JSONException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
