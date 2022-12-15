package com.asecl.simdc.org.simdc_project.graphql.entity.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MacUpgradeInput {
    private String address;
    private String macType;
    private String newName;
    private long newStatusID;
    private String newStatus;
    private String newTesterEmplayeeId;
    private String newSipSerialName;
    private String newTestConfigLotCode;
}
