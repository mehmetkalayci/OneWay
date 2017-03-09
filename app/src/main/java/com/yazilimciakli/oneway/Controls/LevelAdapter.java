package com.yazilimciakli.oneway.Controls;


import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yazilimciakli.oneway.Level.Level;
import com.yazilimciakli.oneway.R;

import java.util.List;


public class LevelAdapter extends BaseAdapter {

    Context context;
    Typeface typeface;
    List<Level> levelList;

    public LevelAdapter(Context context,List<Level> levelList) {
        this.context = context;
        this.levelList = levelList;
        typeface = Typeface.createFromAsset(context.getResources().getAssets(), "fonts/Atma.ttf");
    }

    @Override
    public int getCount() {
        return levelList.size();
    }

    @Override
    public Object getItem(int position) {
        return levelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView title;
        ImageView star1, star2, star3;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.level_item, parent, false);
        title = (TextView) itemView.findViewById(R.id.levelText);
        title.setTypeface(typeface);

        star1 = (ImageView) itemView.findViewById(R.id.star1);
        star2 = (ImageView) itemView.findViewById(R.id.star2);
        star3 = (ImageView) itemView.findViewById(R.id.star3);

        star1.setImageResource(R.drawable.ic_star_gold_48dp);
        star2.setImageResource(R.drawable.ic_star_gold_48dp);
        star3.setImageResource(R.drawable.ic_star_gold_48dp);

        title.setText(levelList.get(position).name);
        return itemView;
    }


}