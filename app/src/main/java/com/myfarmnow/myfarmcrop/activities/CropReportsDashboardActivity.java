package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.myfarmnow.myfarmcrop.R;

public class CropReportsDashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_reports_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       //
       //
    }

    public void openYieldPerfomance(View view){
        Intent openReports = new Intent(this, CropYieldPerformanceActivity.class);
        startActivity(openReports);
    }
    public void openGraphs(View view){
        Intent openReports = new Intent(this, CropFarmReportsActivity.class);
        startActivity(openReports);
    }

}
