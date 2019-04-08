package com.myfarmnow.myfarmcrop.models;

public interface CropActivity {

       int CROP_ACTIVITY_CULTIVATE =1;
      int CROP_ACTIVITY_FERTILIZER_APPLICATION =2;
      int CROP_ACTIVITY_SPRAYING =3;
     int getType();
}
