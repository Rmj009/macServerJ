package com.asecl.simdc.org.simdc_project.db.mapper;

import com.asecl.simdc.org.simdc_project.db.entity.FirmwareInfo;
import com.asecl.simdc.org.simdc_project.graphql.entity.input.FirmwareQueryInput;
import org.apache.ibatis.annotations.Mapper;
import com.asecl.simdc.org.simdc_project.db.entity.Firmware;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface FirmwareMapper extends BaseMapper<Firmware> {
    Firmware getByNameAndVersion(String name, String version);
    int getCountByNameInTestconfiguration(String name, String version);
    int getCountByNameAndVersoin(String name, String version);
    void deleteByNameAndVersion(String name, String version);
    List<FirmwareInfo> getAllInfo();
    List<FirmwareInfo> get(FirmwareQueryInput input);
}
