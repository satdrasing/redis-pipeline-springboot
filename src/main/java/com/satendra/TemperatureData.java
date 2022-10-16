package com.satendra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashMap;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TemperatureData {

	public TemperatureData(Object ob){
		this.uuid = ((LinkedHashMap<?, ?>)ob).get("uuid").toString();
		this.weather = ((LinkedHashMap<?, ?>)ob).get("weather").toString();
		this.degree = ((LinkedHashMap<?, ?>)ob).get("degree").toString();
		this.dateTime = ((LinkedHashMap<?, ?>)ob).get("dateTime").toString();
		this.timestamp = ((LinkedHashMap<?, ?>)ob).get("timestamp").toString();
	}

	private String uuid;

	private String weather;

	private String degree;

	private String timestamp;

	private String dateTime;

}
