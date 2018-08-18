package com.example.kmj.a2018appjam;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetWorkHelper {
    final static String url = "http://aws.soylatte.kr";
    final static int port = 3000;


    private static Retrofit retrofit;

    public static NetWorkInterface getInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(url + ":" + port)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(NetWorkInterface.class);
    }

}
