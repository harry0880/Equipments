package com.equipments;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/*  void setInstitute()
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

  void savedata()
  {
    ContentValues cv=new ContentValues();
    cv.put(DBConstant.C_Dist_Code,getset.getDcode_ds());
    cv.put(DBConstant.C_Doc_Inst_TypeID,getset.getInstitutetype_id());
    cv.put(DBConstant.C_EquipmentId,getset.getEquipmentNameMaster_id());
    cv.put(DBConstant.C_SupplierId,getset.getEquipmentSupplier_id());
    cv.put(DBConstant.C_CategoryId,getset.getEquipmentcategory_id());
    cv.put(DBConstant.C_ManufacturerID,getset.getEquipmentManufacturer_id());
    cv.put(DBConstant.C_Doc_Inst_ID,getset.getInstid());
  }
}
