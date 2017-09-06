package com.infotechincubator.nearme;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.places.PlaceManager;
import com.facebook.places.model.PlaceFields;
import com.facebook.places.model.PlaceSearchRequestParams;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;

public class TestActivity extends BaseActivity implements PlaceManager.OnRequestReadyCallback, GraphRequest.Callback {
    private static final String TAG = TestActivity.class.getSimpleName();

    PlaceAutocompleteFragment placeAutocompleteFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_test);



        testPlaceAutocomplete();
        //testFbSearchPlace();
    }

    private void testPlaceAutocomplete() {
        TelephonyManager tm = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
        String countryCodeValue = tm.getNetworkCountryIso();

        AutocompleteFilter autocompleteFilter = new AutocompleteFilter.Builder().setCountry(countryCodeValue).setTypeFilter(AutocompleteFilter.TYPE_FILTER_ESTABLISHMENT).build();

        LatLng currentLatLng = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());

        placeAutocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.activity_test_autocomplete_fragment);
        placeAutocompleteFragment.setBoundsBias(Utils.toBounds(currentLatLng, 1000));
        placeAutocompleteFragment.setFilter(autocompleteFilter);
        placeAutocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(com.google.android.gms.location.places.Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName());
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });
    }

    private void testFbSearchPlace() {
        PlaceSearchRequestParams.Builder builder = new PlaceSearchRequestParams.Builder();
        builder.setDistance(1000);
        builder.setLimit(10);
        builder.addField(PlaceFields.CATEGORY_LIST);
        builder.addField(PlaceFields.NAME);
        builder.addField(PlaceFields.LOCATION);
        builder.addField(PlaceFields.OVERALL_STAR_RATING);

        PlaceManager.newPlaceSearchRequest(builder.build(), this);
    }

    @Override
    public void onLocationError(PlaceManager.LocationError error) {
        Log.i("onLocationError: ", error.toString());
    }

    @Override
    public void onRequestReady(GraphRequest graphRequest) {
        graphRequest.setCallback(this);
        graphRequest.executeAndWait();
    }

    @Override
    public void onCompleted(GraphResponse response) {
        if(response != null) {
            Log.i("onCompleted: ", response.toString());
        }
    }
}
