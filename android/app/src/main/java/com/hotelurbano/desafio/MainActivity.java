package com.hotelurbano.desafio;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.hotelurbano.desafio.model.Config;
import com.hotelurbano.desafio.model.Place;
import com.hotelurbano.desafio.utils.Consts;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static String TAG = "MainActivity";
    private EditText mQuery;
    private EditText mfromDate;
    private EditText mToDate;
    private Config mConfig;

    private DatePickerDialog mFromDatePickerDialog;
    private DatePickerDialog mToDatePickerDialog;

    private SimpleDateFormat mDateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState==null) {
            setContentView(R.layout.activity_main);

            bindViews();


        }
        mDateFormatter = new SimpleDateFormat("dd, MMM yyyy");

        mConfig = Config.current(this);

        bindValues();

        Bundle extras = getIntent().getExtras();

        if (extras!=null) {
            String placeName = extras.getString(Consts.PLACE_NAME);
            Place place = (Place) extras.get(Consts.PLACE);
            if (place!=null) {
                mQuery.setText(placeName);
                mConfig.setPlaceName(placeName);
                mConfig.setPlaceId(place.getId());
                mConfig.setType(place.getType());
                mConfig.saveAsCurrent(this);
            }
        }

        configureQueryEditFocusListener();

        configureDateFields();
    }

    private void configureDateFields() {
        //Configuring the dates dialogs.
        mfromDate.setOnClickListener(this);
        mToDate.setOnClickListener(this);

        final  Calendar newCalendar = Calendar.getInstance();

        if (mConfig.isStartDateValidForDate(newCalendar.getTime())) {

            newCalendar.setTime(mConfig.getStartDate());
        }

        mFromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                mfromDate.setText(mDateFormatter.format(newDate.getTime()));
                mConfig.setStartDate(newDate.getTime());
                mConfig.saveAsCurrent(MainActivity.this);
                configureToDate(newDate);
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        mFromDatePickerDialog.getDatePicker().setMinDate(Calendar.getInstance().getTime().getTime());

        configureToDate(newCalendar);

        configureDateEditsFocusListeners();

    }

    private void configureDateEditsFocusListeners() {
        mfromDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            //Adding on focus listener to grant access to the action
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mFromDatePickerDialog.show();
                }
            }
        });

        mToDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            //Adding on focus listener to grant access to the action
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mToDatePickerDialog.show();
                }
            }
        });
    }

    private void configureToDateMinDate() {
        Calendar newCalendar = Calendar.getInstance();

        //Setting the min date for toDate as fromDate+1 to avoid
        //user to select wrong periods.
        if (mConfig.isStartDateValidForDate(newCalendar.getTime())) {
            newCalendar.setTime(mConfig.getStartDate());
            newCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        Date calendarDate = newCalendar.getTime();

        mToDatePickerDialog.getDatePicker().setMinDate(calendarDate.getTime());

    }

    private void configureToDate(Calendar newCalendar) {

        if (mConfig.isEndDateValidForDate(newCalendar.getTime())) {

            newCalendar.setTime(mConfig.getEndDate());
        }

        mToDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                mToDate.setText(mDateFormatter.format(newDate.getTime()));
                mConfig.setEndDate(newDate.getTime());
                mConfig.saveAsCurrent(MainActivity.this);
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        configureToDateMinDate();
    }


    private void configureQueryEditFocusListener() {
        //Adding on focus listener to grant access to the action
        mQuery.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    search(null);
                }
            }
        });
    }

    public void search(View view) {
        Intent intent = new Intent(this, PlaceSearchActivity.class);
        startActivity(intent);
    }

    private void bindViews() {
        mQuery = (EditText) findViewById(R.id.edit_query);

        mfromDate = (EditText) findViewById(R.id.from_date);
        mfromDate.setInputType(InputType.TYPE_NULL);

        mToDate = (EditText) findViewById(R.id.to_date);
        mToDate.setInputType(InputType.TYPE_NULL);
    }

    private void bindValues() {
        if (mConfig!=null) {
            if (mConfig.getStartDate()!=null) {
                mfromDate.setText(mDateFormatter.format(mConfig.getStartDate()));
            }
            if (mConfig.getEndDate()!=null) {
                mToDate.setText(mDateFormatter.format(mConfig.getEndDate()));
            }
            mQuery.setText(mConfig.getPlaceName());
        }
    }

    public void check(View view) {
        if (!mConfig.isEndDateAfter()) {
            Toast.makeText(this, R.string.check_end_date_error, Toast.LENGTH_SHORT).show();
            return;
        }

        if (mConfig.isSearchOK()) {
            Intent intent = new Intent(this, HotelResultActivity.class);
            intent.putExtra(Consts.PLACE_NAME, mConfig.getPlaceName());
            intent.putExtra(Consts.PLACE_TYPE, mConfig.getType());
            intent.putExtra(Consts.PLACE_ID, mConfig.getPlaceId());
            intent.putExtra(Consts.FROM_DATE, mConfig.getStartDate());
            intent.putExtra(Consts.TO_DATE, mConfig.getEndDate());
            startActivity(intent);
        }

    }

    @Override
    public void onClick(View view) {
        if(view == mfromDate) {
            mFromDatePickerDialog.show();
        } else if(view == mToDate) {
            mToDatePickerDialog.show();
        }

    }
}
