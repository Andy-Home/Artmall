package com.count.andy.adapter;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.count.andy.artmall.R;
import com.count.andy.network.GetBitMap;
import com.count.andy.structure.Person;
import com.count.andy.structure.Single;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by andy on 15-11-27.
 */
public class FairHeadAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Person> persons = new ArrayList<Person>();

    public FairHeadAdapter(Context context, ArrayList<Person> persons) {
        this.persons = persons;
        this.context = context;
    }

    @Override
    public int getCount() {
        return persons.size();
    }

    @Override
    public Object getItem(int i) {
        return persons.get(i);
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
            view = LayoutInflater.from(context).inflate(R.layout.widget_fair_head, viewGroup, false);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.widget_fair_head_image);
            viewHolder.name = (TextView) view.findViewById(R.id.widget_fair_head_name);
            viewHolder.price = (TextView) view.findViewById(R.id.widget_fair_head_price);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        try {
            GetBitMap.getBitmap(persons.get(i).bitmap, context, viewHolder.imageView);
        } catch (IOException e) {
            e.printStackTrace();
        }
        viewHolder.name.setText(persons.get(i).name);
        viewHolder.price.setText("￥" + persons.get(i).price);
        return view;
    }

    static class ViewHolder {
        private ImageView imageView;
        private TextView name, price;
    }
}
