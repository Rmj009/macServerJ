package com.asecl.simdc.org.simdc_project.db.mapper;

import com.asecl.simdc.org.simdc_project.db.entity.ProductFamily;
import com.asecl.simdc.org.simdc_project.graphql.entity.input.ProductFamilyQueryInput;
import com.asecl.simdc.org.simdc_project.graphql.entity.input.ProductFamilyUpgradeInput;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface ProductFamilyMapper extends BaseMapper<ProductFamily> {
    int getCountByName(String name);
    long getIdByName(String name);
    ProductFamily getByName(String name);
    void updateData(@Param("input") ProductFamilyUpgradeInput input, @Param("id") long id);
    List<ProductFamily> get(ProductFamilyQueryInput input);
}
