package com.zxventures.zedelivery.resolvers;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.vividsolutions.jts.io.ParseException;
import com.zxventures.zedelivery.grapqhql.models.FormPdv;
import com.zxventures.zedelivery.grapqhql.models.Pdv;
import com.zxventures.zedelivery.grapqhql.services.NewPdvService;

public class Mutation implements GraphQLMutationResolver {
	private NewPdvService newPdvService;

    public Mutation(NewPdvService newPdvService) {
		this.newPdvService = newPdvService;
    }

    public Pdv newPdv(FormPdv formPdv) throws ParseException {
    	return newPdvService.createPdvOf(formPdv);
    }

}