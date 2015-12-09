package com.count.andy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.count.andy.artmall.R;
import com.count.andy.structure.FourGrid;
import com.count.andy.structure.FourList;

import java.util.ArrayList;

/**
 * Created by andy on 15-12-3.
 */
public class FourGridAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<FourGrid> fourGrids = new ArrayList<FourGrid>();

    public FourGridAdapter(Context context, ArrayList<FourGrid> fourGrids) {
        this.context = context;
        this.fourGrids = fourGrids;
    }

    @Override
    public int getCount() {
        return fourGrids.size();
    }

    @Override
    public Object getItem(int i) {
        return fourGrids.get(i);
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
            view = LayoutInflater.from(context).inflate(R.layout.widget_four_list_gridview_text, viewGroup, false);
            viewHolder.name = (TextView) view.findViewById(R.id.widget_four_list_gridView_text);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.name.setText(fourGrids.get(i).name);
        return view;
    }

    static class ViewHolder {
        private TextView name;
    }
}
