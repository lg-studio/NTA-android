package com.usinformatics.nytrip.ui.selection.map;

import com.google.android.gms.maps.model.Marker;

/**
 * Created by D1m11n on 29.07.2015.
 */
public class MapMarkerClicker {

    public static void displayInfo(Marker marker){
        if(marker.isInfoWindowShown())
            marker.hideInfoWindow();
        else
            marker.showInfoWindow();;
    }
}
