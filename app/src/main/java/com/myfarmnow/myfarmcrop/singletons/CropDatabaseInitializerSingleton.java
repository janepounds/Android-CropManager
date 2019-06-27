package com.myfarmnow.myfarmcrop.singletons;

import androidx.annotation.NonNull;

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
        crops.add(new CropItem("Sunflower",80,60,60));
        crops.add(new CropItem("Tobacco",50,80,150));
        crops.add(new CropItem("Corn(for grain)",360,130,240));
        crops.add(new CropItem("Alfalfa",0,130,200));
        crops.add(new CropItem("Crimson Clover",0,50,50));
        crops.add(new CropItem("Perennial Peanuts",0,60,130));
        crops.add(new CropItem("White Clover",0,50,50));
        crops.add(new CropItem("Asparagus",80,120,160));
        crops.add(new CropItem("Cabbage",0,0,0));//TODO change these values
        crops.add(new CropItem("Basil",120,100,100));
        crops.add(new CropItem("Carrots",0,0,0));//TODO change these values
        crops.add(new CropItem("Cucumbers",150,80,90));
        crops.add(new CropItem("Eggplants",130,80,90));
        crops.add(new CropItem("English Peas",90,120,120));
        crops.add(new CropItem("Tomatoes(Greenhouse)",240,150,200));
        crops.add(new CropItem("Irish Potatoes",150,150,200));
        crops.add(new CropItem("Lettuce",100,80,120));
        crops.add(new CropItem("Lima Beans",90,70,70));
        crops.add(new CropItem("Mustard",180,150,150));
        crops.add(new CropItem("Okra",125,80,90));
        crops.add(new CropItem("Onions",120,80,90));
        crops.add(new CropItem("Parsley",130,80,90));
        crops.add(new CropItem("Pepper",130,95,95));
        crops.add(new CropItem("Pumpkin",120,80,90));
        crops.add(new CropItem("Spinach",180,150,150));
        crops.add(new CropItem("Sweet Corn",170,90,90));
        crops.add(new CropItem("Sweet Potatoes",80,120,120));
        crops.add(new CropItem("Watermelon",120,80,90));


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
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Buckwheat",17,5.0,4.4));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Canola grain",32,16,8.0));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Corn grain",12,6.3,4.5));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Corn silage(67% water)per t of grain",29,9.1,21));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Corn silage(67% water)",4.9,1.6,3.7));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Corn stover per t of grain",8.0,2.9,20));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Corn stover",8.0,2.9,20));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Cotton(lint)",64,28,38));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Cotton stover",9.4,3.3,11));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Fescue(DM)",19,6.0,27));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Flax grain",45,13,11));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Flax straw",13,2.9,39));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Millet grain",28,8.0,8.0));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Millet straw",7.7,2.2,20));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Mint oil",1900,1100,4500));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Oat grain",24,8.8,5.9));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Oat straw per t of grain",9.7,5.0,29));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Oat straw",6.0,3.2,19));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Orchardgrass(DM)",18,6.5,27));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Peanut nuts",35,5.5,8.5));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Peanut stover",16,3.4,12));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Potato tuber",3.0,1.5,6.5));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Potato above-ground stems & leaves",1.9,0.60,5.3));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Red clover(DM)",23,6.0,21));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Reed canarygrass(DM)",15,6.6,13));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Rice grain",13,6.7,3.6));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Rice straw",8.3,2.7,21));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Rye grain",25,8.2,5.5));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Rye straw per t of grain",14,3.8,27));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Rye straw",6.0,1.5,11));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Ryegrass(DM)",22,6.0,22));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Sorghum grain",13,7.8,5.4));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Sorghum stover per t of grain",11,3.2,17));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Sorghum stover",14,4.2,21));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Sorghum-sudan(DM)",15,4.8,17));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Soybean grain",55,12,20));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Soybean hay(DM)",23,5.5,13));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Soybean stover per t of grain",18,4.0,17));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Soybean stover",20,4.4,19));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Sugarbeet root",1.9,1.1,3.7));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Sugarbeet top",3.7,2.0,10));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Sugarcane",1.0,0.65,1.8));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Sunflower grain",27,9.7,9.0));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Sunflower stover per t of grain",28,2.4,41));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Sunflower stover",12,1.0,17));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Switchgrass(DM)",11,6.0,29));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Timothy(DM)",13,5.5,21));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Tomatoes",1.3,0.46,2.9));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Tobacco leaves",36,9.0,57));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Vetch(DM)",29,7.5,25));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Wheat straw per t of grain",12,2.7,20));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Wheat straw",7.6,1.9,15));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Wheat(spring)grain",25,9.5,5.5));
        crops.add(new CropItem(CropItem.IS_FOR_NUTRIENT_REMOVAL,"Wheat(winter)grain",19,8.0,4.8));

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
        fertilizers.add(new CropFertilizer("Ammonium Phosphate Sulphate",FERTILIZER_TYPE_NPK,18,9,0));
        fertilizers.add(new CropFertilizer("Urea Ammonium Phosphate",FERTILIZER_TYPE_NPK,20,20,0));
        fertilizers.add(new CropFertilizer("Nitrophosphate",FERTILIZER_TYPE_NPK,20,20,0));
        fertilizers.add(new CropFertilizer("Ammonium Phosphate Sulphate",FERTILIZER_TYPE_NPK,20,20,0));
        fertilizers.add(new CropFertilizer("Ammonium Nitrate Phosphate",FERTILIZER_TYPE_NPK,24,24,0));
        fertilizers.add(new CropFertilizer("Urea Ammonium Phosphate",FERTILIZER_TYPE_NPK,28,28,0));
        fertilizers.add(new CropFertilizer("Urea Ammonium Phosphate",FERTILIZER_TYPE_NPK,29,29,0));
        fertilizers.add(new CropFertilizer("Urea Ammonium Phosphate",FERTILIZER_TYPE_NPK,33,20,0));
        fertilizers.add(new CropFertilizer("Urea Ammonium Phosphate",FERTILIZER_TYPE_NPK,34,17,0));
        fertilizers.add(new CropFertilizer("Sulphur Fortified Single",FERTILIZER_TYPE_NPK,0,8,0));
        fertilizers.add(new CropFertilizer("Single Super Phosphate - SSP",FERTILIZER_TYPE_NPK,0,14,0));
        fertilizers.add(new CropFertilizer("Single Super Phosphate - SSP",FERTILIZER_TYPE_NPK,0,16,0));
        fertilizers.add(new CropFertilizer("Sulphur Coated Triple Super Phosphate - SSP",FERTILIZER_TYPE_NPK,0,16,0));
        fertilizers.add(new CropFertilizer("Fused Magnesium Phosphate",FERTILIZER_TYPE_NPK,0,20,0));
        fertilizers.add(new CropFertilizer("Deflourinated Phosphate",FERTILIZER_TYPE_NPK,0,37,0));
        fertilizers.add(new CropFertilizer("Dicalcium Phosphate",FERTILIZER_TYPE_NPK,0,42,0));
        fertilizers.add(new CropFertilizer("Triple Super Phosphate - TSP",FERTILIZER_TYPE_NPK,0,46,0));
        fertilizers.add(new CropFertilizer("Triple Super Phosphate - TSP",FERTILIZER_TYPE_NPK,0,48,0));
        fertilizers.add(new CropFertilizer("Dicalcium Phosphate Anhydrous",FERTILIZER_TYPE_NPK,0,52.2,0));
        fertilizers.add(new CropFertilizer("Phosphoric Acid Merchant Grade",FERTILIZER_TYPE_NPK,0,54,0));
        fertilizers.add(new CropFertilizer("Super Phosphoric Acid",FERTILIZER_TYPE_NPK,0,70,0));
        fertilizers.add(new CropFertilizer("Phosphoric Acid",FERTILIZER_TYPE_NPK,0,74.2,0));


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
        fertilizers.add(new CropFertilizer("Ammonium Sulphate Nitrate",FERTILIZER_TYPE_NITROGENOUS,26,0,0.0));
        fertilizers.add(new CropFertilizer("Calcium Ammonium Nitrate",FERTILIZER_TYPE_NITROGENOUS,26,0,0.0));
        fertilizers.add(new CropFertilizer("Nitrogold",FERTILIZER_TYPE_NITROGENOUS,27,0,0.0));
        fertilizers.add(new CropFertilizer("Urea Ammonium Nitrate",FERTILIZER_TYPE_NITROGENOUS,32,0,0.0));
        fertilizers.add(new CropFertilizer("Potassium Nitrate",FERTILIZER_TYPE_NITROGENOUS,38,0,0.0));
        fertilizers.add(new CropFertilizer("Anhydrous Ammonia",FERTILIZER_TYPE_NITROGENOUS,82,0,0.0));
        fertilizers.add(new CropFertilizer("Anhydrous Ammonia",FERTILIZER_TYPE_NITROGENOUS,99,0,0.0));


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
