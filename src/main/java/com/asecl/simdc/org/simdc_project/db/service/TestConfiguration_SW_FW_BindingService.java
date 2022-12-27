package com.asecl.simdc.org.simdc_project.db.service;

import com.asecl.simdc.org.simdc_project.db.entity.TestConfiguration_SW_FW_Binding;
import com.asecl.simdc.org.simdc_project.db.mapper.TestConfiguration_SW_FW_BindingMapper;
import com.asecl.simdc.org.simdc_project.graphql.entity.input.TestConfigurationUpgradeInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TestConfiguration_SW_FW_BindingService {
    @Autowired
    private TestConfiguration_SW_FW_BindingMapper mMapper;

    @Transactional(rollbackFor = Exception.class)
    public TestConfiguration_SW_FW_Binding get(TestConfigurationUpgradeInput input){
        return this.mMapper.get(input);
    }

    @Transactional(rollbackFor = Exception.class)
    public TestConfiguration_SW_FW_Binding getIsActivedByLotCode(String lotcode){
        return this.mMapper.getIsActivedByLotCode(lotcode);
    }


}
