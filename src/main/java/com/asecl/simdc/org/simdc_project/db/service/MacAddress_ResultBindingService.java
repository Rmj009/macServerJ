package com.asecl.simdc.org.simdc_project.db.service;

import com.asecl.simdc.org.simdc_project.db.entity.MacAddress_ResultBinding;
import com.asecl.simdc.org.simdc_project.db.mapper.MacAddress_ResultBindingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MacAddress_ResultBindingService {
    @Autowired
    private MacAddress_ResultBindingMapper mMapper;

    @Transactional
    public void insert(MacAddress_ResultBinding type){
        this.mMapper.insert(type);
    }

    @Transactional
    public List<MacAddress_ResultBinding> getAll(){
        return this.mMapper.getAll();
    }

}
