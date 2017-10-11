package com.sdl.app.donate;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by vishvanatarajan on 3/10/17.
 */

public interface APIService {

    @Headers("Content-Type: application/json")
    @POST("users/register")
    Call<MSG> userSignUp(@Body User user);

    @Headers("Content-Type: application/json")
    @POST("users/authenticate")
    Call<List<MSG>> userLogIn(@Body User user);


}
