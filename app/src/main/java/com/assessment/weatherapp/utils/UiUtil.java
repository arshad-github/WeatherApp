package com.assessment.weatherapp.utils;

import com.assessment.weatherapp.weather.MainWeatherDetail;
import com.assessment.weatherapp.weather.WeatherInfo;
import com.assessment.weatherapp.weather.WeatherResponse;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.assessment.weatherapp.MainActivity.BLANK;
import static com.assessment.weatherapp.MainActivity.NOT_AVAILABLE;

public class UiUtil {

    public static final double KELVIN_DIFFERENCE = 273.15;
    public static final String FORMATTING = "#.##";
    public static final String LOADING = "Loading...";
    public static final String CELSIUS = "°C";

    public static WeatherResponse clearWeatherContent() {
        WeatherResponse weatherResponse = new WeatherResponse();
        MainWeatherDetail mainWeatherDetail = new MainWeatherDetail();
        List<WeatherInfo> weatherInfoList = new ArrayList<>();

        mainWeatherDetail.setTemp(BLANK);
        WeatherInfo weatherInfo = new WeatherInfo();
        weatherInfo.setMain(BLANK);
        weatherInfo.setDescription(BLANK);
        weatherInfoList.add(weatherInfo);

        weatherResponse.setCityName(LOADING);
        weatherResponse.setMainWeatherDetail(mainWeatherDetail);
        weatherResponse.setWeatherInfoList(weatherInfoList);

        return weatherResponse;
    }

    public static WeatherResponse errorWithWeatherRetrieval() {
        WeatherResponse weatherResponse = new WeatherResponse();
        MainWeatherDetail mainWeatherDetail = new MainWeatherDetail();
        List<WeatherInfo> weatherInfoList = new ArrayList<>();

        mainWeatherDetail.setTemp(BLANK);
        WeatherInfo weatherInfo = new WeatherInfo();
        weatherInfo.setMain(BLANK);
        weatherInfo.setDescription(BLANK);
        weatherInfoList.add(weatherInfo);

        weatherResponse.setCityName(NOT_AVAILABLE);
        weatherResponse.setMainWeatherDetail(mainWeatherDetail);
        weatherResponse.setWeatherInfoList(weatherInfoList);

        return weatherResponse;
    }

    public static String convertKelvinToCelsius(String kelvin) {
        Double dKelvin = Double.valueOf(kelvin);
        DecimalFormat df = new DecimalFormat(FORMATTING);
        return String.valueOf(Double.valueOf(df.format(dKelvin - KELVIN_DIFFERENCE)));
    }

    public static String determineTemperature(WeatherResponse weatherResponse) {
        String temp = weatherResponse.getMainWeatherDetail().getTemp();
        return temp.equals(BLANK) ? BLANK : String.format("%s%s", convertKelvinToCelsius(weatherResponse.getMainWeatherDetail().getTemp()), CELSIUS);
    }
}
