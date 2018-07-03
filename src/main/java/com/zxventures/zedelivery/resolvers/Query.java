package com.zxventures.zedelivery.resolvers;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import com.zxventures.zedelivery.exceptions.AddressNotCoveredException;
import com.zxventures.zedelivery.grapqhql.models.Pdv;
import com.zxventures.zedelivery.repositories.PdvRepository;

public class Query implements GraphQLQueryResolver {

	
	private PdvRepository pdvRepository;

	public Query(PdvRepository pdvRepository) {
		this.pdvRepository = pdvRepository;
	}
	
	public Pdv findPdv(Long id) {
		return new Pdv(pdvRepository.findById(id).get());
    }
	
	public Iterable<Pdv> searchPdv(Double lng, Double lat) throws ParseException, AddressNotCoveredException {
		Point endereco = (Point) new WKTReader().read("POINT(" +lng+ " " + lat + ")");
		return pdvRepository.searchPdvsThatCovergeThis(endereco, Pdv.class);
    }
}