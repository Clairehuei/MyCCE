package com.codekitchen.allen.mycce.api;

import android.app.Application;
import android.content.Context;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;


/**
 * Created by 6193 on 2015/9/17.
 */

@Module
public class ApplicationModule {

    private final Application application;
    private final Bus bus;
    private final RestAdapter restAdapter;

    public ApplicationModule(Application application) {
        this.application = application;
        bus = new Bus(ThreadEnforcer.MAIN);
        restAdapter = new RestAdapter.Builder().setEndpoint("127.0.0.1").build();
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }

    @Provides
    @Singleton
    Bus provideBus() {
        return this.bus;
    }

    @Provides
    @Singleton
    RestAdapter provideRestAdapter() {
        restAdapter.setLogLevel(RestAdapter.LogLevel.FULL);
        return this.restAdapter;
    }
}
