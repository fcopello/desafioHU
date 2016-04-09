package com.hotelurbano.desafio.model;

import java.io.Serializable;

/**
 * Created by fabiocopello on 4/7/16.
 */
public class Place implements Serializable {

    public Place(String name) {
        this.name = name;
    }

    public Place(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public static final int TYPE_CITY = 1;
    public static final int TYPE_HOTEL = 2;

    private int id;
    private int type;
    private String name;

    public Place asCity() {
        this.type = TYPE_CITY;
        return this;
    }

    public Place asHotel() {
        this.type = TYPE_HOTEL;
        return this;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

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
