package org.example.practice;


import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@Builder
@Jacksonized
public class MapsPojoUsingBuilder {
	private Location location;
	private int accuracy;
	private String name;
	private String phone_number;
	private String address;
	private List<String> types;
	private String website;
	private String language;

}

@Data
@Builder
@Jacksonized
class Location {
	private double lat;
	private double lng;
}
