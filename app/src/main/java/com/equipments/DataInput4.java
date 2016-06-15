package com.equipments;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.equipments.GettersSetters.GetSet;
import com.equipments.SpinnerAdapter.Category;
import com.equipments.SpinnerAdapter.Equipment;
import com.equipments.SpinnerAdapter.InstituteType;
import com.equipments.SpinnerAdapter.Manufacturer;
import com.equipments.SpinnerAdapter.Supplier;
import com.equipments.Utils.DBConstant;
import com.equipments.Utils.Dbhandler;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import mehdi.sakout.fancybuttons.FancyButton;

public class DataInput4 extends Fragment {
  SearchableSpinner spInstType,spDistrict,spEquipment,spCategory,spManufacturer,spSupplier,spInstitute;
  ArrayAdapter<InstituteType> instituteTypeAdapter;
  Dbhandler db;
  GetSet getset;
  FancyButton btnproceed2;
  String idd;
   @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    View view= inflater.inflate(R.layout.fragment_datainput4, container, false);
  db=new Dbhandler(getActivity());
    getset=new GetSet();
/*  idd=container.getTag().toString();*/
    return view;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    initialize(view);

 /* setInstType();
    setDistrictSpinner();*/
    setCategory();
    setEquipment();
    setManufacturer();
    setSupplier();

    btnproceed2.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        savedata2();
      }
    });

  /*  spDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position>=0)
        {
          District district=((District) spDistrict.getSelectedItem());
          getset.setDcode_ds(district.getDcode_ds());
          Districtselected = district.getDcode_ds();
         *//* setInstitute();*//*
        }

      }
      @Override
      public void onNothingSelected(AdapterView<?> parent) {
      }
    });*/
    spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position>=0)
        {
          Category category = ((Category)spCategory.getSelectedItem());
          getset.setEquipmentcategory_id(category.getEquipmentcategory_id());
        }
      }
      @Override
      public void onNothingSelected(AdapterView<?> parent) {
      }
    });
    spSupplier.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position>=0)
        {
          Supplier supplier = ((Supplier)spSupplier.getSelectedItem());

          getset.setEquipmentSupplier_id(supplier.getEquipmentSupplier_id());
        }
      }
      @Override
      public void onNothingSelected(AdapterView<?> parent) {
      }
    });
    spManufacturer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position>=0)
        {
          Manufacturer manufacturer =((Manufacturer)spManufacturer.getSelectedItem());
          getset.setEquipmentManufacturer_id(manufacturer.getEquipmentManufacturer_id());
        }
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });
    spEquipment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position>=0)
        {
          Equipment eq = ((Equipment) spEquipment.getSelectedItem());
          getset.setEquipmentNameMaster_id(eq.getEquipmentNameMaster_id());
        }
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });
   /* spInstitute.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
        *//*  setInstitute();*//*
        }
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });*/

   /* TextView title = (TextView) view.findViewById(R.id.item_title);
    title.setText(String.valueOf(position));*/
  }
  void initialize(View view)
  {
        /*etInstName=(SearchableSpinner)findViewById(R.id.etInstituteName);*/
    btnproceed2=(FancyButton) view.findViewById(R.id.btnProceed2);
    spCategory=(SearchableSpinner) view.findViewById(R.id.spCategory);
    spSupplier=(SearchableSpinner) view.findViewById(R.id.spSupplier);
    spManufacturer=(SearchableSpinner) view.findViewById(R.id.spManufacturer);
    spEquipment=(SearchableSpinner) view.findViewById(R.id.spEquipment);

  }

 /* void setInstType()
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
  }*/

  void setEquipment()
  {
    ArrayAdapter<Equipment> Adapter = new ArrayAdapter<Equipment>(getActivity(), android.R.layout.simple_spinner_item, db.getEquipment());
    Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spEquipment.setAdapter(Adapter);
    spEquipment.setTitle("Equipment");
  }

  void setManufacturer()
  {
    ArrayAdapter<Manufacturer> Adapter = new ArrayAdapter<Manufacturer>(getActivity(), android.R.layout.simple_spinner_item, db.getManufacturer());
    Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spManufacturer.setAdapter(Adapter);
    spManufacturer.setTitle("Manufacturer");
  }

  void setSupplier()
  {
    ArrayAdapter<Supplier> Adapter = new ArrayAdapter<Supplier>(getActivity(), android.R.layout.simple_spinner_item, db.getSupplier());
    Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spSupplier.setAdapter(Adapter);
    spSupplier.setTitle("Supplier");
  }

/*
 void setInstitute()
  {
    ArrayAdapter<Institute> Adapter = new ArrayAdapter<Institute>(getActivity(), android.R.layout.simple_spinner_item, db.getInstitute(Districtselected,Instituteypeselected));
    Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spInstitute.setAdapter(Adapter);
  }
*/

  void setCategory()
  {
    ArrayAdapter<Category> Adapter = new ArrayAdapter<Category>(getActivity(), android.R.layout.simple_spinner_item, db.getCategory());
    Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spCategory.setAdapter(Adapter);
    spCategory.setTitle("Category");
  }

  void savedata2()
  {
    ContentValues cv=new ContentValues();
    cv.put(DBConstant.C_EquipmentId,getset.getEquipmentNameMaster_id());
    cv.put(DBConstant.C_CategoryId,getset.getEquipmentcategory_id());
    cv.put(DBConstant.C_ManufacturerID,getset.getEquipmentManufacturer_id());
    cv.put(DBConstant.C_SupplierId,getset.getEquipmentSupplier_id());
    db.UpdateFrag2(cv,db.getId());


  }
}
