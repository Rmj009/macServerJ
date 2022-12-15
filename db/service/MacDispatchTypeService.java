package com.asecl.simdc.org.simdc_project.db.service;

import com.asecl.simdc.org.simdc_project.db.entity.MacDispatchType;
import com.asecl.simdc.org.simdc_project.db.entity.MacStatus;
import com.asecl.simdc.org.simdc_project.db.mapper.MacDispatchTypeMapper;
import com.asecl.simdc.org.simdc_project.db.mapper.MacStatusMapper;
import com.asecl.simdc.org.simdc_project.graphql.entity.input.MacDispatchUpdateStatusInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MacDispatchTypeService {
    @Autowired
    private MacDispatchTypeMapper mMapper;

    @Transactional
    public void insert(MacDispatchType type){
        this.mMapper.insert(type);
    }

    @Transactional
    public List<MacDispatchType> getAll(){
        return this.mMapper.getAll();
    }

    @Transactional
    public int getTotalCount(){
        return this.mMapper.getTotalCount();
    }

    @Transactional
    public MacDispatchType getByName(String name){
        return this.mMapper.getByName(name);
    }

    @Transactional
    public void update(MacDispatchType type){
        this.mMapper.update(type);
    }

    @Transactional
    public void deleteByID(long id){
        this.mMapper.deleteByID(id);
    }

    @Transactional
    public void deleteByName(String name){ this.mMapper.deleteByName(name); }

    @Transactional
    public int getCountByUsing(String lotCode,String mac){
        return this.mMapper.getCountByUsing(lotCode,mac);
    }

    @Transactional
    public int getCountByStatus_ID(String lotCode,String status){
        return this.mMapper.getCountByStatus_ID(lotCode,status);
    }

    @Transactional
    public void updateMacStatus(String mac,String status,String lotCode){ this.mMapper.updateMacStatus(mac,status,lotCode); }

//    @Transactional
//    public int getCountByMacStatus(String lotCode){
//        return this.mMapper.getCountByMacStatus(lotCode);
//    }

}
