package com.thinkmobiles.mysmallcommunity.api;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

/**
 * Created by feltsan on 20.11.15.
 */
public abstract class API {
    static ArrayList<String> strings = null;
    public static List<String> getPosts(){
        strings = new ArrayList<>();
        strings.add("1");
        strings.add("2");
        strings.add("3");
        strings.add("3");
        strings.add("4");

        Call<List<String>> call = RetrofitAdapter.getInterface().getPosts();
        call.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Response<List<String>> response) {
                strings = new ArrayList<>();
                strings.addAll(response.body());
                Log.e("Success SI", response.toString());
            }

            @Override
            public void onFailure(Throwable t) {

                Log.e("Failure SI", t.toString());
            }
        });

        return strings;
    }
}
