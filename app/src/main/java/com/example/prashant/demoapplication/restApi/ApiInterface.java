package com.example.prashant.demoapplication.restApi;

import com.example.prashant.demoapplication.model.DataModel;

import java.util.List;

import retrofit2.http.GET;

/**
 * Created by prashant on 21/1/17.
 */
public interface ApiInterface {

    @GET("/posts")
    public List<DataModel> getPostData();
}
