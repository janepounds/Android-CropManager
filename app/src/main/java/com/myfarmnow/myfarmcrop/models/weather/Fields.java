package com.myfarmnow.myfarmcrop.models.weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Fields {
    @SerializedName("precipitation")
    @Expose
    private String precipitation;

    @SerializedName("visibility")
    @Expose
    private String visibility;


    @SerializedName("temp")
    @Expose
    private String temperature;

    @SerializedName("humidity")
    @Expose
    private String humidity;

    public Fields(String precipitation, String visibility, String temperature, String humidity) {
        this.precipitation = precipitation;
        this.visibility = visibility;
        this.temperature = temperature;
        this.humidity = humidity;
    }

    public String getPrecipitation() {
        return precipitation;
    }

    public String getVisibility() {
        return visibility;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getHumidity() {
        return humidity;
    }
}
