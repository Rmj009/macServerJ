package com.asecl.simdc.org.simdc_project.db.mapper;

import com.asecl.simdc.org.simdc_project.db.entity.*;
import com.asecl.simdc.org.simdc_project.graphql.entity.input.TestConfigurationQueryInput;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

@Component
@Mapper
public interface TestConfigurationMapper extends BaseMapper<TestConfiguration> {
        int getCountByLotCode(String lotcode);
        String getStatusByLotCode(String lotcode );
        String getProductDeviceByLotCode(String lotCode);
        String getProductFamilyByLotCode(String lotCode);
        List<TestConfiguration> get(TestConfigurationQueryInput input);
        int getCountByTFCBinding(String lotcode);
        int getCountByTFCStatus(String lotcode);
        TestConfiguration getByLotCode(String lotCode);

        TestConfiguration getByProductDeviceIdAndLotCode( String lotCode);

        void deleteByLotCode(String lotcode);
        void updateTFCStatus(String lotcode);
        void updateTestStatusByLotCode(String status,String lotcode);
        void updateToTestingStatusById(@Param("testId") long testId, @Param("statusId") long statusId
                , @Param("startUserId") long startUserId);
        void updateToTestEndStatusById(@Param("testId") long testId, @Param("statusId") long statusId
                , @Param("forceUserId") long forceUserId, @Param("forceRemark") String forceRemark, @Param("endDate") Timestamp endDate);
        void updateData(@Param("p") ProductDevice p, @Param("c") Customer c, @Param("sw") Software sw, @Param("fw") Firmware fw, @Param("Device") String Device);

}
