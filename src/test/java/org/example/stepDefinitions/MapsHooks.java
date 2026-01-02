package org.example.stepDefinitions;


import io.cucumber.java.Before;

import java.io.FileNotFoundException;

public class MapsHooks {

    @Before("@DeletePlace")
    public void beforeScenario() throws FileNotFoundException {
        if (MapsAPI_StepDef.place_id == null) {
            MapsAPI_StepDef mapsAPIStepDef = new MapsAPI_StepDef();
            mapsAPIStepDef.add_place_payload_with_values("29, side layout, cohen 091232","Frontline house123",
                    "(+65) 983 893 3937","https://google.com","German-UK");
            mapsAPIStepDef.user_calls_with_http_request("AddPlaceAPI", "POST");
            mapsAPIStepDef.verify_place_id_created_maps_to_using("Frontline house123","GetPlaceAPI");
        }
    }
}
