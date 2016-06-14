package com.equipments.SpinnerAdapter;

/**
 * Created by Administrator on 13/06/2016.
 */
public class Category {

    public String getEquipmentcategory_id() {
        return Equipmentcategory_id;
    }

    public void setEquipmentcategory_id(String equipmentcategory_id) {
        Equipmentcategory_id = equipmentcategory_id;
    }

    public String getEquipmentcategory_detail() {
        return Equipmentcategory_detail;
    }

    public void setEquipmentcategory_detail(String equipmentcategory_detail) {
        Equipmentcategory_detail = equipmentcategory_detail;
    }

    String Equipmentcategory_id;

    public Category(String equipmentcategory_id, String equipmentcategory_detail) {
        Equipmentcategory_id = equipmentcategory_id;
        Equipmentcategory_detail = equipmentcategory_detail;
    }

    String Equipmentcategory_detail;

    public String toString()
    {
        return Equipmentcategory_detail;
    }
}
