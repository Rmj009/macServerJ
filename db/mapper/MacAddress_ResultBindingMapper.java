package com.asecl.simdc.org.simdc_project.db.mapper;

import com.asecl.simdc.org.simdc_project.db.entity.MacAddress_ResultBinding;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface MacAddress_ResultBindingMapper extends BaseMapper<MacAddress_ResultBinding>{
    long getIdByMacId(long macId);
    void deleteByMacId(long macId);
}
