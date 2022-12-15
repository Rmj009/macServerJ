package com.asecl.simdc.org.simdc_project.graphql.entity.out;

import lombok.Data;

@Data
public class ResponseMGRStatus {
    private String CHANNEL_NAME;
    private String MEMBER_ID;
    private String MEMBER_HOST;
    private String MEMBER_PORT;
    private String MEMBER_STATE;
    private String MEMBER_ROLE;
    private String MEMBER_VERSION;
}
