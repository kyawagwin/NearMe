package com.infotechincubator.nearme;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class PlaceTypeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    PlaceType mPlaceType;

    TextView titleTV;
    ImageView placeTypeIV;

    public PlaceTypeViewHolder(View itemView) {
        super(itemView);

        titleTV = (TextView) itemView.findViewById(R.id.layout_place_type_item_titleTV);
        placeTypeIV = (ImageView) itemView.findViewById(R.id.layout_place_type_item_placeTypeIV);

        itemView.setOnClickListener(this);
    }

    protected void bindViewHolder(PlaceType placeType) {
        mPlaceType = placeType;

        titleTV.setText(placeType.getTitle());
        Glide.with(itemView.getContext()).asBitmap().load(mPlaceType.getImageId()).into(placeTypeIV);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), PlaceListActivity.class);
        intent.putExtra(Constants.PLACE_TYPE_EXTRA, mPlaceType.getCode());
        intent.putExtra(Constants.PLACE_TYPE_NAME_EXTRA, mPlaceType.getTitle());
        view.getContext().startActivity(intent);
    }
}
