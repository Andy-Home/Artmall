package com.count.andy.structure;

import android.graphics.Bitmap;

/**
 * Created by andy on 15-11-16.
 */
public class ArtStage {
    public String date, name, author, title, id;
    public String bitmap;

    public ArtStage(String date, String title, String name, String author, String bitmap, String id) {
        this.date = date;
        this.title =title;
        this.name = name;
        this.author = author;
        this.bitmap = bitmap;
        this.id = id;
    }
}
