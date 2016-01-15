package com.usinformatics.nytrip.ui.account.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.usinformatics.nytrip.R;
import com.usinformatics.nytrip.ui.account.AccountPresenter;

/**
 * Created by admin on 7/18/15.
 */
public class CheckYourEmailFragment extends Fragment {

    private AccountPresenter mPresenter;

    public static CheckYourEmailFragment newInstance(AccountPresenter presenter) {
        CheckYourEmailFragment frg = new CheckYourEmailFragment();
        frg.mPresenter = presenter;
        return frg;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_check_your_email, container, false);
        return view;
    }
}
