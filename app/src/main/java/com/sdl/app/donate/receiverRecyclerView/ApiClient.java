package com.sdl.app.donate.receiverRecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vikas on 4/10/17.
 */

public class ApiClient {

    //10.10.15.24

    public static final String BASE_URL=  "http://192.168.43.200:3000/"; //put your ip:port
    public static Retrofit retrofit = null;

    public static Retrofit getapiClient() {
        if(null == retrofit) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).
                    addConverterFactory(GsonConverterFactory.create(gson)).build();
        }
        return retrofit;
    }
}
