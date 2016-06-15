package com.equipments.GettersSetters;

/**
 * Created by ABHISHEK on 6/15/2016.
 */
public class CardGetSet {
    public CardGetSet(String id, String intituteType, String machineryname) {
        Id = id;
        IntituteType = intituteType;
        Machineryname = machineryname;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getIntituteType() {
        return IntituteType;
    }

    public void setIntituteType(String intituteType) {
        IntituteType = intituteType;
    }

    public String getMachineryname() {
        return Machineryname;
    }

    public void setMachineryname(String machineryname) {
        Machineryname = machineryname;
    }

    String Id,IntituteType,Machineryname="Not Available";
}
