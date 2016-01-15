package com.zl.android.ui.features.catalog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;

import com.usinformatics.nytrip.R;
import com.usinformatics.nytrip.storages.StorageFactory;
import com.usinformatics.nytrip.ui.additional.dialogs.DialogFactory;
import com.zl.android.domain.features.catalog.CatalogUseCase;
import com.zl.android.domain.features.catalog.model.Course;
import com.zl.android.domain.features.catalog.model.Episode;
import com.zl.android.ui.Presenter;
import com.zl.android.ui.scopes.ActivityScope;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/** Loads and episodes and scenes and shows them. */
@Presenter.LayoutId(R.layout.frg_episodes_with_scenes)
public class EpisodesBrowserPresenter extends Presenter {

    private static final String TAG = EpisodesBrowserPresenter.class.getSimpleName();

    // global scope
    @Inject CatalogUseCase mCatalogUseCase;
    @Inject Context mContext;

    // views
    @Bind(R.id.lv_act_scenes) ExpandableListView mEpisodesListView;

    // local scope
    private EpisodesListAdapter mEpisodesListAdapter;
    private Observable<List<Episode>> mGetEpisodes;
    private Subscription mGetEpisodesSubscription;

    @Override
    public void create(@NonNull View view, @NonNull ActivityScope injector, @Nullable Bundle savedInstanceState) {
        injector.inject(this);
        ButterKnife.bind(this, view);

        mEpisodesListAdapter = new EpisodesListAdapter();
        mEpisodesListView.setAdapter(mEpisodesListAdapter);

        // todo this should be replaced with either user scope or with observables
        String groupId = StorageFactory.getUserStorage(mContext).getUser().getClasses()[0];

        // In the chain down below we first query all courses
        // and then query all episodes of the first course
        mGetEpisodes = mCatalogUseCase.getCourses(groupId)
                .flatMap(new Func1<List<Course>, Observable<List<Episode>>>() {
                    @Override
                    public Observable<List<Episode>> call(List<Course> courses) {
                        return mCatalogUseCase.getEpisodes(courses.get(0).id);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    @Override
    public void resume() {
        mGetEpisodesSubscription = mGetEpisodes.subscribe(new Observer<List<Episode>>() {
            @Override
            public void onCompleted() {
                // nop
            }

            @Override
            public void onError(Throwable e) {
                DialogFactory.showSimpleOneButtonDialog(mContext,
                        "Error", "Cannot load episodes: " + e.getMessage());
                Log.e(TAG, "cannot load episodes", e);
            }

            @Override
            public void onNext(List<Episode> episodes) {
                mEpisodesListAdapter.setEpisodes(episodes);
            }
        });
    }

    @Override
    public void pause() {
        mGetEpisodesSubscription.unsubscribe();
    }

    @Override
    public void destroy() {
    }

}
