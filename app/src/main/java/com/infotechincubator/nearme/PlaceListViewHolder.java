package com.infotechincubator.nearme;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public class PlaceListViewHolder extends RecyclerView.ViewHolder {
    private Place mPlace;

    public PlaceListViewHolder(View itemView) {
        super(itemView);
    }

    protected void bindViewHolder(Place place) {
        mPlace = place;

        // TODO: 8/24/2017 to bind view holder
    }
}
