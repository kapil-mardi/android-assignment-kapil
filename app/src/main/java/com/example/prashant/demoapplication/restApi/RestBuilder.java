package com.example.prashant.demoapplication.restApi;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by prashant on 21/1/17.
 */
public class RestBuilder {

    public static final String BASE = "http://jsonplaceholder.typicode.com";
    private static Retrofit retrofit = null;

    public static  Retrofit getInstance(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
