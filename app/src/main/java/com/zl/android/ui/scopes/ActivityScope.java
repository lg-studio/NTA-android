package com.zl.android.ui.scopes;

import com.zl.android.ui.features.catalog.EpisodesBrowserPresenter;
import com.zl.android.ui.scopes.ActivityScopeModule.PerActivity;

import dagger.Component;

/** Activity related scope exists while an activity exists. */
@PerActivity
@Component( modules = ActivityScopeModule.class, dependencies = AppScope.class)
public interface ActivityScope {

    EpisodesBrowserPresenter inject(EpisodesBrowserPresenter presenter);

}
