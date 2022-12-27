package com.asecl.simdc.org.simdc_project.graphql.entity.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MacCreationInput {
    private String address;
    private int totalCount;
    private String macType;
    private String increaseType;
    private String ownerEmplayeeId;
}
