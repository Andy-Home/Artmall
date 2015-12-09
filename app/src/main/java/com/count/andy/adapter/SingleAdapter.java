package com.count.andy.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.count.andy.artmall.CenterActivity;
import com.count.andy.artmall.R;
import com.count.andy.structure.Single;
import com.count.andy.widget.HammerWidget;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cn.iwgang.countdownview.CountdownView;

/**
 * Created by andy on 15-11-11.
 */
public class SingleAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Single> singles = new ArrayList<Single>();
    private static int flag = 0;

    public SingleAdapter(Context context, ArrayList<Single> singles) {
        this.context = context;
        this.singles = singles;
    }

    @Override
    public int getCount() {
        return singles.size();
    }

    @Override
    public Object getItem(int position) {
        return singles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.widget_single, viewGroup, false);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.widget_single_image);
            viewHolder.title = (TextView) view.findViewById(R.id.widget_single_title);
            viewHolder.price = (TextView) view.findViewById(R.id.widget_single_price);
            viewHolder.hammerWidget = (HammerWidget) view.findViewById(R.id.widget_single_hammer);
            viewHolder.countdownView = (CountdownView) view.findViewById(R.id.widget_single_time);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.imageView.setImageBitmap(singles.get(i).bitmap);
        viewHolder.title.setText(singles.get(i).name);
        viewHolder.price.setText("￥" + singles.get(i).currentPrice);
        viewHolder.hammerWidget.setTimes(singles.get(i).rebidStatus + "次");
        try {
            viewHolder.countdownView.start(time(singles.get(i).endAt));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return view;
    }

    static class ViewHolder {
        private ImageView imageView;
        private TextView title, price;
        private HammerWidget hammerWidget;
        private CountdownView countdownView;
    }

    public long time(String endtime) throws ParseException {
        //获取系统时间
        Date current = new Date();

        SimpleDateFormat sDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sDate.parse(endtime);
        //date转成毫秒
        long beginTime = date.getTime()- current.getTime();
        return beginTime;
    }

}
