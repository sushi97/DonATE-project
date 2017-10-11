package com.sdl.app.donate.receiverRecyclerView;

import com.sdl.app.donate.User;
import com.sdl.app.donate.ngolist;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiInterface {
//    @GET("/{id}/{loc}")
//    Call<List<ngoInfo>> getInfo(@Path("id") Integer id,
//                                @Path("loc") String location);

    @GET("/users/ngos")
    Call<List<ngolist>> getorglist();

    @Headers("Content-Type: application/json")
    @POST("/requests/donate")
    Call <List<ngolist>> getrecv(@Body ngolist infos);

    @GET("/requests/donate/{username}")
    Call<List<ngolist>> getuserdonation(@Path("username") String username);

    @Headers("Authorization : utoken")
    @GET("http://192.168.43.200:3000/users/profile")
    Call<List<User>> getProfile();


}
