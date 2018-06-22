package com.zxventures.zedelivery.resolvers;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.zxventures.zedelivery.models.Pdv;
import com.zxventures.zedelivery.repositories.PdvRepository;

public class Query implements GraphQLQueryResolver {

	
	private PdvRepository pdvRepository;

	public Query(PdvRepository pdvRepository) {
		this.pdvRepository = pdvRepository;
	}
	
	public Iterable<Pdv> findAllPdvs() {
        return pdvRepository.findAll();
    }
}