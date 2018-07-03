package com.zxventures.zedelivery.grapqhql.services;

import org.springframework.stereotype.Component;

import com.vividsolutions.jts.io.ParseException;
import com.zxventures.zedelivery.grapqhql.models.FormPdv;
import com.zxventures.zedelivery.grapqhql.models.Pdv;
import com.zxventures.zedelivery.models.PontoDeVenda;
import com.zxventures.zedelivery.repositories.PdvRepository;
import com.zxventures.zedelivery.validators.StoreDataValidator;

@Component
public class NewPdvService {

	private PdvRepository pdvRepository;
	private StoreDataValidator pontoDeVendaValidator;

    public NewPdvService(PdvRepository pdvRepository, StoreDataValidator pontoDeVendaValidator) {
		this.pdvRepository = pdvRepository;
		this.pontoDeVendaValidator = pontoDeVendaValidator;
    }
    
    public Pdv createPdvOf(FormPdv formPdv) throws ParseException {
    	pontoDeVendaValidator.validator(formPdv);
    	PontoDeVenda pv = formPdv.getPontoDeVenda(); 
    	pdvRepository.save(pv);
    	return new Pdv(pv);
    }
	
}
