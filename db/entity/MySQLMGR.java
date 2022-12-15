package com.asecl.simdc.org.simdc_project.db.entity;

import lombok.Data;

@Data
public class MySQLMGR {
    private String CHANNEL_NAME;
    private String MEMBER_ID;
    private String MEMBER_HOST;
    private String MEMBER_PORT;
    private String MEMBER_STATE;
    private String MEMBER_ROLE;
    private String MEMBER_VERSION;
}
