package com.asecl.simdc.org.simdc_project.db.mapper;

import com.asecl.simdc.org.simdc_project.db.entity.TestConfigurationStatus;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface TestConfigurationStatusMapper extends BaseMapper<TestConfigurationStatus>{

    TestConfigurationStatus getByName(String name);

    void deleteByName(String name);
}
