package com.asrul.revoprint.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "http://192.168.43.100/rest-server/";
    private static final String RAJAONGKIR_BASE_URL = "https://api.rajaongkir.com/starter/";
    private static Retrofit retrofit, rajaongkir;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getRajaOngkir() {
        if (rajaongkir == null) {
            rajaongkir = new Retrofit.Builder()
                    .baseUrl(RAJAONGKIR_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return rajaongkir;
    }
}
