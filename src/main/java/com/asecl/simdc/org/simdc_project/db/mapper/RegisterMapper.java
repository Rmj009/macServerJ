package com.asecl.simdc.org.simdc_project.db.mapper;

import com.asecl.simdc.org.simdc_project.db.entity.Register;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface RegisterMapper extends BaseMapper<Register> {

    Register getByRegisterEMail(String email);

    Register getByRegisterEmployeeID(String employeeID);

}
