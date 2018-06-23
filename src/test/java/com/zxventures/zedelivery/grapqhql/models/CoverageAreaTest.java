package com.zxventures.zedelivery.grapqhql.models;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import com.zxventures.zedelivery.builders.ObjectMother;

public class CoverageAreaTest {

	private ObjectMother mother;

	@Before
	public void setUp() {
		mother = new ObjectMother();
	}
	
	@Test
	public void criaUmMultiPolygonEmCimaDasCoordenadas() throws ParseException {
		List<List<Double>> linha1 = mother.umaLinha().addPonto(35, 21).addPonto(43, 56).addPonto(12, 34).addPonto(35, 21).build();
		List<List<Double>> linha2 = mother.umaLinha().addPonto(15, 10).addPonto(42, 15).addPonto(6, 12).addPonto(15, 10).build();
		List<List<List<Double>>> poligono1 = mother.umPoligono().addLinha(linha1).build();
		List<List<List<Double>>> poligono2 = mother.umPoligono().addLinha(linha2).build();
		List<List<List<List<Double>>>> multiPoligono = mother.umMultiPoligono().addPoligono(poligono1).addPoligono(poligono2).build();
		CoverageArea coverageArea = new CoverageArea(multiPoligono);
		
		MultiPolygon multiPolygon = (MultiPolygon) new WKTReader().read("MULTIPOLYGON (((35 21, 43 56, 12 34, 35 21)),((15 10, 42 15, 6 12, 15 10)))");
		Assert.assertEquals(multiPolygon, coverageArea.getMultiPolygon());
		
	}
	
}
