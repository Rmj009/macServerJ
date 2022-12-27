package com.asecl.simdc.org.simdc_project.db.mapper;

import com.asecl.simdc.org.simdc_project.db.entity.FirmwareInfo;
import com.asecl.simdc.org.simdc_project.db.entity.Software;
import com.asecl.simdc.org.simdc_project.db.entity.SoftwareInfo;
import com.asecl.simdc.org.simdc_project.graphql.entity.input.SoftwareQueryInput;
import com.asecl.simdc.org.simdc_project.graphql.entity.input.SoftwareUpgradeInput;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface SoftwareMapper extends BaseMapper<Software>{
    Software getByName(String name);
    int getCountByNameAndVersoin(String name, String version);
    int getCountByNameInTestconfiguration(String name, String version);
    void deleteByName(String name);
    Software getByNameAndVersion(String name, String version);
    void deleteByNameAndVersion(String name, String version);
    void updateData(@Param("input") SoftwareUpgradeInput input, @Param("id") long id);
    List<SoftwareInfo> getAllInfo();
    List<SoftwareInfo> get(SoftwareQueryInput input);
}
