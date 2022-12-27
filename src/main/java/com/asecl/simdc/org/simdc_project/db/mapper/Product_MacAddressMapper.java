package com.asecl.simdc.org.simdc_project.db.mapper;

import com.asecl.simdc.org.simdc_project.db.entity.MacAddress;
import com.asecl.simdc.org.simdc_project.db.entity.Product_MacAddress;
import com.asecl.simdc.org.simdc_project.graphql.entity.input.MacUpgradeInput;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface Product_MacAddressMapper extends BaseMapper<Product_MacAddress>{
    void fastBatchInsert(List<Product_MacAddress> list);
    void fastBatchInsertById(@Param("list")List<Product_MacAddress> list, @Param("macTypeId")long macTypeId, @Param("macStatusId")long macStatusId);
    Product_MacAddress getFirstMacByLotcodeAndPO(@Param("lotCode")String lotCode, @Param("po")String po);
    void deleteByID(long id, long macTypeID);
    Integer getDispatchMacCountByPOAndLotCode(@Param("po")String po, @Param("lotCode")String lotCode);
    List<Product_MacAddress> getDispatchMacAddress( @Param("macStatusID") long macStatusID, @Param("lotCode")String lotCode, @Param("po")String po, @Param("totalCount")int totalCount);

    Product_MacAddress getByTestConfigIdAndStatusId(@Param("id")long id, @Param("statusId")long statusId);
    void fastBatchUpdate(List<Product_MacAddress> adds);
    void updateTestUserIdAndStatusIdByAddress(@Param("address")String address, @Param("statusId")long statusId);
    Product_MacAddress getMACBySipSerialName(@Param("SipSerialName")String SipSerialName);
    List<Long> getTotalMACStatusByMacAddress(@Param("Address")String address);
    List<Long> getTotalMACStatusByLotCode(@Param("LotCode")String lotcode);
    Product_MacAddress getLastTestMacByLotcodeId(@Param("LotCode")String lotcode);

    List<Long> getTotalMACStatusByConfigurationID(@Param("id")long id);
//    int getUsedAndUsedFailCountByTestConfigId(@Param("id")long id);
//    int getCountByTestConfigId(@Param("id")long id);

    String getMacAddressStatus(@Param("Address")String address);
    Product_MacAddress getByAddress(long Address, long macTypeID);
    void updateSipSerialNameByAddress(@Param("address")String address, @Param("sipserialname")String sipserialname);
    void updateSipLicenseByAddress(@Param("address")String address, @Param("license")String license);
    void updateData(@Param("input") MacUpgradeInput input,@Param("id") long id, @Param("testUserId") long testUserId, @Param("testLotCodeId") long testLotCodeId);
}
