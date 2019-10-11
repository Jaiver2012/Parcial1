package com.example.reto1.app;

import android.app.Application;
import android.content.Context;

public class Organizador extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        Organizador.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return Organizador.context;
    }
}
