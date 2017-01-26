package com.j7arsen.dribbleviewer.network;

import android.support.annotation.NonNull;

import com.j7arsen.dribbleviewer.app.Constants;
import com.j7arsen.dribbleviewer.network.service.GetShotRequestService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by j7ars on 23.01.2017.
 */

public class ApiFactory {

    private static final int CONNECT_TIMEOUT = 15;
    private static final int WRITE_TIMEOUT = 30;
    private static final int TIMEOUT = 30;

    private static final OkHttpClient CLIENT = getOkHttpClient(Constants.ACCESS_TOKEN);

    private static OkHttpClient getOkHttpClient(String authToken) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(TIMEOUT, TimeUnit.SECONDS);
        builder.addInterceptor(chain -> {
            Request request = chain.request();
            Request authRequest = request.newBuilder().addHeader("Authorization", "Bearer " + authToken).build();
            return chain.proceed(authRequest);
        });
        return builder.build();
    }

    public static GetShotRequestService getShots(){
        return getRetrofit().create(GetShotRequestService.class);
    }

    @NonNull
    private static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(Urls.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(CLIENT)
                .build();
    }


}
