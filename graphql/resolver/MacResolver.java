package com.asecl.simdc.org.simdc_project.graphql.resolver;


import com.asecl.simdc.org.simdc_project.db.entity.*;
import com.asecl.simdc.org.simdc_project.db.service.*;
import com.asecl.simdc.org.simdc_project.exception.QLException;
import com.asecl.simdc.org.simdc_project.file.xml.Jaxb;
import com.asecl.simdc.org.simdc_project.file.xml.JaxbList;
import com.asecl.simdc.org.simdc_project.graphql.entity.input.*;
import com.asecl.simdc.org.simdc_project.graphql.entity.out.ResponseModel;
import com.asecl.simdc.org.simdc_project.graphql.entity.customEnum.EnumIncreaseType;
import com.asecl.simdc.org.simdc_project.graphql.entity.customEnum.EnumMacType;
import com.asecl.simdc.org.simdc_project.util.Constant;
import com.asecl.simdc.org.simdc_project.util.UtilFunc;
import com.github.pagehelper.PageHelper;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class MacResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    @Value("${mac.insert.max-size}")
    private long MAC_INSERT_MAX;

    @Value("${product.mac.file.path}")
    private String mMacFilePath;

    @Autowired
    private MacAddressService mMacAddressService;

    @Autowired
    private UserService mUserService;

    @Autowired
    private MacTypeService mMacTypeService;

    @Autowired
    private MacDispatchTypeService mMacDispatchType;

    @Autowired
    private TestConfigurationService mTestConfigurationService;

    @Autowired
    private MacStatusService mMacStatusService;

    private JaxbService mJaxbService;

    @Value("${mac.lock.time-out-sec}")
    private int TRY_LOCK_TIMEOUT;

    public ResponseModel CreateMacXLM(MacXMLCreate model ){
        try{
            //            Jaxb測試用接口 2020/9/15

            Integer i=1;
            List<String> macSplits12 = null;
            macSplits12 = mMacAddressService._checkMacAddressFormat(model.getAddress_start());
            List<String> macSplits_end = null;
            macSplits_end = mMacAddressService._checkMacAddressFormat(model.getAddress_end());
            long startMacLong12 = UtilFunc.MacStringToMacLong(macSplits12);
            long startMacLong_end = UtilFunc.MacStringToMacLong(macSplits_end);
            JaxbList list_odd = new JaxbList();
            list_odd.setEmployees(new ArrayList<Jaxb>());
            JaxbList list_even = new JaxbList();
            list_even.setEmployees(new ArrayList<Jaxb>());

            while(startMacLong12 <= startMacLong_end)
            {
                Jaxb policy = new Jaxb();
                policy.setAddress(UtilFunc.MacLongToMacString(startMacLong12));
                startMacLong12++;


                policy.setPO(model.getPo());
                policy.setSipLicense(i.toString());
                policy.setMacType(model.getMacType());
                policy.setLotCode(model.getLotcode());
                list_odd.getJaxbList().add(policy);
//                if(i % 2 == 0)
//                {
//                    policy.setLotCode("0");
//                    list_even.getJaxbList().add(policy);
//                }
//                else
//                {
//                    policy.setLotCode("1");
//                    list_odd.getJaxbList().add(policy);
//                }
                i++;

            }


//            String path_even = "C://fw/mac_list_even.xml";
            String filePath = mMacFilePath+ File.separator + model.getPo() + "_" + model.getLotcode() + ".xml";
//            String path_odd = "C://fw/"+model.getPo()+"_"+model.getLotcode()+".xml";
            System.out.println("---將對象轉換成File類型的xml Start---");
            //String str_even = mJaxbService.convertToXml(list_even, path_even);
            String str_odd = mJaxbService.convertToXml(list_odd, filePath);
            //System.out.println(str_even);
//            System.out.println(str_odd);
            System.out.println("---將對象轉換成File類型的xml End---");

//            System.out.println("---將File類型的xml轉換成對象 Start---");
//            Jaxb policyObj = (Jaxb)mJaxbService.convertXmlFileToObject(Jaxb.class, path_odd);

//            Jaxb bbb = new Jaxb();
//            bbb.setUpdateUser("111");
//
//            //Jaxb bb = (Jaxb)policyObj;
//
//            System.out.println(policyObj.toString());
//            System.out.println(bbb.toString());
//            System.out.println("---將File類型的xml轉換成對象 End---");
        }
        catch(Exception ex){
            throw new QLException(ex);
         }

        ResponseModel r = new ResponseModel();
        r.setResult(true);
        return r;
    }

    public ResponseModel CreateMacAddress(MacCreationInput model ){
        try{
            User owner = null;
            MacType macType = null;
            if(model.getTotalCount() <= 0 || model.getTotalCount() > MAC_INSERT_MAX){
                throw new RuntimeException("Mac Total Count Only Limit > 0 &&  <= " + MAC_INSERT_MAX);
            }

            if((owner = this.mUserService.getByEmployeeID(model.getOwnerEmplayeeId()) ) == null){
                throw new RuntimeException("Not Found User By EmplayeeId : " + model.getOwnerEmplayeeId());
            }

            if(!this.mMacAddressService._checkMacIncreaseTypeInput(model.getIncreaseType())){
                throw new RuntimeException("Unknown Input Increase Type: " + model.getIncreaseType());
            }

            if(!this.mMacAddressService._checkMacTypeInput(model.getMacType())){
                throw new RuntimeException("Unknown Input Mac Type: " + model.getMacType());
            }

            if((macType = this.mMacTypeService.getByName(model.getMacType())) == null){
                throw new RuntimeException("Unknown Mac Type In DB: " + model.getMacType());
            }

            if(model.getIncreaseType().toLowerCase().equals(Constant.EnumIncreateType_OddAndEvenRotateId.toLowerCase()) ||
                    model.getIncreaseType().toLowerCase().equals(Constant.EnumIncreateType_OddAndEven.toLowerCase())){
                if((model.getTotalCount() & 1) != 0){
                    throw new RuntimeException("IncreaseType : OddAndEven , totalCount must be even !!");
                }
            }

            List<String> macSplits = null;

            if((macSplits = mMacAddressService._checkMacAddressFormat(model.getAddress())) == null){
                throw new RuntimeException("Mac Format Error !! Mac Format Must Match xx-xx-xx-xx-xx-xx");
            }

            long startMacLong = UtilFunc.MacStringToMacLong(macSplits);
            long endMacLong = 0;
            int duplicateCount = 0;
            int insertStep = 0;

            switch (model.getIncreaseType()){
                case Constant.EnumIncreateType_Even:
                case Constant.EnumIncreateType_EvenRotateId:
                    if((startMacLong & 1) != 0){
                        throw new RuntimeException("Mac Address Start Must Be Even For Even Increase Type");
                    }
                    insertStep = 2;
                    endMacLong = startMacLong + (model.getTotalCount() * 2) - 2;
                    duplicateCount = this.mMacAddressService.getMacAddressMatchRangeCountByEven(startMacLong, endMacLong, macType.getID());
                    break;
                case Constant.EnumIncreateType_Odd:
                case Constant.EnumIncreateType_OddRotateId:
                    if((startMacLong & 1) != 1){
                        throw new RuntimeException("Mac Address Start Must Be Odd For Odd Increase Type");
                    }
                    insertStep = 2;
                    endMacLong = startMacLong + (model.getTotalCount() * 2) - 2;
                    duplicateCount = this.mMacAddressService.getMacAddressMatchRangeCountByOdd(startMacLong, endMacLong, macType.getID());
                    break;
                default:
                    insertStep = 1;
                    endMacLong = startMacLong + model.getTotalCount() - 1;
                    duplicateCount = this.mMacAddressService.getMacAddressMatchRangeCount(startMacLong, endMacLong, macType.getID());
                    break;
            }

            if(duplicateCount > 0){
                throw new RuntimeException("Input Mac Is Already Exists (Duplicate Count : " + duplicateCount + ") !!");
            }

            long ouiMax = UtilFunc.GetOUIMacMaxValue(macSplits.get(0), macSplits.get(1), macSplits.get(2));
            if(endMacLong > ouiMax){
                throw new RuntimeException("Input Mac Range Is Already Over Oui Max Value!!");
            }

            this.mMacAddressService.fastBatchInsert(model.getIncreaseType(),
                    insertStep, startMacLong, endMacLong, owner.getID(),
                    macType.getID());

//            boolean isLock = false;
//            try{
//                if(Constant.MAC_OP_LOCK.tryLock(TRY_LOCK_TIMEOUT, TimeUnit.SECONDS)){
//                    isLock = true;
//                    this.mMacAddressService.fastBatchInsert(model.getIncreaseType(),
//                            insertStep, startMacLong, endMacLong, owner.getID(),
//                            macType.getID());
//                }else{
//                    throw new RuntimeException("Mac Table Is Already Used By Other People...");
//                }
//            }
//            catch (Exception e)
//            {
//                throw e;
//            }finally {
//                if(isLock){
//                    Constant.MAC_OP_LOCK.unlock();
//                }
//            }
        }catch(Exception ex){
            throw new QLException(ex);
        }

        ResponseModel r = new ResponseModel();
        r.setResult(true);
        return r;
    }

    public List<MacAddress> QueryMacAddress(MacQueryInput input){
        List<MacAddress> macs = null;
        try{
            if(input == null){
                macs = this.mMacAddressService.getAll();
            }else{
                if(input.getMacType() != null){
                    if(!this.mMacAddressService._checkMacTypeInput(input.getMacType())){
                        throw new RuntimeException("Unknown Input Mac Type: " + input.getMacType());
                    }
                }
                if(input.getPageNumber() > 0 && input.getPageSize() > 0){
                    PageHelper.startPage(input.getPageNumber(), input.getPageSize());
                }
                macs = this.mMacAddressService.get(input);
            }
        }catch(Exception ex){
            throw new QLException(ex);
        }
        return macs;
    }

    public MacAddress QueryLastBTMacAddressInMP(MacQueryInput input){
        MacAddress macs = null;
        try{
            macs = this.mMacAddressService.getLastBTMacAddress();
        }catch(Exception ex){
            throw new QLException(ex);
        }
        return macs;
    }

