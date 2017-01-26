package com.j7arsen.dribbleviewer.managers;

import com.j7arsen.dribbleviewer.app.DribbleViewerApp;
import com.j7arsen.dribbleviewer.dataclasses.Shot;
import com.j7arsen.dribbleviewer.db.DBHelper;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by j7ars on 24.01.2017.
 */

public class DBManager {

    private static DBManager mInstance;

    private DBManager() {

    }

    public static DBManager getInstance() {
        if (mInstance == null) {
            mInstance = new DBManager();
        }
        return mInstance;
    }

    public Observable<List<Shot>> getShotFromDB() {
        return DBHelper.getInstance(DribbleViewerApp.mInstance)
                .loadShots()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .cache();
    }

}
