package com.myfarmnow.myfarmcrop.network;

import com.myfarmnow.myfarmcrop.models.weather.Fields;
import com.myfarmnow.myfarmcrop.models.weather.WeatherResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiRequests {
    //weather api requests
    @GET("weather/forecast/daily")
    Call<WeatherResponse> getDailyWeather(
            @Field("lat") float latitude,
            @Field("lon") float longitude,
            @Field("location_id") String location_id,
            @Field("unit_system") String unit_system,
            @Field("start_time") String start_time,
            @Field("fields") ArrayList<Fields> fields
    );

    @GET("weather/realtime")
    Call<WeatherResponse>getRealtimeWeather(
            @Query("apikey") String apikey,
            @Query("lat") float latitude,
            @Query("lon") float longitude,
            @Query("location_id") String location_id,
            @Query("unit_system") String unit_system,
            @Query("fields") ArrayList<String> fields
    );

    @GET("weather/forecast/hourly")
    Call<WeatherResponse[]> getHourlyCastTemp(
            @Query("apikey") String apikey,
            @Query("lat") float latitude,
            @Query("lon") float longitude,
            @Query("location_id") String location_id,
            @Query("unit_system") String unit_system,
            @Query("start_time") String start_time,
            @Query("end_time") String end_time,
            @Query("fields") ArrayList<String> fields
    );
}
