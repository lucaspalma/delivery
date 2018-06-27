package com.zxventures.zedelivery.exceptions;

import java.util.List;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

public class AddressNotCoveredException extends Exception implements GraphQLError {

	@Override
	public String getMessage() {
		return "There is not any pdv to attend your address";
	}

	@Override
	public List<SourceLocation> getLocations() {
		return null;
	}

	@Override
	public ErrorType getErrorType() {
		return ErrorType.OperationNotSupported;
	}

}
