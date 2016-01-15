package com.usinformatics.nytrip.ui.account.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.usinformatics.nytrip.R;
import com.usinformatics.nytrip.ui.account.AccountPresenter;
import com.usinformatics.nytrip.ui.account.activities.AccountActivity;

/**
 * Created by admin on 7/17/15.
 */
public class PasswordRecoveryFragment extends Fragment {

    private AccountPresenter mPresenter;
    private EditText mEnterEmail;
    private AccountActivity mActivity;

    public static PasswordRecoveryFragment newInstance(AccountPresenter presenter) {
        PasswordRecoveryFragment frg = new PasswordRecoveryFragment();
        frg.mPresenter = presenter;
        return frg;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (AccountActivity) activity;
        ((AccountActivity) activity).setTitle(getActivity().getString(R.string.password_recovery));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.frg_passvord_recovery, container, false);
        initEnterEmail(view);
        initSendBtn(view);

        return view;
    }

    private void initEnterEmail(View view) {
        mEnterEmail = ((EditText) view.findViewById(R.id.password_recovery_enter_email));
    }

    private void initSendBtn(View view) {
        view.findViewById(R.id.password_recovery_send_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFieldEmpty()) {
                    sendRecoveryPasswordRequest();

                } else {
                    showErrorMsg();
                }
            }
        });
    }

    private void showErrorMsg() {
        mEnterEmail.setError(getActivity().getString(R.string.field_is_empty));
    }

    private boolean isFieldEmpty() {
        return TextUtils.isEmpty(mEnterEmail.getText());
    }


    private void sendRecoveryPasswordRequest() {
        openCheckYourEmailFragment();
        hideKeyboard();

    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEnterEmail.getWindowToken(), 0);
    }

    private void openCheckYourEmailFragment() {
        mActivity.openCheckYourEmailFragment();
    }
}
