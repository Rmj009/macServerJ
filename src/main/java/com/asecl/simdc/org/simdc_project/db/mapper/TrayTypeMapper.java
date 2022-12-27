package com.asecl.simdc.org.simdc_project.db.mapper;

import com.asecl.simdc.org.simdc_project.db.entity.LoginType;
import com.asecl.simdc.org.simdc_project.db.entity.TrayType;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface TrayTypeMapper extends BaseMapper<TrayType>{
    TrayType getByName(String name);
    void deleteByName(String name);
}
