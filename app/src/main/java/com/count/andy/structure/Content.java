package com.count.andy.structure;

import android.graphics.Bitmap;

/**
 * Created by andy on 15-11-30.
 */
public class Content {
    public String name, artname, viewerNum, content_url;
    public String artBitmap, background_url;

    public Content(String name, String artname, String viewerNum, String artBitmap, String background_url, String content_url) {
        this.name = name;
        this.artname = artname;
        this.viewerNum = viewerNum;
        this.artBitmap = artBitmap;
        this.background_url = background_url;
        this.content_url = content_url;
    }
}
