package com.asecl.simdc.org.simdc_project.db.mapper;

import com.asecl.simdc.org.simdc_project.db.entity.Product_TrayPosition;
import com.asecl.simdc.org.simdc_project.db.entity.TrayCount;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface Product_TrayPositionMapper extends BaseMapper<Product_TrayPosition>{
    List<TrayCount> getLastXYByTypeIdAndCfgId(long typeId, long cfgId);
}
