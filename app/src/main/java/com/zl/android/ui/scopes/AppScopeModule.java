package com.zl.android.ui.scopes;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.usinformatics.nytrip.NyTripApplication;
import com.zl.android.domain.features.catalog.CatalogRepository;
import com.zl.android.repository.storages.db.DbStorage;
import com.zl.android.repository.features.catalog.CatalogRepo;
import com.zl.android.repository.features.catalog.CatalogWebService;
import com.zl.android.repository.common.upload.UploadTaskQueue;
import com.zl.android.repository.storages.memory.MemoryStorage;
import com.zl.android.repository.storages.web.WebServiceLocator;
import com.zl.android.repository.storages.web.WebStorage;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/** Application scope module providing instances living in this scope. */
@Module
public class AppScopeModule {

    private final NyTripApplication mApplication;

    public AppScopeModule(NyTripApplication app) {
        mApplication = app;
    }

    // region -- Common singleton instances

    @Provides @Singleton
    NyTripApplication provideApplication() {
        return mApplication;
    }

    @Provides @Singleton
    Context provideApplicationContext() {
        return mApplication;
    }

    @Provides @Singleton
    CatalogWebService provideWebService(WebServiceLocator locator) {
        return locator.getCatalogWebService();
    }

    @Provides @Singleton
    WebServiceLocator provideWebServiceLocator() {
        return new WebServiceLocator();
    }

    @Provides @Singleton
    WebStorage provideWebStorage(CatalogWebService catalogWebService, UploadTaskQueue uploadTaskQueue, Context context) {
        return new WebStorage(catalogWebService, uploadTaskQueue, context);
    }

    @Provides @Singleton
    DbStorage provideDbStorage() {
        return new DbStorage();
    }

    @Provides @Singleton
    MemoryStorage provideMemoryStorage() {
        return new MemoryStorage();
    }

    @Provides @Singleton
    Gson provideGson() {
        return new GsonBuilder().create();
    }

    @Provides @Singleton
    UploadTaskQueue provideUploadTaskQueue(Context context, Gson gson) {
        return UploadTaskQueue.create(context, gson);
    }

    // endregion

    // region -- Repositories

    @Provides @Singleton
    CatalogRepository provideCatalogRepository(MemoryStorage memoryStorage,
                                               DbStorage dbStorage,
                                               WebStorage webStorage) {
        return new CatalogRepo(memoryStorage, dbStorage, webStorage);
    }

    // endregion

}
