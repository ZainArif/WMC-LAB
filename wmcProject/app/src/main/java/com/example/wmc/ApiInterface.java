package com.example.wmc;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @POST("postuser")
    Call<userResponse> CREATE_USER_RESPONSE_CALL(@Body userData userData);

    @GET("getusers")
    Call<List<userData>> allUsers();

    @DELETE("delUser")
    Call<userData> delUser(@Query("id") String id);

    @PATCH("updateUser")
    Call<userData> updateUser(@Query("id") String id, @Body userData userData);
}
