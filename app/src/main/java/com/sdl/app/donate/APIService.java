package com.sdl.app.donate;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;

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

    @Headers("Content-Type: application/json")
    @GET("users/profile")
    Call<List<Profile>> userProfile(@Header("Authorization") String token);

    @Headers("Content-Type: application/json")
    @PUT("users/profile") // to be changed - ASK SUSHRUT
    Call<List<MSG>> userUpdate(@Body User user, @Header("Authorization") String token);

}
