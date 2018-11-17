package com.pshekha.githubrepos.Network;

/**
 * Created by krrathore on 11/13/18.
 */

import com.pshekha.githubrepos.Utils.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClient {

    private static NetworkClient mInstance;
    private Retrofit retrofit;


    private NetworkClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    //get Singleton instance of Network Client
    public static synchronized NetworkClient getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkClient();
        }
        return mInstance;
    }

    public NetworkService getNetworkService() {
        return retrofit.create(NetworkService.class);
    }
}

