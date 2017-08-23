package com.infotechincubator.nearme;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class PlaceTypeAdapter extends RecyclerView.Adapter<PlaceTypeViewHolder> {
    List<PlaceType> mPlaceTypes;

    public PlaceTypeAdapter(List<PlaceType> placeTypes) {
        this.mPlaceTypes = placeTypes;
    }

    @Override
    public PlaceTypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_place_type_item, parent, false);

        return new PlaceTypeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PlaceTypeViewHolder holder, int position) {
        PlaceType placeType = mPlaceTypes.get(position);
        holder.bindViewHolder(placeType);
    }

    @Override
    public int getItemCount() {
        return mPlaceTypes.size();
    }
}
