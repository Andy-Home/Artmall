package com.count.andy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.count.andy.artmall.R;
import com.count.andy.network.GetBitMap;
import com.count.andy.structure.ArtStage;
import com.count.andy.structure.Choiceness;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by andy on 15-11-25.
 */
public class FourAdapter extends RecyclerView.Adapter<FourAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Choiceness> fours = null;
    OnItemClickListener mItemClickListener;

    public FourAdapter(Context context, ArrayList<Choiceness> fours) {
        this.context = context;
        this.fours = fours;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.widget_four, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        try {
            GetBitMap.getBitmap(fours.get(i).bitmap, context, viewHolder.imageView);
        } catch (IOException e) {
            e.printStackTrace();
        }
        viewHolder.title.setText(fours.get(i).name);
        viewHolder.price.setText("￥" + fours.get(i).sellingPrice);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getItemCount() {
        return fours.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView imageView;
        private TextView title, price;

        public ViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.widget_four_image);
            title = (TextView) view.findViewById(R.id.widget_four_name);
            price = (TextView) view.findViewById(R.id.widget_four_price);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(view, getPosition());
            }
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view , int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}
