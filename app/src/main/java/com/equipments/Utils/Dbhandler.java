package com.equipments.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.equipments.GettersSetters.CardGetSet;
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
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

/**
 * Created by Administrator on 13/06/2016.
 */
public class Dbhandler extends SQLiteOpenHelper {


    final String NameSpace="http://tempuri.org/";
    String URL="http://10.88.229.42:90/Service.asmx";

    String LoadMasterMathod = "master";
    String SoapLinkMaster="http://tempuri.org/master";

    String SendEquipmentsEnntry = "getEquipmentsEnntry";
    String SoapLinkSendEquipmentsEnntry="http://tempuri.org/getEquipmentsEnntry";


    static String Id="0";
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
        db.execSQL(DBConstant.Create_Table_Image);
        
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

        String[] Response= res.split("#"),JsonNames={"District","EquipmentCategory","Institutetype","EquipmentManufacturer","Institute","EquipmentSupplier","EquipmentNameMaster"};
        int lengthJsonArr ;
        try {
            for (int i = 0; i < JsonNames.length; i++) {
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
                        values.put(DBConstant.C_ManufacturerID,jsonChildNode.optString("EquipmentManufacturer_id").toString());
                        values.put(DBConstant.C_ManufacturerName,jsonChildNode.optString("EquipmentManufacturer_detail").toString());
                        SQLiteDatabase writeableDB = getWritableDatabase();
                        writeableDB.insert(DBConstant.T_Manufacturer, null, values);
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
public Boolean SendEquipmentEntries(String id) {
    SQLiteDatabase db = getReadableDatabase();
    Cursor cursor = db.rawQuery("select * from " + DBConstant.T_Inspection_Entries + ";", null);
    cursor.moveToFirst();
    if (cursor.getCount() <= 0) {
        return false;
    } else {
        do {
            String res = null;
            SoapObject request = new SoapObject(NameSpace, SendEquipmentsEnntry);
            PropertyInfo pi = new PropertyInfo();

            pi.setName("AndroidRowId");
            pi.setValue(cursor.getString(cursor.getColumnIndex(DBConstant.C_ID)));
            pi.setType(String.class);
            request.addProperty(pi);


          pi = new PropertyInfo();
            pi.setName("UserId");
              pi.setValue("userid");
          /*  pi.setValue(cursor.getString(cursor.getColumnIndex(DBConstant.))); add username*/
            pi.setType(String.class);
            request.addProperty(pi);

            pi = new PropertyInfo();
            pi.setName("DistrictId");
            pi.setValue(cursor.getString(cursor.getColumnIndex(DBConstant.C_Dist_Code)));
            pi.setType(String.class);
            request.addProperty(pi);

            pi = new PropertyInfo();
            pi.setName("InstituteTypeId");
            pi.setValue(cursor.getString(cursor.getColumnIndex(DBConstant.C_Doc_Inst_TypeID)));
            pi.setType(String.class);
            request.addProperty(pi);

            pi = new PropertyInfo();
            pi.setName("InstituteId");
            pi.setValue(cursor.getString(cursor.getColumnIndex(DBConstant.C_Doc_Inst_ID)));
            pi.setType(String.class);
            request.addProperty(pi);

            pi = new PropertyInfo();
            pi.setName("EquipmentId");
            pi.setValue(cursor.getString(cursor.getColumnIndex(DBConstant.C_EquipmentId)));
            pi.setType(String.class);
            request.addProperty(pi);

            pi = new PropertyInfo();
            pi.setName("EquipmentCategoryId");
            pi.setValue(cursor.getString(cursor.getColumnIndex(DBConstant.C_CategoryId)));
            pi.setType(String.class);
            request.addProperty(pi);

            pi = new PropertyInfo();
            pi.setName("ManufacturerNameId");
            pi.setValue(cursor.getString(cursor.getColumnIndex(DBConstant.C_ManufacturerID)));
            pi.setType(String.class);
            request.addProperty(pi);

            pi = new PropertyInfo();
            pi.setName("SuplierNameId");
            pi.setValue(cursor.getString(cursor.getColumnIndex(DBConstant.C_SupplierId)));
            pi.setType(String.class);
            request.addProperty(pi);

            pi = new PropertyInfo();
            pi.setName("ModelNameId");
            pi.setValue(cursor.getString(cursor.getColumnIndex(DBConstant.C_ModelId)));
            pi.setType(String.class);
            request.addProperty(pi);

            pi = new PropertyInfo();
            pi.setName("SerialNumber");
            pi.setValue(cursor.getString(cursor.getColumnIndex(DBConstant.C_SerialNo)));
            pi.setType(String.class);
            request.addProperty(pi);

            pi = new PropertyInfo();
            pi.setName("InstallationDate");
            pi.setValue(cursor.getString(cursor.getColumnIndex(DBConstant.C_DateOfInstallment)));
            pi.setType(String.class);
            request.addProperty(pi);

            pi = new PropertyInfo();
            pi.setName("InspectionDate");
            pi.setValue(cursor.getString(cursor.getColumnIndex(DBConstant.C_DateOfInspection)));
            pi.setType(String.class);
            request.addProperty(pi);

            pi = new PropertyInfo();
            pi.setName("AdnroidEntryDate");
            pi.setValue(cursor.getString(cursor.getColumnIndex(DBConstant.C_DateOfInspectionGPS)));
            pi.setType(String.class);
            request.addProperty(pi);


            pi = new PropertyInfo();
            pi.setName("Location");
            pi.setValue("Loc");
        /*    pi.setValue(cursor.getString(cursor.getColumnIndex())); location*/
            pi.setType(String.class);
            request.addProperty(pi);

            pi = new PropertyInfo();
            pi.setName("Remarks");
            pi.setValue(cursor.getString(cursor.getColumnIndex(DBConstant.C_Remarks)));
            pi.setType(String.class);
            request.addProperty(pi);

            pi = new PropertyInfo();
            pi.setName("CreatedBy");
            pi.setValue("createdby");
            /*pi.setValue(cursor.getString(cursor.getColumnIndex())); username*/
            pi.setType(String.class);
            request.addProperty(pi);


            SoapSerializationEnvelope envolpe = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envolpe.dotNet = true;
            envolpe.setOutputSoapObject(request);
            HttpTransportSE androidHTTP = new HttpTransportSE(URL);

            try {
                androidHTTP.call(SoapLinkSendEquipmentsEnntry, envolpe);
                SoapPrimitive response = (SoapPrimitive) envolpe.getResponse();
                res = response.toString();
                //System.out.println(res);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } while (cursor.moveToNext());


    }
}
    public ArrayList<District> getDistrict()
    {
        SQLiteDatabase db=getReadableDatabase();
        Cursor cr=db.rawQuery("select * from "+DBConstant.T_District_Master,null);
        cr.moveToFirst();
        ArrayList<District> list=new ArrayList<District>();
        do {
            list.add(new District(cr.getString(0),cr.getString(1)));
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
            list.add(new Category(cr.getString(0),cr.getString(1)));
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
            list.add(new InstituteType(cr.getString(0),cr.getString(1)));
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
            list.add(new Manufacturer(cr.getString(0),cr.getString(1)));
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
            list.add(new Supplier(cr.getString(0),cr.getString(1)));
        }while (cr.moveToNext());
        return list;
    }

    public ArrayList<Institute> getInstitute(String District,String InstituteType) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cr = db.rawQuery("select * from " + DBConstant.T_Doc_Inst + " where " + DBConstant.C_Dist_Code + " ='" + District + "' and " + DBConstant.C_Doc_Inst_TypeID + "='" + InstituteType + "'", null);
        cr.moveToFirst();
        ArrayList<Institute> list = new ArrayList<Institute>();
        if (cr.getCount() > 0)
        {
            do {
                list.add(new Institute(cr.getString(0), cr.getString(1)));
            } while (cr.moveToNext());
    }
        return list;
    }
    public ArrayList<Equipment> getEquipment()
    {
        SQLiteDatabase db=getReadableDatabase();
        Cursor cr=db.rawQuery("select * from "+DBConstant.T_Equipment,null);
        cr.moveToFirst();
        ArrayList<Equipment> list=new ArrayList<Equipment>();
        do {
            list.add(new Equipment(cr.getString(0),cr.getString(1)));
        }while (cr.moveToNext());
        return list;
    }

    public long SaveFrag1(ContentValues cv)
    {
        SQLiteDatabase db=getWritableDatabase();
        long id=db.insert(DBConstant.T_Inspection_Entries,null,cv);
        Id=id+"";
        db.close();
        return id;

    }

    public String getId()
    {
        return  Id;
    }

    public void UpdateFrag2(ContentValues cv,String idd)
    {
        SQLiteDatabase db=getWritableDatabase();
        db.update(DBConstant.T_Inspection_Entries,cv,DBConstant.C_ID+"='"+idd+"'",null);
        db.close();
    }

    public void savefrag3(ContentValues cv,String idd)
    {
        SQLiteDatabase db=getWritableDatabase();
        db.update(DBConstant.T_Inspection_Entries,cv,DBConstant.C_ID+"='"+idd+"'",null);
        db.close();
    }

    public Boolean saveimg(ContentValues cv)
    {
        SQLiteDatabase db=getWritableDatabase();
        if(db.insert(DBConstant.TBL_Img_Data,null,cv)!=-1)
            return true;
        else
            return false;
    }

    public Bitmap getImage(int id, String UserID)
    {
        Bitmap decodedByte;
        SQLiteDatabase db = getReadableDatabase();
        Cursor result=null;
        try {
            result=db.rawQuery("select * from " + DBConstant.TBL_Img_Data + " where " + DBConstant.C_Image_Id + "='" +id+ "' and "+DBConstant.C_ID+"='"+UserID+"';",null);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        int d=result.getCount();
        if (result.getCount() <= 0)
        {
            return null;
        }
        result.moveToFirst();

        do {
            byte[] arr= Base64.decode(result.getString(1), Base64.DEFAULT);
            decodedByte = BitmapFactory.decodeByteArray(arr, 0, arr.length);
        } while (result.moveToNext());
        db.close();
        return decodedByte;
    }

    public String getEquipmentName(String EqipId)
    {
        SQLiteDatabase db=getReadableDatabase();
        Cursor cr=db.rawQuery("select "+DBConstant.C_EquipmentName+" from "+DBConstant.T_Equipment+" where "+DBConstant.C_EquipmentId+"='"+EqipId+"'",null);
        cr.moveToFirst();
        return cr.getString(0);
    }

    public String getInstName(String InstId)
    {
        SQLiteDatabase db=getReadableDatabase();
        Cursor cr=db.rawQuery("select "+DBConstant.C_Doc_Inst_Detail+" from "+DBConstant.T_Doc_Inst+" where "+DBConstant.C_Doc_Inst_ID+"='"+InstId+"'",null);
        cr.moveToFirst();
        return cr.getString(0);

    }

    public ArrayList<CardGetSet> getCardData()
    {
        SQLiteDatabase db=getReadableDatabase();
        Cursor cr=db.rawQuery("select * from " + DBConstant.T_Inspection_Entries + ";", null);
        cr.moveToFirst();
        ArrayList<CardGetSet> arr=new ArrayList<>();
        if(cr.getCount()>0)
        {
            do {
              arr.add(new CardGetSet(cr.getString(cr.getColumnIndex(DBConstant.C_ID)),getInstName(cr.getString(cr.getColumnIndex(DBConstant.C_Doc_Inst_TypeID))),getInstName(cr.getString(cr.getColumnIndex(DBConstant.C_EquipmentId)))));
            }while(cr.moveToNext());

        }
        db.close();
        return arr;
    }

    public ArrayList<Cursor> getData(String Query) {
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[] { "mesage" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);


        try{
            String maxQuery = Query ;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);


            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {


                alc.set(0,c);
                c.moveToFirst();

                return alc ;
            }
            return alc;
        } catch(Exception sqlEx){
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        }

    }


}
