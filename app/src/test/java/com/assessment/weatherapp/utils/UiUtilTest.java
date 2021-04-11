package com.assessment.weatherapp.utils;

import com.assessment.weatherapp.weather.MainWeatherDetail;
import com.assessment.weatherapp.weather.WeatherInfo;
import com.assessment.weatherapp.weather.WeatherResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static com.assessment.weatherapp.MainActivity.BLANK;
import static com.assessment.weatherapp.MainActivity.NOT_AVAILABLE;
import static com.assessment.weatherapp.utils.UiUtil.CELSIUS;
import static com.assessment.weatherapp.utils.UiUtil.LOADING;
import static com.assessment.weatherapp.utils.UiUtil.clearWeatherContent;
import static com.assessment.weatherapp.utils.UiUtil.convertKelvinToCelsius;
import static com.assessment.weatherapp.utils.UiUtil.determineTemperature;
import static com.assessment.weatherapp.utils.UiUtil.errorWithWeatherRetrieval;
import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class UiUtilTest {

    public static final String KELVIN300 = "300";
    public static final String KELVIN100 = "100";
    public static final String KELVIN500 = "500";
    public static final String EXPECTED_CELSIUS_100 = "-173.15";
    public static final String EXPECTED_CELSIUS_300 = "26.85";
    public static final String EXPECTED_CELSIUS_500 = "226.85";

    @Test
    public void testClearContent() {
        WeatherResponse result = clearWeatherContent();
        List<WeatherInfo> weatherInfoList = result.getWeatherInfoList();

        assertEquals(LOADING, result.getCityName());
        assertEquals(BLANK, result.getMainWeatherDetail().getTemp());
        assertEquals(BLANK, weatherInfoList.get(0).getMain());
        assertEquals(BLANK, weatherInfoList.get(0).getDescription());
    }

    @Test
    public void testErrorWithWeatherRetrieval() {
        WeatherResponse result = errorWithWeatherRetrieval();
        List<WeatherInfo> weatherInfoList = result.getWeatherInfoList();

        assertEquals(NOT_AVAILABLE, result.getCityName());
        assertEquals(BLANK, result.getMainWeatherDetail().getTemp());
        assertEquals(BLANK, weatherInfoList.get(0).getMain());
        assertEquals(BLANK, weatherInfoList.get(0).getDescription());
    }

    @Test
    public void testConvertKelvinToCelsius() {
        String celsiusEquiv100 = convertKelvinToCelsius(KELVIN100);
        String celsiusEquiv300 = convertKelvinToCelsius(KELVIN300);
        String celsiusEquiv500 = convertKelvinToCelsius(KELVIN500);

        assertEquals(EXPECTED_CELSIUS_100, celsiusEquiv100);
        assertEquals(EXPECTED_CELSIUS_300, celsiusEquiv300);
        assertEquals(EXPECTED_CELSIUS_500, celsiusEquiv500);
    }

    @Test
    public void testDetermineTemperature() {
        WeatherResponse weatherResponse = new WeatherResponse();
        MainWeatherDetail mainWeatherDetail = new MainWeatherDetail();
        mainWeatherDetail.setTemp("300");
        weatherResponse.setMainWeatherDetail(mainWeatherDetail);
        String temp = determineTemperature(weatherResponse);

        assertEquals(EXPECTED_CELSIUS_300 + CELSIUS, temp);
    }
}