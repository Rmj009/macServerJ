package com.asecl.simdc.org.simdc_project.db.mapper;

import com.asecl.simdc.org.simdc_project.db.entity.MacAddress;
import com.asecl.simdc.org.simdc_project.graphql.entity.input.MacQueryInput;
import com.asecl.simdc.org.simdc_project.graphql.entity.input.MacUpgradeInput;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface MacAddressMapper extends BaseMapper<MacAddress>{
    void fastBatchInsert(List<MacAddress> list);
    void fastBatchUpdate(List<MacAddress> list);
    MacAddress getByAddress(long Address, long macTypeID);
    String getLastInsertNameIdBySortTimestamp(String name, long macTypeID);
    List<MacAddress> getByOuiAddress(String oui, long macTypeID);
    int getMacAddressMatchRangeCount(long startAddress, long endAddress, long macTypeID);
    int getMacAddressMatchRangeCountByOdd(long startAddress, long endAddress, long macTypeID);
    int getMacAddressMatchRangeCountByEven(long startAddress, long endAddress, long macTypeID);
    int getcountByAddressbinding(String address);

    int getCountByTestConfigurationLotCode(String LotCode);

    int getCountByTestConfigurationId(long id);

    List<MacAddress> getDispatchMacAddress(@Param("startAddress") long startAddress, @Param("macStatusID") long macStatusID, @Param("macTypeID") long macTypeID, @Param("totalCount") int totalCount, @Param("isEvenType") int isEvenType, @Param("isOddType") int isOddType);

    List<MacAddress> get(MacQueryInput input);
    void updateData(@Param("input") MacUpgradeInput input,@Param("id") long id, @Param("testUserId") long testUserId, @Param("testLotCodeId") long testLotCodeId);
    void deleteByAddress(long Address, long macTypeID);
    void deleteByID(long id, long macTypeID);
    void unbindingTestConfigIdByStateUnused(@Param("testId")long testId, @Param("unusedId")long unusedId);

    MacAddress getByTestConfigIdAndStatusId(@Param("id")long id, @Param("statusId")long statusId);

    void updateTestUserIdAndStatusIdByAddress(@Param("address")String address, @Param("statusId")long statusId, @Param("testUserId")long testUserId);

    int checkMacAddressesHaveUsing(long testconfigurationId);

    String getMacAddressStatus(@Param("Address")String address);
    List<Long> getTotalMACStatusByMacAddress(@Param("Address")String address);
    List<Long> getTotalMACStatusByLotCode(@Param("LotCode")String lotcode);
    MacAddress getLastTestMacByLotcodeId(@Param("LotCode")String lotcode);
    void updateSipSerialNameByAddress(@Param("address")String address, @Param("sipserialname")String sipserialname);
    MacAddress getLastBTMacAddress();
}