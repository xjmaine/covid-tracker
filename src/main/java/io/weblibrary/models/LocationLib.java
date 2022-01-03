package io.weblibrary.models;

import lombok.Data;

@Data
public class LocationLib {
	private String state;
	private String country;
	private int latestTotal;
	private String totalCases;
	private String totalActive;

}
