package com.infotechincubator.nearme;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class PlaceTypeViewHolder extends RecyclerView.ViewHolder {
    TextView titleTV;
    ImageView placeTypeIV;

    public PlaceTypeViewHolder(View itemView) {
        super(itemView);

        titleTV = (TextView) itemView.findViewById(R.id.layout_place_type_item_titleTV);
        placeTypeIV = (ImageView) itemView.findViewById(R.id.layout_place_type_item_placeTypeIV);
    }

    protected void bindViewHolder(PlaceType placeType) {
        titleTV.setText(placeType.getTitle());
        placeTypeIV.setImageResource(placeType.getImageId());
    }
}
