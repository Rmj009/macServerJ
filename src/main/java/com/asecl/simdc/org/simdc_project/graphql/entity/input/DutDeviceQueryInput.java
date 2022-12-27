package com.asecl.simdc.org.simdc_project.graphql.entity.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DutDeviceQueryInput {
    private String productDevice;
    private String hostname;
    private String groupPc;
    private String ownerEmplayeeId;
    private Timestamp startTime;
    private Timestamp endTime;
    private int pageNumber;
    private int pageSize;
}

