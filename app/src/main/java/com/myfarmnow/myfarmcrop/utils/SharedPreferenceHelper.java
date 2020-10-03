package com.myfarmnow.myfarmcrop.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceHelper {
    private SharedPreferences sharedPreferences;
    private String animal = "selectedAnimal";

    public SharedPreferenceHelper(Context context) {
        String preferenceName = "SharedPreference";
        sharedPreferences = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
    }

    public void insertSelectedAnimal(String selectedAnimal) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(animal, selectedAnimal);
        editor.apply();
    }

    public String getSelectedAnimal(){
        return sharedPreferences.getString(animal,"");
    }
}
