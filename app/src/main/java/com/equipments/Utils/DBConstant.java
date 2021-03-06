package com.equipments.Utils;

/**
 * Created by Administrator on 08/06/2016.
 */
public class DBConstant {

    public static final String DBNameData="InspectionNoteEntry";
    public static final String DBNameMaster="Master";
    public static final int DBVersion=2;

    public static final String T_District_Master="TBLDistrictMaster";
    public  static final String C_Dist_Code="Dcode";
    public  static final String C_Dist_Name="Dname";

    public  static final String T_Doc_Inst="TBLInsMaster";
    public  static final String C_Doc_Inst_ID="InstID";
    public  static final String C_Doc_Inst_Detail="DocInstName";

    public  static final String T_Doc_Inst_Type="TBLDoc_Inst_Type";
    public  static final String C_Doc_Inst_TypeID="Doc_Inst_TypeID";
    public  static final String C_Doc_Inst_Type_Name="Doc_Inst_Type_Name";

    public  static final String T_Category="TBLCategory";
    public  static final String C_CategoryId="CategoryID";
    public  static final String C_CategoryName="CategoryName";

    public  static final String T_Manufacturer="TBLManufacturer";
    public  static final String C_ManufacturerID="ManucafaturerID";
    public  static final String C_ManufacturerName="ManufacturerName";

    public  static final String T_Model="TBLModel";
    public  static final String C_ModelId="ModelId";
    public  static final String C_ModelName="ModelName";

    public  static final String T_Equipment="TBLEquipment";
    public  static final String C_EquipmentId="EquipmentId";
    public  static final String C_EquipmentName="EquipmentName";

    public  static final String T_Supplier="Supplier";
    public  static final String C_SupplierId="SupplierId";
    public  static final String C_SupplierName="SupplierName";

    public  static final String T_TeamMembers="TBLTeamMembers";
    public  static final String C_TeamMemberId="TeamMemberID";
    public  static final String C_TeamMemberName="TeamMemberName";
    public  static final String C_TeamMemberDesignation="TeamMemberDesignation";

    public static final String T_Inspection_Entries="InspectionEntries";

    public static final String C_ID="_id";
    public static final String C_SerialNo="SerialNo";
    public static final String C_DateOfInstallment="DateOfInstallment";
    public static final String C_Remarks="Remarks";
    public static final String C_DateOfInspection="DateOfInspection";
    public static final String C_DateOfInspectionGPS="DateOfInspectionGPS";
    public static final String C_Location="Location";
    public static final String C_Update1="update1";
    public static final String C_Update2="update2";

    public final static String TBL_Img_Data="Img";
    public final static String C_Image="Image";
    public final static String C_Image_Id="Image_Id";
    public final static String C_Image_Desc="Desc";

    public final static int C_No_of_Image=5;

    public final static String T_Login="Login";
    public final static String C_UserId="Userid";
    public final static String C_Password="Password";

    public static final String CREATE_TABLE_Login="CREATE TABLE "+T_Login+"(" +C_UserId+" TEXT,"
            +C_Password+ " TEXT);";

    public static final String CREATE_TABLE_District_Master= "CREATE TABLE "+ T_District_Master + "(" + C_Dist_Code/*1*/ + " TEXT,"
            + C_Dist_Name/*2*/+ " TEXT);";

    public static final String CREATE_TABLE_Institute_Type_Master = "CREATE TABLE "+ T_Doc_Inst_Type + "(" + C_Doc_Inst_TypeID/*0*/ + " TEXT,"
            +C_Doc_Inst_Type_Name+" TEXT);";

    public static final String CREATE_TABLE_Institute_MASTER = "CREATE TABLE "+ T_Doc_Inst + "(" + C_Doc_Inst_ID/*0*/ + " TEXT,"
            + C_Doc_Inst_Detail/*1*/ +" TEXT,"
            + C_Doc_Inst_TypeID/*1*/ +" TEXT,"
            +C_Dist_Code+" TEXT);";
    public static final String CREATE_TABLE_Category_Master= "CREATE TABLE "+ T_Category + "(" + C_CategoryId + " TEXT,"
            + C_CategoryName+ " TEXT);";

    public static final String CREATE_TABLE_Manufacturer_Master= "CREATE TABLE "+ T_Manufacturer + "(" + C_ManufacturerID + " TEXT,"
            + C_ManufacturerName+ " TEXT);";

    public static final String CREATE_TABLE_Model_Master= "CREATE TABLE "+ T_Model + "(" + C_ModelId + " TEXT,"
            + C_ModelName+ " TEXT);";

    public static final String CREATE_TABLE_Equipment_Master= "CREATE TABLE "+ T_Equipment + "(" + C_EquipmentId + " TEXT,"
            + C_EquipmentName+ " TEXT);";

    public static final String CREATE_TABLE_Supplier_Master= "CREATE TABLE "+ T_Supplier + "(" + C_SupplierId + " TEXT,"
            + C_SupplierName+ " TEXT);";

    public static final String CREATE_TABLE_Team_Memebers= "CREATE TABLE "+ T_TeamMembers + "(" + C_ID + " TEXT,"
            +C_TeamMemberId+" TEXT,"
            +C_TeamMemberName+" TEXT,"
            + C_TeamMemberDesignation+ " TEXT);";

    public static final String CREATE_TABLE_Equipment_Entries= "CREATE TABLE "+ T_Inspection_Entries + "(" + C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + C_Dist_Code+ " TEXT,"
            +C_Doc_Inst_ID+" TEXT,"
            +C_Doc_Inst_TypeID+" TEXT,"
            +C_CategoryId+ " TEXT,"
            +C_ManufacturerID+" TEXT,"
            +C_ModelId+" TEXT,"
            +C_EquipmentId+" TEXT,"
            +C_SupplierId+" TEXT,"
            +C_SerialNo+" TEXT,"
            +C_DateOfInstallment+" TEXT,"
            +C_DateOfInspection+" TEXT,"
            +C_DateOfInspectionGPS+" TEXT,"
            +C_Location+" TEXT,"
            +C_Remarks+ " TEXT,"
            +C_Update1+ " TEXT,"
            +C_Update2+ " TEXT);";

    public final static String Create_Table_Image="Create table "+ TBL_Img_Data+ "( "+C_ID+" TEXT,"
            +C_Image+" TEXT,"
            +C_Image_Id+" TEXT,"
            +C_Image_Desc+" TEXT)";


}
