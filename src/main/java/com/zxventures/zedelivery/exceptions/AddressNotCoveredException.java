package com.zxventures.zedelivery.exceptions;

import graphql.GraphQLException;

public class AddressNotCoveredException extends GraphQLException {

	public AddressNotCoveredException() {
		super("There is not any pdv to attend your address");
	}

}
