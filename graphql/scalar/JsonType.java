package com.asecl.simdc.org.simdc_project.graphql.scalar;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import graphql.language.StringValue;
import graphql.schema.*;
import org.springframework.stereotype.Component;


@Component
public class JsonType extends GraphQLScalarType {
    public JsonType() {
        super("Json", "Json value", new Coercing() {
            @Override
            public Object serialize(Object o) throws CoercingSerializeException {
                if(o instanceof JSONObject){
                    try{
                        return JSON.toJSONString(o);
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
                        String str = String.valueOf(o);
                        return JSONObject.parseObject(str);
                    }catch(Exception ex){
                        throw new CoercingSerializeException("Json parseValue scalar error : " + ex.getMessage());
                    }
                }
                throw new CoercingParseValueException("Unable to parse variable value " + o + " as Json");
            }

            @Override
            public Object parseLiteral(Object o) throws CoercingParseLiteralException {
                if (o instanceof StringValue) {
                    try{
                        String str = ((StringValue) o).getValue();
                        return JSONObject.parseObject(str);
                    }catch(Exception ex){
                        throw new CoercingSerializeException("Json parseValue scalar error : " + ex.getMessage());
                    }
                }
                throw new CoercingParseLiteralException(
                        "Value is not any Json value : '" + String.valueOf(o) + "'"
                );
            }
        });
    }
}
