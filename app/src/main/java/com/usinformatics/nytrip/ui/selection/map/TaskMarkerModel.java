package com.usinformatics.nytrip.ui.selection.map;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by D1m11n on 18.06.2015.
 */
public class TaskMarkerModel {

    public LatLng latlng;

    public String taskId;

    public String iconUrl;

    public String title;

//    public double latitude;
//
//    public double longitude;

    public String snippet;

    public int idIcon;


    public LatLng getLatLng(){
//      if (latlng==null)
//          latlng= new LatLng(latitude, longitude);
        return latlng;
    }

    @Override
    public String toString() {
        return "TaskMarkerModel{" +
                "latlng=" + latlng +
                ", taskId='" + taskId + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", title='" + title + '\'' +
                ", snippet='" + snippet + '\'' +
                ", idIcon=" + idIcon +
                '}';
    }
}
