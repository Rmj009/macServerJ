package com.asecl.simdc.org.simdc_project.db.service;

import com.asecl.simdc.org.simdc_project.db.entity.LoginType;
import com.asecl.simdc.org.simdc_project.db.entity.TrayType;
import com.asecl.simdc.org.simdc_project.db.mapper.LoginTypeMapper;
import com.asecl.simdc.org.simdc_project.db.mapper.TrayTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TrayTypeService {
    @Autowired
    private TrayTypeMapper mMapper;

    @Transactional
    public void insert(TrayType type){
        this.mMapper.insert(type);
    }

    @Transactional
    public List<TrayType> getAll(){
        return this.mMapper.getAll();
    }

    @Transactional
    public int getTotalCount(){
        return this.mMapper.getTotalCount();
    }

    @Transactional
    public TrayType getByName(String name){
        return this.mMapper.getByName(name);
    }

    @Transactional
    public void update(TrayType type){
        this.mMapper.update(type);
    }

    @Transactional
    public void deleteByID(long id){
        this.mMapper.deleteByID(id);
    }

    @Transactional
    public void deleteByName(String name){ this.mMapper.deleteByName(name); }
}
