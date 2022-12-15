package com.asecl.simdc.org.simdc_project.db.service;

import com.asecl.simdc.org.simdc_project.db.entity.TestConfigurationStatus;
import com.asecl.simdc.org.simdc_project.db.mapper.TestConfigurationStatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TestConfigurationStatusService {
    @Autowired
    private TestConfigurationStatusMapper mMapper;

    @Transactional
    public void insert(TestConfigurationStatus type){
        this.mMapper.insert(type);
    }

    @Transactional
    public List<TestConfigurationStatus> getAll(){
        return this.mMapper.getAll();
    }

    @Transactional
    public int getTotalCount(){
        return this.mMapper.getTotalCount();
    }

    @Transactional
    public TestConfigurationStatus getByName(String name){
        return this.mMapper.getByName(name);
    }

    @Transactional
    public void update(TestConfigurationStatus type){
        this.mMapper.update(type);
    }

    @Transactional
    public void deleteByID(long id){
        this.mMapper.deleteByID(id);
    }

    @Transactional
    public void deleteByName(String name){ this.mMapper.deleteByName(name); }
}
