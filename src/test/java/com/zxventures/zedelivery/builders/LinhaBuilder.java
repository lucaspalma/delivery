package com.zxventures.zedelivery.builders;

import java.util.ArrayList;
import java.util.List;

public class LinhaBuilder {

	private final List<List<Double>> line;
	
	public LinhaBuilder() {
		this.line = new ArrayList<>();
	}
	
	public LinhaBuilder addPonto(double x, double y) {
		List<Double> point = new ArrayList<>();
		point.add(x);
		point.add(y);
		this.line.add(point);
		return this;
	}
	
	public List<List<Double>> build() {
		return this.line;
	}
	
}
