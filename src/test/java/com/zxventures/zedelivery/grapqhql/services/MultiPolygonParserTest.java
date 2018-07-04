package com.zxventures.zedelivery.grapqhql.services;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;


public class MultiPolygonParserTest {

	@Test
	public void geraAsCoordenadasParaApenasUmPoligono() throws ParseException {
		MultiPolygon multiPolygon = (MultiPolygon) new WKTReader().read("MULTIPOLYGON (((35 21, 43 56, 12 34, 35 21)))");
		List<List<List<List<Double>>>> coordinates = MultiPolygonParser.getCoordinatesList(multiPolygon);;
		assertEquals("[[[[35.0, 21.0], [43.0, 56.0], [12.0, 34.0], [35.0, 21.0]]]]",
					coordinates.toString());
	}
	
	@Test
	public void geraAsCoordenadasCorretasAPartirDosPoligonosDaAreaDeCobertura() throws ParseException {
		MultiPolygon multiPolygon = (MultiPolygon) new WKTReader().read("MULTIPOLYGON (((35 21, 43 56, 12 34, 35 21)),((15 10, 42 15, 11 22, 6 12, 15 10)))");
		List<List<List<List<Double>>>> coordinates = MultiPolygonParser.getCoordinatesList(multiPolygon);;
		assertEquals("[[[[35.0, 21.0], [43.0, 56.0], [12.0, 34.0], [35.0, 21.0]]], [[[15.0, 10.0], [42.0, 15.0], [11.0, 22.0], [6.0, 12.0], [15.0, 10.0]]]]",
					coordinates.toString());
	}
	
	@Test
	public void mesmoParaPoligonosComMuitosAneisSalvaTodasAsCoordenadas() throws ParseException {
		MultiPolygon multiPolygon = (MultiPolygon) new WKTReader().read("MULTIPOLYGON (((35 21, 43 56, 12 34, 35 21), (25 11, 33 46, 2 24, 25 11)),((15 10, 42 15, 6 12, 15 10)))");
		List<List<List<List<Double>>>> coordinates = MultiPolygonParser.getCoordinatesList(multiPolygon);;
		assertEquals("[[[[35.0, 21.0], [43.0, 56.0], [12.0, 34.0], [35.0, 21.0]], [[25.0, 11.0], [33.0, 46.0], [2.0, 24.0], [25.0, 11.0]]], [[[15.0, 10.0], [42.0, 15.0], [6.0, 12.0], [15.0, 10.0]]]]",
					coordinates.toString());
	}
	
	
}
