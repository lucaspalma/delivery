package com.zxventures.zedelivery.resolvers;

import java.util.ArrayList;
import java.util.List;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import com.zxventures.zedelivery.exceptions.AddressNotCoveredException;
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
	
	public Pdv findPdv(Long id) {
		return new Pdv(pdvRepository.findById(id).get());
    }
	
	public Iterable<Pdv> searchPdv(Double lng, Double lat) throws ParseException, AddressNotCoveredException {
		List<Pdv> pdvs = new ArrayList<>();
		Point endereco = (Point) new WKTReader().read("POINT(" +lng+ " " + lat + ")");
		for (PontoDeVenda pontoDeVenda : pdvRepository.searchPdvsThatCovergeThis(endereco)) {
			pdvs.add(new Pdv(pontoDeVenda));
		}
		return pdvs;
    }
}