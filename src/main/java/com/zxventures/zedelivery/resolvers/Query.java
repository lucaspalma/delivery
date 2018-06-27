package com.zxventures.zedelivery.resolvers;

import java.util.ArrayList;
import java.util.List;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.vividsolutions.jts.io.ParseException;
import com.zxventures.zedelivery.actions.SearchPdvAction;
import com.zxventures.zedelivery.exceptions.AddressNotCoveredException;
import com.zxventures.zedelivery.grapqhql.models.Pdv;
import com.zxventures.zedelivery.models.PontoDeVenda;
import com.zxventures.zedelivery.repositories.PdvRepository;

public class Query implements GraphQLQueryResolver {

	
	private PdvRepository pdvRepository;
	private SearchPdvAction searchPdvAction;

	public Query(PdvRepository pdvRepository, SearchPdvAction searchPdvAction) {
		this.pdvRepository = pdvRepository;
		this.searchPdvAction = searchPdvAction;
	}
	
	public Iterable<Pdv> findAllPdvs() {
		List<Pdv> pdvs = new ArrayList<>();
		for (PontoDeVenda pontoDeVenda : pdvRepository.findAll()) {
			pdvs.add(new Pdv(pontoDeVenda));
		}
        return pdvs;
    }
	
	public Pdv findPdv(Long id) {
		return new Pdv(pdvRepository.findById(id).get());
    }
	
	public Pdv searchPdv(Double lng, Double lat) throws ParseException, AddressNotCoveredException {
		PontoDeVenda closest = searchPdvAction.searchClosestPdv(lng, lat);
		return new Pdv(closest);
    }
}