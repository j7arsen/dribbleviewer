package com.j7arsen.dribbleviewer.app;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by j7ars on 23.01.2017.
 */

public class DribbleViewerApp extends Application{

    public static Context mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        Fresco.initialize(this);
    }

}
