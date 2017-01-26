package com.j7arsen.dribbleviewer.utils;

import com.j7arsen.dribbleviewer.app.DribbleViewerApp;

/**
 * Created by j7ars on 23.01.2017.
 */

public class FieldConverter {

    public static String getString(int resId) {
        return DribbleViewerApp.mInstance.getResources().getString(resId);
    }

    public static int getColor(int resId) {
        return DribbleViewerApp.mInstance.getResources().getColor(resId);
    }

}
