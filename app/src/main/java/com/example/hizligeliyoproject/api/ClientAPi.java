package com.example.hizligeliyoproject.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientAPi {
    public static final String BASE_URL="https://fakestoreapi.com/";
    private static Retrofit retrofit=null;
    public static Retrofit getRetrofit(){
        if ((retrofit==null)){
            retrofit=new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
