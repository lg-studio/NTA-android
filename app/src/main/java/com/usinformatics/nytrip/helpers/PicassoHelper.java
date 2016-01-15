package com.usinformatics.nytrip.helpers;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import common.picasso.transformations.CircleTransform;

/**
 * Created by D1m11n on 23.07.2015.
 */
public class PicassoHelper {

    private PicassoHelper(){};

    public static void showSimpleImage(final Context context, String url, final int idError, final ImageView view){
        Picasso.with(context).load(url).error(idError).placeholder(idError).into(view);
    }

    public static void showRoundedImage(final Context context, String url, final int idError, final ImageView view){
        Picasso.with(context).load(url).transform(new CircleTransform()).error(idError).placeholder(idError).into(view);
    }


}
