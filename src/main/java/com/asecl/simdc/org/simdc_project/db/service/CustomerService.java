package com.asecl.simdc.org.simdc_project.db.service;

import com.asecl.simdc.org.simdc_project.db.entity.Customer;
import com.asecl.simdc.org.simdc_project.db.mapper.CustomerMapper;
import com.asecl.simdc.org.simdc_project.graphql.entity.input.CustomerQueryInput;
import com.asecl.simdc.org.simdc_project.graphql.entity.input.CustomerUpgradeInput;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerMapper mMapper;

    @Transactional
    public void insert(Customer type){
        this.mMapper.insert(type);
    }

    @Transactional
    public List<Customer> getAll(){
        return this.mMapper.getAll();
    }

    @Transactional
    public int getTotalCount(){
        return this.mMapper.getTotalCount();
    }

    @Transactional
    public void update(Customer type){
        this.mMapper.update(type);
    }

    @Transactional
    public void updateData(@Param("input") CustomerUpgradeInput input,@Param("id") long id){
        this.mMapper.updateData(input,id);
    }

    @Transactional
    public void deleteByID(long id){
        this.mMapper.deleteByID(id);
    }

    @Transactional
    public void deleteByName(String name){
        this.mMapper.deleteByName(name);
    }

    @Transactional
    public Customer getByRemark(String remark){
        return this.mMapper.getByRemark(remark);
    }

    @Transactional
    public Customer getByName(String name){
        return this.mMapper.getByName(name);
    }

    @Transactional
    public int getCountByName(String name){
        return this.mMapper.getCountByName(name);
    }

    @Transactional
    public List<Customer> get(CustomerQueryInput input){
        return this.mMapper.get(input);
    }

    @Transactional
    public long getIdByName(String name){
        return this.mMapper.getIdByName(name);
    }

    @Transactional
    public long getCountByNameInTestconfiguration(String name ){
        return this.mMapper.getCountByNameInTestconfiguration(name);
    }

}
