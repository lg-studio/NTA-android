package com.usinformatics.nytrip.ui.selection.fragments;

import android.app.Fragment;

import com.zl.android.ui.Presenter;
import com.zl.android.ui.PresenterFragment;

public class EpisodesPresenterFragment extends PresenterFragment implements IFragment {

    public static EpisodesPresenterFragment newInstance(Class<? extends Presenter> presenterClazz) {
        return (EpisodesPresenterFragment) new EpisodesPresenterFragment()
                .initWithPresenter(presenterClazz);
    }

    @Override
    public Type getFragmentType() {
        return Type.EPISODES;
    }

    @Override
    public void updateContent() {
        // todo
    }

    @Override
    public Fragment getInstance() {
        return this;
    }

}
