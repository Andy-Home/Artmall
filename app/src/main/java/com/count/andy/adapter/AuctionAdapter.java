package com.count.andy.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.count.andy.artmall.R;
import com.count.andy.structure.Auction;
import com.count.andy.structure.Single;
import com.count.andy.widget.HammerWidget;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cn.iwgang.countdownview.CountdownView;

/**
 * Created by andy on 15-11-27.
 */
public class AuctionAdapter extends BaseAdapter {
    private Context context;
    private static int flag = 0;
    private ArrayList<Auction> auctions = new ArrayList<Auction>();

    public AuctionAdapter(Context context, ArrayList<Auction> auctions) {
        this.context = context;
        this.auctions = auctions;
    }

    @Override
    public int getCount() {
        return auctions.size();
    }

    @Override
    public Object getItem(int position) {
        return auctions.get(position);
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
            view = LayoutInflater.from(context).inflate(R.layout.widget_auction, viewGroup, false);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.widget_auction_image);
            viewHolder.title = (TextView) view.findViewById(R.id.widget_auction_title);
            viewHolder.hammerWidget = (HammerWidget) view.findViewById(R.id.widget_auction_hammer);
            viewHolder.countdownView = (CountdownView) view.findViewById(R.id.widget_auction_time);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Log.d("test", "" + (flag++));
        viewHolder.imageView.setImageBitmap(auctions.get(i).bitmap);
        viewHolder.title.setText(auctions.get(i).name);
        viewHolder.hammerWidget.setTimes(auctions.get(i).specialBidNum + "次");
        try {
            viewHolder.countdownView.start(time(auctions.get(i).endAt));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return view;
    }

    static class ViewHolder {
        private ImageView imageView;
        private HammerWidget hammerWidget;
        private TextView title;
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
