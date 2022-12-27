package com.asecl.simdc.org.simdc_project.db.mapper;

import com.asecl.simdc.org.simdc_project.db.entity.LoginType;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface LoginTypeMapper extends BaseMapper<LoginType>{
    LoginType getByName(String name);
    void deleteByName(String name);
}
