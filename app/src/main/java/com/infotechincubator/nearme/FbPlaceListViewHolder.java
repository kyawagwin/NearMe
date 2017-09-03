package com.infotechincubator.nearme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class FbPlaceListViewHolder extends RecyclerView.ViewHolder {
    private FbPlace mPlace;

    ImageView placeIV;
    TextView nameTV;
    TextView checkInTV;
    RatingBar overallRB;
    TextView priceRangeTV;
    TextView categoryTV;

    public FbPlaceListViewHolder(View itemView) {
        super(itemView);

        placeIV = (ImageView) itemView.findViewById(R.id.layout_fb_place_list_item_placeImageIV);
        nameTV = (TextView) itemView.findViewById(R.id.layout_fb_place_list_item_placeNameTV);
        checkInTV = (TextView) itemView.findViewById(R.id.layout_fb_place_list_item_checkinTV);
        overallRB = (RatingBar) itemView.findViewById(R.id.layout_fb_place_list_item_overallRatingStar);
        overallRB.setMax(5);
        priceRangeTV = (TextView) itemView.findViewById(R.id.layout_fb_place_list_item_priceRangeTV);
        categoryTV = (TextView) itemView.findViewById(R.id.layout_fb_place_list_item_placeCategoryTV);
    }

    protected void bindViewHolder(FbPlace place) {
        mPlace = place;

        nameTV.setText(mPlace.name);
        checkInTV.setText(Integer.toString(mPlace.checkins) + " people(s) have been here");
        overallRB.setRating(mPlace.overall_star_rating);
        categoryTV.setText(mPlace.category);
        if (mPlace.price_range != null) {
            priceRangeTV.setText("Price range: " + mPlace.price_range);
        }
        Glide.with(itemView.getContext()).load(mPlace.picture_url).into(placeIV);

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
