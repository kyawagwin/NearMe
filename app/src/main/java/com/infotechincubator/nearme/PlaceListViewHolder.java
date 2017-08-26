package com.infotechincubator.nearme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

public class PlaceListViewHolder extends RecyclerView.ViewHolder {
    private Place mPlace;

    TextView nameTV;
    TextView distanceTV;
    RatingBar ratingRB;

    public PlaceListViewHolder(View itemView) {
        super(itemView);

        nameTV = (TextView) itemView.findViewById(R.id.layout_place_list_item_nameTV);
        distanceTV = (TextView) itemView.findViewById(R.id.layout_place_list_item_distanceTV);
        ratingRB = (RatingBar) itemView.findViewById(R.id.layout_place_list_item_ratingRB);

    }

    protected void bindViewHolder(Place place) {
        mPlace = place;

        nameTV.setText(mPlace.getName());
        distanceTV.setText(Integer.toString(mPlace.getDistance()) + " Meters");
        ratingRB.setRating(mPlace.getRating());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.PLACE_BUNDLE, mPlace);
                Intent intent = new Intent(view.getContext(), MapsActivity.class);
                intent.putExtra(Constants.PLACE_BUNDLE, bundle);
                view.getContext().startActivity(intent);
            }
        });
    }
}
