package com.hotelurbano.desafio;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.hotelurbano.desafio.adapter.PlaceRecyclerViewAdapter;
import com.hotelurbano.desafio.api.HotelUrbanoAPI;
import com.hotelurbano.desafio.model.Place;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class PlaceSearchActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private PlaceRecyclerViewAdapter mAdapter;
    private EditText mEditQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_search);

        configureRecyclerView();

        mEditQuery = (EditText) findViewById(R.id.edit_query);
        mEditQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchPlaces(s.toString());
            }
        });


    }

    private void searchPlaces(String query) {
        HotelUrbanoAPI.getService().getPlaces(query, new Callback<List<Place>>() {
            @Override
            public void success(List<Place> places, Response response) {

                mAdapter.setPlaces(places);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }



    private void configureRecyclerView() {

        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(
                StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);

        mLayoutManager = staggeredGridLayoutManager;

        mAdapter = new PlaceRecyclerViewAdapter(
                new ArrayList<Place>(), this);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);

    }


}
