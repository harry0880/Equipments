package com.equipments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.equipments.GettersSetters.InpectionId;
import com.equipments.Utils.Dbhandler;
import com.equipments.Utils.RVAdapter;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class InspectionList extends AppCompatActivity {
    Dbhandler dbh;
    RecyclerView lvItems;
    RelativeLayout rl;
    RVAdapter rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        lvItems = (RecyclerView) findViewById(R.id.rv);
        rl=(RelativeLayout) findViewById(R.id.rlNoRecords);
        setSupportActionBar(toolbar);
        dbh = new Dbhandler(this);
        InitializeAdapter();
        InpectionId.setNewentry(false);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        lvItems.setLayoutManager(llm);
        lvItems.setHasFixedSize(true);

        rv.setOnItemClickListener(new RVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String Id) {
                InpectionId.setNewentry(true);
                InpectionId.setId(Id);
              startActivity(new Intent(InspectionList.this,Main.class));
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InspectionList.this, Main.class));
            }
        });
    }

    void InitializeAdapter()
    {
        if(dbh.getCardData()!=null) {
           rl.setVisibility(View.INVISIBLE);
            lvItems.setVisibility(View.VISIBLE);
            rv = new RVAdapter(dbh.getCardData());
            lvItems.setAdapter(rv);
        }
        else
        {
            rl.setVisibility(View.VISIBLE);
            lvItems.setVisibility(View.INVISIBLE);
        }


    }

    @Override
    protected void onResume() {
        InitializeAdapter();
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.Sync:
                new SyncData().execute();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private class SyncData extends AsyncTask<Void,Void,Boolean>
    {
        ProgressDialog dialog =new ProgressDialog(InspectionList.this);

        @Override
        protected void onPreExecute() {
            if(dialog.isShowing())
                dialog.dismiss();
            dialog.setTitle("Syncing Data");
            dialog.setMessage("Please Wait");
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);

            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            return  dbh.SendEquipmentEntries();
        }

        @Override
        protected void onPostExecute(Boolean s) {

            if(s && dialog.isShowing())
            {
                dialog.dismiss();
                new SweetAlertDialog(InspectionList.this,SweetAlertDialog.SUCCESS_TYPE).setTitleText("Synced").show();
            }
            else {
                dialog.dismiss();
                new SweetAlertDialog(InspectionList.this,SweetAlertDialog.ERROR_TYPE).setTitleText("Sync Failed").show();
            }
            super.onPostExecute(s);
        }
    }
}
