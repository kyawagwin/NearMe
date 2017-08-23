package com.infotechincubator.nearme;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static List<PlaceType> getPlaceTypes() {
        List<PlaceType> placeTypes = new ArrayList<>();

        placeTypes.add(new PlaceType("Art gallery", R.drawable.art_gallery, "Art gallery", "art_gallery"));
        placeTypes.add(new PlaceType("ATM", R.drawable.atm, "Automatic Teller Machine", "atm"));
        placeTypes.add(new PlaceType("Bakery", R.drawable.bakery, "Bakery", "bakery"));
        placeTypes.add(new PlaceType("Bank", R.drawable.bank, "Bank", "bank"));
        placeTypes.add(new PlaceType("Bar", R.drawable.bar, "Bar", "bar"));

        return placeTypes;
    }
}
