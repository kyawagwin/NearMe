package com.infotechincubator.nearme;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class PlaceListAdapter extends RecyclerView.Adapter<PlaceListViewHolder> {
    List<Place> mPlaces;

    public PlaceListAdapter(List<Place> places) {
        this.mPlaces = places;
    }

    @Override
    public PlaceListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_place_list_item, parent, false);

        return new PlaceListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlaceListViewHolder holder, int position) {
        Place place = mPlaces.get(position);
        holder.bindViewHolder(place);
    }

    @Override
    public int getItemCount() {
        return mPlaces.size();
    }
}
