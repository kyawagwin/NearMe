package com.infotechincubator.nearme;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class PlaceListViewHolder extends RecyclerView.ViewHolder {
    private Place mPlace;

    TextView nameTV;
    TextView distanceTV;

    public PlaceListViewHolder(View itemView) {
        super(itemView);

        nameTV = (TextView) itemView.findViewById(R.id.layout_place_list_item_nameTV);
        distanceTV = (TextView) itemView.findViewById(R.id.layout_place_list_item_distanceTV);
    }

    protected void bindViewHolder(Place place) {
        mPlace = place;

        nameTV.setText(mPlace.getName());
    }
}
