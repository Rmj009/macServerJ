package com.asecl.simdc.org.simdc_project.db.service;

import com.asecl.simdc.org.simdc_project.db.entity.Login;
import com.asecl.simdc.org.simdc_project.db.mapper.LoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
public class LoginService {
    @Autowired
    private LoginMapper mMapper;

    @Transactional
    public void insert(Login type){
        this.mMapper.insert(type);
    }

    @Transactional
    public List<Login> getAll(){
        return this.mMapper.getAll();
    }

    @Transactional
    public int getTotalCount(){
        return this.mMapper.getTotalCount();
    }

    @Transactional
    public Login getByJwtToken(String jwt){
        return this.mMapper.getByJwtToken(jwt);
    }

    @Transactional
    public void update(Login type){
        this.mMapper.update(type);
    }

    @Transactional
    public void deleteByID(long id){
        this.mMapper.deleteByID(id);
    }

    @Transactional
    public void deleteByJwtToken(String name) { this.mMapper.deleteByJwtToken(name); }

    @Transactional
    public Login setByLastModifyTime(Timestamp time){
        return this.mMapper.setByLastModifyTime(time);
    }
}
