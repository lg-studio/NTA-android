package com.zl.android.ui;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zl.android.ui.scopes.ActivityScope;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Presenter is a class communicating to UI components on one side and UseCases on the other side.
 *
 * <br>
 * Its lifecycle is closely coupled with a lifecycle of the hosting fragment. Implementing classes
 * can use @LayoutId annotation for their type. For such presenters layout will be inflated
 * automatically. Otherwise {@link #inflateView(LayoutInflater, ViewGroup)} method can be
 * overridden and provide view for the fragment.
 *
 * @author sergej
 */
public abstract class Presenter {

    /** Presenters annotated with this will be inflating referenced view automatically. */
    @Retention(RUNTIME) @Target(TYPE)
    public @interface LayoutId {
        @LayoutRes
        int value();
    }

    /**
     * Called when presenter needs to be created. Activity and Fragment are already created at
     * that time.
     *
     * <br>
     *     Implementation should inject global scope fields (like UseCases, Context etc.) and
     *     UI views. It should also request Observable instances, if some are to be used by
     *     presenter.
     *
     * @param view  instance of root view
     */
    public abstract void create(@NonNull View view, @NonNull ActivityScope injector, @Nullable Bundle savedInstanceState);

    /**
     * Called when hosting fragment resumes.
     *
     * <br>
     *     Implementation should subscribe observers at observables created in
     *     {@link #create(View, ActivityScope, Bundle)} method.
     */
    public abstract void resume();

    /**
     * Called when hosting fragment pauses.
     *
     * <br>
     *     Implementation should unsubscribe observers subscribed in {@link #resume()}
     */
    public abstract void pause();

    /**
     * Called when hosting fragment is about to be destroyed.
     *
     * <br>
     *     Implementation should unbind views and release all other used resources.
     */
    public abstract void destroy();

    /**
     * Implementation can override this method and store a state for been eventually received in
     * {@link #create(View, ActivityScope, Bundle)} method after new instance of presenter is
     * created on configuration change.
     *
     * @see {@link android.support.v4.app.Fragment#onSaveInstanceState(Bundle)}
     * @param outState
     */
    public void saveInstanceState(Bundle outState) { }

    /** Called by {@link PresenterFragment} and creates a view from {@LayoutId} */
    protected View inflateView(LayoutInflater inflater, ViewGroup container) {
        Presenter.LayoutId ann = getClass().getAnnotation(Presenter.LayoutId.class);
        if (ann == null) {
            throw new IllegalArgumentException(
                    "Provided fragment must have @LayoutId annotation for class. Provider: " + getClass());
        }
        return inflater.inflate(ann.value(), container, false);
    }

    protected <T> Observable.Transformer<T, T> applySubscribeIoObserveMain() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
