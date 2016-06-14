package com.equipments.SpinnerAdapter;

/**
 * Created by Administrator on 13/06/2016.
 */
public class Manufacturer {

    String EquipmentManufacturer_id;

    public String getEquipmentManufacturer_id() {
        return EquipmentManufacturer_id;
    }

    public void setEquipmentManufacturer_id(String equipmentManufacturer_id) {
        EquipmentManufacturer_id = equipmentManufacturer_id;
    }

    public String getEquipmentManufacturer_detail() {
        return EquipmentManufacturer_detail;
    }

    public void setEquipmentManufacturer_detail(String equipmentManufacturer_detail) {
        EquipmentManufacturer_detail = equipmentManufacturer_detail;
    }

    public Manufacturer(String equipmentManufacturer_id, String equipmentManufacturer_detail) {
        EquipmentManufacturer_id = equipmentManufacturer_id;
        EquipmentManufacturer_detail = equipmentManufacturer_detail;
    }

    String EquipmentManufacturer_detail;

    public String toString()
    {
        return EquipmentManufacturer_detail;
    }

}
