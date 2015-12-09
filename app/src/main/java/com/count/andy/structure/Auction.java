package com.count.andy.structure;

import android.graphics.Bitmap;

/**
 * Created by andy on 15-11-11.
 */
public class Auction {
    //public String specialAdPictureUrl;
    public int specialBidNum;
    public String name;
    public String endAt;
    public Bitmap bitmap;
    public String id;

    public Auction(Bitmap bitmap, int specialBidNum, String name, String endAt, String id) {
        this.bitmap = bitmap;
        this.specialBidNum = specialBidNum;
        this.name = name;
        this.endAt = endAt;
        this.id = id;
    }
}
