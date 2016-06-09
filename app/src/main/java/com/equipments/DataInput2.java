package com.equipments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;

import mehdi.sakout.fancybuttons.FancyButton;

public class DataInput2 extends Fragment {
    ArrayList<String> teamMember;
    ArrayAdapter<String> dataAdapter;
    SwipeMenuListView listview;
    ViewGroup.LayoutParams lvp;
    SearchableSpinner spInstType;
    ArrayAdapter<String> instituteTypeAdapter;
    FancyButton btnAdd;
    private final String[] array = {"Hello"};
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_datainput2, container, false);



        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialize(view);
        teamMember=new ArrayList<String>();
        dataAdapter=new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, teamMember);
        listview.setAdapter(dataAdapter);
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getActivity());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                openItem.setWidth(dp2px(90));
                // set item title
                openItem.setTitle("Open");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getActivity());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set a icon
                deleteItem.setIcon(R.drawable.common_google_signin_btn_icon_dark);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

// set creator
        listview.setMenuCreator(creator);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lvp.height = listview.getHeight() + 100;
                teamMember.add("Hello");
                listview.requestLayout();
                dataAdapter.notifyDataSetChanged();

            }
        });

      /*  setInstType();*/

   /* TextView title = (TextView) view.findViewById(R.id.item_title);
    title.setText(String.valueOf(position));*/
    }
    void initialize(View view)
    {
        listview=(SwipeMenuListView)view.findViewById(R.id.listView);
        lvp=(ViewGroup.LayoutParams)listview.getLayoutParams();
        btnAdd=(FancyButton) view.findViewById(R.id.btnAdd);

    }
    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    void setInstType()
    {
        ArrayList<String> ar=new ArrayList<>();
        ar.add("GH0 M");
        ar.add("MMS");
        ar.add("MMg");
        ar.add("MMq");
        ar.add("ght mm");
        instituteTypeAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,ar);
        instituteTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spInstType.setAdapter(instituteTypeAdapter);
        spInstType.setTitle("Institute Type");
    }


}
