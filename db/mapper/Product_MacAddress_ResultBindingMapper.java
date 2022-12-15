package com.asecl.simdc.org.simdc_project.db.mapper;

import com.asecl.simdc.org.simdc_project.db.entity.Product_MacAddress_ResultBinding;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface Product_MacAddress_ResultBindingMapper extends BaseMapper<Product_MacAddress_ResultBinding>{
    long getIdByMacId(long macId);
    void deleteByMacId(long macId , long tcId);
}
