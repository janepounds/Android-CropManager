package com.myfarmnow.myfarmcrop;

import androidx.multidex.MultiDexApplication;

public class CropManagerApp extends MultiDexApplication {
  
  @Override
  public void onCreate() {
    super.onCreate();
    TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "font/roboto_regular.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf
  }
}