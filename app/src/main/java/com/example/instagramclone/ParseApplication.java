package com.example.instagramclone;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Post.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("8AaMvWnXxtzcsyi6IP1xM2jiQRy1PgCVRO391XdR")
                .clientKey("ZLKf9Z3lTawcNwdNRtANsM1sNkCNFORkvjwDNLpq")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}

