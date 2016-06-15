package com.equipments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.equipments.Utils.AndroidDatabaseManager;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;

/**
 * Created by Administrator on 09/06/2016.
 */
public class Main extends AppCompatActivity {
    SearchableSpinner spInstType;
    ArrayAdapter<String> instituteTypeAdapter;
    Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityviewpager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Equipments");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        ViewGroup tab = (ViewGroup) findViewById(R.id.tab);
        tab.addView(LayoutInflater.from(this).inflate(R.layout.tabs, tab, false));

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);

        FragmentPagerItems pages = new FragmentPagerItems(this);

        pages.add(FragmentPagerItem.of("Location", DataInput1.class));
        pages.add(FragmentPagerItem.of("Equipments 1", DataInput4.class));
        pages.add(FragmentPagerItem.of("Equipments 2", DataInput2.class));
        pages.add(FragmentPagerItem.of("Snap", DataInput3.class));


        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), pages);

        viewPager.setAdapter(adapter);
        viewPagerTab.setViewPager(viewPager);



    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Main.this, AndroidDatabaseManager.class));
        super.onBackPressed();
    }
}
