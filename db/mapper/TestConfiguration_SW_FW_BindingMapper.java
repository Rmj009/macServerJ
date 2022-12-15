package com.asecl.simdc.org.simdc_project.db.mapper;


import com.asecl.simdc.org.simdc_project.db.entity.TestConfiguration_SW_FW_Binding;
import com.asecl.simdc.org.simdc_project.graphql.entity.input.TestConfigurationUpgradeInput;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface TestConfiguration_SW_FW_BindingMapper extends BaseMapper<TestConfiguration_SW_FW_Binding>{
    void changeIsActived(long Id, int IsActive);
    TestConfiguration_SW_FW_Binding get(TestConfigurationUpgradeInput input);
    TestConfiguration_SW_FW_Binding getIsActivedByLotCode(String lotcode);
}