//    public ResponseModel UpdateMacAddress(MacUpgradeInput model){
//        try{
//            MacType macType = null;
//            MacAddress macAddress = null;
//            User testUser = null;
//            MacStatus status = null;
//            TestConfiguration testConfiguration = null;
//            if(!this._checkMacTypeInput(model.getMacType())){
//                throw new RuntimeException("Unknown Input Mac Type: " + model.getMacType());
//            }
//
//            if((macType = this.mMacTypeService.getByName(model.getMacType())) == null){
//                throw new RuntimeException("Unknown Mac Type In DB: " + model.getMacType());
//            }
//
//            if(this.mMacAddressService.getcountByAddressbinding(model.getAddress()) != 1){
//                throw new RuntimeException("Mac id is binding ， you need unbinding");
//            }
//
//            if(model.getAddress() != null){
//                List<String> macSplits = null;
//                if((macSplits = _checkMacAddressFormat(model.getAddress())) == null){
//                    throw new RuntimeException("Mac Format Error !! Mac Format Must Match xx-xx-xx-xx-xx-xx");
//                }
//
//                long macAddressDec = UtilFunc.MacStringToMacLong(macSplits);
//                if ((macAddress = this.mMacAddressService.getByAddress(macAddressDec, macType.getID())) == null) {
//                    throw new RuntimeException("Not Found Mac Address : " + model.getAddress());
//                }
//            }
//
//            if(model.getNewTesterEmplayeeId() != null){
//                if((testUser = this.mUserService.getByEmployeeID(model.getNewTesterEmplayeeId())) == null){
//                    throw new RuntimeException("Not Found Test User EmployeeID : " + model.getNewTesterEmplayeeId());
//                }
//            }
//
//            if(model.getNewTestConfigLotCode() != null){
//                if((testConfiguration = this.mTestConfigurationService.getByLotCode(model.getNewTestConfigLotCode())) == null){
//                    throw new RuntimeException("Not Found Test Configuration LotCode : " + model.getNewTestConfigLotCode());
//                }
//            }
//
//            if(model.getNewStatus() != null){
//                if((status = this.mMacStatusService.getByName(model.getNewStatus())) == null){
//                    throw new RuntimeException("Not Found Mac Status Name : " + model.getNewStatus());
//                }
//                model.setNewStatusID(status.getID());
//            }
//
//            this.mMacAddressService.updateData(model, macAddress.getID()
//                    , testUser == null ? -1 : testUser.getID(), testConfiguration == null ? -1: testConfiguration.getID());
//
//        }catch(Exception ex){
//            throw new QLException(ex);
//        }
//        ResponseModel r = new ResponseModel();
//        r.setResult(true);
//        return r;
//    }
//
//    public ResponseModel DeleteMacAddress(MacDeleteInput input){
//        try{
//            MacType macType = null;
//            MacAddress macAddress = null;
//
//            if(!this._checkMacTypeInput(input.getMacType())){
//                throw new RuntimeException("Unknown Input Mac Type: " + input.getMacType());
//            }
//
//            if(this.mMacAddressService.getcountByAddressbinding(input.getAddress()) != 1){
//                throw new RuntimeException("Mac id is binding ， you need unbinding");
//            }
//
//            if((macType = this.mMacTypeService.getByName(input.getMacType())) == null){
//                throw new RuntimeException("Unknown Mac Type In DB: " + input.getMacType());
//            }
//
//            List<String> macSplits = null;
//            if((macSplits = _checkMacAddressFormat(input.getAddress())) == null){
//                throw new RuntimeException("Mac Format Error !! Mac Format Must Match xx-xx-xx-xx-xx-xx");
//            }
//
//            long macAddressDec = UtilFunc.MacStringToMacLong(macSplits);
//            if ((macAddress = this.mMacAddressService.getByAddress(macAddressDec, macType.getID())) == null) {
//                throw new RuntimeException("Not Found Mac Address : " + input.getAddress());
//            }
//
//            this.mMacAddressService.deleteByID(macAddress.getID(), macType.getID());
//
//        }catch (Exception ex){
//            throw new QLException(ex);
//        }
//        ResponseModel r = new ResponseModel();
//        r.setResult(true);
//        return r;
//    }


