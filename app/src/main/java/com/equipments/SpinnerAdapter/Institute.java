package com.equipments.SpinnerAdapter;

/**
 * Created by Administrator on 13/06/2016.
 */
public class Institute {
    public Institute(String institutetype_id, String institutetype_detail) {
        Institutetype_id = institutetype_id;
        Institutetype_detail = institutetype_detail;
    }

    String Institutetype_id;

    public String getInstitutetype_detail() {
        return Institutetype_detail;
    }

    public void setInstitutetype_detail(String institutetype_detail) {
        Institutetype_detail = institutetype_detail;
    }

    public String getInstitutetype_id() {
        return Institutetype_id;
    }

    public void setInstitutetype_id(String institutetype_id) {
        Institutetype_id = institutetype_id;
    }

    String Institutetype_detail;

    public String toString()
    {
        return Institutetype_detail;
    }
}
