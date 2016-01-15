package com.usinformatics.nytrip.ui.account.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.usinformatics.nytrip.BaseFragment;
import com.usinformatics.nytrip.DevConsts;
import com.usinformatics.nytrip.R;
import com.usinformatics.nytrip.models.UserModel;
import com.usinformatics.nytrip.storages.StorageFactory;
import com.usinformatics.nytrip.ui.account.AccountPresenter;
import com.usinformatics.nytrip.ui.account.activities.AccountActivity;
import com.usinformatics.nytrip.ui.account.model.AccountType;

/**
 * Created by D1m11n on 24.06.2015.
 */
public class LoginFragment extends BaseFragment {


    private static final String TAG = "LOGIN_FRAGMENT";
    private View mRootView;
    private AccountPresenter mPresenter;
    private EditText metEmail, metPassword;
    private Button mLoginBtn;
    private AccountActivity mActivity;
    private TextView mtvForgotPassword;
    private TextView mtvRegister;

    public static LoginFragment newInstance(AccountPresenter presenter) {
        LoginFragment frg = new LoginFragment();
        frg.mPresenter = presenter;
        return frg;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (AccountActivity) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.frg_login, container, false);
        findViews();
        initViews();

        return mRootView;
    }

    private void findViews() {
        metEmail = (EditText) mRootView.findViewById(R.id.et_email);
        metPassword = (EditText) mRootView.findViewById(R.id.et_passwd);
        mLoginBtn = (Button) mRootView.findViewById(R.id.btn_login);
        mtvForgotPassword = (TextView) mRootView.findViewById(R.id.tv_forgot_password);
        mtvRegister = (TextView) mRootView.findViewById(R.id.tv_register_here);
    }

    private void initViews() {
        if (DevConsts.IS_FAKE_USER) {
            metEmail.setText(DevConsts.FAKE_USER_EMAIL);
            metPassword.setText(DevConsts.FAKE_USERPASS);
        }else{
            metEmail.setText(StorageFactory.getUserStorage(mActivity).getLastUsedEmail());
        }
        initLoginBtn();
        initForgotPasswordBtn();
        initRegistrationBtn();
    }

    private void initLoginBtn() {

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateUserFields())
                    return;
                if (!validatePassword())
                    return;
                UserModel u = new UserModel();
                u.email = metEmail.getText().toString();
                mPresenter.startLogin(AccountType.DEFAULT, u, metPassword.getText().toString());
            }
        });
    }

    private void initForgotPasswordBtn() {
        mtvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.openForgotPasswordFragment();
            }
        });
    }

    private void initRegistrationBtn() {
        mtvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.openRegisterFragment();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mActivity.setTitle("Login");
    }


    private boolean validateUserFields() {
        if (TextUtils.isEmpty(metEmail.getText())) {
            mActivity.setUsernameError("Email is empty");
            return false;
        }
        return true;
    }

    private boolean validatePassword() {
        if (TextUtils.isEmpty(metPassword.getText())) {
            Log.e(TAG, "Password is empty");
            mActivity.setUsernameError("Password password is empty");
            return false;
        }
        return true;
    }
}
