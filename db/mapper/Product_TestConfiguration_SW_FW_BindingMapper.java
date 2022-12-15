package com.asecl.simdc.org.simdc_project.db.mapper;

import com.asecl.simdc.org.simdc_project.db.entity.Product_TestConfiguration_SW_FW_Binding;
import com.asecl.simdc.org.simdc_project.db.entity.TestConfiguration_SW_FW_Binding;
import com.asecl.simdc.org.simdc_project.graphql.entity.input.Product_TestConfigurationUpgradeInput;
import com.asecl.simdc.org.simdc_project.graphql.entity.input.TestConfigurationUpgradeInput;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface Product_TestConfiguration_SW_FW_BindingMapper extends BaseMapper<Product_TestConfiguration_SW_FW_Binding>{
    void changeIsActived(long Id, int IsActive);
    Product_TestConfiguration_SW_FW_Binding get(Product_TestConfigurationUpgradeInput input);
    Product_TestConfiguration_SW_FW_Binding getIsActivedByLotCode(String lotcode);
}
