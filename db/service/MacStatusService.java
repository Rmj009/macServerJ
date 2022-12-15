package com.asecl.simdc.org.simdc_project.db.service;

import com.asecl.simdc.org.simdc_project.db.entity.MacStatus;
import com.asecl.simdc.org.simdc_project.db.entity.MacType;
import com.asecl.simdc.org.simdc_project.db.mapper.MacStatusMapper;
import com.asecl.simdc.org.simdc_project.db.mapper.MacTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MacStatusService {
    @Autowired
    private MacStatusMapper mMapper;

    @Transactional
    public void insert(MacStatus type){
        this.mMapper.insert(type);
    }

    @Transactional
    public List<MacStatus> getAll(){
        return this.mMapper.getAll();
    }

    @Transactional
    public int getTotalCount(){
        return this.mMapper.getTotalCount();
    }

    @Transactional
    public MacStatus getByName(String name){
        return this.mMapper.getByName(name);
    }

    @Transactional
    public void update(MacStatus type){
        this.mMapper.update(type);
    }

    @Transactional
    public void deleteByID(long id){
        this.mMapper.deleteByID(id);
    }

    @Transactional
    public void deleteByName(String name){ this.mMapper.deleteByName(name); }
}
