package com.zxventures.zedelivery.grapqhql.services;

import java.util.ArrayList;
import java.util.List;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.MultiPolygon;

public class MultiPolygonParser {

	public static List<List<List<List<Double>>>> getCoordinatesList(MultiPolygon multiPolygon) {
		List<List<List<List<Double>>>> coordinates = new ArrayList<>();
		for (int i = 0; i < multiPolygon.getNumGeometries(); i++) {
			Geometry polygon = multiPolygon.getGeometryN(i).getBoundary();
			List<List<List<Double>>> polygonList = new ArrayList<>();
			if(polygon instanceof MultiLineString) {				
				for (int j = 0; j < polygon.getNumGeometries(); j++) {
					List<List<Double>> linearRing = getLinearRing((LinearRing) polygon.getGeometryN(j));
					polygonList.add(linearRing);
				}
			} else {
				List<List<Double>> linearRing = getLinearRing((LinearRing) polygon);
				polygonList.add(linearRing);
			}
			coordinates.add(polygonList);
		}
		return coordinates;
	}
	
	private static List<List<Double>> getLinearRing(LinearRing linear) {
		List<List<Double>> linearRing = new ArrayList<>();
		for (int z = 0; z < linear.getNumPoints(); z++) {
			List<Double> point = new ArrayList<>();
			Coordinate coordinate = linear.getPointN(z).getCoordinate();
			point.add(coordinate.x);
			point.add(coordinate.y);
			linearRing.add(point);
		}
		return linearRing;
	}
	
}
