package com.equipments;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;

import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.equipments.GettersSetters.Barcode;
import com.equipments.Utils.DBConstant;
import com.equipments.Utils.Dbhandler;
import com.equipments.Utils.SimpleScannerFragmentActivity;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;

import mehdi.sakout.fancybuttons.FancyButton;

public class DataInput2 extends Fragment {
    ArrayList<String> teamMember;
    ArrayAdapter<String> dataAdapter;
    SwipeMenuListView listview;
    ViewGroup.LayoutParams lvp;
    EditText etSerialno,etDOI,etDOInspec,etRemarks;
    SearchableSpinner spInstType;
    FancyButton btnAdd,btnUpdate,btnCancel,btnDelete;
    ImageButton barcode;
    Barcode barcodee;
    String idd;
    Dbhandler db;
    static int cnt=0;
    ContentValues[] contentValues;
    private final String[] array = {"Hello"};
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view= inflater.inflate(R.layout.fragment_datainput2, container, false);
/*       idd=  container.getTag().toString();*/

        db=new Dbhandler(getActivity());
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialize(view);
        teamMember=new ArrayList<String>();
        dataAdapter=new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, teamMember);
        listview.setAdapter(dataAdapter);

        barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(getContext(),SimpleScannerFragmentActivity.class));
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lvp.height = listview.getHeight() + 100;
                teamMember.add("Hello");
                listview.requestLayout();
                dataAdapter.notifyDataSetChanged();

            }
        });



    }
    void initialize(View view)
    {
        listview=(SwipeMenuListView) view.findViewById(R.id.listView);
        lvp=(ViewGroup.LayoutParams)listview.getLayoutParams();
        btnAdd=(FancyButton) view.findViewById(R.id.btnAddtoList);
        btnCancel=(FancyButton) view.findViewById(R.id.btnCancel);
        btnUpdate=(FancyButton) view.findViewById(R.id.btnUpdate);
        btnDelete=(FancyButton) view.findViewById(R.id.btndelete);
        etSerialno=(EditText)view.findViewById(R.id.etSerialNumber);
        etDOI=(EditText)view.findViewById(R.id.DateOfInstallment);
        etDOInspec=(EditText)view.findViewById(R.id.DateOfInspection);
        barcode=(ImageButton) view.findViewById(R.id.barcode);
        barcodee=new Barcode();
    }

    void SaveData()
    {
        ContentValues cv =new ContentValues();
        cv.put(DBConstant.C_SerialNo,etSerialno.getText().toString());
        cv.put(DBConstant.C_DateOfInstallment,etDOI.getText().toString());
        cv.put(DBConstant.C_DateOfInspection,etDOInspec.getText().toString());
        db.savefrag3(cv,db.getId());
    }


    @Override
    public void onResume() {
        etSerialno.setText(barcodee.getBarcodee());
        super.onResume();
    }
}
