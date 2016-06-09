package com.equipments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;

public class DataInput1 extends Fragment {
  SearchableSpinner spInstType;
  ArrayAdapter<String> instituteTypeAdapter;

  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    View view= inflater.inflate(R.layout.fragment_datainput1, container, false);

    return view;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    initialize(view);

    setInstType();

   /* TextView title = (TextView) view.findViewById(R.id.item_title);
    title.setText(String.valueOf(position));*/
  }
  void initialize(View view)
  {
        /*etInstName=(SearchableSpinner)findViewById(R.id.etInstituteName);*/
    spInstType=(SearchableSpinner) view.findViewById(R.id.spInstType);

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
