package com.assessment.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.assessment.weatherapp.utils.PropertyUtil;
import com.assessment.weatherapp.api.ApiClient;
import com.assessment.weatherapp.api.ApiInterface;
import com.assessment.weatherapp.weather.WeatherInfo;
import com.assessment.weatherapp.weather.WeatherResponse;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.assessment.weatherapp.utils.UiUtil.clearWeatherContent;
import static com.assessment.weatherapp.utils.UiUtil.determineTemperature;
import static com.assessment.weatherapp.utils.UiUtil.errorWithWeatherRetrieval;

public class MainActivity extends AppCompatActivity {

    public static final String NOT_AVAILABLE = "Data not available";
    public static final String API_KEY = "API_KEY";
    public static final String BLANK = "";
    private WeatherResponse weatherResponse;
    private List<WeatherInfo> weatherInfo;
    private ApiInterface apiInterface;

    final int REQUEST_CODE = 101;
    final long TIME_THRESHOLD = 5000;
    final float DISTANCE_THRESHOLD = 1000;

    String gpsProvider = LocationManager.GPS_PROVIDER;
    LocationManager locationManager;
    LocationListener locationListener;

    @BindView(R.id.mainWeather) TextView mainWeather;
    @BindView(R.id.temperature) TextView temperature;
    @BindView(R.id.nameOfCity) TextView nameOfCity;
    @BindView(R.id.weatherDescription) TextView weatherDescription;
    @BindView(R.id.refreshButton) Button refreshButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupRefresh();
        getWeatherForCurrentLocation();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (locationManager != null) {
            locationManager.removeUpdates(locationListener);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getWeatherForCurrentLocation();
            } else {
                // user denied permission
            }
        }
    }

    private void setupRefresh() {
        refreshButton.setOnClickListener(v -> {
            WeatherResponse weatherResponse = clearWeatherContent();
            populateUiData(weatherResponse);
            getWeatherForCurrentLocation();
        });
    }

    protected void fetchWeather(String lat, String lon, String appid) {
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<WeatherResponse> call = apiInterface.getWeatherInfo(lat, lon, appid);

        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                weatherResponse = response.body();
                populateUiData(weatherResponse);
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                weatherResponse = errorWithWeatherRetrieval();
                populateUiData(weatherResponse);
            }
        });
    }

    private void populateUiData(WeatherResponse weatherResponse) {
        nameOfCity.setText(weatherResponse.getCityName());
        temperature.setText(determineTemperature(weatherResponse));
        weatherInfo = weatherResponse.getWeatherInfoList();
        mainWeather.setText(weatherInfo.get(0).getMain());
        weatherDescription.setText(weatherInfo.get(0).getDescription());
    }

    private void getWeatherForCurrentLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = setupLocationListener();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }

        locationManager.requestLocationUpdates(gpsProvider, TIME_THRESHOLD, DISTANCE_THRESHOLD, locationListener);
    }

    private LocationListener setupLocationListener() {
        return new LocationListener() {

            @Override
            public void onLocationChanged(@NonNull Location location) {
                try {
                    fetchWeather(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()), PropertyUtil.getProperty(API_KEY, getApplicationContext()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                // Do nothing
            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {
                // Do nothing
            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {
                // No location
            }
        };
    }
}