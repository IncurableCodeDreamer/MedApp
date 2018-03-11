package com.example.klaudia.medicalcenter;

import com.example.klaudia.medicalcenter.Remote.IGoogleApiService;
import com.example.klaudia.medicalcenter.Remote.RetrofitClient;

/**
 * Created by Klaudia on 11.03.2018.
 */

public class Common {
    private static final String GOOGLE_API_URL="https://maps.googleapis.com/";
    public static IGoogleApiService getGoogleAPIService()
    {
        return RetrofitClient.getClient(GOOGLE_API_URL).create(IGoogleApiService.class);
    }
}
