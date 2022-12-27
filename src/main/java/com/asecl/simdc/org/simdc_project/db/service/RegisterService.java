package com.asecl.simdc.org.simdc_project.db.service;

import com.asecl.simdc.org.simdc_project.db.entity.Register;
import com.asecl.simdc.org.simdc_project.db.mapper.RegisterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RegisterService {
    @Autowired
    private RegisterMapper mMapper;

    @Transactional
    public void insert(Register register){
        this.mMapper.insert(register);
    }

    @Transactional(readOnly = true)
    List<Register> getAll(){
        return this.mMapper.getAll();
    }

    @Transactional(readOnly = true)
    int getTotalCount(){
        return this.mMapper.getTotalCount();
    }

    @Transactional(readOnly = true)
    Register getByRegisterEMail(String email){
        return this.mMapper.getByRegisterEMail(email);
    }

    @Transactional(readOnly = true)
    Register getByRegisterEmployeeID(String employeeID){
        return this.mMapper.getByRegisterEmployeeID(employeeID);
    }

    @Transactional(readOnly = true)
    void deleteByID(long id){
        this.deleteByID(id);
    }
}
