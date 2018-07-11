package com.example.schmidegv.bakingapp.networking;

import okhttp3.OkHttpClient;

/**
 * Created by schmidegv on 2018. 07. 09..
 */

public abstract class OkHttpProvider {
    private static OkHttpClient instance = null;

    public static OkHttpClient getOkHttpInstance() {
        if (instance == null) {
            instance = new OkHttpClient();
        }
        return instance;
    }
}