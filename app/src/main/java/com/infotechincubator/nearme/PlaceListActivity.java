package com.infotechincubator.nearme;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

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
import java.util.HashMap;
import java.util.List;

public class PlaceListActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = PlaceListActivity.class.getSimpleName();

    private List<Place> mPlaces;
    PlaceListAdapter adapter;
    Location mLastKnownLocation;
    String mPlaceType;
    String mPlaceTypeName;

    RecyclerView placeListRV;
    TextView emptyTV;
    Button mapViewButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        placeListRV = (RecyclerView) findViewById(R.id.activity_place_list_placeListRV);
        emptyTV = (TextView) findViewById(R.id.activity_place_list_emptyTV);
        mapViewButton = (Button) findViewById(R.id.activity_place_list_mapViewButton);

        mPlaceType = getIntent().getStringExtra(Constants.PLACE_TYPE_EXTRA);
        mPlaceTypeName = getIntent().getStringExtra(Constants.PLACE_TYPE_NAME_EXTRA);

        setTitle(mPlaceTypeName);

        initialize();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void initialize() {
        super.initialize();

        mapViewButton.setOnClickListener(this);

        mLastKnownLocation = getLastKnownLocation();

        if (mLastKnownLocation != null) {
            mPlaces = new ArrayList<>();
            StringBuilder sbValue = new StringBuilder(constructApiUrl(mLastKnownLocation, mPlaceType));
            PlacesTask placesTask = new PlacesTask();
            placesTask.execute(sbValue.toString());
            adapter = new PlaceListAdapter(mPlaces);
            placeListRV.setHasFixedSize(true);
            placeListRV.setItemViewCacheSize(20);
            placeListRV.setDrawingCacheEnabled(true);
            placeListRV.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);
            placeListRV.setAdapter(adapter);
            placeListRV.setLayoutManager(new LinearLayoutManager(this));
        } else {
            togglePlaceListUI(false);
        }
    }

    private void togglePlaceListUI(boolean visible) {
        if(visible) {
            placeListRV.setVisibility(View.VISIBLE);
            mapViewButton.setVisibility(View.VISIBLE);
            emptyTV.setVisibility(View.GONE);
        } else {
            placeListRV.setVisibility(View.GONE);
            mapViewButton.setVisibility(View.GONE);
            emptyTV.setVisibility(View.VISIBLE);
        }
    }

    public StringBuilder constructApiUrl(Location location, String placeType) {
        double mLatitude = location.getLatitude();
        double mLongitude = location.getLongitude();

        StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        sb.append("location=" + mLatitude + "," + mLongitude);
        sb.append("&radius=");
        sb.append(getResources().getInteger(R.integer.scan_radius));
        sb.append("&types=" + placeType);
        sb.append("&sensor=true");
        sb.append("&key=");
        sb.append(getResources().getString(R.string.google_maps_key));
        Log.i(TAG, "API URL : " + sb.toString());

        return sb;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_place_list_mapViewButton:
                showMapView();
                break;
        }
    }

    private void showMapView() {
        ArrayList<Place> placeArrayList = (ArrayList<Place>)mPlaces;
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Constants.PLACE_LIST_BUNDLE, placeArrayList);
        Intent intent = new Intent(this, MapsActivity.class);
        intent.putExtra(Constants.PLACE_BUNDLE, bundle);
        intent.putExtra(Constants.PLACE_TYPE_NAME_EXTRA, mPlaceTypeName);
        startActivity(intent);
    }

    private class PlacesTask extends AsyncTask<String, Integer, String> {

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
            Log.d("result", "<><> result: " + result);
            ParserTask parserTask = new ParserTask();

            // Start parsing the Google places in JSON format
            // Invokes the "doInBackground()" method of the class ParserTask
            parserTask.execute(result);
        }
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

    private class ParserTask extends AsyncTask<String, Integer, List<HashMap<String, String>>> {

        JSONObject jObject;

        // Invoked by execute() method of this object
        @Override
        protected List<HashMap<String, String>> doInBackground(String... jsonData) {

            List<HashMap<String, String>> places = null;
            Place_JSON placeJson = new Place_JSON();

            try {
                jObject = new JSONObject(jsonData[0]);

                places = placeJson.parse(jObject);

            } catch (Exception e) {
                Log.d("Exception", e.toString());
            }
            return places;
        }

        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(List<HashMap<String, String>> list) {

            if (list != null) {
                for (int i = 0; list.size() > i; i++) {
                    // Getting a place from the places list
                    HashMap<String, String> hmPlace = list.get(i);
                    Log.d("Place", hmPlace.toString());

                    if (hmPlace.get("id") != null) {
                        Place place = new Place(hmPlace.get("place_id"),
                                "google",
                                new LatLng(Double.parseDouble(hmPlace.get("lat")), Double.parseDouble(hmPlace.get("lng"))),
                                Float.parseFloat(hmPlace.get("rating")),
                                0,
                                hmPlace.get("place_name"),
                                hmPlace.get("vicinity"),
                                hmPlace.get("phone_number"),
                                hmPlace.get(("iconUrl")));

                        Double distance = Utils.getDistance(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude(), place.getLatLng().latitude, place.getLatLng().longitude);
                        place.setDistance(distance.intValue());

                        mPlaces.add(place);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            togglePlaceListUI(mPlaces.size() > 0);
        }
    }

    public class Place_JSON {

        /**
         * Receives a JSONObject and returns a list
         */
        public List<HashMap<String, String>> parse(JSONObject jObject) {

            JSONArray jPlaces = null;
            try {
                /** Retrieves all the elements in the 'places' array */
                jPlaces = jObject.getJSONArray("results");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            /** Invoking getPlaces with the array of json object
             * where each json object represent a place
             */
            return getPlaces(jPlaces);
        }

        private List<HashMap<String, String>> getPlaces(JSONArray jPlaces) {
            int placesCount = jPlaces.length();
            List<HashMap<String, String>> placesList = new ArrayList<HashMap<String, String>>();
            HashMap<String, String> place = null;

            /** Taking each place, parses and adds to list object */
            for (int i = 0; i < placesCount; i++) {
                try {
                    /** Call getPlace with place JSON object to parse the place */
                    place = getPlace((JSONObject) jPlaces.get(i));
                    placesList.add(place);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return placesList;
        }

        /**
         * Parsing the Place JSON object
         */
        private HashMap<String, String> getPlace(JSONObject jPlace) {

            HashMap<String, String> place = new HashMap<String, String>();
            String placeName = "-NA-";
            String vicinity = "-NA-";
            String latitude = "";
            String longitude = "";
            String reference = "";
            String rating = "";
            String iconUrl = "";
            String id = "";

            try {
                // Extracting Place name, if available
                if (!jPlace.isNull("name")) {
                    placeName = jPlace.getString("name");
                }

                // Extracting Place Vicinity, if available
                if (!jPlace.isNull("vicinity")) {
                    vicinity = jPlace.getString("vicinity");
                }

                latitude = jPlace.getJSONObject("geometry").getJSONObject("location").getString("lat");
                longitude = jPlace.getJSONObject("geometry").getJSONObject("location").getString("lng");
                reference = jPlace.getString("reference");
                rating = jPlace.getString("rating");
                iconUrl = jPlace.getString("icon");
                id = jPlace.getString("place_id");

                place.put("place_name", placeName);
                place.put("vicinity", vicinity);
                place.put("lat", latitude);
                place.put("lng", longitude);
                place.put("reference", reference);
                place.put("rating", rating);
                place.put("iconUrl", iconUrl);
                place.put("id", id);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return place;
        }
    }
}
