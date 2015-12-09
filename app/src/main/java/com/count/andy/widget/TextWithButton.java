package com.count.andy.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.count.andy.artmall.R;

/**
 * Created by andy on 15-11-10.
 */
public class TextWithButton extends LinearLayout {

    public TextWithButton(Context context) {
        this(context, null);
    }

    public TextWithButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.widget_text_button, this, true);
    }
}
