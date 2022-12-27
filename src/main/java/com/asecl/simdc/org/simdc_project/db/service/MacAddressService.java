package com.asecl.simdc.org.simdc_project.db.service;

import com.asecl.simdc.org.simdc_project.db.entity.*;
import com.asecl.simdc.org.simdc_project.db.mapper.MacAddressMapper;
import com.asecl.simdc.org.simdc_project.db.mapper.MacStatusMapper;
import com.asecl.simdc.org.simdc_project.graphql.entity.customEnum.EnumIncreaseType;
import com.asecl.simdc.org.simdc_project.graphql.entity.customEnum.EnumMacType;
import com.asecl.simdc.org.simdc_project.graphql.entity.input.MacQueryInput;
import com.asecl.simdc.org.simdc_project.graphql.entity.input.MacUpgradeInput;
import com.asecl.simdc.org.simdc_project.util.Constant;
import com.asecl.simdc.org.simdc_project.util.UtilFunc;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class MacAddressService {
    @Autowired
    private MacAddressMapper mMapper;

    @Autowired
    private MacStatusMapper mMacStatusMapper;

    @Autowired
    private SqlSessionFactory mSqlSessionFactory;


    @Value("${mac.insert.sql_batch_count}")
    private long MAC_INSERT_BATCH_COUNT;

    @Transactional
    public void insert(MacAddress type){
        this.mMapper.insert(type);
    }

    @Transactional
    public List<MacAddress> getAll(){
        return this.mMapper.getAll();
    }

    @Transactional
    public int getTotalCount(){
        return this.mMapper.getTotalCount();
    }

    @Transactional
    public void update(MacAddress type){
        this.mMapper.update(type);
    }

    @Transactional
    public void deleteByID(long id, long macTypeID){
        this.mMapper.deleteByID(id, macTypeID);
    }

    @Transactional
    public void deleteByAddress(long Address, long macTypeID){
        this.mMapper.deleteByAddress(Address, macTypeID);
    }

    @Transactional
    public MacAddress getByAddress(long Address, long macTypeID){
        return this.mMapper.getByAddress(Address, macTypeID);
    }

    @Transactional
    public List<MacAddress> getByOuiAddress(String oui, long macTypeID){
        return this.mMapper.getByOuiAddress(oui, macTypeID);
    }

    @Transactional
    public String getLastInsertNameIdBySortTimestamp(String name, long macTypeID){
        return this.mMapper.getLastInsertNameIdBySortTimestamp(name, macTypeID);
    }

    @Transactional
    public int getMacAddressMatchRangeCount(long startAddress, long endAddress, long macTypeID){
        return this.mMapper.getMacAddressMatchRangeCount(startAddress, endAddress, macTypeID);
    }

    @Transactional
    public int getMacAddressMatchRangeCountByOdd(long startAddress, long endAddress, long macTypeID){
        return this.mMapper.getMacAddressMatchRangeCountByOdd(startAddress, endAddress, macTypeID);
    }

    @Transactional
    public int getMacAddressMatchRangeCountByEven(long startAddress, long endAddress, long macTypeID){
        return this.mMapper.getMacAddressMatchRangeCountByEven(startAddress, endAddress, macTypeID);
    }

    @Transactional
    public List<Long> getTotalMACStatusByMacAddress(String Address){
        return this.mMapper.getTotalMACStatusByMacAddress(Address);
    }

    @Transactional
    public MacAddress getLastBTMacAddress(){
        return this.mMapper.getLastBTMacAddress();
    }

    @Transactional
    public int getcountByAddressbinding(String address){
        return this.mMapper.getcountByAddressbinding(address);
    }

//    @Transactional
//    public void deleteByAddress(long Address){
//        this.mMapper.deleteByAddress(Address);
//    }

//    @Transactional(rollbackFor = Exception.class)
//    public void batchInsert(String eType, int increaseStep, long startMac, long EndMac, String employeeIdOwner, String macType, String name){
//        SqlSession sqlSession = mSqlSessionFactory.openSession(ExecutorType.BATCH);
//        MacAddressMapper mapper = sqlSession.getMapper(MacAddressMapper.class);
//        User owner = new User();
//        owner.setEmployeeID(employeeIdOwner);
//        MacType type = new MacType();
//        type.setName(macType);
//        MacStatus initStatus = this.mMacStatusMapper.getByName(Constant.MacStatus_UsePrepare);
//
//        long loop = 0;
//        long group = 0;
//        for(long start = startMac; start <=  EndMac; start += increaseStep){
//            MacAddress address = new MacAddress();
//            address.setAddress(UtilFunc.MacLongToMacString(start));
//            address.setAddressDecimal(start);
//            address.setCreatedOwner(owner);
//            address.setMacType(type);
//            address.setMacStatus(initStatus);
//            address.setTestUser(null);
//            address.setSipSerialName(null);
//            address.setTestConfiguration(null);
//            switch (eType){
//                case Constant.EnumIncreateType_EvenRotateId:
//                case Constant.EnumIncreateType_OddRotateId:
//                case Constant.EnumIncreateType_NormalRotateId:
//                    address.setName(name + "_" + (loop + 1));
//                    break;
//                case Constant.EnumIncreateType_OddAndEvenRotateId:
//                    if((loop & 1) == 0){
//                        group++;
//                    }
//                    address.setName(name + "_" + (group));
//                    break;
//            }
//
//            mapper.insert(address);
//            if((loop + 1) % MAC_INSERT_BATCH_COUNT == 0){
//                sqlSession.flushStatements();
//            }
//            loop++;
//        }
//        sqlSession.flushStatements();
//    }

    @Transactional(rollbackFor = Exception.class, isolation = Isolation.REPEATABLE_READ)
    public void fastBatchInsert(String eType, int increaseStep, long startMac, long EndMac, long ownerId, long macTypeId){
        User owner = new User();
        owner.setID(ownerId);
        MacType type = new MacType();
        type.setID(macTypeId);

        MacStatus initStatus = this.mMacStatusMapper.getByName(Constant.MacStatus_UsePrepare);


      //  long idName = getLastMacNameId(name, macTypeId);
//        long loop = 0;
        List<MacAddress> addresses = new ArrayList<>();
        for(long start = startMac; start <=  EndMac; start += increaseStep){
            MacAddress address = new MacAddress();
            address.setAddress(UtilFunc.MacLongToMacString(start));
            address.setAddressDecimal(start);
            address.setCreatedOwner(owner);
            address.setMacType(type);
            address.setMacStatus(initStatus);
            address.setTestUser(null);
            address.setTestConfiguration(null);
            address.setName(null);
//            switch (eType){
//                case Constant.EnumIncreateType_EvenRotateId:
//                case Constant.EnumIncreateType_OddRotateId:
//                case Constant.EnumIncreateType_NormalRotateId:
//                    address.setName(name + "_" + (idName + 1));
//                    idName++;
//                    break;
//                case Constant.EnumIncreateType_OddAndEvenRotateId:
//                    if((loop & 1) == 0){
//                        idName++;
//                    }
//                    address.setName(name + "_" + (idName));
//                    break;
//                default:
//                    address.setName(name);
//                    break;
//            }
            addresses.add(address);
//            loop++;

            if(addresses.size() >= MAC_INSERT_BATCH_COUNT){
                this.mMapper.fastBatchInsert(addresses);
                addresses.clear();
            }
        }

        if(addresses.size() > 0){
            this.mMapper.fastBatchInsert(addresses);
        }
    }

    private long getLastMacNameId(String name, long macTypeID){
        String id = this.mMapper.getLastInsertNameIdBySortTimestamp(name, macTypeID);
        if(id == null || id.length() == 0){
            return 0;
        }
        String idSplit[] = id.split("_");
        return idSplit.length == 2 ? Long.parseLong(idSplit[1]) : 0;
    }

    @Transactional(rollbackFor = Exception.class)
    public List<MacAddress> get(MacQueryInput input){
        if (input == null){
            return this.mMapper.getAll();
        }
        return this.mMapper.get(input);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateData(MacUpgradeInput input, long id, long testUserId, long testLotCodeId){
        this.mMapper.updateData(input, id, testUserId, testLotCodeId);
    }

    @Transactional(rollbackFor = Exception.class)
    public List<MacAddress> getDispatchMacAddress(long startAddress, long macStatusID, long macTypeID, int totalCount, int isEvenType, int isOddType){
        return this.mMapper.getDispatchMacAddress(startAddress, macStatusID, macTypeID, totalCount, isEvenType, isOddType);
    }

    @Transactional(rollbackFor = Exception.class)
    public String getMacAddressStatus(String address){
        return this.mMapper.getMacAddressStatus(address);
    }

//    @Transactional(rollbackFor = Exception.class)
    public List<String> _checkMacAddressFormat(String mac){
        if(mac.length() != 17){
            return null;
        }

        List<String> result = new ArrayList<String>();

        String split[] = mac.split("-");
        if(split.length != 6){
            return null;
        }

        for(int i = 0; i < 6 ; i++){
            long chkNum = Long.parseLong(split[i].trim(), 16);
            if(chkNum < 0 || chkNum > 0xff){
                return null;
            }else{
                result.add(split[i].trim());
            }
        }
        return result;
    }

//    @Transactional(rollbackFor = Exception.class)
    public boolean _checkMacTypeInput(String macTypeInput){
        boolean isExist = false;
        for(EnumMacType item: EnumMacType.values()){
            if(macTypeInput.toLowerCase().equals(item.toString().toLowerCase())){
                isExist =  true;
                break;
            }
        }
        return isExist;
    }

//    @Transactional(rollbackFor = Exception.class)
    public boolean _checkMacIncreaseTypeInput(String macIncreaseTypeInput){
        boolean isExist = false;

        for(EnumIncreaseType item: EnumIncreaseType.values()){
            if(macIncreaseTypeInput.toLowerCase().equals(item.toString().toLowerCase())){
                isExist =  true;
                break;
            }
        }
        return isExist;
    }

}
