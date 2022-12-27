package com.asecl.simdc.org.simdc_project.graphql.entity.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MacXMLCreate {
    private String address_start;
    private String address_end;
    private String po;
    private String lotcode;
    private String macType;
}

