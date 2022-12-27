package com.asecl.simdc.org.simdc_project.db.mapper;

import com.asecl.simdc.org.simdc_project.db.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface RoleMapper extends BaseMapper<Role>{

    Role getByName(String name);

    void deleteByName(String name);
}
