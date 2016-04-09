package com.hotelurbano.desafio.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hotelurbano.desafio.MainActivity;
import com.hotelurbano.desafio.R;
import com.hotelurbano.desafio.model.Place;
import com.hotelurbano.desafio.utils.Consts;

import java.util.List;

/**
 * Created by fabiocopello on 4/8/16.
 */
public class PlaceRecyclerViewAdapter extends RecyclerView.Adapter<PlaceRecyclerViewAdapter.PlaceViewHolder> {


    private Context mContext;
    private List<Place> mPlaces;

    public PlaceRecyclerViewAdapter(List<Place> places, Context context) {
        this.mPlaces = places;
        this.mContext = context;
    }

    @Override
    public PlaceViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(
                mContext).inflate(R.layout.card_place_item, viewGroup, false);

        return new PlaceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PlaceViewHolder placeViewHolder, int i) {
        final Place place = mPlaces.get(i);

        placeViewHolder.name.setText(place.getName());

        if (place.isHotel()) {
            placeViewHolder.hotelIcon.setVisibility(View.VISIBLE);
            placeViewHolder.cityIcon.setVisibility(View.GONE);
        } else {
            placeViewHolder.hotelIcon.setVisibility(View.GONE);
            placeViewHolder.cityIcon.setVisibility(View.VISIBLE);
        }

        placeViewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MainActivity.class);
                intent.putExtra(Consts.PLACE_NAME, place.getName());
                intent.putExtra(Consts.PLACE, place);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPlaces.size();
    }

    public void setPlaces(List<Place> places) {
        this.mPlaces.clear();
        this.mPlaces.addAll(places);
        notifyDataSetChanged();
    }

    public class PlaceViewHolder extends RecyclerView.ViewHolder {

        View container;
        TextView name;
        ImageView hotelIcon;
        ImageView cityIcon;

        public PlaceViewHolder(View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.main);
            name = (TextView) itemView.findViewById(R.id.name);
            hotelIcon = (ImageView) itemView.findViewById(R.id.icon_hotel);
            cityIcon = (ImageView) itemView.findViewById(R.id.icon_city);
        }
    }

}