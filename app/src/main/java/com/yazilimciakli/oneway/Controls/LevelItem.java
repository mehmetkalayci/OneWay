package com.yazilimciakli.oneway.Controls;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yazilimciakli.oneway.R;

public class LevelItem extends BaseAdapter {

    Context cntx;
    String titles[];
    LayoutInflater inflater;

    public LevelItem(Context context, String[] title) {
        titles = title;
        cntx = context;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView title;
        ImageView str1, str2, str3;

        inflater = (LayoutInflater) cntx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.level_item, parent, false);
        title = (TextView) itemView.findViewById(R.id.levelText);

        str1 = (ImageView) itemView.findViewById(R.id.star1);
        str2 = (ImageView) itemView.findViewById(R.id.star2);
        str3 = (ImageView) itemView.findViewById(R.id.star3);

        str1.setImageResource(R.drawable.ic_star_gold_48dp);
        str2.setImageResource(R.drawable.ic_star_gold_48dp);
        title.setText(titles[position]);
        return itemView;
    }
}