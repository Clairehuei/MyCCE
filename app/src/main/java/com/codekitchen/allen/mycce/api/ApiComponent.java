package com.codekitchen.allen.mycce.api;

import com.codekitchen.allen.mycce.Activity_Bingo;
import com.codekitchen.allen.mycce.Activity_Prd_Detail;
import com.codekitchen.allen.mycce.Activity_Prd_Query_Result;
import com.codekitchen.allen.mycce.api.response.ApiModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by 6193 on 2015/9/17.
 */

@Singleton
@Component(modules = {
        ApplicationModule.class,
        ApiModule.class
})
public interface ApiComponent {
    void inject(Activity_Prd_Query_Result activity);
    void inject(Activity_Prd_Detail activity);
    void inject(Activity_Bingo activity);
}
