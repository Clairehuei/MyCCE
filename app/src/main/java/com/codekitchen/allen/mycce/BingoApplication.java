package com.codekitchen.allen.mycce;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParsePush;

/**
 * Created by 6193 on 2015/9/23.
 */
public class BingoApplication extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        Parse.initialize(this, "9tSRL1oZilYZFjsZBoJtKj7topN2XvpULglP4sNV", "v8Dpwc1meXY4d83T3xYrEC5tXaODTPMoP9HaBAXc");
        ParseInstallation.getCurrentInstallation().saveInBackground();
        ParsePush.subscribeInBackground("bingo");
    }
}
