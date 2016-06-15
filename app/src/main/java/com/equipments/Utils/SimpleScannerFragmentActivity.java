package com.equipments.Utils;

import android.os.Bundle;

import com.equipments.BaseScannerActivity;
import com.equipments.R;

public class SimpleScannerFragmentActivity extends BaseScannerActivity {
    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_simple_scanner_fragment);
        setupToolbar();
    }
}