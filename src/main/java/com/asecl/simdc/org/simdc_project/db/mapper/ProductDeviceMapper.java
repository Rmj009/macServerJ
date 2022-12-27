package com.asecl.simdc.org.simdc_project.db.mapper;

import com.asecl.simdc.org.simdc_project.db.entity.ProductDevice;
import com.asecl.simdc.org.simdc_project.graphql.entity.input.ProductDeviceQueryInput;
import com.asecl.simdc.org.simdc_project.graphql.entity.input.ProductDeviceUpgradeInput;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface ProductDeviceMapper extends BaseMapper<ProductDevice> {
    ProductDevice getByName(String Name, long FamilyID);
    ProductDevice getByNameStr(String Name, String Family);
    ProductDevice getByProductDeviceName(String Name);
    List<ProductDevice> get(ProductDeviceQueryInput input);
    void updateData(@Param("input") ProductDeviceUpgradeInput input, @Param("id") long id, @Param("familyID") long familyID, @Param("newFamilyID") long newFamilyID);
    int getCountByName(String Name, long FamilyID);
    void deleteByName(String Name);

    ProductDevice getByLotName(String Name);
}
