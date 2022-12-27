package com.asecl.simdc.org.simdc_project.db.entity;

import com.asecl.simdc.org.simdc_project.db.entity.User;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class FirmwareInfo {
    private String Name;
    private List<FirmwareVersion> Versions;
}
