package com.zxventures.zedelivery.grapqhql.models;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

public class CoverageArea {

	@NotNull
	@NotBlank
	private String type = "MultiPolygon";

	@Size(min=2,max=2)
	private List<List<List<List<Double>>>> coordinates = new ArrayList<>();
	
	@Deprecated
	public CoverageArea() {}
	    
	public CoverageArea(List<List<List<List<Double>>>> coordinates) {
		this.coordinates = coordinates;
	}

	public String getType() {
		return type;
	}

	public List<List<List<List<Double>>>> getCoordinates() {
		return coordinates;
	}

	public MultiPolygon getMultiPolygon() throws ParseException {
		StringBuilder multiPolygonsText = new StringBuilder();
		multiPolygonsText.append("(");
			
		for (int i = 0; i < this.coordinates.size(); i++) {
			List<List<List<Double>>> polygon = this.coordinates.get(i);			
			multiPolygonsText.append("(" + getPolygonText(polygon) + ")");
			if(i < this.coordinates.size() - 1) {
				multiPolygonsText.append(", ");
			}
		}
		multiPolygonsText.append(")");
		return (MultiPolygon ) new WKTReader().read(type.toUpperCase() + multiPolygonsText);
	}

	private String getPolygonText(List<List<List<Double>>> polygon) {
		StringBuilder polygonsText = new StringBuilder();
		for (int i = 0; i < polygon.size(); i++) {
			List<List<Double>> linearRing = polygon.get(i);
			polygonsText.append("(" + getLinearRingText(linearRing) + ")");
			if(i < polygon.size() - 1) {
				polygonsText.append(", ");
			}
			
		}
		return polygonsText.toString();
	}

	private String getLinearRingText(List<List<Double>> linearRing) {
		StringBuilder linearRingText = new StringBuilder();
		for (int i = 0; i < linearRing.size(); i++) {
			List<Double> point = linearRing.get(i);
			linearRingText.append(point.get(0) + " " + point.get(1));
			if(i < linearRing.size() - 1) {
				linearRingText.append(", ");
			}
		}
		return linearRingText.toString();
	}
	
}
