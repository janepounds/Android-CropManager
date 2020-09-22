package com.myfarmnow.myfarmcrop.activities;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.adapters.CropContactsListRecyclerAdapter;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;

public class CropContactsListActivity extends AppCompatActivity {

    RecyclerView contactsListRecyclerView;
    CropContactsListRecyclerAdapter cropContactsListRecyclerAdapter;
    LinearLayoutManager linearLayoutManager;
    MyFarmDbHandlerSingleton dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_contacts_list);

        dbHandler= MyFarmDbHandlerSingleton.getHandlerInstance(this);
        contactsListRecyclerView = findViewById(R.id.crop_contact_recyc_view);
        cropContactsListRecyclerAdapter = new CropContactsListRecyclerAdapter(this,dbHandler.getCropContacts(DashboardActivity.RETRIEVED_USER_ID));
        contactsListRecyclerView.setAdapter(cropContactsListRecyclerAdapter);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        contactsListRecyclerView.setLayoutManager(linearLayoutManager);

    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.crop_list_activitys_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_add_new:
                openCropContactManagerActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openCropContactManagerActivity() {
        Intent intent = new Intent(this, CropContactManagerActivity.class);
        startActivity(intent);
    }

}
