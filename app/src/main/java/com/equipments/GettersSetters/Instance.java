package com.equipments.GettersSetters;

/**
 * Created by Administrator on 29/06/2016.
 */
public class Instance {
    public static String getInstanceId() {
        return instanceId;
    }

    public static void setInstanceId(String instanceId) {
        Instance.instanceId = instanceId;
    }

    static String instanceId;

}
