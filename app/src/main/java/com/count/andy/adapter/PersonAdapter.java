package com.count.andy.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.count.andy.artmall.R;
import com.count.andy.network.GetBitMap;
import com.count.andy.structure.ArtStage;
import com.count.andy.structure.Person;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by andy on 15-11-18.
 */
public class PersonAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Person> persons = new ArrayList<Person>();

    public PersonAdapter(Context context, ArrayList<Person> persons) {
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
            view = LayoutInflater.from(context).inflate(R.layout.widget_person, viewGroup, false);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.widget_person_image);
            viewHolder.name = (TextView) view.findViewById(R.id.widget_person_name);
            viewHolder.auctiontime = (TextView) view.findViewById(R.id.widget_person_auctiontime);
            viewHolder.price = (TextView) view.findViewById(R.id.widget_person_current_price);
            viewHolder.time = (TextView) view.findViewById(R.id.widget_person_time);
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
        viewHolder.auctiontime.setText("出价次数："+persons.get(i).auctiontime+"次");
        String str = "<font color=\"@color/silver\">当前价：</font>"
                +"<font color=\"#FF4500\">￥"+ persons.get(i).price +"</font>";
        viewHolder.price.setText(Html.fromHtml(str));
        viewHolder.time.setText("距结束："+ persons.get(i).time);
        return view;
    }

    static class ViewHolder {
        private ImageView imageView;
        private TextView name, auctiontime, price, time;
    }
}
