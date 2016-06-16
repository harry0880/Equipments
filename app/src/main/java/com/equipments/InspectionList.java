package com.equipments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.equipments.Utils.Dbhandler;
import com.equipments.Utils.RVAdapter;

public class InspectionList extends AppCompatActivity {
    Dbhandler dbh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        RecyclerView lvItems = (RecyclerView) findViewById(R.id.rv);
        dbh=new Dbhandler(this);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        lvItems.setLayoutManager(llm);
        lvItems.setHasFixedSize(true);

        RVAdapter rv=new RVAdapter(dbh.getCardData());
        lvItems.setAdapter(rv);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
