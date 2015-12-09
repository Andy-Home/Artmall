package com.count.andy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.count.andy.artmall.R;
import com.count.andy.structure.Choiceness;

import java.util.ArrayList;

/**
 * Created by andy on 15-11-14.
 */
public class ChoicenessAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Choiceness> choicenesses = new ArrayList<Choiceness>();

    public ChoicenessAdapter(Context context, ArrayList<Choiceness> choicenesses) {
        this.context = context;
        this.choicenesses = choicenesses;
    }

    @Override
    public int getCount() {
        return choicenesses.size();
    }

    @Override
    public Object getItem(int i) {
        return choicenesses.get(i);
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
            view = LayoutInflater.from(context).inflate(R.layout.widget_choiceness, viewGroup, false);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.widget_choiceness_image);
            viewHolder.title = (TextView) view.findViewById(R.id.widget_choiceness_title);
            viewHolder.price = (TextView) view.findViewById(R.id.widget_choiceness_price);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.imageView.setImageBitmap(choicenesses.get(i).bitmap);
        viewHolder.title.setText(choicenesses.get(i).name);
        viewHolder.price.setText("￥" + choicenesses.get(i).sellingPrice);
        return view;
    }

    static class ViewHolder {
        private ImageView imageView;
        private TextView title, price;
    }
}
