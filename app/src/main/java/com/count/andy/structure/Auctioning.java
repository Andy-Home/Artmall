package com.count.andy.structure;

import android.graphics.Bitmap;

/**
 * Created by andy on 15-11-20.
 */
public class Auctioning {
    public String name, time, detail;
    public String bitmap;

    public Auctioning(String bitmap, String name, String time, String detail) {
        this.bitmap = bitmap;
        this.name = name;
        this.time = time;
        this.detail = detail;
    }
}
