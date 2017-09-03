package com.infotechincubator.nearme;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.maps.android.SphericalUtil;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static List<PlaceType> getPlaceTypes() {
        List<PlaceType> placeTypes = new ArrayList<>();

        placeTypes.add(new PlaceType("Amusement park", R.drawable.amusement_park, "Amusement park", "amusement_park"));
        placeTypes.add(new PlaceType("Aquarium", R.drawable.aquarium, "Aquarium", "aquarium"));
        placeTypes.add(new PlaceType("Art gallery", R.drawable.art_gallery, "Art gallery", "art_gallery"));
        placeTypes.add(new PlaceType("ATM", R.drawable.atm, "Automatic Teller Machine", "atm"));
        placeTypes.add(new PlaceType("Bakery", R.drawable.bakery, "Bakery", "bakery"));
        placeTypes.add(new PlaceType("Bank", R.drawable.bank, "Bank", "bank"));
        placeTypes.add(new PlaceType("Bar", R.drawable.bar, "Bar", "bar"));
        placeTypes.add(new PlaceType("Beauty salon", R.drawable.beauty_salon, "Beauty salon", "beauty_salon"));
        placeTypes.add(new PlaceType("Bicycle store", R.drawable.bicycle_store, "Bicycle store", "bicycle_store"));
        placeTypes.add(new PlaceType("Book store", R.drawable.book_store, "Book store", "book_store"));
        placeTypes.add(new PlaceType("Bowling", R.drawable.bowling_alley, "Bowling", "bowling_alley"));
        placeTypes.add(new PlaceType("Bus station", R.drawable.bus_station, "Bus station", "bus_station"));
        placeTypes.add(new PlaceType("Cafe", R.drawable.cafe, "Cafe", "cafe"));
        placeTypes.add(new PlaceType("Car repair", R.drawable.car_repair, "Car repair", "car_repair"));
        placeTypes.add(new PlaceType("Car wash", R.drawable.car_wash, "Car wash", "car_wash"));
        placeTypes.add(new PlaceType("Clinic", R.drawable.clinic, "Clinic", "doctor"));
        placeTypes.add(new PlaceType("Clothing store", R.drawable.clothing_store, "Clothing store", "clothing_store"));
        placeTypes.add(new PlaceType("Convenience store", R.drawable.convenience_store, "Convenience store", "convenience_store"));
        placeTypes.add(new PlaceType("Dental clinic", R.drawable.dental_clinic, "Dental clinic", "dentist"));
        placeTypes.add(new PlaceType("Electrician", R.drawable.electrician, "Electrician", "electrician"));
        placeTypes.add(new PlaceType("Electronics store", R.drawable.electronics_store, "Electronics store", "electronics_store"));
        placeTypes.add(new PlaceType("Florist", R.drawable.florist, "Florist", "florist"));
        placeTypes.add(new PlaceType("Furniture store", R.drawable.furniture_store, "Furniture store", "furniture_store"));
        placeTypes.add(new PlaceType("Gas station", R.drawable.gas_station, "Gas Station", "gas_station"));
        placeTypes.add(new PlaceType("Gym", R.drawable.gym, "Gym", "gym"));
        placeTypes.add(new PlaceType("Hair salon", R.drawable.hair_salon, "Hair salon", "hair_care"));
        placeTypes.add(new PlaceType("Hardware store", R.drawable.hardware_store, "Hardware store", "hardware_store"));
        placeTypes.add(new PlaceType("Hospital", R.drawable.hospital, "Hospital", "hospital"));
        placeTypes.add(new PlaceType("Jewelry store", R.drawable.jewelry_store, "Jewelry store", "jewelry_store"));
        placeTypes.add(new PlaceType("Laundry", R.drawable.laundry, "Laundry", "laundry"));
        placeTypes.add(new PlaceType("Liquor store", R.drawable.liquor_store, "Liquor store", "liquor_store"));
        placeTypes.add(new PlaceType("Lock smith", R.drawable.lock_smith, "Lock smith", "locksmith"));
        placeTypes.add(new PlaceType("Movie theater", R.drawable.movie_theater, "Movie theater", "movie_theater"));
        placeTypes.add(new PlaceType("Night club", R.drawable.night_club, "Night club", "night_club"));
        placeTypes.add(new PlaceType("Park", R.drawable.park, "Park", "park"));
        placeTypes.add(new PlaceType("Parking", R.drawable.parking, "Parking", "parking"));
        placeTypes.add(new PlaceType("Pet store", R.drawable.pet_store, "Pet store", "pet_store"));
        placeTypes.add(new PlaceType("Pharmacy", R.drawable.pharmacy , "Pharmacy", "pharmacy"));
        placeTypes.add(new PlaceType("Police", R.drawable.police , "Police", "police"));
        placeTypes.add(new PlaceType("Restaurant", R.drawable.restaurant , "Restaurant", "restaurant"));
        placeTypes.add(new PlaceType("School", R.drawable.school , "School", "school"));
        placeTypes.add(new PlaceType("Shoe store", R.drawable.shoe_store , "Shoe store", "shoe_store"));
        placeTypes.add(new PlaceType("Shopping mall", R.drawable.shopping_mall , "Shopping mall", "shopping_mall"));
        placeTypes.add(new PlaceType("Spa", R.drawable.spa , "Spa", "spa"));
        placeTypes.add(new PlaceType("Stadium", R.drawable.stadium , "Stadium", "stadium"));
        placeTypes.add(new PlaceType("Zoo", R.drawable.zoo , "Zoo", "zoo"));

        return placeTypes;
    }

    public static double getDistance(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        float dist = (float) (earthRadius * c);

        return dist;
    }

    public static LatLngBounds toBounds(LatLng center, double radiusInMeters) {
        double distanceFromCenterToCorner = radiusInMeters * Math.sqrt(2.0);
        LatLng southwestCorner =
                SphericalUtil.computeOffset(center, distanceFromCenterToCorner, 225.0);
        LatLng northeastCorner =
                SphericalUtil.computeOffset(center, distanceFromCenterToCorner, 45.0);
        return new LatLngBounds(southwestCorner, northeastCorner);
    }
}
