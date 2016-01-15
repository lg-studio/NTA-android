package com.usinformatics.nytrip.ui.account.activities;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.usinformatics.nytrip.R;
import com.usinformatics.nytrip.models.UserModel;
import com.usinformatics.nytrip.socials.facebook.FacebookState;
import com.usinformatics.nytrip.socials.gplus.models.GPlusState;
import com.usinformatics.nytrip.ui.account.AccountPresenter;
import com.usinformatics.nytrip.ui.account.IAccountView;
import com.usinformatics.nytrip.ui.account.fragments.CheckYourEmailFragment;
import com.usinformatics.nytrip.ui.account.fragments.LoginFragment;
import com.usinformatics.nytrip.ui.account.fragments.PasswordRecoveryFragment;
import com.usinformatics.nytrip.ui.account.fragments.RegisterFragment;
import com.usinformatics.nytrip.ui.account.model.AccountType;
import com.usinformatics.nytrip.ui.additional.dialogs.DialogFactory;
import com.usinformatics.nytrip.ui.intros.IntroActivity;

/**
 * Created by D1m11n on 11.06.2015.
 */

//http://stackoverflow.com/questions/26835209/appcompat-v7-toolbar-up-back-arrow-not-working
public class AccountActivity extends BaseAccountActivity implements IAccountView {

    private static final String TAG = "ACCOUNT_ACTIVITY";
    private AccountPresenter mPresenter;
    private int ID_CONTENT=R.id.content_account;
    private LoginFragment mLoginFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_account);
        initToolbar();
        mPresenter = new AccountPresenter(this, this);
        mLoginFragment=LoginFragment.newInstance(mPresenter);
        findAndInitViews();
    }

    private void initToolbar() {
        Toolbar tl=(Toolbar) findViewById(R.id.tl);
        setSupportActionBar(tl);
        tl.setTitleTextColor(this.getResources().getColor(android.R.color.white));
        //tl.setNavigationIcon(R.mipmap.ic_launcher);
    }

    @Override
    protected void unhandledFacebookActivityResult(int requestCode, int responseCode, Intent intent) {

    }

    private void findAndInitViews() {
        getFragmentManager().beginTransaction().replace(ID_CONTENT,mLoginFragment).commit();
    }

    @Override
    public void onGplusClientActionsChange(GPlusState states, Object... data) {
        mPresenter.onGplusClientActionsChange(states, data);
    }

    @Override
    public void showProgress(AccountType type, String progressMessage) {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showDialog(final String title, final String message) {
        DialogFactory.showSimpleOneButtonDialog(AccountActivity.this,"Error" + title, message);
    }

    @Override
    public void setUsernameError(final String error) {
        DialogFactory.showSimpleOneButtonDialog(AccountActivity.this, "Error username", error);
    }


    @Override
    public void setPasswordError(final String error) {
        DialogFactory.showSimpleOneButtonDialog(AccountActivity.this, "Error password", error);
    }

    @Override
    public void setNetworkError(String error) {
        DialogFactory.showSimpleOneButtonDialog(AccountActivity.this, "Error network", error);
    }

    @Override
    public void navigateToMain() {
        startActivity(new Intent(AccountActivity.this, IntroActivity.class));
    }

    @Override
    public void showAccountData(UserModel user) {

    }

    @Override
    public void setTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void finishActivity() {
        this.finish();
    }

    @Override
    public void onFacebookActionsChange(FacebookState states, Object... data) {
        mPresenter.onFacebookActionsChange(states, data);
    }

    public void openRegisterFragment(){
        getFragmentManager().beginTransaction().replace(ID_CONTENT, RegisterFragment.newInstance(mPresenter)).commit();
    }


    public void openForgotPasswordFragment(){
        getFragmentManager().beginTransaction().replace(ID_CONTENT, PasswordRecoveryFragment.newInstance(mPresenter)).commit();
    }

     public void openCheckYourEmailFragment(){
        getFragmentManager().beginTransaction().replace(ID_CONTENT, CheckYourEmailFragment.newInstance(mPresenter)).commit();
    }

    @Override
    public void onBackPressed() {
        Fragment frg=getFragmentManager().findFragmentById(ID_CONTENT);
        if (frg==null||frg instanceof LoginFragment){
            this.onPause();
            return;
        }
        getFragmentManager().beginTransaction().replace(ID_CONTENT, mLoginFragment).commit();
        Log.e(TAG, frg.getClass().getSimpleName());
    }
}
