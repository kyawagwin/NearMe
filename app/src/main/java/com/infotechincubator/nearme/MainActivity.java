package com.infotechincubator.nearme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    PlaceTypeAdapter placeTypeAdapter;
    RecyclerView placeTypeRV;
    ImageView bgIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // TODO: 23/8/17 remember to do bitmap processing off of the main thread:
        /*
        // adapt the image to the size of the display
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Bitmap bmp = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
                getResources(),R.drawable.bg_main),size.x,size.y,true);

        // fill the background ImageView with the resized image
        ImageView bgIV = (ImageView) findViewById(R.id.activity_main_bgIV);
        bgIV.setImageBitmap(bmp);
        */

        placeTypeAdapter = new PlaceTypeAdapter(Utils.getPlaceTypes());
        placeTypeRV = (RecyclerView) findViewById(R.id.activity_main_placeTypeRV);
        placeTypeRV.setLayoutManager(new GridLayoutManager(this, 2));
        placeTypeRV.setHasFixedSize(true);
        placeTypeRV.setAdapter(placeTypeAdapter);
    }
}
