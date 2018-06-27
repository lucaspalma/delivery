package com.zxventures.zedelivery.resolvers;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.vividsolutions.jts.io.ParseException;
import com.zxventures.zedelivery.grapqhql.models.FormPdv;
import com.zxventures.zedelivery.grapqhql.models.Pdv;
import com.zxventures.zedelivery.models.PontoDeVenda;
import com.zxventures.zedelivery.repositories.PdvRepository;
import com.zxventures.zedelivery.validators.PontoDeVendaValidator;

public class Mutation implements GraphQLMutationResolver {
	private PdvRepository pdvRepository;
	private PontoDeVendaValidator pontoDeVendaValidator;

    public Mutation(PdvRepository pdvRepository, PontoDeVendaValidator pontoDeVendaValidator) {
		this.pdvRepository = pdvRepository;
		this.pontoDeVendaValidator = pontoDeVendaValidator;
    }

    public Pdv newPdv(FormPdv formPdv) throws ParseException {
    	PontoDeVenda pv = formPdv.getPontoDeVenda(); 
    	pontoDeVendaValidator.validator(pv);
    	pdvRepository.save(pv);
    	return new Pdv(pv);
    }

}