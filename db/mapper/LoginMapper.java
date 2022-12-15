package com.asecl.simdc.org.simdc_project.db.mapper;

import com.asecl.simdc.org.simdc_project.db.entity.Login;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
@Mapper
public interface LoginMapper extends BaseMapper<Login> {
    Login getByJwtToken(String jwt);
    Login setByLastModifyTime(Timestamp time);
    void deleteByJwtToken(String jwt);
}
