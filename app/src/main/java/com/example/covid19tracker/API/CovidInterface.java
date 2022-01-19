package com.example.covid19tracker.API;

import com.example.covid19tracker.Models.CovidModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CovidInterface {

    @GET("countries")
    Call<List<CovidModel>> getCountryStatus();

}
