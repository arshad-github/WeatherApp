package com.assessment.weatherapp.weather;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherResponse {
    @SerializedName("weather")
    private List<WeatherInfo> weatherInfoList;

    @SerializedName("main")
    private MainWeatherDetail mainWeatherDetail;

    @SerializedName("name")
    private String cityName;

    public List<WeatherInfo> getWeatherInfoList() {
        return weatherInfoList;
    }

    public void setWeatherInfoList(List<WeatherInfo> weatherInfoList) {
        this.weatherInfoList = weatherInfoList;
    }

    public MainWeatherDetail getMainWeatherDetail() {
        return mainWeatherDetail;
    }

    public void setMainWeatherDetail(MainWeatherDetail mainWeatherDetail) {
        this.mainWeatherDetail = mainWeatherDetail;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
