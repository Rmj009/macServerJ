package com.asecl.simdc.org.simdc_project.db.service;

import com.asecl.simdc.org.simdc_project.db.entity.ProductDevice;
import com.asecl.simdc.org.simdc_project.db.entity.ProductFamily;
import com.asecl.simdc.org.simdc_project.db.mapper.ProductFamilyMapper;
import com.asecl.simdc.org.simdc_project.db.mapper.ProductDeviceMapper;
import com.asecl.simdc.org.simdc_project.graphql.entity.input.ProductDeviceQueryInput;
import com.asecl.simdc.org.simdc_project.graphql.entity.input.ProductDeviceUpgradeInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductDeviceService {
    @Autowired
    private ProductDeviceMapper mMapper;

    @Autowired
    private ProductFamilyMapper mProductFamilyMapper;

    @Transactional
    public void insert(ProductDevice type){
        this.mMapper.insert(type);
    }

    @Transactional
    public List<ProductDevice> getAll(){
        return this.mMapper.getAll();
    }

    @Transactional
    public int getTotalCount(){
        return this.mMapper.getTotalCount();
    }

    @Transactional
    public ProductDevice getByName(String Name, long FamilyID){
        return this.mMapper.getByName(Name, FamilyID);
    }

    @Transactional
    public int getCountByName(String Name, long FamilyID){
        return this.mMapper.getCountByName(Name, FamilyID);
    }

    @Transactional
    public List<ProductDevice> get(ProductDeviceQueryInput input){
        return this.mMapper.get(input);
    }

    @Transactional
    public void update(ProductDevice type){
        this.mMapper.update(type);
    }

    @Transactional
    public void updateData(ProductDeviceUpgradeInput input, long id, long familyID, long newFamilyID){
        this.mMapper.updateData(input, id , familyID, newFamilyID);
    }

    @Transactional
    public void deleteByID(long id){
        this.mMapper.deleteByID(id);
    }

    @Transactional
    public void deleteByName(String name) { this.mMapper.deleteByName(name); }

    @Transactional
    public ProductDevice getByName(String Name, String FamilyName){
        ProductFamily family = null;
        if((family = this.mProductFamilyMapper.getByName(FamilyName)) == null){
            throw new RuntimeException("Not Found Product Family Name : " + FamilyName);
        }

        return this.mMapper.getByName(Name, family.getID());
    }
}
