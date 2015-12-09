package com.count.andy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.count.andy.artmall.R;
import com.count.andy.structure.ArtStage;

import java.util.ArrayList;

/**
 * Created by andy on 15-11-16.
 */
public class ArtStageAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ArtStage> artStages = null;
    public ArtStageAdapter(Context context, ArrayList<ArtStage> artStages){
        this.context =context;
        this.artStages = artStages;
    }

    @Override
    public int getCount() {
        return artStages.size();
    }

    @Override
    public Object getItem(int i) {
        return artStages.get(i);
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
            view = LayoutInflater.from(context).inflate(R.layout.widget_art_stage, viewGroup, false);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.widget_art_stage_image);
            viewHolder.title = (TextView) view.findViewById(R.id.widget_art_stage_title);
            viewHolder.date = (TextView) view.findViewById(R.id.widget_art_stage_date);
            viewHolder.name = (TextView) view.findViewById(R.id.widget_art_stage_name);
            viewHolder.author = (TextView) view.findViewById(R.id.widget_art_stage_author);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.imageView.setImageBitmap(artStages.get(i).bitmap);
        viewHolder.title.setText(artStages.get(i).title);
        viewHolder.date.setText(artStages.get(i).date);
        viewHolder.author.setText(artStages.get(i).author);
        viewHolder.name.setText(artStages.get(i).name);
        return view;
    }

    static class ViewHolder {
        private ImageView imageView;
        private TextView title, date, name, author;
    }
}
