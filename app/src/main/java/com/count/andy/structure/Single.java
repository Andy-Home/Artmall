package com.count.andy.structure;

import android.graphics.Bitmap;

/**
 * Created by andy on 15-11-11.
 */
public class Single {
    public Double currentPrice;
    public String endAt;
    public int rebidStatus;
    public String name, id;
    public Bitmap bitmap;

    public Single(Double currentPrice, String endAt, int rebidStatus, String name, Bitmap bitmap, String id){
        this.currentPrice = currentPrice;
        this.endAt = endAt;
        this.rebidStatus = rebidStatus;
        this.name = name;
        this.bitmap = bitmap;
        this.id = id;
    }
}
