package com.asecl.simdc.org.simdc_project.db.service;

import com.asecl.simdc.org.simdc_project.db.entity.LoginType;
import com.asecl.simdc.org.simdc_project.db.mapper.LoginTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LoginTypeService {
    @Autowired
    private LoginTypeMapper mMapper;

    @Transactional
    public void insert(LoginType type){
        this.mMapper.insert(type);
    }

    @Transactional
    public List<LoginType> getAll(){
        return this.mMapper.getAll();
    }

    @Transactional
    public int getTotalCount(){
        return this.mMapper.getTotalCount();
    }

    @Transactional
    public LoginType getByName(String name){
        return this.mMapper.getByName(name);
    }

    @Transactional
    public void update(LoginType type){
        this.mMapper.update(type);
    }

    @Transactional
    public void deleteByID(long id){
        this.mMapper.deleteByID(id);
    }

    @Transactional
    public void deleteByName(String name){ this.mMapper.deleteByName(name); }
}
