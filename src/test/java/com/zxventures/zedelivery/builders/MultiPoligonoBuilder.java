package com.zxventures.zedelivery.builders;

import java.util.ArrayList;
import java.util.List;

public class MultiPoligonoBuilder {

	private final List<List<List<List<Double>>>> multiPolygon;
	
	public MultiPoligonoBuilder() {
		this.multiPolygon = new ArrayList<>();
	}
	
	
	public MultiPoligonoBuilder addPoligono(List<List<List<Double>>> poligono) {
		this.multiPolygon.add(poligono);
		return this;
	}


	public List<List<List<List<Double>>>> build() {
		return this.multiPolygon;
	}

}
