package com.myfarmnow.myfarmcrop;

import android.app.Application;
import android.support.multidex.MultiDexApplication;

public class CropManagerApp extends MultiDexApplication {
  
  @Override
  public void onCreate() {
    super.onCreate();
    TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "font/source_sans_pro_regular.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf
  }
}