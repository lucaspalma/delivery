package com.zxventures.zedelivery.exceptions;

import graphql.GraphQLException;

public class DuplicatedDocumentException extends GraphQLException {

	public DuplicatedDocumentException(String document) {
		super("The document " + document + " already exists!");
	}
	
}
