package com.example.playmusic.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofit;
    private static Retrofit retrofit_search;


    public static Retrofit getRetrofit(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl("https://mp3.zing.vn/xhr/").addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }

    public static Retrofit getRetrofit_Search(){
        if(retrofit_search == null){
            retrofit_search = new Retrofit.Builder().baseUrl("http://ac.mp3.zing.vn/").addConverterFactory(GsonConverterFactory.create()).build();
        }
        return  retrofit_search;
    }
}
