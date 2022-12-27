package com.asecl.simdc.org.simdc_project.db.mapper;

import com.asecl.simdc.org.simdc_project.db.entity.TrayCount;
import com.asecl.simdc.org.simdc_project.db.entity.TrayPosition;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface TrayPositionMapper extends BaseMapper<TrayPosition> {
    List<TrayCount> getLastXYByTypeIdAndCfgId(long typeId, long cfgId);
}
