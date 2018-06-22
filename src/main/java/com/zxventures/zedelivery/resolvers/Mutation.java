package com.zxventures.zedelivery.resolvers;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.zxventures.zedelivery.models.PontoDeVenda;
import com.zxventures.zedelivery.repositories.PdvRepository;

public class Mutation implements GraphQLMutationResolver {
	private PdvRepository pdvRepository;

    public Mutation(PdvRepository pdvRepository) {
		this.pdvRepository = pdvRepository;
    }

    public PontoDeVenda newPdv(String tradingName,String ownerName,String document) {
    	PontoDeVenda pv = new PontoDeVenda(tradingName, ownerName, document);
    	pdvRepository.save(pv);
    	return pv;
    }

}