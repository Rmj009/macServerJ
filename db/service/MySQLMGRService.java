package com.asecl.simdc.org.simdc_project.db.service;

import com.asecl.simdc.org.simdc_project.db.entity.MySQLMGR;
import com.asecl.simdc.org.simdc_project.db.mapper.MySQLMGRMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MySQLMGRService {

    @Autowired
    private MySQLMGRMapper mMapper;

    @Value("${mysql.mgr.hostname}")
    private String MGR_HOST_NAME;

    @Value("${mysql.mgr.mgrport}")
    private String MGR_PORT;

    @Transactional
    public List<MySQLMGR> getAll(){
        return this.mMapper.getAll();
    }

    @Transactional
    public List<MySQLMGR> forceToBecomeSingleMember(){
        if(MGR_HOST_NAME == null || MGR_HOST_NAME.length() == 0){
            throw new RuntimeException("Environment: MGR_HOST_NAME Not Setting !!");
        }

        if(MGR_PORT == null || MGR_PORT.length() == 0){
            throw new RuntimeException("Environment: MGR_PORT Not Setting !!");
        }

        List<MySQLMGR> mgrDatas = this.mMapper.getAll();
        String online = "ONLINE";
        String unreachable = "UNREACHABLE";
        String currentOnline = "";
        int onlineCount = 0;
        int unreachableCount = 0;
        int totalCount = mgrDatas.size();
        for (MySQLMGR mgr : mgrDatas) {
            if(mgr.getMEMBER_STATE().equals(online)){
                currentOnline = mgr.getMEMBER_HOST();
                onlineCount++;
            }else if(mgr.getMEMBER_STATE().equals(unreachable)){
                unreachableCount++;
            }
        }

        if(onlineCount == 1 && totalCount == 1){
            return mgrDatas;
        }

        if(onlineCount > 1){
            throw new RuntimeException("MGR ONLINE Count > 1 !!");
        }

        if(unreachableCount != (totalCount - 1)){
            throw new RuntimeException("MGR UNREACHABLE Count Not Match !!");
        }

        if(!currentOnline.equals(MGR_HOST_NAME)){
            throw new RuntimeException("MGR ONLINE HostName Not Match Your Server Host !! Server : " +
                    currentOnline + " , Your Setting : " + MGR_HOST_NAME);
        }

        String member = MGR_HOST_NAME + ":" + MGR_PORT;
        this.mMapper.replicationForceMember(member);

        mgrDatas = this.mMapper.getAll();

        return mgrDatas;
    }
}
