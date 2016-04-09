package com.hotelurbano.desafio.utils;

import com.hotelurbano.desafio.model.Hotel;
import com.hotelurbano.desafio.model.Place;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by fabiocopello on 4/8/16.
 */
public class PlaceUtils {

    public static List<Place> buildPalces(List<Hotel> hotels) {

        Map<String, Place> placeMap = new HashMap<>();

        for (Hotel hotel: hotels) {
            if (!placeMap.containsKey(hotel.getCity())) {
                placeMap.put(hotel.getCity(), new Place(hotel.getCity()).asCity());
            }
            placeMap.put(String.valueOf(hotel.getId()), new Place(hotel.getName(), hotel.getId()).asHotel());
        }

        return new ArrayList<>(placeMap.values());
    }
}
