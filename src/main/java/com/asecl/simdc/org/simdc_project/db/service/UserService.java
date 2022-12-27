package com.asecl.simdc.org.simdc_project.db.service;


import com.asecl.simdc.org.simdc_project.db.entity.User;
import com.asecl.simdc.org.simdc_project.db.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper mUserMapper;

    @Transactional
    public void insert(User user){
        this.mUserMapper.insert(user);
    }

    @Transactional
    public List<User> getAll(){
        return this.mUserMapper.getAll();
    }

    @Transactional
    public int getTotalCount(){
        return this.mUserMapper.getTotalCount();
    }

    @Transactional
    public User getByNickName(String nickName) { return this.mUserMapper.getByNickName(nickName);}

    @Transactional
    public User getByEMail(String email){ return this.mUserMapper.getByEMail(email);}

    @Transactional
    public User getByEmployeeID(String emplayeeID){ return this.mUserMapper.getByEmployeeID(emplayeeID);}


    @Transactional
    public User getByPhone(String phone){ return this.mUserMapper.getByPhone(phone);}

    @Transactional
    public void update(User user){ this.mUserMapper.update(user);}

    @Transactional
    public void deleteByID(long id){ this.mUserMapper.deleteByID(id);}

    @Transactional
    public void deleteByEMail(String email){ this.mUserMapper.deleteByEMail(email);}

    @Transactional
    public void deleteByEmployeeID(String emplayeeID) { this.mUserMapper.getByEmployeeID(emplayeeID);}

    @Transactional
    public void deleteByPhone(String phone){ this.mUserMapper.deleteByPhone(phone);}

    @Transactional
    public int getCountByEmployeeID(String emplayeeID){
        return this.mUserMapper.getCountByEmployeeID(emplayeeID);
    }
}
