package com.infotechincubator.nearme;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    PlaceTypeAdapter placeTypeAdapter;
    RecyclerView placeTypeRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        placeTypeRV = (RecyclerView) findViewById(R.id.activity_main_placeTypeRV);

        initialize();
    }

    @Override
    protected void initialize() {
        super.initialize();

        placeTypeAdapter = new PlaceTypeAdapter(Utils.getPlaceTypes());
        placeTypeRV.setLayoutManager(new GridLayoutManager(this, 2));
        placeTypeRV.setHasFixedSize(true);
        placeTypeRV.setAdapter(placeTypeAdapter);

        Location lastKnownLocation = getLastKnownLocation();
        if (lastKnownLocation != null) {
            Geocoder gCoder = new Geocoder(this);
            List<Address> addresses = null;
            try {
                addresses = gCoder.getFromLocation(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude(), 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (addresses != null && addresses.size() > 0) {
                setTitle(addresses.get(0).getThoroughfare());
            }
        }
    }
}
