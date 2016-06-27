package com.equipments;

import android.content.ContentValues;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import com.equipments.GettersSetters.InpectionId;
import com.equipments.GettersSetters.GetSet;
import com.equipments.SpinnerAdapter.Category;
import com.equipments.SpinnerAdapter.Equipment;
import com.equipments.SpinnerAdapter.InstituteType;
import com.equipments.SpinnerAdapter.Manufacturer;
import com.equipments.SpinnerAdapter.Supplier;
import com.equipments.Utils.DBConstant;
import com.equipments.Utils.Dbhandler;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;

import mehdi.sakout.fancybuttons.FancyButton;

public class DataInput4 extends Fragment {
  SearchableSpinner spEquipment,spCategory,spManufacturer,spSupplier;
  ArrayAdapter<InstituteType> instituteTypeAdapter;
  Dbhandler db;
  GetSet getset;
  FancyButton btnproceed2;
  String idd;
  String[] savedEntry;
   @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    View view= inflater.inflate(R.layout.fragment_datainput4, container, false);
  db=new Dbhandler(getActivity());
    getset=new GetSet();
    return view;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
     initialize(view);
    if(InpectionId.getNewentry())
    savedEntry=db.getDataInput2(InpectionId.getId()).split("#");
    setCategory();
    setEquipment();
    setManufacturer();
    setSupplier();

    btnproceed2.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(!InpectionId.getId().equals("-1")) {
          savedata2();
          ((Main) getActivity()).switchFragment();
          btnproceed2.setBackgroundColor(Color.parseColor("#FF63727B"));
        }
      }
    });


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

  }
  void initialize(View view)
  {
    btnproceed2=(FancyButton) view.findViewById(R.id.btnProceed2);
    spCategory=(SearchableSpinner) view.findViewById(R.id.spCategory);
    spSupplier=(SearchableSpinner) view.findViewById(R.id.spSupplier);
    spManufacturer=(SearchableSpinner) view.findViewById(R.id.spManufacturer);
    spEquipment=(SearchableSpinner) view.findViewById(R.id.spEquipment);

  }


  void setEquipment()
  {
    ArrayList<Equipment> Al= db.getEquipment();
    ArrayAdapter<Equipment> Adapter = new ArrayAdapter<Equipment>(getActivity(), android.R.layout.simple_spinner_item,Al);
    Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spEquipment.setAdapter(Adapter);
    spEquipment.setTitle("Equipment");
    if (InpectionId.getNewentry() && savedEntry!=null) {
      int cnt=0;
      for (Equipment list : Al) {
        cnt++;
        if (savedEntry[0].equals(list.getEquipmentNameMaster_id())) {
          getset.setEquipmentNameMaster_id(savedEntry[0]);
          spEquipment.onSearchableItemClicked(Adapter.getItem(Adapter.getPosition(list)),cnt);
          break;
        }
      }
    }
  }

  void setManufacturer()
  {
    ArrayList<Manufacturer> Al=db.getManufacturer();
    ArrayAdapter<Manufacturer> Adapter = new ArrayAdapter<Manufacturer>(getActivity(), android.R.layout.simple_spinner_item, Al);
    Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spManufacturer.setAdapter(Adapter);
    spManufacturer.setTitle("Manufacturer");
    if (InpectionId.getNewentry() && savedEntry!=null) {
      int cnt=0;
      for (Manufacturer list : Al) {
        cnt++;
        if (savedEntry[2].equals(list.getEquipmentManufacturer_id())) {
          getset.setEquipmentManufacturer_id(savedEntry[2]);
          spManufacturer.onSearchableItemClicked(Adapter.getItem(Adapter.getPosition(list)),cnt);
          break;
        }
      }
    }

  }

  void setSupplier()
  {
    ArrayList<Supplier> Al= db.getSupplier();
    ArrayAdapter<Supplier> Adapter = new ArrayAdapter<Supplier>(getActivity(), android.R.layout.simple_spinner_item, Al);
    Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spSupplier.setAdapter(Adapter);
    spSupplier.setTitle("Supplier");
    if (InpectionId.getNewentry() && savedEntry!=null) {
      int cnt=0;
      for (Supplier list : Al) {
        cnt++;
        if (savedEntry[3].equals(list.getEquipmentSupplier_id())) {
          getset.setEquipmentSupplier_id(savedEntry[3]);
          spSupplier.onSearchableItemClicked(Adapter.getItem(Adapter.getPosition(list)),cnt);
          break;
        }
      }
    }
  }



  void setCategory()
  {
    ArrayList<Category> Al=db.getCategory();
    ArrayAdapter<Category> Adapter = new ArrayAdapter<Category>(getActivity(), android.R.layout.simple_spinner_item, Al);
    Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spCategory.setAdapter(Adapter);
    spCategory.setTitle("Category");
    if (InpectionId.getNewentry() && savedEntry!=null){
      int cnt=0;
      for (Category list : Al) {
        cnt++;
        if (savedEntry[1].equals(list.getEquipmentcategory_id())) {
          getset.setEquipmentcategory_id(savedEntry[1]);
          spCategory.onSearchableItemClicked(Adapter.getItem(Adapter.getPosition(list)),cnt);
          break;
        }
      }
    }
  }

  void savedata2()
  {
    ContentValues cv=new ContentValues();
    cv.put(DBConstant.C_EquipmentId,getset.getEquipmentNameMaster_id());
    cv.put(DBConstant.C_CategoryId,getset.getEquipmentcategory_id());
    cv.put(DBConstant.C_ManufacturerID,getset.getEquipmentManufacturer_id());
    cv.put(DBConstant.C_SupplierId,getset.getEquipmentSupplier_id());
    cv.put(DBConstant.C_Update1,"1");

    db.UpdateFrag2(cv,InpectionId.getId());


  }
}
