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
public class AuctionWidget extends LinearLayout {
    private ImageView imageView;
    private TextView title;

    public AuctionWidget(Context context) {
        this(context, null);
    }

    public AuctionWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.widget_auction, this, true);
        imageView = (ImageView)view.findViewById(R.id.widget_auction_image);
        title = (TextView) view.findViewById(R.id.widget_auction_title);
    }

    public void setImageView(Bitmap bitmap){
        imageView.setImageBitmap(bitmap);
    }

    public void setTitle(String string){
        title.setText(string);
    }
}
