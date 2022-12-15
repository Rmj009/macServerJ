package com.asecl.simdc.org.simdc_project.db.mapper;

import com.asecl.simdc.org.simdc_project.db.entity.MacLog;
import com.asecl.simdc.org.simdc_project.db.entity.MacStatus;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface MacLogMapper extends BaseMapper<MacLog> {
}
