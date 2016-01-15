package com.zl.android.ui.scopes;

import com.zl.android.domain.features.catalog.CatalogRepository;
import com.zl.android.repository.storages.web.WebServiceLocator;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Singleton injection scope assigned to application instance.
 */
@Singleton
@Component(modules = AppScopeModule.class)
public interface AppScope {

    // region -- Expose instances to depending modules and code

    CatalogRepository catalogRepository();
    WebServiceLocator webServiceLocator();

    // endregion

}
