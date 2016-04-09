package com.hotelurbano.desafio.model;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by fabiocopello on 4/8/16.
 */
public class Config {

    public static final String CONFIG_PREFS = "CONFIG_PREFS";
    public static final String PREF_START_DATE = "PREF_START_DATE";
    public static final String PREF_END_DATE = "PREF_END_DATE";
    public static final String PREF_PLACE_ID = "PREF_PLACE_ID";
    public static final String PREF_PLACE_NAME = "PREF_PLACE_NAME";
    public static final String PREF_PLACE_TYPE = "PREF_PLACE_TYPE";

    private Date startDate;
    private Date endDate;
    private int placeId;
    private int type;
    private String placeName;


    private static Config current;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getPlaceId() {
        return placeId;
    }

    public void setPlaceId(int placeId) {
        this.placeId = placeId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static Config current(Context context) {
        if (current != null) {
            return current;
        }

        SharedPreferences userPrefs = getUserPrefs(context);

        current = loadConfig(userPrefs);

        return current;
    }

    private static Config loadConfig(SharedPreferences configPrefs) {
        Config config = new Config();
        config.setPlaceName(configPrefs.getString(PREF_PLACE_NAME, null));
        config.setPlaceId(configPrefs.getInt(PREF_PLACE_ID, 0));
        config.setType(configPrefs.getInt(PREF_PLACE_TYPE, 0));
        long startDateLong = configPrefs.getLong(PREF_START_DATE, 0);
        long endDateLong = configPrefs.getLong(PREF_END_DATE, 0);

        if (startDateLong!=0){
            Date startDate = new Date();
            startDate.setTime(startDateLong);
            config.setStartDate(startDate);
        }

        if (endDateLong!=0){
            Date endDate = new Date();
            endDate.setTime(endDateLong);
            config.setEndDate(endDate);
        }

        return config;
    }

    public void saveAsCurrent(Context context) {
        SharedPreferences.Editor userPrefs = getUserPrefs(context).edit();
        userPrefs.putString(PREF_PLACE_NAME, placeName);
        userPrefs.putInt(PREF_PLACE_ID, placeId);
        userPrefs.putInt(PREF_PLACE_TYPE, type);

        if (startDate!=null) {
            userPrefs.putLong(PREF_START_DATE, startDate.getTime());
        }

        if (endDate!=null) {
            userPrefs.putLong(PREF_END_DATE, endDate.getTime());
        }

        userPrefs.commit();
    }

    private static SharedPreferences getUserPrefs(Context context) {
        return context.getSharedPreferences("CONFIG_PREFS", Context.MODE_PRIVATE);
    }

    public boolean isSearchOK() {
        return this.placeName!=null
                && this.type!=0
                && this.startDate!=null
                && this.endDate!=null;

    }

    public boolean isEndDateValidForDate(Date date) {
        return endDate!=null &&
                !endDate.before(date);
    }

    public boolean isStartDateValidForDate(Date date) {
        return startDate!=null &&
                !startDate.before(date);
    }

    public boolean isEndDateAfter() {
        return endDate.after(startDate);
    }
}
