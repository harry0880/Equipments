package com.equipments;

import android.content.ContentValues;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.equipments.GettersSetters.GetSet;
import com.equipments.GettersSetters.InpectionId;
import com.equipments.GettersSetters.Location;
import com.equipments.GettersSetters.Time;
import com.equipments.SpinnerAdapter.District;
import com.equipments.SpinnerAdapter.Institute;
import com.equipments.SpinnerAdapter.InstituteType;
import com.equipments.Utils.DBConstant;
import com.equipments.Utils.Dbhandler;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import mehdi.sakout.fancybuttons.FancyButton;

public class DataInput1 extends Fragment {
  SearchableSpinner spInstType,spDistrict,spInstitute;
  ArrayAdapter<InstituteType> instituteTypeAdapter;
  FancyButton btnSubmit;
  Dbhandler db;
  GetSet getset;
  Location locGetSet;
  String Districtselected=null;
  String Instituteypeselected=null;
  long idd=0;
  Boolean dataExist;
  String preExistingData="false";
   @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    View view= inflater.inflate(R.layout.fragment_datainput1, container, false);
    final ViewGroup con=container;
     initialize(view);

  db=new Dbhandler(getActivity());
    getset=new GetSet();
    /* preExistingData=db.getDataFrag1(InpectionId.getId());*/
     if(!(preExistingData.equals("false")))
     {
       dataExist=true;
     }
     btnSubmit.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
       idd=savedata1();
         InpectionId.setId(idd+"");
         if(idd==-1)
         {
           Snackbar.make(v,"Please Select all Entries",Snackbar.LENGTH_SHORT).show();
         }
         else {
           ((Main) getActivity()).switchFragment();
           btnSubmit.setEnabled(false);
           btnSubmit.setBackgroundColor(Color.parseColor("#FF63727B"));
          /* btnSubmit.setBackgroundColor(R.color.icon_disabled);*/
         }
       }
     });

    return view;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);



    setInstType();
    setDistrictSpinner();


    spDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position>=0)
        {
          District district=((District) spDistrict.getSelectedItem());
          getset.setDcode_ds(district.getDcode_ds());
          Districtselected = district.getDcode_ds();
          setInstitute();
        }
      }
      @Override
      public void onNothingSelected(AdapterView<?> parent) {
      }
    });



    spInstitute.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position>=0)
        {
          Institute institute = ((Institute)spInstitute.getSelectedItem());
          getset.setInstid(institute.getInstitute_id());
        }
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });

    spInstType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position>=0) {
          InstituteType intype = ((InstituteType) spInstType.getSelectedItem());
          getset.setInstitutetype_id(intype.getInstitutetype_id());
          Instituteypeselected=intype.getInstitutetype_id();
          setInstitute();
        }
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });

  }
  void initialize(View view)
  {
    spInstType=(SearchableSpinner) view.findViewById(R.id.spInstType);
    spInstitute=(SearchableSpinner) view.findViewById(R.id.spInstName);
    spDistrict=(SearchableSpinner) view.findViewById(R.id.spDist);
    btnSubmit=(FancyButton) view.findViewById(R.id.btnSubmit);
    locGetSet=new Location();
  }

  void setInstType()
  {
    instituteTypeAdapter=new ArrayAdapter<InstituteType>(getActivity(),android.R.layout.simple_spinner_item,db.getInstituteType());
    instituteTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spInstType.setAdapter(instituteTypeAdapter);
    spInstType.setTitle("Institute Type");
  }

  void setDistrictSpinner()
  {
    ArrayAdapter<District> Adapter = new ArrayAdapter<District>(getActivity(), android.R.layout.simple_spinner_item, db.getDistrict());
    Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spDistrict.setAdapter(Adapter);
    spDistrict.setTitle("District");
  /*  if(dataExist)
    {
     *//* spDistrict.setSelection(Adapter.getPosition((preExistingData.split("#"))[0]);*//*
    }*/
  }




 void setInstitute()
  {
    ArrayAdapter<Institute> Adapter = new ArrayAdapter<Institute>(getActivity(), android.R.layout.simple_spinner_item, db.getInstitute(Districtselected,Instituteypeselected));
    Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spInstitute.setAdapter(Adapter);
  }


  long savedata1()
  {

    if(spDistrict.getSelectedItem()!=null && spInstitute.getSelectedItem()!=null && spInstType.getSelectedItem()!=null) {
      ContentValues cv = new ContentValues();
      cv.put(DBConstant.C_Dist_Code, getset.getDcode_ds());
      cv.put(DBConstant.C_Doc_Inst_TypeID, getset.getInstitutetype_id());
      cv.put(DBConstant.C_Doc_Inst_ID, getset.getInstid());
      cv.put(DBConstant.C_Location, locGetSet.getLocation());
      cv.put(DBConstant.C_DateOfInspectionGPS, Time.getTime());
      return db.SaveFrag1(cv);
    }
    else
        return -1;

  }
}
