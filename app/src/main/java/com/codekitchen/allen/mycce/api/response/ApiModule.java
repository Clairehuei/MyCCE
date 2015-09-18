package com.codekitchen.allen.mycce.api.response;

import com.codekitchen.allen.mycce.api.Bingo;
import com.codekitchen.allen.mycce.api.Product;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;

/**
 * Created by 6193 on 2015/9/17.
 */

@Module
public class ApiModule {

    @Provides @Singleton Product product(RestAdapter restAdapter) {
        return restAdapter.create(Product.class);
    }

    @Provides @Singleton Bingo bingo(RestAdapter restAdapter) {
        return restAdapter.create(Bingo.class);
    }
}
