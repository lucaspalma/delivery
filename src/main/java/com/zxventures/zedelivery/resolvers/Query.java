package com.zxventures.zedelivery.resolvers;

import java.util.ArrayList;
import java.util.List;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.zxventures.zedelivery.grapqhql.models.Pdv;
import com.zxventures.zedelivery.models.PontoDeVenda;
import com.zxventures.zedelivery.repositories.PdvRepository;

public class Query implements GraphQLQueryResolver {

	
	private PdvRepository pdvRepository;

	public Query(PdvRepository pdvRepository) {
		this.pdvRepository = pdvRepository;
	}
	
	public Iterable<Pdv> findAllPdvs() {
		List<Pdv> pdvs = new ArrayList<>();
		for (PontoDeVenda pontoDeVenda : pdvRepository.findAll()) {
			pdvs.add(new Pdv(pontoDeVenda));
		}
        return pdvs;
    }
}