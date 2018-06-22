package com.zxventures.zedelivery.grapqhql.models;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

public class Address {

	@NotNull
	@NotBlank
	private String type = "Point";

	@Size(min=2,max=2)
	private List<Double> coordinates = new ArrayList<>();
	
	@Deprecated
	public Address() {}
	
	public Address(Point point) {
		this.coordinates.add(point.getX());
		this.coordinates.add(point.getY());
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Double> getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(List<Double> coordinates) {
		this.coordinates = coordinates;
	}

	public Point getPoint() throws ParseException {
		return (Point) new WKTReader().read(type.toUpperCase() + "(" + coordinates.get(0) + " " + coordinates.get(1) + ")");
	}
}
