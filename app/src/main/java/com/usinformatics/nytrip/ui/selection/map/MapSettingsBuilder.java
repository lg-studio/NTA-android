package com.usinformatics.nytrip.ui.selection.map;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.UiSettings;

/**
 * Created by D1m11n on 18.06.2015.
 */
public class MapSettingsBuilder {


    public static GoogleMap setDefault(GoogleMap map) {
        UiSettings sets = map.getUiSettings();
        sets.setCompassEnabled(false);
        sets.setRotateGesturesEnabled(false);
        sets.setZoomControlsEnabled(true);
        sets.setMyLocationButtonEnabled(true);
        return map;
    }
}
