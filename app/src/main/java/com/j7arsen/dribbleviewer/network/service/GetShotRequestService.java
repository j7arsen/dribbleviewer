package com.j7arsen.dribbleviewer.network.service;

import com.j7arsen.dribbleviewer.dataclasses.Shot;
import com.j7arsen.dribbleviewer.network.Urls;

import java.util.HashMap;
import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by j7ars on 24.01.2017.
 */

public interface GetShotRequestService {

    @GET(Urls.SHOTS)
    Observable<List<Shot>> getShots(@QueryMap HashMap<String, String> params);

}
