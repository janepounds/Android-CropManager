package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.adapters.CropTasksListRecyclerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;

public class CropTasksListActivity extends AppCompatActivity {
    RecyclerView tasksListRecyclerView;
    CropTasksListRecyclerAdapter cropTasksListRecyclerAdapter;
    LinearLayoutManager linearLayoutManager;
    MyFarmDbHandlerSingleton dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_tasks_list);

        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);
        tasksListRecyclerView = findViewById(R.id.crop_task_recyc_view);
        cropTasksListRecyclerAdapter = new CropTasksListRecyclerAdapter(this,dbHandler.getCropTasks(CropDashboardActivity.getPreferences("userId",this)));
        tasksListRecyclerView.setAdapter(cropTasksListRecyclerAdapter);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        tasksListRecyclerView.setLayoutManager(linearLayoutManager);

    }



    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.crop_list_activitys_menu, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_add_new:
                openCropTaskActivity();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openCropTaskActivity() {
        Intent intent = new Intent(this, CropTaskManagerActivity.class);
        startActivity(intent);
    }
}
