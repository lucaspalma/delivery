package com.zxventures.zedelivery.resolvers;

import java.util.ArrayList;
import java.util.List;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
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
	
	public Pdv searchPdv(Double lng, Double lat) throws ParseException {
		Point point = (Point) new WKTReader().read("POINT(" + lng + " " + lat + ")");
		List<PontoDeVenda> pontosDeVenda = pdvRepository.searchPdvsThatCovergeThis(point);
		double minimo = Double.POSITIVE_INFINITY;
		PontoDeVenda pdv = null;
		for (PontoDeVenda pontoDeVenda : pontosDeVenda) {
			double distancia = pontoDeVenda.getAddress().distance(point);
			if(distancia < minimo) {
				pdv = pontoDeVenda;
				minimo = distancia;
			}
		}
		return new Pdv(pdv);
    }
}