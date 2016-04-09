package com.hotelurbano.desafio;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hotelurbano.desafio.adapter.HotelResultRecyclerViewAdapter;
import com.hotelurbano.desafio.adapter.PlaceRecyclerViewAdapter;
import com.hotelurbano.desafio.api.HotelUrbanoAPI;
import com.hotelurbano.desafio.model.Hotel;
import com.hotelurbano.desafio.model.Place;
import com.hotelurbano.desafio.utils.Consts;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class HotelResultActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private HotelResultRecyclerViewAdapter mAdapter;
    private Date mFromDate;
    private Date mToDate;
    private TextView mDateLabel;
    private SimpleDateFormat mDateFormat;
    private SimpleDateFormat mDateLabelFormat;
    private RelativeLayout mNoResultsContainer;
    private RelativeLayout mLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_result);

        mDateFormat = new SimpleDateFormat("M/d/yyyy");
        mDateLabelFormat = new SimpleDateFormat("d, MMM yyyy");

        mDateLabel = (TextView) findViewById(R.id.date_label);
        mNoResultsContainer = (RelativeLayout) findViewById(R.id.no_results_container);
        mLoading = (RelativeLayout) findViewById(R.id.loading);

        configureRecyclerView();

        Bundle extras = getIntent().getExtras();

        String id = null;
        int type = 0;
        int placeId = 0;
        String city = null;
        String startDate = null;
        String endDate = null;
        String placeName = null;


        if (extras!=null) {
            type = extras.getInt(Consts.PLACE_TYPE);
            placeName = extras.getString(Consts.PLACE_NAME);
            placeId = extras.getInt(Consts.PLACE_ID);
            mFromDate = (Date) extras.get(Consts.FROM_DATE);
            mToDate = (Date) extras.get(Consts.TO_DATE);

            //Bypassing between id or city name
            if (type == Place.TYPE_HOTEL) {
                id = String.valueOf(placeId);
            } else {
                city = placeName;
            }
            //Date format
            if (mFromDate!=null) {
                startDate = mDateFormat.format(mFromDate);
            }
            if (mToDate!=null) {
                endDate = mDateFormat.format(mToDate);
            }

            if (mFromDate!=null && mToDate!=null) {
                StringBuilder dateLbBuilder = new StringBuilder();
                dateLbBuilder.append(mDateLabelFormat.format(mFromDate));
                dateLbBuilder.append(" - ");
                dateLbBuilder.append(mDateLabelFormat.format(mToDate));
                mDateLabel.setText(dateLbBuilder.toString());
            }
        }

        HotelUrbanoAPI.getService().getDisp(id, city, startDate,
                endDate, new Callback<List<Hotel>>() {
            @Override
            public void success(List<Hotel> hotels, Response response) {

                mLoading.setVisibility(View.GONE);

                if (hotels.isEmpty()) {
                    mNoResultsContainer.setVisibility(View.VISIBLE);
                } else {
                    mAdapter.setHotels(hotels);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(HotelResultActivity.this,
                        R.string.result_error, Toast.LENGTH_LONG).show();
            }
        });


    }

     private void configureRecyclerView() {

        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(
                StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);

        mLayoutManager = staggeredGridLayoutManager;

        mAdapter = new HotelResultRecyclerViewAdapter(
                new ArrayList<Hotel>(), this);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

    }

    public void tryAgain(View view) {
        super.onBackPressed();
    }
}
