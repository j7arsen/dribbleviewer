package com.j7arsen.dribbleviewer.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.j7arsen.dribbleviewer.dataclasses.Shot;

import java.util.List;

import rx.Observable;

/**
 * Created by j7ars on 24.01.2017.
 */

public class DBHelper extends SQLiteOpenHelper implements DBColumn{

    private static final String DATABASE_NAME = "shots";

    private static final int DB_VERSION = 1;

    private static DBHelper mInstance;

    public static DBHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DBHelper(context);
        }
        return mInstance;
    }

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SHOTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + SHOTS_TABLE);
        this.onCreate(db);
    }

    public void addShots(List<Shot> shotDataList){
        DBHelperManager.fillShotList(mInstance, shotDataList);
    }

    public Observable<List<Shot>> loadShots(){
        return DBHelperManager.getListShots(mInstance);
    }

    public int getShotsCount(){
        return DBHelperManager.getShotsCount(mInstance);
    }

    public void clearShotsTable(){
        DBHelperManager.clearShotsTable(mInstance);
    }
}
