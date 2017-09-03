package com.infotechincubator.nearme;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class FbPlaceListAdapter extends RecyclerView.Adapter<FbPlaceListViewHolder> {
    private static final String TAG = FbPlaceListAdapter.class.getSimpleName();

    List<FbPlace> mPlaces;

    public FbPlaceListAdapter(List<FbPlace> mPlaces) {
        this.mPlaces = mPlaces;
    }

    @Override
    public FbPlaceListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_fb_place_list_item, parent, false);

        return new FbPlaceListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FbPlaceListViewHolder holder, int position) {
        FbPlace place = mPlaces.get(position);
        holder.bindViewHolder(place);

        Log.i(TAG, "onBindViewHolder: " + place.name);
    }

    @Override
    public int getItemCount() {
        return mPlaces.size();
    }

    public void setPlaces(List<FbPlace> mPlaces) {
        this.mPlaces = mPlaces;
    }
}
