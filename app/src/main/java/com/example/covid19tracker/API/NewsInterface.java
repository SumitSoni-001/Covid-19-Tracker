package com.example.covid19tracker.API;

import com.example.covid19tracker.Models.NewsModel;

import java.util.Date;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsInterface {

    @GET("everything")
    Call<NewsModel> getNews(
            @Query("q") String Category,
            @Query("sortBy") String Sort,
            @Query("language") String Language,
            @Query("pageSize") int pageSize,
            @Query("apiKey") String key
    );

}