package com.asecl.simdc.org.simdc_project.db.service;

import com.asecl.simdc.org.simdc_project.db.entity.TestConfiguration_DutBinding;
import com.asecl.simdc.org.simdc_project.db.mapper.TestConfiguration_DutBindingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TestConfiguration_DutBindingService {
    @Autowired
    private TestConfiguration_DutBindingMapper mMapper;

    @Transactional
    public void insert(TestConfiguration_DutBinding type){
        this.mMapper.insert(type);
    }

    @Transactional
    public List<TestConfiguration_DutBinding> getAll(){
        return this.mMapper.getAll();
    }

    @Transactional
    public int getTotalCount(){
        return this.mMapper.getTotalCount();
    }

    @Transactional
    public void update(TestConfiguration_DutBinding type){
        this.mMapper.update(type);
    }

    @Transactional
    public void deleteByID(long id){
        this.mMapper.deleteByID(id);
    }

    @Transactional
    public int getDutDevice_IDCount(long id){
        return this.mMapper.getDutDevice_IDCount(id);
    }

    @Transactional
    public int checkDutDeviceStatusIsOccupy(long dut_id){
        return this.mMapper.checkDutDeviceStatusIsOccupy(dut_id);
    }

}
