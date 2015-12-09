package com.count.andy.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.count.andy.artmall.R;

/**
 * Created by andy on 15-11-10.
 */
public class SingleWidget extends LinearLayout {
    private ImageView imageView;
    private TextView title, price;
    public SingleWidget(Context context) {
        this(context, null);
    }

    public SingleWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.widget_single, this, true);
        imageView = (ImageView) view.findViewById(R.id.widget_single_image);
        title = (TextView) view.findViewById(R.id.widget_single_title);
        price = (TextView) view.findViewById(R.id.widget_single_price);
    }

    public void setImageView(Bitmap bitmap){
        imageView.setImageBitmap(bitmap);
    }

    public void setTitle(String string){
        title.setText(string);
    }

    public void setPrice(String string){
        price.setText(string);
    }
}
