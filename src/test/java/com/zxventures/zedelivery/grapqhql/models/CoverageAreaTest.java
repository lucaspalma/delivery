package com.zxventures.zedelivery.grapqhql.models;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

public class CoverageAreaTest {

	@Test
	public void criaUmMultiPolygonEmCimaDasCoordenadas() throws ParseException {
		List<List<List<List<Double>>>> multiPolygonList = new ArrayList<>();
		List<List<List<Double>>> polygon1 = new ArrayList<>();
		List<List<List<Double>>> polygon2 = new ArrayList<>();
		multiPolygonList.add(polygon1);
		multiPolygonList.add(polygon2);
		List<List<Double>> line1 = new ArrayList<>();
		List<List<Double>> line2 = new ArrayList<>();
		polygon1.add(line1);
		polygon2.add(line2);
		List<Double> point1 = new ArrayList<>();
		List<Double> point2 = new ArrayList<>();
		List<Double> point3 = new ArrayList<>();
		List<Double> point4 = new ArrayList<>();
		List<Double> point5 = new ArrayList<>();
		List<Double> point6 = new ArrayList<>();
		List<Double> point7 = new ArrayList<>();
		List<Double> point8 = new ArrayList<>();
		point1.add(35.0);
		point1.add(21.0);
		line1.add(point1);
		point2.add(43.0);
		point2.add(56.0);
		line1.add(point2);
		point3.add(12.0);
		point3.add(34.0);
		line1.add(point3);
		point4.add(35.0);
		point4.add(21.0);
		line1.add(point4);
		
		point5.add(15.0);
		point5.add(10.0);
		line2.add(point5);
		point6.add(42.0);
		point6.add(15.0);
		line2.add(point6);
		point7.add(6.0);
		point7.add(12.0);
		line2.add(point7);
		point8.add(15.0);
		point8.add(10.0);
		line2.add(point8);
		CoverageArea coverageArea = new CoverageArea(multiPolygonList);
		MultiPolygon multiPolygon = (MultiPolygon) new WKTReader().read("MULTIPOLYGON (((35 21, 43 56, 12 34, 35 21)),((15 10, 42 15, 6 12, 15 10)))");
		Assert.assertEquals(multiPolygon, coverageArea.getMultiPolygon());
		
	}
	
}
