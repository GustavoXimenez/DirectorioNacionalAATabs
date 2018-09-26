package com.programandounmundomejor.directorionacionalaa.Clases;

import android.app.Application;
import android.os.SystemClock;

public class SplashScreenMethods extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        SystemClock.sleep(3000);
    }
}
