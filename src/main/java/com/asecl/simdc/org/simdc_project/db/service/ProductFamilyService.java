package com.asecl.simdc.org.simdc_project.db.service;

import com.asecl.simdc.org.simdc_project.db.entity.ProductFamily;
import com.asecl.simdc.org.simdc_project.db.mapper.ProductFamilyMapper;
import com.asecl.simdc.org.simdc_project.graphql.entity.input.ProductFamilyQueryInput;
import com.asecl.simdc.org.simdc_project.graphql.entity.input.ProductFamilyUpgradeInput;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductFamilyService {
    @Autowired
    private ProductFamilyMapper mMapper;

    @Transactional
    public void insert(ProductFamily type){
        this.mMapper.insert(type);
    }

    @Transactional
    public List<ProductFamily> getAll(){
        return this.mMapper.getAll();
    }

    @Transactional
    public int getTotalCount(){
        return this.mMapper.getTotalCount();
    }

    @Transactional
    public int getCountByName(String name){
        return this.mMapper.getCountByName(name);
    }

    @Transactional
    public long getIdByName(String name){
        return this.mMapper.getIdByName(name);
    }

    @Transactional
    public ProductFamily getByName(String name){
        return this.mMapper.getByName(name);
    }

    @Transactional
    public List<ProductFamily> get(ProductFamilyQueryInput input){
        return this.mMapper.get(input);
    }

    @Transactional
    public void update(ProductFamily type){
        this.mMapper.update(type);
    }

    @Transactional
    public void updateData(@Param("input") ProductFamilyUpgradeInput input, @Param("id") long id){
        this.mMapper.updateData(input, id);
    }

    @Transactional
    public void deleteByID(long id){
        this.mMapper.deleteByID(id);
    }

}
