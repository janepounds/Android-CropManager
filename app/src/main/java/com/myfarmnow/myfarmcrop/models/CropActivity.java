package com.myfarmnow.myfarmcrop.models;

public interface CropActivity {

    int CROP_ACTIVITY_CULTIVATE =1;
    int CROP_ACTIVITY_FERTILIZER_APPLICATION =2;
    int CROP_ACTIVITY_SPRAYING =3;
    int CROP_ACTIVITY_SCOUTING =4;
    int CROP_ACTIVITY_HARVESTING =5;
    int CROP_ACTIVITY_IRRIGATION =6;
    int CROP_ACTIVITY_TRANSPLANTING =7;
    int getType();
    String getDate();
}
