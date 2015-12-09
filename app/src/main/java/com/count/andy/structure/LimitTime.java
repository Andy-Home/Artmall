package com.count.andy.structure;

import android.graphics.Bitmap;

/**
 * Created by andy on 15-11-14.
 */
public class LimitTime {
    public Bitmap bitmap;
    public Double price;
    public String name, author, id;

    public LimitTime(Bitmap bitmap, Double price, String name, String author, String id) {
        this.bitmap = bitmap;
        this.price = price;
        this.name = name;
        this.author = author;
        this.id = id;
    }
}
