package com.infotechincubator.nearme;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.places.PlaceManager;
import com.facebook.places.model.CurrentPlaceRequestParams;
import com.facebook.places.model.PlaceFields;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.*;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements PlaceManager.OnRequestReadyCallback, GraphRequest.Callback, PlaceSelectionListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    ArrayList<FbPlace> fbPlaces;

    PlaceTypeAdapter placeTypeAdapter;
    FbPlaceListAdapter fbPlaceListAdapter;
    RecyclerView placeTypeRV;
    RecyclerView fbPlaceRV;
    PlaceAutocompleteFragment placeAutocompleteFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        placeTypeRV = (RecyclerView) findViewById(R.id.activity_main_placeTypeRV);
        fbPlaceRV = (RecyclerView) findViewById(R.id.activity_main_fbPlaceTypeRV);

        testFbCurrentPlace();
        testFsCurrentPlace();

        initialize();
    }

    @Override
    protected void initialize() {
        super.initialize();

        TelephonyManager tm = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
        String countryCodeValue = tm.getNetworkCountryIso();

        // TODO: 2/9/17 replace hardcoded country code
        AutocompleteFilter autocompleteFilter = new AutocompleteFilter.Builder().setCountry("MM").setTypeFilter(AutocompleteFilter.TYPE_FILTER_ESTABLISHMENT).build();

        placeAutocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.activity_main_autocomplete_fragment);
        placeAutocompleteFragment.setFilter(autocompleteFilter);
        placeAutocompleteFragment.setOnPlaceSelectedListener(this);
        if (lastKnownLocation != null) {
            LatLng currentLatLng = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
            placeAutocompleteFragment.setBoundsBias(Utils.toBounds(currentLatLng, 1000));
        }

        placeTypeAdapter = new PlaceTypeAdapter(Utils.getPlaceTypes());
        placeTypeRV.setLayoutManager(new GridLayoutManager(this, 2));
        placeTypeRV.setHasFixedSize(true);
        placeTypeRV.setAdapter(placeTypeAdapter);

        fbPlaces = new ArrayList<>();
        fbPlaceListAdapter = new FbPlaceListAdapter(fbPlaces);
        fbPlaceRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        fbPlaceRV.setAdapter(fbPlaceListAdapter);

        Location lastKnownLocation = getLastKnownLocation();
        if (lastKnownLocation != null) {
            Geocoder gCoder = new Geocoder(this);
            List<Address> addresses = null;
            try {
                addresses = gCoder.getFromLocation(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude(), 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            /*
            // set title for current address
            if (addresses != null && addresses.size() > 0) {
                setTitle(addresses.get(0).getThoroughfare());
            }
            */
        }
    }

    private void testFbCurrentPlace() {
        CurrentPlaceRequestParams.Builder builder = new CurrentPlaceRequestParams.Builder();
        builder.setMinConfidenceLevel(CurrentPlaceRequestParams.ConfidenceLevel.LOW);
        builder.setLimit(10);
        //builder.addField(PlaceFields.ABOUT);
        //builder.addField(PlaceFields.APP_LINKS);
        builder.addField(PlaceFields.CATEGORY_LIST);
        builder.addField(PlaceFields.CHECKINS);
        //builder.addField(PlaceFields.CONFIDENCE_LEVEL);
        //builder.addField(PlaceFields.CONTEXT);
        //builder.addField(PlaceFields.COVER);
        //builder.addField(PlaceFields.DESCRIPTION);
        //builder.addField(PlaceFields.ENGAGEMENT);
        //builder.addField(PlaceFields.HOURS);
        //builder.addField(PlaceFields.IS_ALWAYS_OPEN);
        //builder.addField(PlaceFields.IS_PERMANENTLY_CLOSED);
        //builder.addField(PlaceFields.IS_VERIFIED);
        //builder.addField(PlaceFields.LINK);
        builder.addField(PlaceFields.LOCATION);
        builder.addField(PlaceFields.NAME);
        builder.addField(PlaceFields.OVERALL_STAR_RATING);
        //builder.addField(PlaceFields.PARKING);
        //builder.addField(PlaceFields.PAYMENT_OPTIONS);
        //builder.addField(PlaceFields.PHONE);
        //builder.addField(PlaceFields.PHOTOS_UPLOADED);
        builder.addField(PlaceFields.PICTURE);
        builder.addField(PlaceFields.PRICE_RANGE);
        //builder.addField(PlaceFields.RATING_COUNT);
        //builder.addField(PlaceFields.RESTAURANT_SERVICES);
        //builder.addField(PlaceFields.RESTAURANT_SPECIALTIES);
        //builder.addField(PlaceFields.SINGLE_LINE_ADDRESS);
        //builder.addField(PlaceFields.WEBSITE);

        PlaceManager.newCurrentPlaceRequest(builder.build(), this);
    }

    private void testFsCurrentPlace() {
        // calling for the image
        // https://igx.4sqi.net/img/general/width1920/48064500_Ty_JRH2qGNyMUOeh13fXmV8n0UUgi3MWiiIRznrxucU.jpg

        StringBuilder sb = new StringBuilder("https://api.foursquare.com/v2/venues/explore?");
        sb.append("v=");
        sb.append(getResources().getString(R.string.foursquare_version_param));
        sb.append("&ll=");
        sb.append(lastKnownLocation.getLatitude() + "," + lastKnownLocation.getLongitude());
        sb.append("&radius=");
        sb.append(getResources().getInteger(R.integer.scan_radius));
        sb.append("&section=topPicks");
        sb.append("&limit=");
        sb.append(10);
        sb.append("&section=food");
        sb.append("&venuePhotos=");
        sb.append(1);
        sb.append("&client_id=");
        sb.append(getResources().getString(R.string.foursquare_client_id));
        sb.append("&client_secret=");
        sb.append(getResources().getString(R.string.foursquare_client_secret));

        VenuesTask venuesTask = new VenuesTask();
        venuesTask.execute(sb.toString());

        Log.i(TAG, "testFsCurrentPlace: " + sb.toString());


    }

    @Override
    public void onLocationError(PlaceManager.LocationError error) {
        // Invoked if the Places Graph SDK failed to retrieve
        // the device location.
        Log.e(TAG, "onLocationError: " + error.toString());
    }

    @Override
    public void onRequestReady(GraphRequest graphRequest) {
        // The place search request is ready to be executed.
        // You can customize the request here (if needed).

        // Sets the callback, and executes the request.
        graphRequest.setCallback(this);
        graphRequest.executeAsync();
    }

    @Override
    public void onCompleted(GraphResponse response) {
        // Event invoked when the place search response is received.
        // Parse the places from the response object.
        Gson gson = new Gson();
        JSONObject jsonObject = response.getJSONObject();
        fbPlaces = new ArrayList<>();
        JSONArray jsonArray = null;
        FbPlace fbPlace;
        try {
            jsonArray = jsonObject.getJSONArray("data");

            for(int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                fbPlace = gson.fromJson(jsonObject.toString(), FbPlace.class);
                fbPlace.is_silhouette = jsonObject.getJSONObject("picture").getJSONObject("data").getBoolean("is_silhouette");
                fbPlace.picture_url = jsonObject.getJSONObject("picture").getJSONObject("data").getString("url");
                fbPlace.category = jsonObject.getJSONArray("category_list").getJSONObject(0).getString("name");
                fbPlace.lat = jsonObject.getJSONObject("location").getDouble("latitude");
                fbPlace.lng = jsonObject.getJSONObject("location").getDouble("longitude");
                fbPlaces.add(fbPlace);
                Log.i(TAG, "onCompleted: " + fbPlace.toString());
            }
            fbPlaceListAdapter.setPlaces(fbPlaces);
            fbPlaceListAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPlaceSelected(Place place) {
        Log.i(TAG, "onPlaceSelected: " + place.getId());
    }

    @Override
    public void onError(Status status) {
        Log.e(TAG, "onError: " + status.getStatusMessage());
    }

    private class VenuesTask extends AsyncTask<String, Integer, String> {

        String data = null;

        // Invoked by execute() method of this object
        @Override
        protected String doInBackground(String... url) {
            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(String result) {
            Log.d(TAG, "VenuesTask => result: " + result);
            parseFsVenues(result);
        }
    }

    private void parseFsVenues(String result) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(result, JsonObject.class);
        JsonArray jsonArray = jsonObject.getAsJsonObject("response").getAsJsonArray("groups");
        JsonArray jsonItems = jsonArray.get(0).getAsJsonObject().getAsJsonArray("items");
        for(int i = 0; i < jsonItems.size(); i++) {
            JsonElement jsonVenue = jsonItems.get(i);

            Log.i(TAG, "parseFsVenues: (" + i + ") " + jsonVenue.toString());
        }

        /*
        JSONArray jsonArray = null;
        FbPlace fbPlace;
        try {
            jsonArray = jsonObject.getJSONArray("data");

            for(int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                fbPlace = gson.fromJson(jsonObject.toString(), FbPlace.class);
                fbPlace.is_silhouette = jsonObject.getJSONObject("picture").getJSONObject("data").getBoolean("is_silhouette");
                fbPlace.picture_url = jsonObject.getJSONObject("picture").getJSONObject("data").getString("url");
                fbPlace.category = jsonObject.getJSONArray("category_list").getJSONObject(0).getString("name");
                fbPlace.lat = jsonObject.getJSONObject("location").getDouble("latitude");
                fbPlace.lng = jsonObject.getJSONObject("location").getDouble("longitude");
                fbPlaces.add(fbPlace);
                Log.i(TAG, "onCompleted: " + fbPlace.toString());
            }
            fbPlaceListAdapter.setPlaces(fbPlaces);
            fbPlaceListAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        */
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
}
