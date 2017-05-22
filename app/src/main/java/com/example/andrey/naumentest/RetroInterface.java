package com.example.andrey.naumentest;

import com.example.andrey.naumentest.entities.Item;
import com.example.andrey.naumentest.entities.ResultSet;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetroInterface {
    @GET("rest/computers")
    Call<ResultSet> getPage(@Query("p") int p);

    @GET("rest/computers/{id}")
    Call<Item> getComputer(@Path("id") int id);

    @GET("rest/computers/{id}/similar")
    Call<List<Item>> getSimilarList(@Path("id") int id);
}
