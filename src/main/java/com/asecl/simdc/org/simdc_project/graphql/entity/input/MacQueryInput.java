package com.asecl.simdc.org.simdc_project.graphql.entity.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MacQueryInput {
    private String oui;
    private String address;
    private String name;
    private String macType;
    private String macStatus;
    private String ownerEmplayeeId;
    private Timestamp startTime;
    private Timestamp endTime;
    private int pageNumber;
    private int pageSize;
}

