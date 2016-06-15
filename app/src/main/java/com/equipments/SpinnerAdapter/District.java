package com.equipments.SpinnerAdapter;

/**
 * Created by Administrator on 13/06/2016.
 */
public class District {

    public District(String dcode_ds, String ds_detail) {
        this.dcode_ds = dcode_ds;
        this.ds_detail = ds_detail;
    }

    String dcode_ds;

    public String getDs_detail() {
        return ds_detail;
    }

    public void setDs_detail(String ds_detail) {
        this.ds_detail = ds_detail;
    }

    public String getDcode_ds() {
        return dcode_ds;
    }

    public void setDcode_ds(String dcode_ds) {
        this.dcode_ds = dcode_ds;
    }

    public String toString()
    {
        return ds_detail;
    }

    String ds_detail;
}
