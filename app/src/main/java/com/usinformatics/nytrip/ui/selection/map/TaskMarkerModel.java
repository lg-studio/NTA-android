package com.usinformatics.nytrip.ui.selection.map;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by D1m11n on 18.06.2015.
 */
public class TaskMarkerModel {

    private LatLng latlng;

    public int taskId;


    public String iconUrl;

    public String title;

    private double latitude;

    private double longitude;

    public String snippet;


    public LatLng getLatLng(){
      if (latlng==null)
          latlng= new LatLng(latitude, longitude);
        return latlng;
    }

}
