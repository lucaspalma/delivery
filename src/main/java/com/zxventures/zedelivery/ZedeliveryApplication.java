package com.zxventures.zedelivery;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.zxventures.zedelivery.exceptions.GraphQLErrorAdapter;
import com.zxventures.zedelivery.repositories.PdvRepository;
import com.zxventures.zedelivery.resolvers.Mutation;
import com.zxventures.zedelivery.resolvers.Query;
import com.zxventures.zedelivery.validators.PontoDeVendaValidator;

import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.servlet.GraphQLErrorHandler;

@SpringBootApplication
public class ZedeliveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZedeliveryApplication.class, args);
	}
	
	@Bean
	public GraphQLErrorHandler errorHandler() {
		return new GraphQLErrorHandler() {
			@Override
			public List<GraphQLError> processErrors(List<GraphQLError> errors) {
				List<GraphQLError> clientErrors = errors.stream()
						.filter(this::isClientError)
						.collect(Collectors.toList());

				List<GraphQLError> serverErrors = errors.stream()
						.filter(e -> !isClientError(e))
						.map(GraphQLErrorAdapter::new)
						.collect(Collectors.toList());

				List<GraphQLError> e = new ArrayList<>();
				e.addAll(clientErrors);
				e.addAll(serverErrors);
				return e;
			}

			protected boolean isClientError(GraphQLError error) {
				return !(error instanceof ExceptionWhileDataFetching || error instanceof Throwable);
			}
		};
	}
	
	@Bean
	public Query query(PdvRepository pdvRepository) {
		return new Query(pdvRepository);
	}
	
	@Bean
	public Mutation mutation(PdvRepository pdvRepository, PontoDeVendaValidator pontoDeVendaValidator) {
		return new Mutation(pdvRepository, pontoDeVendaValidator);
	}
}
