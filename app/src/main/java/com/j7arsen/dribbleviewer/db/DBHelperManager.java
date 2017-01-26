package com.j7arsen.dribbleviewer.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.j7arsen.dribbleviewer.dataclasses.Shot;
import com.j7arsen.dribbleviewer.dataclasses.ShotImages;
import com.j7arsen.dribbleviewer.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by j7ars on 24.01.2017.
 */

public class DBHelperManager implements DBColumn {

    public static void fillShotList(DBHelper dbHelper, List<Shot> dataList) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            for (Shot data : dataList) {
                addNewShot(db, contentValues, data);
                contentValues.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbHelper.close();
        }

    }

    private static void addNewShot(SQLiteDatabase db, ContentValues contentValues, Shot data) {
        contentValues.put(SHOT_ID, data.getId());
        addContentValues(SHOT_TITLE, data.getTitle(), contentValues);
        addContentValues(SHOT_DESCRIPTION, data.getDescription(), contentValues);
        addContentValues(SHOT_IMG_HI_DPI, data.getImages().getHiDpi(), contentValues);
        addContentValues(SHOT_IMG_NORMAL, data.getImages().getNormal(), contentValues);
        addContentValues(SHOT_IMG_TEASER, data.getImages().getTeaser(), contentValues);
        contentValues.put(SHOT_IMG_HEIGHT, data.getHeight());
        contentValues.put(SHOT_IMG_WIDTH ,data.getWidth());

        db.insert(SHOTS_TABLE, null, contentValues);
    }

    private static void addContentValues(String key, String value, ContentValues contentValues) {
        if (value == null) {
            contentValues.putNull(key);
        } else {
            contentValues.put(key, value);
        }
    }

    public static List<Shot> getShots(DBHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(SHOTS_TABLE, null, null, null, null, null, null);
        ArrayList<Shot> data = null;
        try {
            if (cursor.moveToFirst()) {
                data = new ArrayList<>();
                do {
                    Shot shot = new Shot();
                    shot.setId(cursor.getInt(cursor.getColumnIndex(SHOT_ID)));
                    shot.setTitle(cursor.getString(cursor.getColumnIndex(SHOT_TITLE)));
                    shot.setDescription(cursor.getString(cursor.getColumnIndex(SHOT_DESCRIPTION)));
                    shot.setImages(new ShotImages(cursor.getString(cursor.getColumnIndex(SHOT_IMG_HI_DPI)), cursor.getString(cursor.getColumnIndex(SHOT_IMG_NORMAL)), cursor.getString(cursor.getColumnIndex(SHOT_IMG_TEASER))));
                    shot.setHeight(cursor.getInt(cursor.getColumnIndex(SHOT_IMG_HEIGHT)));
                    shot.setWidth(cursor.getInt(cursor.getColumnIndex(SHOT_IMG_WIDTH)));
                    data.add(shot);
                } while (cursor.moveToNext());
            } else {
                return null;
            }
            return data;
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            cursor.close();
            db.close();
        }
        return null;
    }

    public static Observable<List<Shot>> getListShots(DBHelper dbHelper) {
        return Utils.makeObservable(() -> getShots(dbHelper));
    }

    public static int getShotsCount(DBHelper dbHelper){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(SHOTS_TABLE, null, null, null, null, null, null, null);
        try {
            int cnt = cursor.getCount();
            return cnt;
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            cursor.close();
        }
        return 0;
    }

    public static void clearShotsTable(DBHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM " + SHOTS_TABLE);
        dbHelper.close();
    }
}
