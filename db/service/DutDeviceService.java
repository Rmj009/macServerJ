package com.asecl.simdc.org.simdc_project.db.service;

import com.asecl.simdc.org.simdc_project.db.entity.DutDevice;
import com.asecl.simdc.org.simdc_project.db.mapper.DutDeviceMapper;
import com.asecl.simdc.org.simdc_project.graphql.entity.input.DutDeviceQueryInput;
import com.asecl.simdc.org.simdc_project.graphql.entity.input.DutDeviceUpgradeInput;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DutDeviceService {
    @Autowired
    private DutDeviceMapper mMapper;

    @Transactional
    public void insert(DutDevice type){
        this.mMapper.insert(type);
    }

    @Transactional
    public List<DutDevice> getAll(){
        return this.mMapper.getAll();
    }

    @Transactional
    public int getTotalCount(){
        return this.mMapper.getTotalCount();
    }

    @Transactional
    public void update(DutDevice type){
        this.mMapper.update(type);
    }

    @Transactional
    public void deleteByID(long id){
        this.mMapper.deleteByID(id);
    }

    @Transactional
    public DutDevice getByRemark(String remark){
        return this.mMapper.getByRemark(remark);
    }

    @Transactional
    public int getCountByHostName(String HostName){
        return this.mMapper.getCountByHostName(HostName);
    }

    public DutDevice getByHostNameAndProductDeviceAndGroupPC(String HostName, String productDevice, String groupPC){
        return this.mMapper.getByHostNameAndProductDeviceAndGroupPC(HostName, productDevice, groupPC);
    }

//    @Transactional
//    public int getIDbyLotCode(String LotCode){
//        return this.mMapper.getIDbyLotCode(LotCode);
//    }

//    @Transactional
//    public int getCountByLotCode(String hostName){
//        return this.mMapper.getCountByHostName(hostName);
//    }

    @Transactional
    public DutDevice getByHostName(String HostName){
        return this.mMapper.getByHostName(HostName);
    }

//    @Transactional
//    public DutDevice getByDUTLotCode(String LotCode){
//        return this.mMapper.getb(LotCode);
//    }

    @Transactional
    public void updateData(DutDeviceUpgradeInput input, long id){
        this.mMapper.updateData(input, id);
    }

    @Transactional
    public List<DutDevice> get(DutDeviceQueryInput input){
        return this.mMapper.get(input);
    }

}
