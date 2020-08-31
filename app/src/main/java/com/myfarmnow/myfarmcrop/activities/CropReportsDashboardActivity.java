package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.activities.predictiontools.CropYieldPerformanceActivity;

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
