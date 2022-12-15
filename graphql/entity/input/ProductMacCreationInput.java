package com.asecl.simdc.org.simdc_project.graphql.entity.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductMacCreationInput {
    private String address;
    private String lotcode;
    private String macType;
    private String increaseType;
    private String pid;
    private String sipLicense;
}
