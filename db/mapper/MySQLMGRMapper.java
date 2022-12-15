package com.asecl.simdc.org.simdc_project.db.mapper;

import com.asecl.simdc.org.simdc_project.db.entity.MySQLMGR;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface MySQLMGRMapper extends BaseMapper<MySQLMGR>{
    void replicationForceMember(String member);
}
