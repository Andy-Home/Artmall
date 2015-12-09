package com.count.andy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.count.andy.artmall.R;
import com.count.andy.structure.FourList;

import java.util.ArrayList;

/**
 * Created by andy on 15-12-3.
 */
public class FourListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<FourList> fourLists = new ArrayList<FourList>();

    public FourListAdapter(Context context, ArrayList<FourList> fourLists) {
        this.context = context;
        this.fourLists = fourLists;
    }

    @Override
    public int getCount() {
        return fourLists.size();
    }

    @Override
    public Object getItem(int i) {
        return fourLists.get(i);
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
            view = LayoutInflater.from(context).inflate(R.layout.widget_four_list, viewGroup, false);
            viewHolder.name = (TextView) view.findViewById(R.id.widget_four_list_text);
            viewHolder.gridView = (GridView) view.findViewById(R.id.widget_four_list_gridView);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.name.setText(fourLists.get(i).name);
        FourGridAdapter fourGridAdapter = new FourGridAdapter(context, fourLists.get(i).fourGrids);
        viewHolder.gridView.setAdapter(fourGridAdapter);
        return view;
    }

    static class ViewHolder {
        private TextView name;
        private GridView gridView;
    }
}
