package com.assessment.weatherapp;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.assessment.weatherapp.utils.PropertyUtil;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class TestPropertyUtil {

    public static final String API_KEY = "API_KEY";

    @Test
    public void testGetProperties() throws IOException {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        String apiKey = PropertyUtil.getProperty(API_KEY, appContext);
        assertNotNull(apiKey);
    }
}

