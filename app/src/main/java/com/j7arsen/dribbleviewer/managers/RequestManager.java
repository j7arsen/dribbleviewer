package com.j7arsen.dribbleviewer.managers;

import com.j7arsen.dribbleviewer.app.Constants;
import com.j7arsen.dribbleviewer.app.DribbleViewerApp;
import com.j7arsen.dribbleviewer.dataclasses.Shot;
import com.j7arsen.dribbleviewer.db.DBHelper;
import com.j7arsen.dribbleviewer.network.ApiFactory;
import com.j7arsen.dribbleviewer.network.params.Params;
import com.j7arsen.dribbleviewer.network.service.GetShotRequestService;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by j7ars on 24.01.2017.
 */

public class RequestManager {

    private static RequestManager mInstance;

    private RequestManager() {

    }

    public static RequestManager getInstance() {
        if (mInstance == null) {
            mInstance = new RequestManager();
        }
        return mInstance;
    }


    public Observable<List<Shot>> getShotResponse(int page) {
        GetShotRequestService service = ApiFactory.getShots();
        return service.getShots(Params.getShotsRequestParams(page))
                .map(this::filterShots)
                .map(this::saveShots)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .cache();
    }

    private List<Shot> saveShots(List<Shot> result) {
        DBHelper.getInstance(DribbleViewerApp.mInstance).clearShotsTable();
        DBHelper.getInstance(DribbleViewerApp.mInstance).addShots(result);
        return result;
    }

    private List<Shot> filterShots(List<Shot> shotDataList) {
        ArrayList<Shot> result = new ArrayList<>();
        for (Shot data : shotDataList) {
            if (result.size() < Constants.VISIBLE_SHOTS_COUNT) {
                if (!data.isAnimated()) {
                    result.add(data);
                }
            } else {
                break;
            }
        }
        return result;
    }

}
