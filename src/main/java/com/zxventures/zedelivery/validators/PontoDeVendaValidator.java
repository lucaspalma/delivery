package com.zxventures.zedelivery.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zxventures.zedelivery.models.PontoDeVenda;
import com.zxventures.zedelivery.repositories.PdvRepository;

import graphql.GraphQLException;

@Component
public class PontoDeVendaValidator {

	@Autowired
	private PdvRepository pdvRepository;
	
	public void validator(PontoDeVenda pontoDeVenda) {
		if(pdvRepository.findByDocument(pontoDeVenda.getDocument()) != null) {
			throw new GraphQLException("The document " + pontoDeVenda.getDocument() + " already exists!");
		}
	}
	
}
