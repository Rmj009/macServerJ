package com.asecl.simdc.org.simdc_project.db.service;

import com.asecl.simdc.org.simdc_project.db.entity.Role;
import com.asecl.simdc.org.simdc_project.db.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleMapper mMapper;

    @Transactional
    public void insert(Role role){
        this.mMapper.insert(role);
    }

    @Transactional
    public List<Role> getAll(){
        return this.mMapper.getAll();
    }

    @Transactional
    public int getTotalCount(){
        return this.mMapper.getTotalCount();
    }

    @Transactional
    public Role getByName(String name){
        return this.mMapper.getByName(name);
    }

    @Transactional
    public void update(Role role){
        this.mMapper.update(role);
    }

    @Transactional
    public void deleteByID(long id){
        this.mMapper.deleteByID(id);
    }

    @Transactional
    public void deleteByName(String name){ this.mMapper.deleteByName(name); }
}
