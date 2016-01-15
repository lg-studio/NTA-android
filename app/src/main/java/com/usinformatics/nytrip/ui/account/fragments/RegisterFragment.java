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

import com.usinformatics.nytrip.AppConsts;
import com.usinformatics.nytrip.BaseFragment;
import com.usinformatics.nytrip.DevConsts;
import com.usinformatics.nytrip.R;
import com.usinformatics.nytrip.models.UserModel;
import com.usinformatics.nytrip.ui.account.AccountPresenter;
import com.usinformatics.nytrip.ui.account.activities.AccountActivity;
import com.usinformatics.nytrip.ui.account.model.AccountType;

/**
 * Created by D1m11n on 24.06.2015.
 */
public class RegisterFragment extends BaseFragment {


    private static final String TAG = "REGISTER_FRAGMENT";
    private View mRootView;
    private AccountPresenter mPresenter;
    private EditText metEmail, metPassword, metConfirmPassword, metFirstName, metLastName, metGroup;
    private Button mRegisterBtn;


    private AccountActivity mActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (AccountActivity) activity;
    }

    public static RegisterFragment newInstance(AccountPresenter presenter) {
        RegisterFragment frg = new RegisterFragment();
        frg.mPresenter = presenter;
        return frg;
    }

    @Override
    public void onResume() {
        super.onResume();
        mActivity.setTitle("Registration");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.frg_register, container, false);
        findViews();
        initViews();
        return mRootView;
    }

    private void findViews() {
        metEmail = (EditText) mRootView.findViewById(R.id.et_email);
        metFirstName = (EditText) mRootView.findViewById(R.id.et_firstname);
        metLastName = (EditText) mRootView.findViewById(R.id.et_lastname);
        metConfirmPassword = (EditText) mRootView.findViewById(R.id.et_confirm_passwd);
        metPassword = (EditText) mRootView.findViewById(R.id.et_passwd);
        metGroup = (EditText) mRootView.findViewById(R.id.et_teachercode);
        mRegisterBtn = (Button) mRootView.findViewById(R.id.btn_register);
    }

    private void initViews() {
        if (DevConsts.IS_FAKE_REGISTER) {
            metEmail.setText(DevConsts.FAKE_USER_EMAIL);
            metFirstName.setText(DevConsts.FAKE_USERNAME);
            metLastName.setText(DevConsts.FAKE_USER_LASTNAME);
            metPassword.setText(DevConsts.FAKE_USERPASS);
            metConfirmPassword.setText(DevConsts.FAKE_USERPASS);
            metGroup.setText(DevConsts.FAKE_USERGRPOUP);
        }
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "initViews; on CLick");
                if (!validatePasswords()) {
                    return;
                }
                Log.e(TAG, "initViews; on CLick");
                if (!validateUserFields())
                    return;
                Log.e(TAG, "initViews; on CLick");
                UserModel user = new UserModel();
                user.email = metEmail.getText().toString();
                user.firstName = metFirstName.getText().toString();
                user.lastName = metLastName.getText().toString();
                user.teacherCode = metGroup.getText().toString();
                mPresenter.startRegister(AccountType.DEFAULT, user, metPassword.getText().toString());
            }
        });

    }

    private boolean validateUserFields() {
        if (TextUtils.isEmpty(metFirstName.getText())) {
            mActivity.setUsernameError("First name is empty");
            return false;
        }
        if (TextUtils.isEmpty(metLastName.getText())) {
            mActivity.setUsernameError("Last name is empty");
            return false;
        }
        if (TextUtils.isEmpty(metEmail.getText())) {
            mActivity.setUsernameError("Email is empty");
            return false;
        }
        if (TextUtils.isEmpty(metGroup.getText())) {
            mActivity.setUsernameError("Teacher code is empty");
            return false;
        }
        return true;
    }

    private boolean validatePasswords() {
        if (TextUtils.isEmpty(metPassword.getText()) || TextUtils.isEmpty(metConfirmPassword.getText())) {
            Log.e(TAG, "Password or Confirm password is empty");
            mActivity.setUsernameError("Password or Confirm password is empty");
            return false;
        }
        if (!metPassword.getText().toString().equals(metConfirmPassword.getText().toString())) {
            Log.e(TAG, "Passwords not equals");
            mActivity.setUsernameError("Passwords not equals");
            return false;
        }
        if (metPassword.getText().length() < AppConsts.MIN_PASS_LENGTH) {
            Log.e(TAG, "Password is empty");
            mActivity.setUsernameError("Password is too short ( required min 6 symbols )");
            return false;
        }
        return true;
    }
}
