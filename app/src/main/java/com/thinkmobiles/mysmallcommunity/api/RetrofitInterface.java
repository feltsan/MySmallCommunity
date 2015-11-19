package com.thinkmobiles.mysmallcommunity.api;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by feltsan on 19.11.15.
 */
public interface RetrofitInterface {

    @FormUrlEncoded
    @POST("/personnel/login")
    Call<String> signIn(@Field("email") String email, @Field("pass") String pass);
}
