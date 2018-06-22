package com.zxventures.zedelivery.resolvers;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.ParseException;
import com.zxventures.zedelivery.grapqhql.models.Address;
import com.zxventures.zedelivery.grapqhql.models.Pdv;
import com.zxventures.zedelivery.models.PontoDeVenda;
import com.zxventures.zedelivery.repositories.PdvRepository;

public class Mutation implements GraphQLMutationResolver {
	private PdvRepository pdvRepository;

    public Mutation(PdvRepository pdvRepository) {
		this.pdvRepository = pdvRepository;
    }

    public Pdv newPdv(String tradingName,String ownerName,String document, Address address) throws ParseException {
    	Point point = address.getPoint();
    	PontoDeVenda pv = new PontoDeVenda(tradingName, ownerName, document, point);
    	pdvRepository.save(pv);
    	return new Pdv(pv);
    }

}