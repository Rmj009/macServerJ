package com.asecl.simdc.org.simdc_project.db.service;

import com.asecl.simdc.org.simdc_project.db.entity.RegisterType;
import com.asecl.simdc.org.simdc_project.db.mapper.RegisterTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RegisterTypeService {

    @Autowired
    private RegisterTypeMapper mMapper;

    @Transactional
    public void insert(RegisterType type){
        this.mMapper.insert(type);
    }

    @Transactional
    public List<RegisterType> getAll(){
        return this.mMapper.getAll();
    }

    @Transactional
    public int getTotalCount(){
        return this.mMapper.getTotalCount();
    }

    @Transactional
    public RegisterType getByName(String name){
        return this.mMapper.getByName(name);
    }

    @Transactional
    public void update(RegisterType type){
        this.mMapper.update(type);
    }

    @Transactional
    public void deleteByID(long id){
        this.mMapper.deleteByID(id);
    }

    @Transactional
    public void deleteByName(String name){ this.mMapper.deleteByName(name); }
}
