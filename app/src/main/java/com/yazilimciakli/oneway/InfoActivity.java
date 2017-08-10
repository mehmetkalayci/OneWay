package com.yazilimciakli.oneway;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import com.yazilimciakli.oneway.Controls.ExpandableListviewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Admin on 9.08.2017.
 */

public class InfoActivity extends Activity {

    public List<String> list_parent;
    public ExpandableListviewAdapter expand_adapter;
    public HashMap<String, List<String>> list_child;
    public ExpandableListView expandlist_view;
    public List<String> gs_list;
    public List<String> fb_list;
    public List<String> tt_list;
    public int last_position = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        MainActivity.isBack = false;

        expandlist_view = (ExpandableListView)findViewById(R.id.expand_listview);

        Hazırla(); // expandablelistview içeriğini hazırlamak için

        // Adapter sınıfımızı oluşturmak için başlıklardan oluşan listimizi ve onlara bağlı olan elemanlarımızı oluşturmak için HaspMap türünü yolluyoruz
        expand_adapter = new ExpandableListviewAdapter(getApplicationContext(), list_parent, list_child);
        expandlist_view.setAdapter(expand_adapter);  // oluşturduğumuz adapter sınıfını set ediyoruz
        expandlist_view.setClickable(true);

        expandlist_view.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                String child_name = (String)expand_adapter.getChild(groupPosition, childPosition);
                //Toast.makeText(getApplicationContext(),"hey" + child_name, Toast.LENGTH_LONG).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(InfoActivity.this);
                builder.setMessage(child_name)
                        .setTitle("Mobilhanem Expandablelistview")
                        .setCancelable(false)
                        .setPositiveButton("TAMAM", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();


                return false;
            }
        });


		/*
		expandlist_view.setOnGroupExpandListener(new OnGroupExpandListener() {

			@Override
			public void onGroupExpand(int groupPosition) {

				if(last_position != -1 && last_position != groupPosition)
				{
					expandlist_view.collapseGroup(last_position);
				}
				last_position = groupPosition;

			}
		});
		*/

    }
    public void Hazırla()
    {
        list_parent = new ArrayList<String>();  // başlıklarımızı listemelek için oluşturduk
        list_child = new HashMap<String, List<String>>(); // başlıklara bağlı elemenları tutmak için oluşturduk

        list_parent.add("Nasıl Oynanır?");  // ilk başlığı giriyoruz
        list_parent.add("Kurallar Nelerdir?");   // ikinci başlığı giriyoruz
        list_parent.add("Yapımcılar");   // ikinci başlığı giriyoruz
        gs_list = new ArrayList<String>();  // ilk başlık için alt elemanları tanımlıyoruz
        gs_list.add("Oyun tek seferde şekli tamamlamaya yöneliktir.Ekranda çıkan görsel üzerinde herhangibir noktadan başlayıp tek seferde şekli tamamalamaya çalışmalısınız.");

        fb_list = new ArrayList<String>(); // ikinci başlık için alt elemanları tanımlıyoruz
        fb_list.add("Kural 1: Şekli tek seferde bitir");
        fb_list.add("Kural 2: Şekli çizerken elini kaldırma aksi taktide tüm yaptıkların silinir");
        fb_list.add("Kural 3: Sana verilen süre içinde şekli tamamla");
        fb_list.add("Kural 4: Sinirlendiğin zaman oyunu kapat ve belli bir süre sonra tekrar dene :)");
        fb_list.add("Kural 5: Ne olursa olsun telefonu kırma! :)");
        tt_list = new ArrayList<String>(); // ikinci başlık için alt elemanları tanımlıyoruz
        tt_list.add("Oyun bir internet projesi olan Yazılımcı Aklı tarafından hazırlanmıştır.");
        list_child.put(list_parent.get(0),gs_list); // ilk başlığımızı ve onların elemanlarını HashMap sınıfında tutuyoruz
        list_child.put(list_parent.get(1), fb_list); // ikinci başlığımızı ve onların elemanlarını HashMap sınıfında tutuyoruz
        list_child.put(list_parent.get(2), tt_list);

    }
    @Override
    public void onBackPressed() {
        MainActivity.isBack = true;
        startActivity(new Intent(InfoActivity.this, MainActivity.class));
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(SettingsActivity.getMusicStatus(this))
        {
            MainActivity.musicHelper.playMusic();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!MainActivity.isBack) {
            MainActivity.musicHelper.pauseMusic();
        }
    }

}