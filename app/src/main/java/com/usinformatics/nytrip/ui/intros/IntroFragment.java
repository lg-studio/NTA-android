package com.usinformatics.nytrip.ui.intros;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.usinformatics.nytrip.R;

/**
 * Created by D1m11n on 12.06.2015.
 */
public class IntroFragment extends Fragment {

    private ImageView introImage;
    private View mRootView;

    private String mCoverUrl = "";
    private boolean isLastFragment;
    private int mImageResource;

    public static IntroFragment newInstance(IntroModel data) {
        IntroFragment frg = new IntroFragment();
        if (data != null) {
            frg.mCoverUrl = data.coverUrl;
            frg.mImageResource = data.imageResource;
        }
        return frg;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.frg_intro, container, false);
        findViews(mRootView);
        initFoundedViews();
        return mRootView;
    }

    private void findViews(View rootView) {
        introImage = (ImageView) rootView.findViewById(R.id.intro_image);
    }

    private void initFoundedViews() {
        introImage.setBackgroundResource(mImageResource);
    }
}
