package com.usinformatics.nytrip;

import com.google.android.gms.maps.model.LatLng;
import com.usinformatics.nytrip.ui.additional.popup.ItemRawPopup;
import com.usinformatics.nytrip.ui.intros.IntroModel;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by D1m11n on 12.06.2015.
 */
public class FakeData {


    public static final String SEMESTER_ID="5595c6afbea5bd2407c67708";

    public static final String EPISODE_ID="5595672dd049676425733398";

    public static final String SCENE_ID="5595672dd049676425733395";

    public static IntroModel [] getFakeIntro(){
        int size=3;
        IntroModel [] mm= new IntroModel[size];
        for (int i=0; i<size;i++){
            mm[i]= new IntroModel();
            mm[i].title="fragment " + i;
            mm[i].coverUrl="";
        }
        return mm;
    }

    public static ArrayList<ItemRawPopup> getPopupWindowAkaSettingsData(){
        ArrayList<ItemRawPopup> list = new ArrayList<>();
        for (int i=0; i< ItemRawPopup.values().length; i++){
            list.add(ItemRawPopup.values()[i]);
        }
        return list;
    }

   public static LatLng generateRandomLatLng(double x0, double y0, int radius){
       Random random = new Random();

       // Convert radius from meters to degrees
       double radiusInDegrees = radius / 111000f;

       double u = random.nextDouble();
       double v = random.nextDouble();
       double w = radiusInDegrees * Math.sqrt(u);
       double t = 2 * Math.PI * v;
       double x = w * Math.cos(t);
       double y = w * Math.sin(t);

       // Adjust the x-coordinate for the shrinking of the east-west distances
       double new_x = x / Math.cos(y0);

       double foundLongitude = new_x + x0;
       double foundLatitude = y + y0;
       System.out.println("Longitude: " + foundLongitude + "  Latitude: " + foundLatitude );
       return new LatLng(foundLatitude, foundLongitude);
   }

    public static final class SessionIdentifierGenerator {
        private SecureRandom random = new SecureRandom();
        public String nextSessionId() {
            return new BigInteger(130, random).toString(32);
        }
    }

}
