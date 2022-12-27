package com.asecl.simdc.org.simdc_project.db.mapper;

import com.asecl.simdc.org.simdc_project.db.entity.DutDevice;
import com.asecl.simdc.org.simdc_project.graphql.entity.input.DutDeviceQueryInput;
import com.asecl.simdc.org.simdc_project.graphql.entity.input.DutDeviceUpgradeInput;
import com.asecl.simdc.org.simdc_project.graphql.entity.input.MacUpgradeInput;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface DutDeviceMapper extends BaseMapper<DutDevice> {
    DutDevice getByRemark(String remark);
    DutDevice getByHostNameAndProductDeviceAndGroupPC(String HostName, String productDevice, String groupPC);
    DutDevice getByHostName(String HostName);
    int getCountByHostName(String HostName);
    int getIDByProductDeviceAndGroupPC(String productDevice, String groupPC);
    int getCountByProductDeviceAndGroupPC(String productDevice, String groupPC);
    void updateData(@Param("input") DutDeviceUpgradeInput input, @Param("id") long id);
    void deleteByRemark(String remark);
    void deleteByHostName(String HostName);
    List<DutDevice> get(DutDeviceQueryInput input);
    List<DutDevice> getByProductDeviceAndGroupPC(String productDevice, String groupPC);
}
