package com.count.andy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.count.andy.artmall.R;
import com.count.andy.structure.Auctioning;
import com.count.andy.structure.Person;

import java.util.ArrayList;

/**
 * Created by andy on 15-11-20.
 */
public class AuctioningAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Auctioning> auctionings = new ArrayList<Auctioning>();

    public AuctioningAdapter(Context context, ArrayList<Auctioning> auctionings) {
        this.auctionings = auctionings;
        this.context = context;
    }

    @Override
    public int getCount() {
        return auctionings.size();
    }

    @Override
    public Object getItem(int i) {
        return auctionings.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.widget_auctioning, viewGroup, false);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.widget_auctioning_image);
            viewHolder.name = (TextView) view.findViewById(R.id.widget_auctioning_name);
            viewHolder.detail = (TextView) view.findViewById(R.id.widget_auctioning_introduce);
            viewHolder.time = (TextView) view.findViewById(R.id.widget_auctioning_time);
            viewHolder.up = (ImageView) view.findViewById(R.id.widget_auctioning_up);
            viewHolder.down = (ImageView) view.findViewById(R.id.widget_auctioning_down);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.imageView.setImageBitmap(auctionings.get(i).bitmap);
        viewHolder.name.setText(auctionings.get(i).name);
        viewHolder.detail.setText(auctionings.get(i).detail);
        viewHolder.time.setText(auctionings.get(i).time);
        viewHolder.up = (ImageView) view.findViewById(R.id.widget_auctioning_up);
        viewHolder.down = (ImageView) view.findViewById(R.id.widget_auctioning_down);
        viewHolder.up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.detail.setMaxLines(3);
                viewHolder.detail.requestLayout();
                viewHolder.up.setVisibility(View.GONE);
                viewHolder.down.setVisibility(View.VISIBLE);
            }
        });
        viewHolder.down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.detail.setMaxLines(Integer.MAX_VALUE);
                viewHolder.detail.requestLayout();
                viewHolder.down.setVisibility(View.GONE);
                viewHolder.up.setVisibility(View.VISIBLE);
            }
        });
        return view;
    }

    static class ViewHolder {
        private ImageView up, down;
        private ImageView imageView;
        private TextView name, time, detail;
    }
}
