package com.yazilimciakli.oneway.Controls;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.yazilimciakli.oneway.GameActivity;
import com.yazilimciakli.oneway.R;

public class GridFragment extends Fragment {

    LevelAdapter levelAdapter;

    public static GridFragment newInstance(LevelAdapter levelAdapter) {
        GridFragment fragment = new GridFragment();
        fragment.levelAdapter = levelAdapter;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.grid, container, false);
        GridView gridView = (GridView) view.findViewById(R.id.grid);
        gridView.setAdapter(levelAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(view.getContext(), GameActivity.class);
                intent.putExtra("level", position);
                startActivity(intent);
            }
        });


        return view;
    }
}