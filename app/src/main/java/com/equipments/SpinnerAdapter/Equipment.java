package com.equipments.SpinnerAdapter;

/**
 * Created by Administrator on 13/06/2016.
 */
public class Equipment {

    String EquipmentNameMaster_id;

    public Equipment(String equipmentNameMaster_id, String equipmentNameMaster_detail) {
        EquipmentNameMaster_id = equipmentNameMaster_id;
        EquipmentNameMaster_detail = equipmentNameMaster_detail;
    }

    public String getEquipmentNameMaster_detail() {
        return EquipmentNameMaster_detail;
    }

    public void setEquipmentNameMaster_detail(String equipmentNameMaster_detail) {
        EquipmentNameMaster_detail = equipmentNameMaster_detail;
    }

    public String getEquipmentNameMaster_id() {
        return EquipmentNameMaster_id;
    }

    public void setEquipmentNameMaster_id(String equipmentNameMaster_id) {
        EquipmentNameMaster_id = equipmentNameMaster_id;
    }

    String EquipmentNameMaster_detail;
}
