package com.iu.amlibrary;

import android.os.Bundle;

//import amazonia.iu.com.R;
import com.entel.movil.R;
import amazonia.iu.com.amlibrary.activities.DRComplianceActivity;


public class HostComplianceActivity extends DRComplianceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_compliance);
    }
}