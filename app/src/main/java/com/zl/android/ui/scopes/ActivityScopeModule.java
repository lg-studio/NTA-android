package com.zl.android.ui.scopes;

import android.app.Activity;
import android.content.Context;

import com.zl.android.domain.features.catalog.CatalogRepository;
import com.zl.android.domain.features.catalog.CatalogUseCase;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import dagger.Module;
import dagger.Provides;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/** Module providing instances living in activity score. */
@Module
public class ActivityScopeModule {

    @Scope @Retention(RUNTIME)
    public @interface PerActivity { }

    private final Activity mActivity;

    public ActivityScopeModule(Activity activity) {
        mActivity = activity;
    }

    @Provides @PerActivity
    Context provideContext() {
        return mActivity;
    }

    @Provides @PerActivity
    CatalogUseCase provideCatalogUseCase(CatalogRepository catalogRepository) {

        System.out.println(" !!!!!!! CatalogUseCase is CREATED !!!!!!");

        return new CatalogUseCase(catalogRepository);
    }

}
