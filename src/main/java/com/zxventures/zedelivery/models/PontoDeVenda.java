package com.zxventures.zedelivery.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class PontoDeVenda {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @NotBlank
    private String tradingName;
    
    @NotNull
    @NotBlank
    private String ownerName;
    
    @NotNull
    @NotBlank
    @Column(unique = true)
    private String document;
    
    public PontoDeVenda(String tradingName, String ownerName, String document) {
		this.tradingName = tradingName;
		this.ownerName = ownerName;
		this.document = document;
	}
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	@Override
	public String toString() {
		return "Pdv [id=" + id + ", tradingName=" + tradingName + ", ownerName=" + ownerName + ", document=" + document + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((document == null) ? 0 : document.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PontoDeVenda other = (PontoDeVenda) obj;
		if (document == null) {
			if (other.document != null)
				return false;
		} else if (!document.equals(other.document))
			return false;
		return true;
	}
	
}
