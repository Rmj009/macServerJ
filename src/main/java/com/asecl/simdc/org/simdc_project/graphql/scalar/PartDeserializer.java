package com.asecl.simdc.org.simdc_project.graphql.scalar;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import javax.servlet.http.Part;
import java.io.IOException;

public class PartDeserializer extends JsonDeserializer<Part> {

    @Override
    public Part deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return null;
    }
}