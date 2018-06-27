package com.zxventures.zedelivery.actions;

import java.util.List;

import org.springframework.stereotype.Component;

import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import com.zxventures.zedelivery.exceptions.AddressNotCoveredException;
import com.zxventures.zedelivery.models.PontoDeVenda;
import com.zxventures.zedelivery.repositories.PdvRepository;

@Component
public class SearchPdvAction {
	public PdvRepository pdvRepository;

	public SearchPdvAction(PdvRepository pdvRepository) {
		this.pdvRepository = pdvRepository;
	}
	
	public PontoDeVenda searchClosestPdv(Double lng, Double lat) throws ParseException, AddressNotCoveredException {
		Point point = (Point) new WKTReader().read("POINT(" + lng + " " + lat + ")");
		List<PontoDeVenda> pontosDeVenda = pdvRepository.searchPdvsThatCovergeThis(point);
		double minimo = Double.POSITIVE_INFINITY;
		PontoDeVenda closestPdv = null;
		for (PontoDeVenda pontoDeVenda : pontosDeVenda) {
			double distancia = pontoDeVenda.getAddress().distance(point);
			if(distancia < minimo) {
				closestPdv = pontoDeVenda;
				minimo = distancia;
			}
		}
		if(closestPdv == null) {
			throw new AddressNotCoveredException();
		}
		return closestPdv;
	}
}