package com.equipments.SpinnerAdapter;

/**
 * Created by Administrator on 13/06/2016.
 */
public class Supplier {

    public String getEquipmentSupplier_id() {
        return EquipmentSupplier_id;
    }

    public void setEquipmentSupplier_id(String equipmentSupplier_id) {
        EquipmentSupplier_id = equipmentSupplier_id;
    }

    public String getEquipmentSupplier_detail() {
        return EquipmentSupplier_detail;
    }

    public void setEquipmentSupplier_detail(String equipmentSupplier_detail) {
        EquipmentSupplier_detail = equipmentSupplier_detail;
    }

    String EquipmentSupplier_id;

    public Supplier(String equipmentSupplier_id, String equipmentSupplier_detail) {
        EquipmentSupplier_id = equipmentSupplier_id;
        EquipmentSupplier_detail = equipmentSupplier_detail;
    }

    String EquipmentSupplier_detail;

    public String toString()
    {
        return EquipmentSupplier_detail;
    }

}
