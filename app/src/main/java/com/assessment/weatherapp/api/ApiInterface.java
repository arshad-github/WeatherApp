package com.assessment.weatherapp.api;

import com.assessment.weatherapp.weather.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("weather")
    Call<WeatherResponse> getWeatherInfo(@Query("lat") String lat, @Query("lon") String lon, @Query("appid") String appid);
}