//     幫移到service
//    private List<String> _checkMacAddressFormat(String mac){
//        if(mac.length() != 17){
//            return null;
//        }
//
//        List<String> result = new ArrayList<String>();
//
//        String split[] = mac.split("-");
//        if(split.length != 6){
//            return null;
//        }
//
//        for(int i = 0; i < 6 ; i++){
//            long chkNum = Long.parseLong(split[i].trim(), 16);
//            if(chkNum < 0 || chkNum > 0xff){
//                return null;
//            }else{
//                result.add(split[i].trim());
//            }
//        }
//        return result;
//    }
//
//    private boolean _checkMacTypeInput(String macTypeInput){
//        boolean isExist = false;
//        for(EnumMacType item: EnumMacType.values()){
//            if(macTypeInput.toLowerCase().equals(item.toString().toLowerCase())){
//                isExist =  true;
//                break;
//            }
//        }
//        return isExist;
//    }
//
//    private boolean _checkMacIncreaseTypeInput(String macIncreaseTypeInput){
//        boolean isExist = false;
//
//        for(EnumIncreaseType item: EnumIncreaseType.values()){
//            if(macIncreaseTypeInput.toLowerCase().equals(item.toString().toLowerCase())){
//                isExist =  true;
//                break;
//            }
//        }
//        return isExist;
//    }

    public ResponseModel UpdateDispatchMacStatus(MacDispatchUpdateStatusInput model){
        String str = "Used";
        String str1 = "UsedFail";
        try{

            if(this.mMacDispatchType.getCountByUsing(model.getLotCode(),model.getMac()) != 1)
            {
                throw new RuntimeException("Mac Status is not in testing !!!");
            }

            if(model.getStatus().equals(str) || model.getStatus().equals(str1))
            {
                this.mMacDispatchType.updateMacStatus(model.getMac(),model.getStatus(),model.getLotCode());
            }
            else
            {
                throw new RuntimeException("Your input is error !! " + model.getStatus());
            }

            if(this.mMacDispatchType.getCountByStatus_ID(model.getLotCode(),"Unused") == 0 )
            {
                if( this.mMacDispatchType.getCountByStatus_ID(model.getLotCode(),"Using") == 0 && this.mMacDispatchType.getCountByStatus_ID(model.getLotCode(),str1) > 0 )
                {
                    this.mTestConfigurationService.updateTestStatusByLotCode("TestFail",model.getLotCode());
                }

                if( this.mMacDispatchType.getCountByStatus_ID(model.getLotCode(),"Using") == 0 && this.mMacDispatchType.getCountByStatus_ID(model.getLotCode(),str1) == 0 )
                {
                    this.mTestConfigurationService.updateTestStatusByLotCode("TestFinish",model.getLotCode());
                }
            }

        }catch(Exception ex){
            throw new QLException(ex);
        }
        ResponseModel r = new ResponseModel();
        r.setResult(true);
        return r;
    }

}

