package com.asecl.simdc.org.simdc_project.graphql.scalar;

import graphql.language.StringValue;
import graphql.schema.*;
import org.springframework.stereotype.Component;

@Component
public class LongType extends GraphQLScalarType {
    public LongType(){
        super("Long", "Long value", new Coercing() {
            @Override
            public Object serialize(Object o) throws CoercingSerializeException {
                try{
                    Long.parseLong(o.toString());
                    return o.toString();
                }catch(Exception ex){
                    throw new CoercingParseValueException("Unable to parse Long variable value " + o );
                }
            }

            @Override
            public Object parseValue(Object o) throws CoercingParseValueException {
                if (o instanceof String) {
                    try{
                        return Long.parseLong(o.toString());
                    }catch(Exception ex){
                        throw new CoercingParseValueException("Unable to parse Long variable value " + o );
                    }
                }
                throw new CoercingParseValueException("Unable to parse variable value " + o + " as Long");
            }

            @Override
            public Object parseLiteral(Object o) throws CoercingParseLiteralException {
                if (o instanceof StringValue) {
                    try{
                        return Long.parseLong(((StringValue) o).getValue());
                    }catch(Exception ex){
                        throw new CoercingParseValueException("Unable to parse Long variable value " + o );
                    }
                }
                throw new CoercingParseLiteralException(
                        "Value is not any Long value : '" + String.valueOf(o) + "'"
                );
            }
        });
    }
}

