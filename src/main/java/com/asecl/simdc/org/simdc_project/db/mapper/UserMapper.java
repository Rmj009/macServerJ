package com.asecl.simdc.org.simdc_project.db.mapper;
import com.asecl.simdc.org.simdc_project.db.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UserMapper extends BaseMapper<User>{

    User getByNickName(String nickName);

    User getByEMail(String email);

    User getByEmployeeID(String emplayeID);

    User getByPhone(String phone);

    void deleteByEMail(String email);

    void deleteByEmployeeID(String emplayeID);

    void deleteByPhone(String phone);

    int getCountByEmployeeID(String emplayeID);
}
