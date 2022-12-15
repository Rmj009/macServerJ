package com.asecl.simdc.org.simdc_project.db.mapper;

import com.asecl.simdc.org.simdc_project.db.entity.MacDispatchType;
import com.asecl.simdc.org.simdc_project.db.entity.MacStatus;
import com.asecl.simdc.org.simdc_project.graphql.entity.input.MacDispatchUpdateStatusInput;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface MacDispatchTypeMapper  extends BaseMapper<MacDispatchType>{

    MacDispatchType getByName(String name);
    int getCountByUsing(String lotCode,String mac);
    void deleteByName(String name);
    void updateMacStatus(String mac,String status,String lotCode);
//    int getCountByMacStatus(String lotCode);
    int getCountByStatus_ID(String lotCode,String status);
}
