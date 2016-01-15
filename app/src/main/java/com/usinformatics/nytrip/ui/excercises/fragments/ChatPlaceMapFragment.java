package com.usinformatics.nytrip.ui.excercises.fragments;

import android.app.Activity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.usinformatics.nytrip.R;

/**
 * Created by D1m11n on 17.06.2015.
 */
public class ChatPlaceMapFragment extends MapFragment implements OnMapReadyCallback{

    private static final String TAG = ChatPlaceMapFragment.class.getSimpleName();
    private LatLng mLatLng;
    private String mTitle;

    private static final double OFFSET_Y=0.00012D;
    private static final double OFFSET_X=0.00100D;


    public static ChatPlaceMapFragment newInstance(LatLng place, String title){
        ChatPlaceMapFragment fr= new ChatPlaceMapFragment();
        fr.mLatLng=place;
        fr.mTitle=title;
        return fr;
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Marker m=displayMarker();
        getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, getMap().getMaxZoomLevel() - 6));
        LatLng poiSelectedLatLng = new LatLng(m.getPosition().latitude
                + OFFSET_Y, m.getPosition().longitude +OFFSET_X);
        getMap().animateCamera(CameraUpdateFactory.newLatLng(poiSelectedLatLng));
        printMarkersToLog();
    }

    private void printMarkersToLog() {

    }

    private Marker displayMarker() {
        return this.getMap().addMarker(generateForPlace());
    }

    private MarkerOptions generateForPlace(){
        return new MarkerOptions().
                position(mLatLng).
                title("Name: " + mTitle).
                // snippet("SNIPPET =" + taskMarkerModel.snippet).
                icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_pin));
    }


//    public void setMarkers(ArrayList<TaskMarkerModel> list, String idCentralMarker){
//       if(mMapHelper!=null) {
//           mMapHelper.setMarkers(list);
//           return;
//       }
//        mIdCentralMarker=idCentralMarker;
//        mMarkers =list;
//    }


}
