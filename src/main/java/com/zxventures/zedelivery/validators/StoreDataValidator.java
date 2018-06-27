package com.zxventures.zedelivery.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zxventures.zedelivery.models.StoreData;
import com.zxventures.zedelivery.repositories.PdvRepository;

import graphql.GraphQLException;

@Component
public class StoreDataValidator {

	@Autowired
	private PdvRepository pdvRepository;
	
	public void validator(StoreData storeData) {
		if(pdvRepository.findByDocument(storeData.getDocument()) != null) {
			throw new GraphQLException("The document " + storeData.getDocument() + " already exists!");
		}
	}
	
}
