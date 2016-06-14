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

import com.equipments.SpinnerAdapter.Category;
import com.equipments.SpinnerAdapter.District;
import com.equipments.SpinnerAdapter.Equipment;
import com.equipments.SpinnerAdapter.Institute;
import com.equipments.SpinnerAdapter.InstituteType;
import com.equipments.SpinnerAdapter.Manufacturer;
import com.equipments.SpinnerAdapter.Supplier;
import com.equipments.Utils.DBConstant;
import com.equipments.Utils.Dbhandler;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;

public class DataInput1 extends Fragment {
  SearchableSpinner spInstType,spDistrict,spEquipment,spCategory,spManufacturer,spSupplier,spInstitute;
  ArrayAdapter<InstituteType> instituteTypeAdapter;
  Dbhandler db;
  GetSet getset;
  String Districtselected=null;
  String Instituteypeselected=null;
  Boolean District_spinner_flag=false,InstituteTypeSpinnerflag = false,InstituteSpinnerflag = false,CategorySpinnerflag = false,SupplierSpinnerflag = false ,ManufacturerSpinnerflag = false ,EquipmentSpinnerflag = false;
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    View view= inflater.inflate(R.layout.fragment_datainput1, container, false);
  db=new Dbhandler(getActivity());
    getset=new GetSet();
    return view;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    initialize(view);

    setInstType();
    setDistrictSpinner();
    setCategory();
    setEquipment();
    setManufacturer();
    setSupplier();


   /* TextView title = (TextView) view.findViewById(R.id.item_title);
    title.setText(String.valueOf(position));*/
  }
  void initialize(View view)
  {
        /*etInstName=(SearchableSpinner)findViewById(R.id.etInstituteName);*/
    spInstType=(SearchableSpinner) view.findViewById(R.id.spInstType);
    spCategory=(SearchableSpinner) view.findViewById(R.id.spCategory);
    spSupplier=(SearchableSpinner) view.findViewById(R.id.spSupplier);
    spManufacturer=(SearchableSpinner) view.findViewById(R.id.spManufacturer);
    spEquipment=(SearchableSpinner) view.findViewById(R.id.spEquipment);
    spInstitute=(SearchableSpinner) view.findViewById(R.id.spInstName);
    spDistrict=(SearchableSpinner) view.findViewById(R.id.spDist);

    spDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(District_spinner_flag)
        {
          District district=((District) spDistrict.getSelectedItem());
          getset.setDcode_ds(district.getDcode_ds());
          Districtselected = district.getDcode_ds().toString();
        }
        District_spinner_flag=true;
      }
      @Override
      public void onNothingSelected(AdapterView<?> parent) {
      }
    });
    spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(CategorySpinnerflag)
        {
          Category category = ((Category)spCategory.getSelectedItem());
          getset.setEquipmentcategory_id(category.getEquipmentcategory_id());
        }
        CategorySpinnerflag=true;
      }
      @Override
      public void onNothingSelected(AdapterView<?> parent) {
      }
    });
    spSupplier.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(SupplierSpinnerflag)
        {
          Supplier supplier = ((Supplier)spSupplier.getSelectedItem());

          getset.setEquipmentSupplier_id(supplier.getEquipmentSupplier_id());
        }
        SupplierSpinnerflag=true;
      }
      @Override
      public void onNothingSelected(AdapterView<?> parent) {
      }
    });
    spManufacturer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(ManufacturerSpinnerflag)
        {
          Manufacturer manufacturer =((Manufacturer)spManufacturer.getSelectedItem());
          getset.setEquipmentManufacturer_id(manufacturer.getEquipmentManufacturer_id());
        }
        ManufacturerSpinnerflag=true;
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });
    spEquipment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(EquipmentSpinnerflag)
        {
          Equipment eq = ((Equipment) spEquipment.getSelectedItem());
          getset.setEquipmentNameMaster_id(eq.getEquipmentNameMaster_id());
        }
        EquipmentSpinnerflag=true;
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });
    spInstitute.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
  @Override
  public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       if(InstituteSpinnerflag)
       {
         Institute institute = ((Institute)spInstitute.getSelectedItem());
         getset.setInstid(institute.getInstitute_id());
              }
    InstituteSpinnerflag=true;
  }

  @Override
  public void onNothingSelected(AdapterView<?> parent) {

  }
});
    spInstType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
  @Override
  public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    if(InstituteTypeSpinnerflag) {
      InstituteType intype = ((InstituteType) spInstType.getSelectedItem());
      getset.setInstitutetype_id(intype.getInstitutetype_id());
      Instituteypeselected=intype.getInstitutetype_id().toString();
    }
    InstituteTypeSpinnerflag=true;
  }

  @Override
  public void onNothingSelected(AdapterView<?> parent) {

  }
});

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
  }

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

/* void setInstitute()
  {
    ArrayAdapter<String> Adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, db.getInstitute());
    Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spDistrict.setAdapter(Adapter);
  }*/

  void setCategory()
  {
    ArrayAdapter<Category> Adapter = new ArrayAdapter<Category>(getActivity(), android.R.layout.simple_spinner_item, db.getCategory());
    Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spCategory.setAdapter(Adapter);
    spCategory.setTitle("Category");
  }

  void savedata1()
  {
    ContentValues cv=new ContentValues();
    cv.put(DBConstant.C_Dist_Code,getset.getDcode_ds());
    cv.put(DBConstant.C_Doc_Inst_TypeID,getset.getInstitutetype_id());
    cv.put(DBConstant.C_Doc_Inst_ID,getset.getInstid());
    cv.put(DBConstant.C_EquipmentId,getset.getEquipmentNameMaster_id());
    cv.put(DBConstant.C_CategoryId,getset.getEquipmentcategory_id());
    cv.put(DBConstant.C_ManufacturerID,getset.getEquipmentManufacturer_id());
    cv.put(DBConstant.C_SupplierId,getset.getEquipmentSupplier_id());


  }
}
