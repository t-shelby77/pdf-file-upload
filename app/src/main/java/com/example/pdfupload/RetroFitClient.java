package com.example.pdfupload;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Headers;

public class RetroFitClient {

    private static final String BASE_URL="http://192.168.0.107/android/";
    private static RetroFitClient myClient;//class name
    private Retrofit retrofit;

    private RetroFitClient(){
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static synchronized RetroFitClient getInstance(){
        if (myClient == null){
            myClient = new RetroFitClient();
        }
        return myClient;
    }
//now have to create a java class Interface named as Api
    public Api getAPI(){
        return retrofit.create(Api.class);

    }
}
