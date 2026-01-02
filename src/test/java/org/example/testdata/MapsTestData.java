package org.example.testdata;

import org.example.pojo.Location;
import org.example.pojo.Maps;

import java.util.List;

public class MapsTestData {

    public Maps mapsTestData(String address,String name, String phone_number, String website, String language) {
        Location location = new Location();
        location.setLat(-38.383423);
        location.setLng(33.422332);
        Maps maps = new Maps();
        maps.setLocation(location);
        maps.setAccuracy(50);
        maps.setAddress(address);
        maps.setName(name);
        maps.setPhone_number(phone_number);
        maps.setTypes(List.of("turf","gym"));
        maps.setWebsite(website);
        maps.setLanguage(language);
        return maps;
    }


    public String deletePlacePayload(String place_id) {
        return "{\r\n       \"place_id\":\""+place_id+"\"\r\n}";
    }
}
