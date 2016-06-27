package com.equipments;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.equipments.GettersSetters.Barcode;
import com.equipments.GettersSetters.InpectionId;
import com.equipments.Utils.DBConstant;
import com.equipments.Utils.Dbhandler;
import com.equipments.Utils.SimpleScannerFragmentActivity;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;

import mehdi.sakout.fancybuttons.FancyButton;

public class DataInput2 extends Fragment implements DatePickerDialog.OnDateChangedListener, DatePickerDialog.OnDateSetListener {
    ArrayList<String> teamMember,teamMemberDesig,teamMemberName;
    ArrayAdapter<String> dataAdapter;
    ListView listview;
    int ItemPosition;
    ViewGroup.LayoutParams lvp;
    EditText etSerialno,etDOI,etDOInspec,etRemarks,etNameTM,etDesigTM;
    SearchableSpinner spInstType;
    FancyButton btnAdd,btnUpdate,btnCancel,btnDelete,btnSave,btnSaveList;
    ImageButton barcode;
    Barcode barcodee;
    String[] savedEntry;
    Dbhandler db;
    static int DateviewSelected=0;
    static int cnt=0;
    LinearLayout llAddMemebers;
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
        dataAdapter=new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, teamMember);
        listview.setAdapter(dataAdapter);
        ButtonDisable();
        savedEntry = db.getDataInput3(InpectionId.getId()).split("#");
        if(InpectionId.getNewentry() && savedEntry!=null) {
           barcodee.setBarcodee(savedEntry[0]);
            etDOI.setText(savedEntry[1]);
            etDOInspec.setText(savedEntry[2]);
            etRemarks.setText(savedEntry[3]);
            ArrayList<String>[] Al=db.getDataInput4(InpectionId.getId());
            if(Al!=null)
            {
                for(String name:Al[0])
                {
                    teamMember.add(name);
                    teamMemberName.add(name);
                    lvp.height = listview.getHeight() + 100;
                }
                for (String desig:Al[1])
                {
                    teamMemberDesig.add((desig));
                }
                listview.requestLayout();
                dataAdapter.notifyDataSetChanged();
            }
        }




        barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           startActivity(new Intent(getActivity(), SimpleScannerFragmentActivity.class));
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etNameTM.getText().toString().trim().equals("")
                        ||etDesigTM.getText().toString().equals(""))
                {
                    Snackbar.make(v,"Please fill all fields!!!",Snackbar.LENGTH_SHORT);
                }
                else
                {
                    lvp.height = listview.getHeight() + 100;
                    teamMember.add(etNameTM.getText().toString()+"("+etDesigTM.getText().toString()+")");
                    teamMemberName.add(etNameTM.getText().toString());
                    teamMemberDesig.add(etDesigTM.getText().toString());
                    listview.requestLayout();
                    dataAdapter.notifyDataSetChanged();
                    clearAll();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            clearAll();
                ButtonDisable();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teamMember.remove(ItemPosition);
                teamMemberName.remove(ItemPosition);
                teamMemberDesig.remove(ItemPosition);
                dataAdapter.notifyDataSetChanged();
                lvp.height = listview.getHeight() - 100;
                clearAll();
                ButtonDisable();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teamMember.remove(ItemPosition);
                teamMemberDesig.remove(ItemPosition);
                teamMemberName.remove(ItemPosition);

                teamMember.add(etNameTM.getText().toString()+"("+etDesigTM.getText().toString()+")");
                teamMemberDesig.add(etDesigTM.getText().toString());
                teamMemberName.add(etNameTM.getText().toString());

                dataAdapter.notifyDataSetChanged();
                ButtonDisable();
                clearAll();
            }
        });



        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemPosition=position;
                etNameTM.setText(teamMemberName.get(position));
                etDesigTM.setText(teamMemberDesig.get(position));
                ButtonEnable();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveData();

                btnSave.setEnabled(false);
                llAddMemebers.setVisibility(View.VISIBLE);
            }
        });

        etDOI.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(MotionEvent.ACTION_UP == event.getAction()) {
                   DateviewSelected=0;
                    Calendar now = Calendar.getInstance();
                    DatePickerDialog dpd = DatePickerDialog.newInstance(
                         DataInput2.this,
                            now.get(Calendar.YEAR),
                            now.get(Calendar.MONTH),
                            now.get(Calendar.DAY_OF_MONTH)
                    );
                    dpd.setThemeDark(true);
                    dpd.setAccentColor(Color.parseColor("#3F51B5"));
                    dpd.setMinDate(now);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        dpd.setAllowEnterTransitionOverlap(true);
                        dpd.setAllowReturnTransitionOverlap(true);
                    }
                    // dpd.dismissOnPause(dismissDate.isChecked());
                    dpd.showYearPickerFirst(true);
                    dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
                }

                return true;
            }
        });

        etDOInspec.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(MotionEvent.ACTION_UP == event.getAction()) {
                    Calendar now = Calendar.getInstance();
                    DateviewSelected=1;
                    DatePickerDialog dpd = DatePickerDialog.newInstance(
                            DataInput2.this,
                            now.get(Calendar.YEAR),
                            now.get(Calendar.MONTH),
                            now.get(Calendar.DAY_OF_MONTH)
                    );
                    dpd.setThemeDark(true);
                    dpd.setAccentColor(Color.parseColor("#3F51B5"));
                    dpd.setMinDate(now);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        dpd.setAllowEnterTransitionOverlap(true);
                        dpd.setAllowReturnTransitionOverlap(true);
                    }
                    // dpd.dismissOnPause(dismissDate.isChecked());
                    dpd.showYearPickerFirst(true);
                    dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");

                }
                return true;
            }
        });

