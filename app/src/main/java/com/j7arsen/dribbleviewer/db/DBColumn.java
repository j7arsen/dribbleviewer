package com.j7arsen.dribbleviewer.db;

/**
 * Created by j7ars on 24.01.2017.
 */

public interface DBColumn {

    String SHOTS_TABLE = "shots_table1";

    String ID = "id";
    String SHOT_ID = "shot_id";
    String SHOT_TITLE = "shot_title";
    String SHOT_DESCRIPTION = "shot_description";
    String SHOT_IMG_HI_DPI = "shot_img_hi_dpi";
    String SHOT_IMG_NORMAL = "shot_img_normal";
    String SHOT_IMG_TEASER = "shot_img_teaser";
    String SHOT_IMG_HEIGHT = "shot_img_height";
    String SHOT_IMG_WIDTH = "shot_img_width";


    String CREATE_SHOTS_TABLE = "CREATE TABLE " + SHOTS_TABLE + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + SHOT_ID + " INTEGER, "
            + SHOT_TITLE + " VARCHAR(255), "
            + SHOT_DESCRIPTION + " TEXT, "
            + SHOT_IMG_HI_DPI + " VARCHAR(255), "
            + SHOT_IMG_NORMAL + " VARCHAR(255), "
            + SHOT_IMG_TEASER + " VARCHAR(255), "
            + SHOT_IMG_HEIGHT + " INTEGER, "
            + SHOT_IMG_WIDTH + " INTEGER);";

}
