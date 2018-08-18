package com.example.kmj.a2018appjam;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


interface NetWorkInterface {
    @FormUrlEncoded
    @POST("/auth/login")
    Call<Data> POSTData(@Field("id") String id, @Field("password") String password);

    @GET("/data/couple")
    Call<User> USERData(@Query("token") String token);

    @GET("/hand")
    Call<Hand> HandData(@Query("couple_room_token") String couple_room_token);

    @FormUrlEncoded
    @POST("/hand/update")
    Call<Hand> HandUpdate(@Field("couple_room_token") String couple_room_token);
}
