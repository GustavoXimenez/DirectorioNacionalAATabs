package com.programandounmundomejor.directorionacionalaa;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

import com.programandounmundomejor.directorionacionalaa.Clases.SplashScreenMethods;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    private SplashScreenMethods splashScreenMethods = new SplashScreenMethods();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //quitamos la barra de notificaciones
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Set portrait orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Hide title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Inicialize Crashlitics
        Fabric.with(this, new Crashlytics());

        setContentView(R.layout.activity_splash);

        timerActivity();
    }

    public void timerActivity(){
        TimerTask task = new TimerTask()
        {
            @Override
            public void run()
            {
                // Start the next activity
                Intent mainIntent = new Intent().setClass(SplashActivity.this, MainActivity.class);
                startActivity(mainIntent);

                // Close the activity so the user won't able to go back this
                // activity pressing Back button
                finish();
            }
        };

        // Simulate a long loading process on application startup.
        Timer timer = new Timer();
        timer.schedule(task, 3000);
    }
}
