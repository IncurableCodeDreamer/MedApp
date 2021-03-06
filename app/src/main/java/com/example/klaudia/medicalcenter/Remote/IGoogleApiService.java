package com.example.klaudia.medicalcenter.Remote;

import com.example.klaudia.medicalcenter.RetrofitModel.MyPlaces;
import com.example.klaudia.medicalcenter.RetrofitModel.PlaceDetails;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Klaudia on 11.03.2018.
 */

public interface IGoogleApiService {

    @GET
    Call<MyPlaces> getNearByPlaces(@Url String url);

    @GET
    Call<PlaceDetails> getDetailsPlace(@Url String url);
}
