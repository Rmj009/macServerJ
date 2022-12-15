package com.asecl.simdc.org.simdc_project.db.mapper;

import com.asecl.simdc.org.simdc_project.db.entity.DutDevice;
import com.asecl.simdc.org.simdc_project.db.entity.TestConfiguration_DutBinding;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Mapper
public interface TestConfiguration_DutBindingMapper extends BaseMapper<TestConfiguration_DutBinding>{

    int getDutDevice_IDCount(long id);
    int checkDutDeviceStatusIsOccupy(long dut_id);
    int getIDByDut_TFC(String LotCode,String name);

    List<DutDevice> getDutsByConfigurationId(long testConfigurationId);
    void deleteByDutId(long dutId);
}
