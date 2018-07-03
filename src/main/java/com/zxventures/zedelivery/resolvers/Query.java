package com.zxventures.zedelivery.resolvers;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
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
	
	public Iterable<Pdv> searchPdv(Double lng, Double lat) throws AddressNotCoveredException {
		Point endereco = new GeometryFactory().createPoint(new Coordinate(lng, lat)); 
		return pdvRepository.searchPdvsThatCovergeThis(endereco, Pdv.class);
    }
}