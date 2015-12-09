package com.count.andy.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.count.andy.artmall.R;

/**
 * Created by andy on 15-11-30.
 */
public class ContentWidget extends RelativeLayout {
    private ImageView imageView;
    private TextView name, num, art;
    private WebView content;
    public ContentWidget(Context context) {
        this(context, null);
    }

    public ContentWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.widget_content, this, true);
        imageView = (ImageView) view.findViewById(R.id.content_image);
        name = (TextView) view.findViewById(R.id.content_name);
        num = (TextView) view.findViewById(R.id.content_viewernum);
        art = (TextView) view.findViewById(R.id.content_artname);
        content = (WebView) view.findViewById(R.id.content);
    }

    public void setName(String string) {
        name.setText(string);
    }

    public void setViewerNum(String string) {
        num.setText(string);
    }

    public void setArtName(String string) {
        art.setText(string);
    }

    public void setImageView(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }

    public void setContent(String string) {
        content.loadUrl(string);
    }
}
