package com.equipments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;

import com.equipments.Utils.DBConstant;
import com.equipments.Utils.Dbhandler;
import com.equipments.Utils.TodoCursorAdapter;

public class InspectionList extends AppCompatActivity {
    Dbhandler dbh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ListView lvItems = (ListView) findViewById(R.id.listt);
        dbh=new Dbhandler(this);
        SQLiteDatabase db = dbh.getWritableDatabase();
        Cursor todoCursor = db.rawQuery("SELECT  * FROM "+ DBConstant.T_Inspection_Entries, null);
        TodoCursorAdapter todoAdapter = new TodoCursorAdapter(this, todoCursor, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
// Attach cursor adapter to the ListView
        lvItems.setAdapter(todoAdapter);
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
