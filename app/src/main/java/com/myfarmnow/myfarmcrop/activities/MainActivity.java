package com.myfarmnow.myfarmcrop.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.MenuItem;
import android.util.Log;


import com.myfarmnow.myfarmcrop.R;
import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropField;
import com.myfarmnow.myfarmcrop.models.CropInventoryFertilizer;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton floatingBtn = (FloatingActionButton) findViewById(R.id.fab);
        floatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        MyFarmDbHandlerSingleton handler = MyFarmDbHandlerSingleton.getHandlerInstance(this);
       // handler.openDB();

        CropInventoryFertilizer x = new CropInventoryFertilizer();

        x.setPurchaseDate("2019-02-01");
        x.setType("Solid Manufactured");
        x.setFertilizerName("Solid Manufactured");
        float y =45;
        x.setQuantity(y);
        x.setUserId("1");
        x.setBatchNumber("3562783738");

        handler.insertCropFertilizerInventory(x);

        ArrayList<CropInventoryFertilizer> list = handler.getCropFertilizerInventorys("1");

        Log.d("FERTILIZERS",list.toString());


        CropField n = new CropField();


        n.setSoilType("Sand");
       n.setSoilCategory("Shallow");
        n.setFieldName("Field1");
        n.setWatercourse("Dry Ditch");
        float z =45;
        n.setTotalArea(z);
        n.setUserId("1");
        float b = (float) 20.5;
        n.setCroppableArea(b);



        handler.insertCropField(n);
        //handler.updateCropField(n);

        ArrayList<CropField> robert = handler.getCropFields("1");

        Log.d("FIELDS-before",robert.toString());
        handler.deleteCropField("1");

        ArrayList<CropField> georgia = handler.getCropFields("1");
        for(int i=0; i<georgia.size();i++)
        {
            CropField field = robert.get(i);
            field.setSoilCategory("Medium");
            handler.updateCropField(field);
        }

        georgia =handler.getCropFields("1");

        Log.d("FIELDS-after",georgia.toString());




    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
