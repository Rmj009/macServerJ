package com.asecl.simdc.org.simdc_project.db.mapper;

import com.asecl.simdc.org.simdc_project.db.entity.*;
import com.asecl.simdc.org.simdc_project.graphql.entity.input.Product_TestConfigurationQueryInput;
import com.asecl.simdc.org.simdc_project.graphql.entity.input.TestConfigurationQueryInput;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

@Component
@Mapper
public interface Product_TestConfigurationMapper extends BaseMapper<Product_TestConfiguration>{
    int getCountByLotCode(String lotcode);
    String getStatusByLotCode(String lotcode );
    String getProductDeviceByLotCode(String lotCode);
    String getProductFamilyByLotCode(String lotCode);
    List<Product_TestConfiguration> get(Product_TestConfigurationQueryInput input);
    int getCountByTFCBinding(String lotcode);
    int getCountByTFCStatus(String lotcode);
    Product_TestConfiguration getByLotCode(String lotCode);

    Product_TestConfiguration getByProductDeviceIdAndLotCode( String lotCode);

    void deleteByLotCode(String lotcode);
    void updateTFCStatus(String lotcode);
    void updateTestStatusByLotCode(String status,String lotcode);
    void updateToTestingStatusById(@Param("testId") long testId, @Param("statusId") long statusId
            , @Param("startUserId") long startUserId);
    void updateToTestEndStatusById(@Param("testId") long testId, @Param("statusId") long statusId
            , @Param("forceUserId") long forceUserId, @Param("forceRemark") String forceRemark, @Param("endDate") Timestamp endDate);
    void updateData(@Param("p") ProductDevice p, @Param("c") Customer c, @Param("sw") Software sw, @Param("fw") Firmware fw, @Param("Device") String Device);
}