btnSaveList.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        db.delete_Table_members(InpectionId.getId());
        int index=0;
        for(String i:teamMember)
        {
            ContentValues cv=new ContentValues();
            cv.put(DBConstant.C_ID,InpectionId.getId());
            cv.put(DBConstant.C_TeamMemberId,index+1);
            cv.put(DBConstant.C_TeamMemberName,teamMember.get(index));
            cv.put(DBConstant.C_TeamMemberDesignation,teamMemberDesig.get(index));
            index++;
            db.insert_Team_Members(cv);
        }
        allButtonDisable();
    }
});
    }

    void clearAll()
    {
        etNameTM.setText("");
        etDesigTM.setText("");
    }

    void ButtonDisable()
    {
        btnAdd.setEnabled(true);
        btnCancel.setEnabled(false);
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
        btnAdd.setBackgroundColor(Color.parseColor("#303F9F"));
        btnCancel.setBackgroundColor(Color.parseColor("#FF63727B"));
        btnUpdate.setBackgroundColor(Color.parseColor("#FF63727B"));
        btnDelete.setBackgroundColor(Color.parseColor("#FF63727B"));
    }

    void ButtonEnable()
    {
        btnAdd.setEnabled(false);
        btnDelete.setEnabled(true);
        btnCancel.setEnabled(true);
        btnUpdate.setEnabled(true);
        btnAdd.setBackgroundColor(Color.parseColor("#FF63727B"));
        btnCancel.setBackgroundColor(Color.parseColor("#303F9F"));
        btnUpdate.setBackgroundColor(Color.parseColor("#303F9F"));
        btnDelete.setBackgroundColor(Color.parseColor("#303F9F"));
    }

    void allButtonDisable()
    {
        btnAdd.setEnabled(false);
        btnDelete.setEnabled(false);
        btnCancel.setEnabled(false);
        btnUpdate.setEnabled(false);
        btnSaveList.setEnabled(false);
        btnAdd.setBackgroundColor(Color.parseColor("#FF63727B"));
        btnCancel.setBackgroundColor(Color.parseColor("#FF63727B"));
        btnUpdate.setBackgroundColor(Color.parseColor("#FF63727B"));
        btnDelete.setBackgroundColor(Color.parseColor("#FF63727B"));
        btnSaveList.setBackgroundColor(Color.parseColor("#FF63727B"));
    }

    void initialize(View view)
    {
        listview=(ListView) view.findViewById(R.id.listView);
        lvp=(ViewGroup.LayoutParams)listview.getLayoutParams();
        btnAdd=(FancyButton) view.findViewById(R.id.btnAddtoList);
        btnCancel=(FancyButton) view.findViewById(R.id.btnCancel);
        btnUpdate=(FancyButton) view.findViewById(R.id.btnUpdate);
        btnDelete=(FancyButton) view.findViewById(R.id.btndelete);
        etSerialno=(EditText)view.findViewById(R.id.etSerialNumber);
        btnSave=(FancyButton) view.findViewById(R.id.btnSave);
        etRemarks=(EditText) view.findViewById(R.id.Remarks);
        etDOI=(EditText)view.findViewById(R.id.DateOfInstallment);
        etDOInspec=(EditText)view.findViewById(R.id.DateOfInspection);
        barcode=(ImageButton) view.findViewById(R.id.barcode);
        barcodee=new Barcode();
        etNameTM=(EditText) view.findViewById(R.id.etNameTM);
        etDesigTM=(EditText) view.findViewById(R.id.etDesinationTM);
        llAddMemebers=(LinearLayout) view.findViewById(R.id.llAddMemebers);
        btnSaveList=(FancyButton) view.findViewById(R.id.btnSaveList);
        teamMember=new ArrayList<String>();
        teamMemberDesig=new ArrayList<String>();
        teamMemberName=new ArrayList<String>();
    }

    void SaveData()
    {
        ContentValues cv =new ContentValues();
        cv.put(DBConstant.C_SerialNo,etSerialno.getText().toString());
        cv.put(DBConstant.C_DateOfInstallment,etDOI.getText().toString());
        cv.put(DBConstant.C_DateOfInspection,etDOInspec.getText().toString());
        cv.put(DBConstant.C_Remarks,etRemarks.getText().toString());
        cv.put(DBConstant.C_Update2,"1");
        db.savefrag3(cv,InpectionId.getId());
    }


    @Override
    public void onResume() {
        etSerialno.setText(barcodee.getBarcodee());
        super.onResume();
    }

    @Override
    public void onDateChanged() {

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = year+"-"+(++monthOfYear)+"-"+ dayOfMonth;
       if(DateviewSelected==0)
        etDOI.setText(date);
        if(DateviewSelected==1)
            etDOInspec.setText(date);

    }
}
