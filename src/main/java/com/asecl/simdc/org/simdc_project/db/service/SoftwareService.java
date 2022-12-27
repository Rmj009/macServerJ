package com.asecl.simdc.org.simdc_project.db.service;

import com.asecl.simdc.org.simdc_project.db.entity.FirmwareInfo;
import com.asecl.simdc.org.simdc_project.db.entity.Software;
import com.asecl.simdc.org.simdc_project.db.entity.SoftwareInfo;
import com.asecl.simdc.org.simdc_project.db.mapper.SoftwareMapper;
import com.asecl.simdc.org.simdc_project.graphql.entity.input.SoftwareQueryInput;
import com.asecl.simdc.org.simdc_project.graphql.entity.input.SoftwareUpgradeInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SoftwareService {
    @Autowired
    private SoftwareMapper mMapper;

    @Transactional
    public void insert(Software s){
        this.mMapper.insert(s);
    }

    @Transactional
    public List<Software> getAll(){
        return this.mMapper.getAll();
    }

    @Transactional
    public int getTotalCount(){
        return this.mMapper.getTotalCount();
    }

    @Transactional
    public int getCountByNameAndVersoin(String name, String version){
        return this.mMapper.getCountByNameAndVersoin(name, version);
    }

    @Transactional
    public Software getByName(String name){
        return this.mMapper.getByName(name);
    }

    @Transactional
    public void update(Software s){
        this.mMapper.update(s);
    }

    @Transactional
    public void deleteByID(long id){
        this.mMapper.deleteByID(id);
    }

    @Transactional
    public void deleteByName(String name){ this.mMapper.deleteByName(name); }

    @Transactional
    public Software getByNameAndVersion(String name, String version){
        return this.mMapper.getByNameAndVersion(name, version);
    }

    @Transactional
    public void deleteByNameAndVersion(String name, String version){
        this.mMapper.deleteByNameAndVersion(name, version);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateData(SoftwareUpgradeInput input, long id){
        this.mMapper.updateData(input, id);
    }

    @Transactional(rollbackFor = Exception.class)
    public List<SoftwareInfo> get(SoftwareQueryInput input){
        return this.mMapper.get(input);
    }

    @Transactional
    public List<SoftwareInfo> getAllInfo(){
        return this.mMapper.getAllInfo();
    }

    @Transactional
    public int getCountByNameInTestconfiguration(String name, String version){
        return this.mMapper.getCountByNameInTestconfiguration(name,version);
    }
}
