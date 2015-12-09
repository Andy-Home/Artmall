package com.count.andy.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.count.andy.artmall.R;

/**
 * Created by andy on 15-11-26.
 */
public class HammerWidget extends RelativeLayout{
    private ImageView imageView;
    private TextView times;
    public HammerWidget(Context context) {
        this(context, null);
    }

    public HammerWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.widget_hammer, this, true);
        imageView = (ImageView) view.findViewById(R.id.hammer);
        times = (TextView) view.findViewById(R.id.widget_hammer_times);
    }

    public void setTimes(String string){
        times.setText(string);
    }

}
