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
    public String bitmap;

    public Single(Double currentPrice, String endAt, int rebidStatus, String name, String bitmap, String id) {
        this.currentPrice = currentPrice;
        this.endAt = endAt;
        this.rebidStatus = rebidStatus;
        this.name = name;
        this.bitmap = bitmap;
        this.id = id;
    }
}
