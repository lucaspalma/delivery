package com.zxventures.zedelivery.grapqhql.models;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

public class AddressForm {

	@NotNull
	@NotBlank
	private String type = "Point";

	@Size(min=2,max=2)
	private List<Double> coordinates = new ArrayList<>();
	
	@Deprecated
	public AddressForm() {}
	
	public AddressForm(Point point) {
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

	public Point getPoint() {
		return new GeometryFactory().createPoint(new Coordinate(coordinates.get(0), coordinates.get(1)));
	}
}
