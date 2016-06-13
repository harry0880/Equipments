package com.equipments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.baoyz.swipemenulistview.SwipeMenuListView;
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
    FancyButton btnAdd;
    private final String[] array = {"Hello"};
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_datainput2, container, false);


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
        btnAdd=(FancyButton) view.findViewById(R.id.btnAdd);
        etSerialno=(EditText)view.findViewById(R.id.etSerialNumber);
        etDOI=(EditText)view.findViewById(R.id.DateOfInstallment);
        etDOInspec=(EditText)view.findViewById(R.id.DateOfInspection);


    }







}
