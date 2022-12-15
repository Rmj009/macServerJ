package com.asecl.simdc.org.simdc_project.db.mapper;

import com.asecl.simdc.org.simdc_project.db.entity.Login;
import com.asecl.simdc.org.simdc_project.db.entity.Tray;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
@Mapper
public interface TrayMapper extends BaseMapper<Tray> {
    Tray getByTestConfigurationIdAndName(long cfgId, String name);
}
