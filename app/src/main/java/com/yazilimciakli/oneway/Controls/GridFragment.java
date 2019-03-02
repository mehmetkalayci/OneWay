package com.yazilimciakli.oneway.Controls;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.yazilimciakli.oneway.Database.TableResponse.LevelsResponse;
import com.yazilimciakli.oneway.Dialog.CreditDialog;
import com.yazilimciakli.oneway.Dialog.HealtDialog;
import com.yazilimciakli.oneway.GameActivity;
import com.yazilimciakli.oneway.Level.Level;
import com.yazilimciakli.oneway.MainActivity;
import com.yazilimciakli.oneway.Object.DatabaseObject;
import com.yazilimciakli.oneway.R;
import com.yazilimciakli.oneway.Utils.LevelHelper;

public class GridFragment extends Fragment {

    LevelAdapter levelAdapter;
    Integer pageNumber;
    DatabaseObject databaseObject;

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
        databaseObject=DatabaseObject.newInstance(getContext());

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LevelsResponse tempLevel;

                tempLevel = databaseObject.getLevelsDB().getAll().get(position + ((pageNumber - 1) * 9));

                LevelsResponse level = databaseObject.getLevelsDB().get(tempLevel.getLevelid());
                int healt=databaseObject.getProfileDB().getAll().get(0).getHealt();

                if ((level != null || tempLevel.getLevelid() == 1) && healt > 0) {


                    Intent openLevelIntent = new Intent();
                    openLevelIntent.setClass(view.getContext(), GameActivity.class);
                    //openLevelIntent.putExtra("levelId", position + ((pageNumber - 1) * 9));
                    openLevelIntent.putExtra("levelId", tempLevel.getLevelid());
                    startActivity(openLevelIntent);
                    MainActivity.isBack = true;
                    getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                    getActivity().finish();
                } else if (healt == 0) {
                    HealtDialog creditDialog = new HealtDialog(getContext());
                    creditDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    creditDialog.setCancelable(true);
                    creditDialog.show();
                } else {

                    CreditDialog creditDialog = new CreditDialog(getContext());
                    creditDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    creditDialog.setCancelable(true);
                    creditDialog.show();
                }

            }
        });


        return view;
    }
}