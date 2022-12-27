package com.asecl.simdc.org.simdc_project.db.mapper;

import com.asecl.simdc.org.simdc_project.db.entity.RegisterType;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface RegisterTypeMapper extends BaseMapper<RegisterType> {

    RegisterType getByName(String name);

    void deleteByName(String name);
}
