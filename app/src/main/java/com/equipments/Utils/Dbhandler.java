package com.equipments.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.equipments.SpinnerAdapter.Category;
import com.equipments.SpinnerAdapter.District;
import com.equipments.SpinnerAdapter.Equipment;
import com.equipments.SpinnerAdapter.Institute;
import com.equipments.SpinnerAdapter.InstituteType;
import com.equipments.SpinnerAdapter.Manufacturer;
import com.equipments.SpinnerAdapter.Supplier;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

/**
 * Created by Administrator on 13/06/2016.
 */
public class Dbhandler extends SQLiteOpenHelper {

    String LoadMasterMathod = "master";
    String SoapLinkMaster="http://tempuri.org/master";
    final String NameSpace="http://tempuri.org/";
    String URL="http://10.88.229.42:90/Service.asmx";

    JSONObject jsonResponse ;

    public Dbhandler(Context context) {
        super(context, DBConstant.DBNameMaster, null, DBConstant.DBVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        
        db.execSQL(DBConstant.CREATE_TABLE_Category_Master);
        db.execSQL(DBConstant.CREATE_TABLE_District_Master);
        db.execSQL(DBConstant.CREATE_TABLE_Equipment_Entries);
        db.execSQL(DBConstant.CREATE_TABLE_Equipment_Master);
        db.execSQL(DBConstant.CREATE_TABLE_Institute_MASTER);
        db.execSQL(DBConstant.CREATE_TABLE_Institute_Type_Master);
        db.execSQL(DBConstant.CREATE_TABLE_Manufacturer_Master);
        db.execSQL(DBConstant.CREATE_TABLE_Model_Master);
        db.execSQL(DBConstant.CREATE_TABLE_Supplier_Master);
        
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



    public String Load_Master_tables()
    {
        String res= null;
        SoapObject request=new SoapObject(NameSpace, LoadMasterMathod);
        SoapSerializationEnvelope envolpe=new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envolpe.dotNet=true;
        envolpe.setOutputSoapObject(request);
        HttpTransportSE androidHTTP= new HttpTransportSE(URL);

        try {
            androidHTTP.call(SoapLinkMaster, envolpe);
            SoapPrimitive response = (SoapPrimitive)envolpe.getResponse();
            res=response.toString();
            //System.out.println(res);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }

        String[] Response= res.split("#"),JsonNames={"District","EquipmentCategory","EquipmentModel","Institutetype","EquipmentManufacturer","Institute","EquipmentSupplier","EquipmentNameMaster"};
        int lengthJsonArr ;
        try {
            for (int i = 0; i < 8; i++) {
                Response[i]="{ \""+JsonNames[i]+"\" :"+Response[i]+" }";
                jsonResponse = new JSONObject(Response[i]);
                JSONArray jsonMainNode = jsonResponse.optJSONArray(JsonNames[i]);
                lengthJsonArr = jsonMainNode.length();
                for(int j=0; j < lengthJsonArr; j++)
                {
                    ContentValues values = new ContentValues();
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(j);
                    if(i==0)
                    {
                        values.put(DBConstant.C_Dist_Code,jsonChildNode.optString("dcode_ds").toString());
                        values.put(DBConstant.C_Dist_Name,jsonChildNode.optString("ds_detail").toString());
                        SQLiteDatabase writeableDB = getWritableDatabase();
                        writeableDB.insert(DBConstant.T_District_Master, null, values);
                        writeableDB.close();

                    }
                    if(i==1)
                    {

                        values.put(DBConstant.C_Doc_Inst_TypeID,jsonChildNode.optString("Institutetype_id").toString());
                        values.put(DBConstant.C_Doc_Inst_Type_Name,jsonChildNode.optString("Institutetype_detail").toString());
                        SQLiteDatabase writeableDB = getWritableDatabase();
                        writeableDB.insert(DBConstant.T_Doc_Inst_Type, null, values);
                        writeableDB.close();
                    }
                    /*if(i==2)
                    {
                        values.put(DBConstant.C_Doc_Spl_ID,jsonChildNode.optString("Equipmentmodel_id").toString());
                        values.put(DBConstant.C_Doc_Spl_Detail,jsonChildNode.optString("Equipmentmodel_detail").toString());
                        SQLiteDatabase writeableDB = getWritableDatabase();
                        writeableDB.insert(DBConstant.T_Doc_Spl_Type, null, values);
                        writeableDB.close();
                    }*/
                    if(i==2)
                    {

                        values.put(DBConstant.C_Doc_Inst_ID,jsonChildNode.optString("Instid").toString());
                        values.put(DBConstant.C_Doc_Inst_Detail,jsonChildNode.optString("Instname").toString());
                        values.put(DBConstant.C_Doc_Inst_TypeID,jsonChildNode.optString("InstType").toString());
                        values.put(DBConstant.C_Dist_Code,jsonChildNode.optString("HealthInstituteDCode").toString());
                        SQLiteDatabase writeableDB = getWritableDatabase();
                        writeableDB.insert(DBConstant.T_Doc_Inst, null, values);
                        writeableDB.close();
                    }
                    if(i==3)
                    {

                        values.put(DBConstant.C_CategoryId,jsonChildNode.optString("Equipmentcategory_id").toString());
                        values.put(DBConstant.C_CategoryName,jsonChildNode.optString("Equipmentcategory_detail").toString());
                        SQLiteDatabase writeableDB = getWritableDatabase();
                        writeableDB.insert(DBConstant.T_Category, null, values);
                        writeableDB.close();
                    }
                    if(i==4){
                        values.put(DBConstant.C_EquipmentId,jsonChildNode.optString("EquipmentManufacturer_id").toString());
                        values.put(DBConstant.C_EquipmentName,jsonChildNode.optString("EquipmentManufacturer_detail").toString());
                        SQLiteDatabase writeableDB = getWritableDatabase();
                        writeableDB.insert(DBConstant.T_Equipment, null, values);
                        writeableDB.close();

                    }
                    if(i==5)
                    {

                        values.put(DBConstant.C_EquipmentId,jsonChildNode.optString("EquipmentNameMaster_id").toString());
                        values.put(DBConstant.C_EquipmentName,jsonChildNode.optString("EquipmentNameMaster_detail").toString());
                        SQLiteDatabase writeableDB = getWritableDatabase();
                        writeableDB.insert(DBConstant.T_Equipment, null, values);
                        writeableDB.close();
                    }
                    if(i==6)
                    {
                        values.put(DBConstant.C_SupplierId,jsonChildNode.optString("EquipmentSupplier_id").toString());
                        values.put(DBConstant.C_SupplierName,jsonChildNode.optString("EquipmentSupplier_detail").toString());
                        SQLiteDatabase writeableDB = getWritableDatabase();
                        writeableDB.insert(DBConstant.T_Supplier, null, values);
                        writeableDB.close();
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "ErrorServer";
        }
        return  "Success";
    }

    public ArrayList<District> getDistrict()
    {
        SQLiteDatabase db=getReadableDatabase();
        Cursor cr=db.rawQuery("select * from "+DBConstant.T_District_Master,null);
        cr.moveToFirst();
        ArrayList<District> list=new ArrayList<District>();
        do {
            list.add(new District(cr.getString(1),cr.getString(2)));
        }while (cr.moveToNext());
        return list;
    }

    public ArrayList<Category> getCategory()
    {
        SQLiteDatabase db=getReadableDatabase();
        Cursor cr=db.rawQuery("select * from "+DBConstant.T_Category,null);
        cr.moveToFirst();
        ArrayList<Category> list=new ArrayList<>();
        do {
            list.add(new Category(cr.getString(1),cr.getString(2)));
        }while (cr.moveToNext());
        return list;
    }

    public ArrayList<InstituteType> getInstituteType()
    {
        SQLiteDatabase db=getReadableDatabase();
        Cursor cr=db.rawQuery("select * from "+DBConstant.T_Doc_Inst_Type,null);
        cr.moveToFirst();
        ArrayList<InstituteType> list=new ArrayList<InstituteType>();
        do {
            list.add(new InstituteType(cr.getString(1),cr.getString(2)));
        }while (cr.moveToNext());
        return list;
    }
    public ArrayList<Manufacturer> getManufacturer()
    {
        SQLiteDatabase db=getReadableDatabase();
        Cursor cr=db.rawQuery("select * from "+DBConstant.T_Manufacturer,null);
        cr.moveToFirst();
        ArrayList<Manufacturer> list=new ArrayList<Manufacturer>();
        do {
            list.add(new Manufacturer(cr.getString(1),cr.getString(2)));
        }while (cr.moveToNext());
        return list;
    }

    public ArrayList<Supplier> getSupplier()
    {
        SQLiteDatabase db=getReadableDatabase();
        Cursor cr=db.rawQuery("select * from "+DBConstant.T_Supplier,null);
        cr.moveToFirst();
        ArrayList<Supplier> list=new ArrayList<Supplier>();
        do {
            list.add(new Supplier(cr.getString(1),cr.getString(2)));
        }while (cr.moveToNext());
        return list;
    }

    public ArrayList<Institute> getInstitute(String District)
    {
        SQLiteDatabase db=getReadableDatabase();
        Cursor cr=db.rawQuery("select * from "+DBConstant.T_Doc_Inst+" where "+DBConstant.C_Dist_Code+" ="+District,null);
        cr.moveToFirst();
        ArrayList<Institute> list=new ArrayList<Institute>();
        do {
            list.add(new Institute(cr.getString(1),cr.getString(2)));
        }while (cr.moveToNext());
        return list;
    }
    public ArrayList<Equipment> getEquipment()
    {
        SQLiteDatabase db=getReadableDatabase();
        Cursor cr=db.rawQuery("select * from "+DBConstant.T_Equipment,null);
        cr.moveToFirst();
        ArrayList<Equipment> list=new ArrayList<Equipment>();
        do {
            list.add(new Equipment(cr.getString(1),cr.getString(2)));
        }while (cr.moveToNext());
        return list;
    }
}
