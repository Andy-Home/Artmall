package com.count.andy.structure;

import android.graphics.Bitmap;
import android.widget.TextView;

/**
 * Created by andy on 15-11-18.
 */
public class Person {
    public Bitmap bitmap;
    public String name, time, id;
    public Double price;
    public Integer auctiontime;
    public Person(Bitmap bitmap, String name, Integer auctiontime, Double price, String time, String id){
        this.bitmap = bitmap;
        this.name = name;
        this.auctiontime = auctiontime;
        this.price = price;
        this.time = time;
        this.id = id;
    }
}
