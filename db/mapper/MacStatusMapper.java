package com.asecl.simdc.org.simdc_project.db.mapper;

import com.asecl.simdc.org.simdc_project.db.entity.MacStatus;
import com.asecl.simdc.org.simdc_project.db.entity.MacType;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface MacStatusMapper extends BaseMapper<MacStatus>{

    MacStatus getByName(String name);

    void deleteByName(String name);
}