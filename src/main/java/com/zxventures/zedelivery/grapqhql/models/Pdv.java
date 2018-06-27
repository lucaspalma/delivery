package com.zxventures.zedelivery.grapqhql.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.zxventures.zedelivery.grapqhql.services.MultiPolygonParser;
import com.zxventures.zedelivery.models.PontoDeVenda;
import com.zxventures.zedelivery.models.StoreData;

public class Pdv implements StoreData {
	
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

    private AddressForm address;
    
    private CoverageAreaForm coverageArea;

    public Pdv(PontoDeVenda pontoDeVenda) {
    	this.id =pontoDeVenda.getId();
    	this.tradingName = pontoDeVenda.getTradingName();
    	this.ownerName = pontoDeVenda.getOwnerName();
    	this.document = pontoDeVenda.getDocument();
    	this.address = new AddressForm(pontoDeVenda.getAddress());
    	this.coverageArea = new CoverageAreaForm(MultiPolygonParser.getCoordinatesList(pontoDeVenda.getCoverageArea()));
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

	public AddressForm getAddress() {
		return address;
	}

	public CoverageAreaForm getCoverageArea() {
		return coverageArea;
	}

	
}
