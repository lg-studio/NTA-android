package com.zl.android.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.usinformatics.nytrip.ui.BaseActivity;

/**
 * This fragment is intended to instantiate, host and handle a presenter instance
 * associated with it. It requests {@link com.zl.android.ui.scopes.ActivityScope} from parent
 * activity which is for now {@link BaseActivity}.
 *
 * <br>
 *     Create a new fragment instance like this:
 *     <code>
 *         PresenterFragment fragment = PresenterFragment.newInstance(MyPresenter.class);
 *     </code>
 *
 * @author sergej
 */
public class PresenterFragment extends Fragment {

    protected static final String ARG_PRESENTER = "presenter";

    public static PresenterFragment newInstance(Class<? extends Presenter> presenterClazz) {
        return new PresenterFragment().initWithPresenter(presenterClazz);
    }

    public PresenterFragment initWithPresenter(Class<? extends Presenter> presenterClazz) {
        Bundle args = getArguments();
        if (args == null) {
            args = new Bundle();
            setArguments(args);
        }
        args.putString(ARG_PRESENTER, presenterClazz.getName());
        return this;
    }

    private Presenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            @SuppressWarnings("unchecked") Class<? extends Presenter> clazz = (Class<? extends Presenter>)
                    Class.forName(getArguments().getString(ARG_PRESENTER));

            mPresenter = clazz.newInstance();
            return mPresenter.inflateView(inflater, container);

        } catch (Exception e) {
            throw new IllegalArgumentException("cannot create presenter", e);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        BaseActivity act = (BaseActivity) getActivity();
        mPresenter.create(getView(), act.getScope(), savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.resume();
    }

    @Override
    public void onPause() {
        mPresenter.pause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mPresenter.destroy();
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        mPresenter.saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

}
