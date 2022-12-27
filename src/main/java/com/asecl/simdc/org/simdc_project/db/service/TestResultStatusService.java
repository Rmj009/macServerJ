package com.asecl.simdc.org.simdc_project.db.service;

import com.asecl.simdc.org.simdc_project.db.entity.TestResultStatus;
import com.asecl.simdc.org.simdc_project.db.mapper.TestResultStatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TestResultStatusService {
    @Autowired
    private TestResultStatusMapper mMapper;

    @Transactional
    public void insert(TestResultStatus type){
        this.mMapper.insert(type);
    }

    @Transactional
    public List<TestResultStatus> getAll(){
        return this.mMapper.getAll();
    }

    @Transactional
    public TestResultStatus getIDByResult(String result){
        return this.mMapper.getIDByResult(result);
    }

}
