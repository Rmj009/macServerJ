package com.asecl.simdc.org.simdc_project.graphql.entity.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.servlet.http.Part;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FirmwareCreationInput {
    private String saveName;
    private String name;
    private String version;
    private String remark;
    private String md5;
    private String ownerEmplayeeId;
}
