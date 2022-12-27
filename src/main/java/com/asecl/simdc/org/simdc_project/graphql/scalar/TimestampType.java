package com.asecl.simdc.org.simdc_project.graphql.scalar;

import graphql.language.StringValue;
import graphql.schema.*;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;


@Component
public class TimestampType extends GraphQLScalarType {
    static SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public TimestampType(){
        super("Timestamp", "Timestamp value", new Coercing() {
            @Override
            public Object serialize(Object o) throws CoercingSerializeException {
                if(o instanceof Timestamp){
                    try{
                        return TimestampType.DateFormat.format(new Date(((Timestamp)o).getTime()));
                    }catch(Exception ex){
                        throw new CoercingSerializeException("Timestamp serialize scalar error : " + ex.getMessage());
                    }
                }
                throw new CoercingParseValueException("Unable to parse timestamp variable value " + o + " as string");
            }

            @Override
            public Object parseValue(Object o) throws CoercingParseValueException {
                if (o instanceof String) {
                    try{
                        String timestampStr = String.valueOf(o);
                        Date parsedDate = TimestampType.DateFormat.parse(timestampStr);
                        return new java.sql.Timestamp(parsedDate.getTime());
                    }catch(Exception ex){
                        throw new CoercingSerializeException("Timestamp parseValue scalar error : " + ex.getMessage());
                    }
                }
                throw new CoercingParseValueException("Unable to parse variable value " + o + " as timestamp");
            }

            @Override
            public Object parseLiteral(Object o) throws CoercingParseLiteralException {
                if (o instanceof StringValue) {
                    try{
                        String timestampStr = ((StringValue) o).getValue();
                        Date parsedDate = TimestampType.DateFormat.parse(timestampStr);
                        return new java.sql.Timestamp(parsedDate.getTime());
                    }catch(Exception ex){
                        throw new CoercingSerializeException("Timestamp parseValue scalar error : " + ex.getMessage());
                    }
                }
                throw new CoercingParseLiteralException(
                        "Value is not any timestamp value : '" + String.valueOf(o) + "'"
                );
            }
        });
    }
}
