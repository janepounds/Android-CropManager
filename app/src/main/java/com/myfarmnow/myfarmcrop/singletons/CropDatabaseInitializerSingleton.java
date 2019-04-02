package com.myfarmnow.myfarmcrop.singletons;

import android.content.Context;
import android.support.annotation.NonNull;

import com.myfarmnow.myfarmcrop.database.MyFarmDbHandlerSingleton;
import com.myfarmnow.myfarmcrop.models.CropFertilizer;
import com.myfarmnow.myfarmcrop.models.CropItem;

import java.util.ArrayList;

public class CropDatabaseInitializerSingleton {
    public static final String FERTILIZER_TYPE_NPK ="npk";
    public static final String FERTILIZER_TYPE_NITROGENOUS ="nitrogenous";
    public static final String FERTILIZER_TYPE_POTASSIC ="potassic";
    private static final CropDatabaseInitializerSingleton ourInstance = new CropDatabaseInitializerSingleton();


    public static void initialize( MyFarmDbHandlerSingleton dbHandler ){
        initializeCrops(dbHandler);
        initializeFertilizers(dbHandler);
    }

    public static void initializeCrops(@NonNull  MyFarmDbHandlerSingleton dbHandler){
        ArrayList <CropItem> crops = new ArrayList<>();
        crops.add(new CropItem("Maize",120,45,40));
        crops.add(new CropItem("Cotton",75,60,60));
        crops.add(new CropItem("Sorghum Grain",80,60,60));
        crops.add(new CropItem("Peanuts",0,60,60));
        crops.add(new CropItem("Barley",0,0,0));

        for (CropItem cropItem: crops){
            dbHandler.insertCropItem(cropItem);
        }
    }

    public static void initializeFertilizers(@NonNull  MyFarmDbHandlerSingleton dbHandler){
        ArrayList<CropFertilizer> fertilizers = new ArrayList<>();

        fertilizers.add(new CropFertilizer("Diammonium Phosphate - DAP",FERTILIZER_TYPE_NPK,18.0,46.0,0.0));
        fertilizers.add(new CropFertilizer("Sulphur Coated MAP",FERTILIZER_TYPE_NPK,9.0,19.0,0.0));


        fertilizers.add(new CropFertilizer("Urea",FERTILIZER_TYPE_NITROGENOUS,46,0,0));
        fertilizers.add(new CropFertilizer("Nitric Acid",FERTILIZER_TYPE_NITROGENOUS,16,0,0.0));


        fertilizers.add(new CropFertilizer("Muriate of Potash - MOP",FERTILIZER_TYPE_POTASSIC,0,0,60));
        fertilizers.add(new CropFertilizer("Potassium Magnesium Sulphate",FERTILIZER_TYPE_POTASSIC,0,0,21.9));
        for (CropFertilizer fertilizer: fertilizers){
            dbHandler.insertCropFertilizer(fertilizer);
        }
    }

}
