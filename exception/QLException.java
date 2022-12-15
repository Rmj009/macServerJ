package com.asecl.simdc.org.simdc_project.exception;

import graphql.ErrorClassification;
import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QLException extends RuntimeException implements GraphQLError {
    private Exception mErrorEx = null;

    public QLException(Exception message ) {
        super(message);
        this.mErrorEx = message;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public ErrorClassification getErrorType() {
        return ErrorType.ValidationError;
    }



    @Override
    public Map<String, Object> getExtensions() {
        Map<String, Object> errors = new HashMap<>();
        errors.put("errorMessage", this.mErrorEx.getMessage());
        errors.put("errorStack", ExceptionUtils.getStackTrace(this.mErrorEx));
        return errors;
    }
}

