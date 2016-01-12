package com.count.andy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.count.andy.artmall.R;
import com.count.andy.network.GetBitMap;
import com.count.andy.structure.LimitTime;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by andy on 15-11-14.
 */
public class LimitTimeAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<LimitTime> limitTimes = new ArrayList<LimitTime>();

    public LimitTimeAdapter(Context context, ArrayList<LimitTime> limitTimes) {
        this.context = context;
        this.limitTimes = limitTimes;
    }

    @Override
    public int getCount() {
        return limitTimes.size();
    }

    @Override
    public Object getItem(int i) {
        return limitTimes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.widget_limit_time, viewGroup, false);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.widget_limit_time_imageView);
            viewHolder.title = (TextView) view.findViewById(R.id.widget_limit_time_title);
            viewHolder.author = (TextView) view.findViewById(R.id.widget_limit_time_author);
            viewHolder.price = (TextView) view.findViewById(R.id.widget_limit_time_price);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        try {
            GetBitMap.getBitmap(limitTimes.get(i).bitmap, context, viewHolder.imageView);
        } catch (IOException e) {
            e.printStackTrace();
        }
        viewHolder.title.setText(limitTimes.get(i).name);
        viewHolder.author.setText(limitTimes.get(i).author);
        viewHolder.price.setText("￥" + limitTimes.get(i).price);
        return view;
    }

    static class ViewHolder {
        private ImageView imageView;
        private TextView title, author, price;
    }
}
