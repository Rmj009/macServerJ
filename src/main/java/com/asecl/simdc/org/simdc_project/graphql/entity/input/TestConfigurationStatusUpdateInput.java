package com.asecl.simdc.org.simdc_project.graphql.entity.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestConfigurationStatusUpdateInput {
    String lotcode;
    String status;
    String startEmplayeeId;
    String forceStopEmplayeeId;
    String forceStopReason;
}
