package com.asecl.simdc.org.simdc_project.db.mapper;

import com.asecl.simdc.org.simdc_project.db.entity.Customer;
import com.asecl.simdc.org.simdc_project.graphql.entity.input.CustomerQueryInput;
import com.asecl.simdc.org.simdc_project.graphql.entity.input.CustomerUpgradeInput;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface CustomerMapper extends BaseMapper<Customer> {
    Customer getByName(String name);
    long getIdByName(String name);
    int getCountByName(String name);
    int getCountByNameInTestconfiguration(String name);
    Customer getByRemark(String remark);
    Customer getByPhone(String phone);
    void deleteByName(String name);
    void deleteByRemark(String remark);
    void updateData(@Param("input") CustomerUpgradeInput input,@Param("id") long id);
    List<Customer> get(CustomerQueryInput input);
}
