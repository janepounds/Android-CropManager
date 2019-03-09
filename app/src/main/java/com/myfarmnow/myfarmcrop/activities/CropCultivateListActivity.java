package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.myfarmnow.myfarmcrop.R;

public class CropCultivateListActivity extends AppCompatActivity {

    String cropId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_cultivate_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getIntent().hasExtra("cropId")){
            cropId =getIntent().getStringExtra("cropId");
        }
        else{
            finish();
        }


    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.crop_list_activitys_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        //Log.d("ADD NEW SELECTED",item.getItemId()+" "+R.id.action_add_new);

        switch (item.getItemId()) {

            case R.id.action_add_new:
                Intent intent = new Intent(this, CropSoilAnalysisManagerActivity.class);
                intent.putExtra("cropId",cropId);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
