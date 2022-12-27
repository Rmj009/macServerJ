package com.asecl.simdc.org.simdc_project.db.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class MacLog {
    private long ID;
    private MacAddress MacAddress;
    private JSONObject Log;
    private Timestamp CreatedTime;
}
