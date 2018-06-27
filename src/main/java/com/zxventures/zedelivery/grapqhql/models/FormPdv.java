package com.zxventures.zedelivery.grapqhql.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.zxventures.zedelivery.models.PontoDeVenda;

public class FormPdv {

	@NotNull
	@NotBlank
	private String tradingName;
	@NotNull
	@NotBlank
	private String ownerName;
	@NotNull
	@NotBlank
	private String document;
	@NotNull
	private AddressForm address;
	@NotNull
	private CoverageAreaForm coverageArea;

	public String getTradingName() {
		return tradingName;
	}

	public void setTradingName(String tradingName) {
		this.tradingName = tradingName;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public AddressForm getAddress() {
		return address;
	}

	public void setAddress(AddressForm address) {
		this.address = address;
	}

	public CoverageAreaForm getCoverageArea() {
		return coverageArea;
	}

	public void setCoverageArea(CoverageAreaForm coverageArea) {
		this.coverageArea = coverageArea;
	}

	public PontoDeVenda getPontoDeVenda() throws ParseException {
		Point point = this.address.getPoint();
		MultiPolygon polygon = this.coverageArea.getMultiPolygon();
		return new PontoDeVenda(this.tradingName, this.ownerName, this.document, point, polygon);
	}

}
