package com.equipments.GettersSetters;

/**
 * Created by Administrator on 16/06/2016.
 */
public class InpectionId {

    public static String getId() {
        return id;
    }

    public static void setId(String id) {
        InpectionId.id = id;
    }

    static String id="-1";

    public static Boolean getNewentry() {
        return Newentry;
    }

    public static void setNewentry(Boolean newentry) {
        Newentry = newentry;
    }

    static Boolean Newentry;
}
