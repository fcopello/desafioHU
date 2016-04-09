package com.hotelurbano.desafio.api;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by fabiocopello on 4/7/16.
 */
public class HotelUrbanoAPI {

    private static final String API_ENDPOINT = "http://192.168.0.10:1337";


    private static RestAdapter restAdapter;

    public static HotelUrbanoService getService() {
        return getRestAdapter().create(HotelUrbanoService.class);
    }


    private static RestAdapter getRestAdapter() {

        if (restAdapter == null) {

            Gson gson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .create();

            restAdapter = new RestAdapter.Builder()
                    .setEndpoint(API_ENDPOINT)
                    .setLogLevel(RestAdapter.LogLevel.BASIC)
                    .setConverter(new GsonConverter(gson))
                    .build();
        }

        return restAdapter;

    }
}
