package com.yazilimciakli.oneway.Controls;


import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yazilimciakli.oneway.Database.DatabaseHandler;
import com.yazilimciakli.oneway.Level.Level;
import com.yazilimciakli.oneway.R;

import java.util.List;


public class LevelAdapter extends BaseAdapter {

    Context context;
    Typeface typeface;
    List<Level> levelList;
    DatabaseHandler dbHandler;

    public LevelAdapter(Context context, List<Level> levelList) {
        this.context = context;
        this.levelList = levelList;
        this.dbHandler = new DatabaseHandler(context);
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
        ImageView star1, star2, star3, lock;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.layout_level_item, parent, false);
        title = (TextView) itemView.findViewById(R.id.levelText);
        title.setTypeface(typeface);

        star1 = (ImageView) itemView.findViewById(R.id.star1);
        star2 = (ImageView) itemView.findViewById(R.id.star2);
        star3 = (ImageView) itemView.findViewById(R.id.star3);
        lock = (ImageView) itemView.findViewById(R.id.lock);

        com.yazilimciakli.oneway.Database.Level tempLevel = dbHandler.getLevel(levelList.get(position).levelid);

        if (tempLevel != null || levelList.get(position).levelid == 1) {

            if (tempLevel == null || (tempLevel.getScore() == 0 && tempLevel.getElapsedTime() == 0)) {

                star1.setImageResource(R.drawable.ic_star_black_48dp);
                star2.setImageResource(R.drawable.ic_star_black_48dp);
                star3.setImageResource(R.drawable.ic_star_black_48dp);

            } else {

                if (tempLevel.getScore() == levelList.get(position).score) {
                    star1.setImageResource(R.drawable.ic_star_gold_48dp);
                    star2.setImageResource(R.drawable.ic_star_gold_48dp);
                    star3.setImageResource(R.drawable.ic_star_gold_48dp);
                } else if (tempLevel.getScore() == levelList.get(position).score / 2) {
                    star1.setImageResource(R.drawable.ic_star_gold_48dp);
                    star2.setImageResource(R.drawable.ic_star_gold_48dp);
                    star3.setImageResource(R.drawable.ic_star_black_48dp);
                } else {
                    star1.setImageResource(R.drawable.ic_star_gold_48dp);
                    star2.setImageResource(R.drawable.ic_star_black_48dp);
                    star3.setImageResource(R.drawable.ic_star_black_48dp);
                }

            }
        } else {
            star1.setVisibility(View.INVISIBLE);
            star2.setVisibility(View.INVISIBLE);
            star3.setVisibility(View.INVISIBLE);
            title.setVisibility(View.INVISIBLE);
            lock.setVisibility(View.VISIBLE);
        }

        title.setText(String.valueOf(levelList.get(position).levelid));
        return itemView;
    }


}