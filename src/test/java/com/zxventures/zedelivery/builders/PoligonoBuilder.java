package com.zxventures.zedelivery.builders;

import java.util.ArrayList;
import java.util.List;

public class PoligonoBuilder {

	private final List<List<List<Double>>> polygon;
	
	public PoligonoBuilder() {
		this.polygon = new ArrayList<>();
	}

	public PoligonoBuilder addLinha(List<List<Double>> linha) {
		this.polygon.add(linha);
		return this;
	}

	public List<List<List<Double>>> build() {
		return this.polygon;
	}
	
}
