package com.asecl.simdc.org.simdc_project.db.mapper;


import com.asecl.simdc.org.simdc_project.db.entity.TestResultStatus;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;


@Component
@Mapper
public interface TestResultStatusMapper extends BaseMapper<TestResultStatus>{
    TestResultStatus getIDByResult(String result);

}
