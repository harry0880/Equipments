package com.equipments.SpinnerAdapter;

/**
 * Created by Administrator on 13/06/2016.
 */
public class Institute {
    public Institute(String institute_id, String institute_detail) {

        this.Institute_id = institute_id;
        this.Institute_detail = institute_detail;

        Institute_id = institute_id;
        Institute_detail = institute_detail;
    }

    String Institute_id;

    public String getInstitute_detail() {
        return Institute_detail;
    }

    public void setInstitute_detail(String institute_detail) {
        Institute_detail = institute_detail;
    }

    public String getInstitute_id() {
        return Institute_id;
    }

    public void setInstitute_id(String institute_id) {
        Institute_id = institute_id;
    }

    String Institute_detail;
}
