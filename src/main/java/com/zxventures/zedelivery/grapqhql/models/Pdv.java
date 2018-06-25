package com.zxventures.zedelivery.grapqhql.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.zxventures.zedelivery.grapqhql.services.MultiPolygonParser;
import com.zxventures.zedelivery.models.PontoDeVenda;

public class Pdv {
	
    private Long id;

    @NotNull
    @NotBlank
    private String tradingName;
    
    @NotNull
    @NotBlank
    private String ownerName;
    
    @NotNull
    @NotBlank
    private String document;

    private Address address;
    
    private CoverageArea coverageArea;

    public Pdv(PontoDeVenda pontoDeVenda) {
    	this.id =pontoDeVenda.getId();
    	this.tradingName = pontoDeVenda.getTradingName();
    	this.ownerName = pontoDeVenda.getOwnerName();
    	this.document = pontoDeVenda.getDocument();
    	this.address = new Address(pontoDeVenda.getAddress());
    	this.coverageArea = new CoverageArea(MultiPolygonParser.getCoordinatesList(pontoDeVenda.getCoverageArea()));
	}
    
	public Long getId() {
		return id;
	}

	public String getTradingName() {
		return tradingName;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public String getDocument() {
		return document;
	}

	public Address getAddress() {
		return address;
	}

	public CoverageArea getCoverageArea() {
		return coverageArea;
	}

	
}
