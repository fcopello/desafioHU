package com.hotelurbano.desafio.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hotelurbano.desafio.R;
import com.hotelurbano.desafio.model.Hotel;

import java.util.List;

/**
 * Created by fabiocopello on 4/8/16.
 */
public class HotelResultRecyclerViewAdapter extends RecyclerView.Adapter<HotelResultRecyclerViewAdapter.HotelViewHolder> {

    private List<Hotel> mHotels;
    private Context mContext;

    public HotelResultRecyclerViewAdapter(List<Hotel> hotels, Context context) {
        this.mHotels = hotels;
        this.mContext = context;
    }

    @Override
    public HotelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(
                mContext).inflate(R.layout.card_hotel_item, parent, false);

        return new HotelViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HotelViewHolder holder, int position) {
        Hotel hotel = mHotels.get(position);

        holder.name.setText(hotel.getName());
    }

    public void setHotels(List<Hotel> hotels) {
        mHotels.clear();
        mHotels.addAll(hotels);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mHotels.size();
    }

    public class HotelViewHolder extends RecyclerView.ViewHolder {

        TextView name;

        public HotelViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
        }
    }


}
