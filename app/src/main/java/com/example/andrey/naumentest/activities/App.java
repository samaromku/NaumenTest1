package com.example.andrey.naumentest.activities;


import android.app.Application;

import com.example.andrey.naumentest.RetroInterface;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application{

    private static RetroInterface retroInterface;
    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://testwork.nsd.naumen.ru")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retroInterface = retrofit.create(RetroInterface.class);
    }

    public static RetroInterface getData() {
        return retroInterface;
    }
}
