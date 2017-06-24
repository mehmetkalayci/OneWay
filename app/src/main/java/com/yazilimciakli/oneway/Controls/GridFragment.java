package com.yazilimciakli.oneway.Controls;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.yazilimciakli.oneway.Database.DatabaseHandler;
import com.yazilimciakli.oneway.GameActivity;
import com.yazilimciakli.oneway.Level.Level;
import com.yazilimciakli.oneway.MainActivity;
import com.yazilimciakli.oneway.R;
import com.yazilimciakli.oneway.Utils.LevelHelper;

public class GridFragment extends Fragment {

    LevelAdapter levelAdapter;
    Integer pageNumber;

    public static GridFragment newInstance(LevelAdapter levelAdapter, int page) {
        GridFragment fragment = new GridFragment();
        fragment.levelAdapter = levelAdapter;
        fragment.pageNumber = page;
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_grid, container, false);
        GridView gridView = (GridView) view.findViewById(R.id.grid);
        gridView.setAdapter(levelAdapter);

        final LevelHelper levelHelper = new LevelHelper(getContext());
        final DatabaseHandler dbHandler = new DatabaseHandler(getContext());

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Level tempLevel = new Level();

                tempLevel = levelHelper.getLevel(position + ((pageNumber - 1) * 9));

                com.yazilimciakli.oneway.Database.Level level = dbHandler.getLevel(tempLevel.levelid);

                if (level != null || tempLevel.levelid == 1 ) {

                    Intent openLevelIntent = new Intent();
                    openLevelIntent.setClass(view.getContext(), GameActivity.class);
                    openLevelIntent.putExtra("levelId", position + ((pageNumber - 1) * 9));
                    startActivity(openLevelIntent);
                    MainActivity.isBack=true;
                    getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                    getActivity().finish();
                } else {
                    Toast.makeText(getContext(),""+dbHandler.getPoints(), Toast.LENGTH_SHORT).show();
                }

            }
        });


        return view;
    }
}