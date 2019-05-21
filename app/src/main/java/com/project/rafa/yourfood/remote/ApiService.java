package com.project.rafa.yourfood.remote;

import com.project.rafa.yourfood.data.Food;
import com.project.rafa.yourfood.data.HomeResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("recommend/")
    Call<HomeResponse> recommend(@Field("query_name") String query_name);

}