package com.hotelurbano.desafio.model;

import java.io.Serializable;

/**
 * Created by fabiocopello on 4/7/16.
 */
public class Place implements Serializable {

    public static final int TYPE_CITY = 1;
    public static final int TYPE_HOTEL = 2;

    private int key;
    private int type;
    private String name;

    public int getKey() { return key; }

    public void setKey(int key) { this.key = key; }

    public boolean isHotel() {
        return type == TYPE_HOTEL;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
