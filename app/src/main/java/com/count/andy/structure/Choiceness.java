package com.count.andy.structure;

import android.graphics.Bitmap;

import com.count.andy.network.GetBitMap;

/**
 * Created by andy on 15-11-14.
 */
public class Choiceness {
    public Double sellingPrice;
    public String name, id;
    public String bitmap;

    public Choiceness(Double sellingPrice, String name, String bitmap, String id) {
        this.sellingPrice = sellingPrice;
        this.name = name;
        this.bitmap = bitmap;
        this.id = id;
    }
}
