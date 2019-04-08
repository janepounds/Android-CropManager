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
        crops.add(new CropItem("Barley",0,0,0));//TODO change these values
        crops.add(new CropItem("Oats",0,0,0));//TODO change these values
        crops.add(new CropItem("Wheat",0,0,0));//TODO change these values
        crops.add(new CropItem("Sorghum",150,60,120));
        crops.add(new CropItem("Soybean",0,40,80));
        crops.add(new CropItem("Sugarcane",80,60,60));



        //FOR NUTRIENT REMOVAL
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Alfafa (DM)",26,6,25));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Alsike Clover (DM)",21,5.5,27));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Bahiagrass",22,6,18));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Barley Grain",21,8.3,6.7));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Barley Straw per t of grain",8.3,3.3,25));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Barley Straw",6.5,2.6,20));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Beans (dry)",50,13,15));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Bermuda grass",23,6,25));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Birdsfoot trefoil (DM)",23,5.5,21));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Bluegrass (DM)",15,6,23));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Bromegrass (DM)",16,5.0,23));

        for (CropItem cropItem: crops){
            dbHandler.insertCropItem(cropItem);
        }
    }

    public static void initializeFertilizers(@NonNull  MyFarmDbHandlerSingleton dbHandler){
        ArrayList<CropFertilizer> fertilizers = new ArrayList<>();

        fertilizers.add(new CropFertilizer("Diammonium Phosphate - DAP",FERTILIZER_TYPE_NPK,18.0,46.0,0.0));
        fertilizers.add(new CropFertilizer("Sulphur Coated MAP",FERTILIZER_TYPE_NPK,9.0,19.0,0.0));
        fertilizers.add(new CropFertilizer("Ammonium Polyphosphate Solution",FERTILIZER_TYPE_NPK,10,34,0));
        fertilizers.add(new CropFertilizer("Mono-ammonium Phosphate",FERTILIZER_TYPE_NPK,11,52,0));
        fertilizers.add(new CropFertilizer("Mono-ammonium Phosphate",FERTILIZER_TYPE_NPK,11,22,0));
        fertilizers.add(new CropFertilizer("Ammonium Phosphate",FERTILIZER_TYPE_NPK,14,28,0));
        fertilizers.add(new CropFertilizer("Nitrophosphate with Potash",FERTILIZER_TYPE_NPK,15,15,15));
        fertilizers.add(new CropFertilizer("Diamonium Phosphate",FERTILIZER_TYPE_NPK,16,44,0));
        fertilizers.add(new CropFertilizer("Sulphur Coated DAP",FERTILIZER_TYPE_NPK,16,18,0));
        fertilizers.add(new CropFertilizer("Diammonium Phosphate - DAP",FERTILIZER_TYPE_NPK,18,20,0));


        fertilizers.add(new CropFertilizer("Urea",FERTILIZER_TYPE_NITROGENOUS,46,0,0));
        fertilizers.add(new CropFertilizer("Nitric Acid",FERTILIZER_TYPE_NITROGENOUS,13,0,0.0));
        fertilizers.add(new CropFertilizer("Calcium Nitrate",FERTILIZER_TYPE_NITROGENOUS,16,0,0.0));
        fertilizers.add(new CropFertilizer("Sodium Nitrate",FERTILIZER_TYPE_NITROGENOUS,16,0,0.0));
        fertilizers.add(new CropFertilizer("Ammonium Sulphate",FERTILIZER_TYPE_NITROGENOUS,21,0,0.0));
        fertilizers.add(new CropFertilizer("Ammonia Aqua",FERTILIZER_TYPE_NITROGENOUS,21,0,0.0));
        fertilizers.add(new CropFertilizer("Sulphate of ammonia",FERTILIZER_TYPE_NITROGENOUS,21,0,0.0));
        fertilizers.add(new CropFertilizer("Nitric Acid",FERTILIZER_TYPE_NITROGENOUS,22.2,0,0.0));
        fertilizers.add(new CropFertilizer("Ammonia, Aqua",FERTILIZER_TYPE_NITROGENOUS,24,0,0.0));
        fertilizers.add(new CropFertilizer("Calcium Ammonium Nitrate",FERTILIZER_TYPE_NITROGENOUS,25,0,0.0));
        fertilizers.add(new CropFertilizer("Ammonium Chloride",FERTILIZER_TYPE_NITROGENOUS,25,0,0.0));


        fertilizers.add(new CropFertilizer("Muriate of Potash - MOP",FERTILIZER_TYPE_POTASSIC,0,0,60));
        fertilizers.add(new CropFertilizer("Potassium Magnesium Sulphate",FERTILIZER_TYPE_POTASSIC,0,0,21.9));
        fertilizers.add(new CropFertilizer("Potassium Schoenite",FERTILIZER_TYPE_POTASSIC,0,0,23));
        fertilizers.add(new CropFertilizer("Sulphate of Potash Magnesium",FERTILIZER_TYPE_POTASSIC,0,0,30));
        fertilizers.add(new CropFertilizer("Sulphate of Potash - SOP",FERTILIZER_TYPE_POTASSIC,0,0,41));
        fertilizers.add(new CropFertilizer("Potassium Nitrate",FERTILIZER_TYPE_POTASSIC,0,0,44));
        fertilizers.add(new CropFertilizer("Muriate of Potash - MOP",FERTILIZER_TYPE_POTASSIC,0,0,50));
        fertilizers.add(new CropFertilizer("Sulphate of Potash - SOP",FERTILIZER_TYPE_POTASSIC,0,0,50));
        for (CropFertilizer fertilizer: fertilizers){
            dbHandler.insertCropFertilizer(fertilizer);
        }
    }

}
