package com.equipments.Utils;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.equipments.R;

/**
 * Created by Administrator on 15/06/2016.
 */
public class TodoCursorAdapter extends CursorAdapter {

    Dbhandler db;
    public TodoCursorAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, 0);
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.listview, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView tvInstitute = (TextView) view.findViewById(R.id.tvInstitute);
        TextView tvEquipment = (TextView) view.findViewById(R.id.tvEquipment);

        // Extract properties from cursor
        String InstId = cursor.getString(cursor.getColumnIndexOrThrow(DBConstant.C_Doc_Inst_ID));
        String EqipId = cursor.getString(cursor.getColumnIndexOrThrow(DBConstant.C_EquipmentId));
        Dbhandler db=new Dbhandler(context);
        // Populate fields with extracted properties
        tvInstitute.setText(db.getInstName(InstId));
        tvEquipment.setText(db.getEquipmentName(EqipId));
    }
}