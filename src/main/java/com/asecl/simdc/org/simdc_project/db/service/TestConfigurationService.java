package com.asecl.simdc.org.simdc_project.db.service;

import com.alibaba.fastjson.JSONObject;
import com.asecl.simdc.org.simdc_project.db.entity.*;
import com.asecl.simdc.org.simdc_project.db.mapper.*;
import com.asecl.simdc.org.simdc_project.exception.QLException;
import com.asecl.simdc.org.simdc_project.graphql.entity.input.*;
import com.asecl.simdc.org.simdc_project.graphql.entity.out.ResponseCreateTestConfigurationModel;
import com.asecl.simdc.org.simdc_project.graphql.entity.out.ResponseLotCodeModel;
import com.asecl.simdc.org.simdc_project.graphql.entity.out.ResponseMacModel;
import com.asecl.simdc.org.simdc_project.graphql.entity.out.ResponseTotalModel;
import com.asecl.simdc.org.simdc_project.graphql.resolver.TestconfigurationResolver;
import com.asecl.simdc.org.simdc_project.util.Constant;
import com.asecl.simdc.org.simdc_project.util.ILockCallback;
import com.asecl.simdc.org.simdc_project.util.LockManager;
import com.asecl.simdc.org.simdc_project.util.UtilFunc;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TestConfigurationService {
    @Autowired
    private ResourceLoader mResourceLoader;

    @Autowired
    private TestConfigurationMapper mMapper;

    @Autowired
    @Lazy
    private ProductFamilyMapper mProductFamilyMapper;

    @Autowired
    @Lazy
    private ProductDeviceMapper mProductDeviceMapper;

    @Autowired
    @Lazy
    private UserMapper mUserMapper;

    @Autowired
    @Lazy
    private CustomerMapper mCustomerMapper;

    @Autowired
    @Lazy
    private SoftwareMapper mSoftwareMapper;

    @Autowired
    @Lazy
    private FirmwareMapper mFirmwareMapper;

    @Autowired
    @Lazy
    private DutDeviceMapper mDutMapper;

    @Autowired
    @Lazy
    private TestConfiguration_DutBindingMapper mDutBindingMapper;

    @Autowired
    @Lazy
    private TestConfigurationStatusMapper mTestStatusMapper;

    @Autowired
    @Lazy
    private MacAddressMapper mMacAddressMapper;

    @Autowired
    @Lazy
    private MacDispatchTypeMapper mMacDispatchTypeMapper;

    @Autowired
    @Lazy
    private MacTypeMapper mMacTypeMapper;

    @Autowired
    @Lazy
    private MacStatusMapper mMacStatusMapper;

    @Autowired
    @Lazy
    private MacAddressService mMacAddressService;

    @Autowired
    @Lazy
    private FirmwareService mFirmwareService;

    @Autowired
    @Lazy
    private MacTypeService mMacTypeService;

    @Autowired
    @Lazy
    private TestResultStatusService mTestResultService;

    @Autowired
    @Lazy
    private MacAddress_ResultBindingMapper mMacAddress_ResultBindingMapper;

    @Autowired
    @Lazy
    private MacStatusService mMacStatusService;

    @Autowired
    @Lazy
    private TestConfiguration_SW_FW_BindingMapper mSwFwBindingMapper;

    @Value("${mac.update.sql_batch_count}")
    private long MAC_UPDATE_BATCH_COUNT;

    @Autowired
    @Lazy
    private TestResultStatusMapper mTestResultStatusMapper;

    @Autowired
    @Lazy
    private TrayTypeMapper mTrayTypeMapper;

    @Autowired
    @Lazy
    private TrayMapper mTrayMapper;

    @Autowired
    @Lazy
    private TrayPositionMapper mTrayPositionMapper;

    @Autowired
    @Lazy
    private TestConfiguration_SW_FW_BindingMapper mTestConfiguration_SW_FW_BindingMapper;

    @Autowired
    @Lazy
    private TestConfiguration_SW_FW_Binding_LogMapper mTestConfiguration_SW_FW_Binding_LogMapper;

    @Autowired
    private LockManager mLock;

    //------------
    @Autowired
    @Lazy
    private Product_TestConfigurationMapper mProductTestCfg;

    @Value("${log.upload.filePath}")
    private String mLogPath;

    @Transactional
    public void insert(TestConfiguration type){
        this.mMapper.insert(type);
    }

    @Transactional
    public int getCountBylotCode(String LotCode){
        return this.mMapper.getCountByLotCode(LotCode);
    }

    @Transactional
    public List<TestConfiguration> getAll(){
        return this.mMapper.getAll();
    }

    @Transactional
    public int getTotalCount(){
        return this.mMapper.getTotalCount();
    }

    @Transactional(rollbackFor = Exception.class)
    public List<TestConfiguration> get(TestConfigurationQueryInput input){
        if (input == null){
            return this.mMapper.getAll();
        }
        return this.mMapper.get(input);
    }

    @Transactional
    public int getCountByTFCBinding(String Device){
        return this.mMapper.getCountByTFCBinding(Device);
    }

    @Transactional
    public int getCountByTFCStatus(String Device){
        return this.mMapper.getCountByTFCStatus(Device);
    }

    @Transactional
    public void update(TestConfiguration type){
        this.mMapper.update(type);
    }

    @Transactional
    public void updateTestStatusByLotCode(String status,String Device){
        this.mMapper.updateTestStatusByLotCode(status,Device);
    }

    @Transactional
    public void deleteByID(long id){
        this.mMapper.deleteByID(id);
    }

    @Transactional
    public void updateTFCStatus(String Device){
        this.mMapper.updateTFCStatus(Device);
    }

    @Transactional
    public void deleteByLotCode(String Device){
        this.mMapper.deleteByLotCode(Device);
    }

//    @Transactional
//    public TestConfiguration getByLotCode(String Device){
//        return this.mMapper.getByLotCode(Device);
//    }


    private TestConfiguration insertNewConfigurationForTesting(String lotCode,
                                                     String testFlow,
                                                     String opId,
                                                     ProductDevice p,
                                                     Customer c,
                                                     Software sw,
                                                     Firmware fw,
                                                     User owner,
                                                     List<DutDevice> dts,
                                                     MacDispatchType dispatchType,
                                                     JSONObject logTitle,
                                                     JSONObject logUpper,
                                                     JSONObject logLower,
                                                     JSONObject extraJson,
                                                     String pid,
                                                     int tryMode){



        TestConfigurationStatus status = this.mTestStatusMapper.getByName(Constant.TestConfigStatus_Testing);
        TestConfiguration newTCfg = new TestConfiguration();
        newTCfg.setLotCode(lotCode);
        newTCfg.setCreatedOwner(owner);
        newTCfg.setCustomer(c);
        newTCfg.setProductDevice(p);
        newTCfg.setStatus(status);
        newTCfg.setMacDispatchType(dispatchType);
        newTCfg.setLogTitle(logTitle);
        newTCfg.setLogLimitUpper(logUpper);
        newTCfg.setLogLimitLower(logLower);
        newTCfg.setExtraJson(extraJson);
        newTCfg.setTrayMode(tryMode);
        newTCfg.setPID(pid);
        this.mMapper.insert(newTCfg);

        TestConfiguration_SW_FW_Binding sw_fw_binding = new TestConfiguration_SW_FW_Binding();
        sw_fw_binding.setFirmware(fw);
        sw_fw_binding.setSoftware(sw);
        sw_fw_binding.setTestConfiguration(newTCfg);
        sw_fw_binding.setIsActived(1);
        this.mSwFwBindingMapper.insert(sw_fw_binding);


        // --- save change binding log
        TestConfiguration_SW_FW_Binding_Log log = new TestConfiguration_SW_FW_Binding_Log();
        log.setOPId(opId);
        log.setTestFlow(testFlow);
        log.setTestConfiguration_SW_FW_Binding(sw_fw_binding);
        mTestConfiguration_SW_FW_Binding_LogMapper.insert(log);
        //-------------------------------------------------------------



        if(dts != null){
            for(DutDevice dut : dts){
                TestConfiguration_DutBinding newDutBinding = new TestConfiguration_DutBinding();
                newDutBinding.setDutDevice_ID(dut);
                newDutBinding.setTestConfiguration_ID(newTCfg);
                mDutBindingMapper.insert(newDutBinding);
            }
        }

        return newTCfg;
    }

    private List<MacAddress> getCreateiTestConfigurationMacs( long startMacAddress, MacStatus macStatus, MacType macType,
                                                              int totalMacCount, MacDispatchType dispatchType){

        int isEven = 0;
        int isOdd = 0;
        switch (dispatchType.getName()){
            case Constant.EnumIncreateType_Even:
            case Constant.EnumIncreateType_EvenRotateId:
                isEven = 1;
                break;
            case Constant.EnumIncreateType_Odd:
            case Constant.EnumIncreateType_OddRotateId:
                isOdd = 1;
                break;
            default:
                break;
        }

        List<MacAddress> addresses = this.mMacAddressMapper.getDispatchMacAddress(startMacAddress,
                macStatus.getID(), macType.getID(), totalMacCount, isEven, isOdd);

        if(addresses.size() != totalMacCount){
            throw new RuntimeException("Mac Dispatch Count Is Not Enough !! ");
        }
        return addresses;
    }

    private String getValueByStrObject(String value){
        return value == null ? "" : value;
    }

//    @Transactional(isolation = Isolation.REPEATABLE_READ)
//    public void CreateTestConfiguration(TestConfigurationCreationInput input){
//        LotCode pt = null;
//        Software sw = null;
//        Firmware fw = null;
//        User owner = null;
//        Customer customer = null;
//        List<DutDevice> duts = new ArrayList<>();
//        MacDispatchType dispatchType = null;
//        MacType macType = null;
//        MacStatus macStatus = null;
//        long startMacAddress = -1;
//        boolean isDoingUpdateMac = false;
//
//
//        // --- check lotcode
//        if((this.mMapper.getByLotCode(input.getDevice())) != null){
//            throw new RuntimeException("TestConfiguration By LotCode : " + input.getDevice() + " Is Already Exists !!");
//        }
//
//        // --- check owner
//        if((owner = this.mUserMapper.getByEmployeeID(input.getOwnerEmplayeeId()) ) == null){
//            throw new RuntimeException("Not Found User By EmplayeeId : " + input.getOwnerEmplayeeId());
//        }
//
//        // --- check product
//        if(input.getLotcodeName() != null && input.getProductFamilyName() != null ){
//            if((pt = this.mLotCodeMapper.getByNameStr(input.getLotcodeName(), input.getProductFamilyName())) == null){
//                throw new RuntimeException("Product By Name : " + input.getLotcodeName() + ", ProductFamilyName : " + input.getProductFamilyName() + " Is Not Exists !!");
//            }
//        }
//
//        // --- check software
//        if(input.getSwName() != null && input.getSwVersion() != null){
//            if((sw = this.mSoftwareMapper.getByNameAndVersion(input.getSwName(), input.getSwVersion())) == null){
//                throw new RuntimeException("Software By Name : " + input.getSwName() + ", Version : " + input.getSwVersion() + " Is Not Exists !!");
//            }
//        }
//
//        // --- check firmware
//        if(input.getFwName() != null && input.getFwVersion() != null){
//            if((fw = this.mFirmwareMapper.getByNameAndVersion(input.getFwName(), input.getFwVersion())) == null){
//                throw new RuntimeException("Firmware By Name : " + input.getFwName() + ", Version : " + input.getFwVersion() + " Is Not Exists !!");
//            }
//        }
//
//        // --- check customer
//        if(input.getCustomerName() != null ){
//            if((customer = this.mCustomerMapper.getByName(input.getCustomerName())) == null){
//                throw new RuntimeException("Customer By Name : " + input.getCustomerName() + " Is Not Exists !!");
//            }
//        }
//
//        // --- check duts
//        if(input.getDutNames() != null){
//            if(input.getDutNames().length > 0){
//                for(String s : input.getDutNames()){
//                    DutDevice device = null;
//                    if((device = mDutMapper.getByDUTLotCode(s)) == null){
//                        throw new RuntimeException("DutDevice By Name : " + s + " Is Not Exists !!");
//                    }
//
//                    if(this.mDutBindingMapper.checkDutDeviceStatusIsOccupy(device.getID()) > 0){
//                        throw new RuntimeException("DutDevice By Name : " + s + " Is Using For Other TestConfiguration !!");
//                    }
//                    duts.add(device);
//                }
//            }
//        }
//
//        if(input.getMacCount() > 0 &&
//                input.getMacType() != null &&
//                input.getMacDispatchType() != null &&
//                input.getMacName() != null){
//            if((macType = this.mMacTypeMapper.getByName(input.getMacType())) == null){
//                throw new RuntimeException("Unknown Mac Type In DB: " + input.getMacType());
//            }
//
//            if((macStatus = this.mMacStatusMapper.getByName(Constant.MacStatus_UsePrepare)) == null){
//                throw new RuntimeException("Unknown Mac Status In DB: " + Constant.MacStatus_UsePrepare);
//            }
//
//            if((dispatchType = this.mMacDispatchTypeMapper.getByName(input.getMacDispatchType())) == null){
//                throw new RuntimeException("Unknown Mac Dispatch Type In DB: " + input.getMacDispatchType());
//            }else{
//                if(input.getMacDispatchType().toLowerCase().equals(Constant.EnumIncreateType_OddAndEvenRotateId.toLowerCase()) ||
//                        input.getMacDispatchType().toLowerCase().equals(Constant.EnumIncreateType_OddAndEven.toLowerCase())){
//                    if((input.getMacCount() & 1) != 0){
//                        throw new RuntimeException("Mac Dispatch Type : OddAndEven , macCount must be even !!");
//                    }
//                }
//            }
//
//            List<String> macsplit = null;
//            if(input.getMacStart() != null){
//                if((macsplit = UtilFunc.CheckMacAddressFormat(input.getMacStart())) == null){
//                    throw new RuntimeException("Unknown Mac Dispatch Type In DB: " + input.getMacStart());
//                }
//                startMacAddress = UtilFunc.MacStringToMacLong(macsplit);
//            }
//
//            isDoingUpdateMac = true;
//        }
//
//        JSONObject jObject = new JSONObject();
//        jObject.put("type", getValueByStrObject(input.getMacDispatchType()));
//        jObject.put("macName", getValueByStrObject(input.getMacName()));
//        jObject.put("macType", getValueByStrObject(input.getMacType()));
//        jObject.put("macCount", input.getMacCount());
//        jObject.put("macStart", getValueByStrObject(input.getMacStart()));
//        jObject.put("macRotateStartId", input.getMacRotatIdStart());
//        jObject.put("opId", input.getOpId());
//        jObject.put("testLoadBoard", getValueByStrObject(input.getTestLoadBoard()));
//        jObject.put("testeDevice", getValueByStrObject(input.getDevice()));
//        jObject.put("testMode", getValueByStrObject(input.getTestMode()));
//        TestConfiguration newTCfg = insertNewConfiguration(input.getDevice(), pt , customer, sw, fw, owner, duts, dispatchType, jObject);
//
//        if(isDoingUpdateMac){
//            List<MacAddress> macAddresses =
//                    getCreateiTestConfigurationMacs(startMacAddress, macStatus,
//                            macType, input.getMacCount(), dispatchType);
//
//            boolean isRotateId = false;
//            boolean isEvenAndOdd = false;
//            switch (dispatchType.getName()){
//                case Constant.EnumIncreateType_NormalRotateId:
//                case Constant.EnumIncreateType_EvenRotateId:
//                case Constant.EnumIncreateType_OddRotateId:
//                    isRotateId = true;
//                    break;
//                case Constant.EnumIncreateType_OddAndEvenRotateId:
//                    isRotateId = true;
//                    isEvenAndOdd = true;
//                    break;
//                default:
//                    break;
//            }
//
//            List<MacAddress> adds = new ArrayList<>();
//            int updateLoop = 0;
//            int id = 0;
//            int startId = input.getMacRotatIdStart() > 0 ? input.getMacRotatIdStart() : 1;
//            for(MacAddress mac : macAddresses){
//                if(isRotateId){
//                    if(isEvenAndOdd){
//                        id = updateLoop / 2;
//                    }else{
//                        id = updateLoop;
//                    }
//                    mac.setName(input.getMacName() + "_" + (startId + id));
//                }else{
//                    mac.setName(input.getMacName());
//                }
//                mac.setTestConfiguration(newTCfg);
//                adds.add(mac);
//                if(adds.size() >= MAC_UPDATE_BATCH_COUNT){
//                    this.mMacAddressMapper.fastBatchUpdate(adds);
//                    adds.clear();
//                }
//                updateLoop++;
//            }
//
//            if(adds.size() > 0){
//                this.mMacAddressMapper.fastBatchUpdate(adds);
//            }
//        }
//    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public ResponseCreateTestConfigurationModel CreateTestConfiguration(TestConfigurationCreationInput input){
        long startMacAddress = -1;
        boolean isDoingUpdateMac = false;

        MacDispatchType dispatchType = null;
        MacType macType = null;
        ProductFamily productFamily = null;
        MacStatus macStatus = null;
        ProductDevice productDevice = null;
        User owner = null;
        Customer customer = null;
        Software sw = null;
        Firmware fw = null;
        List<DutDevice> duts = new ArrayList<>();
        TestConfiguration tcf = null;

        // --- check owner
        if((owner = this.mUserMapper.getByEmployeeID(input.getOwnerEmplayeeId()) ) == null){
            throw new RuntimeException("Not Found User By EmplayeeId : " + input.getOwnerEmplayeeId());
        }

        if(input.getProductFamilyName() != null){
            if((productFamily = this.mProductFamilyMapper.getByName(input.getProductFamilyName()) ) == null){
                throw new RuntimeException("productFamily : " + input.getProductFamilyName() + " Is Not Exists !!");
            }
        }else{
            throw new RuntimeException("ProductFamily parameter is empty !!");
        }

        if(input.getProductDeviceName() != null){
            if((productDevice = this.mProductDeviceMapper.getByNameStr(input.getProductDeviceName(), input.getProductFamilyName())) == null){
                throw new RuntimeException("ProductDevice : " + input.getProductDeviceName()  + ", Product Family : " + input.getProductFamilyName() + " Is Not Exists !!");
            }
        }else{
            throw new RuntimeException("ProductDevice parameter is empty !!");
        }

        if(input.getCustomerName() != null ){
            if((customer = this.mCustomerMapper.getByName(input.getCustomerName())) == null){
                throw new RuntimeException("Customer : " + input.getCustomerName() + " Is Not Exists !!");
            }
        }else{
            throw new RuntimeException("Customer parameter is empty !!");
        }

        if(input.getSwName() != null && input.getSwVersion() != null){
            if((sw = this.mSoftwareMapper.getByNameAndVersion(input.getSwName(), input.getSwVersion())) == null){
                throw new RuntimeException("SW Name : " + input.getSwName() + ", SW Version " + input.getSwVersion() + " Is Not Exists !!");
            }
        }else{
            throw new RuntimeException("Software parameter is empty !!");
        }

        if(input.getFwName() != null && input.getFwVersion() != null){
            if((fw = this.mFirmwareMapper.getByNameAndVersion(input.getFwName(), input.getFwVersion())) == null){
                throw new RuntimeException("Firmware By Name : " + input.getFwName() + ", Version : " + input.getFwVersion() + " Is Not Exists !!");
            }
        }else{
            throw new RuntimeException("Firmware parameter is empty !!");
        }

        if(input.getGroupPC() == null){
            throw new RuntimeException("GroupPC parameter is empty !!");
        }

        if((tcf = this.mMapper.getByProductDeviceIdAndLotCode( input.getLotcode())) != null){
            // OK ~~~~
            ResponseCreateTestConfigurationModel rm = new ResponseCreateTestConfigurationModel();
            // 2021.0304
            // --- check tool version --> if not match --> change active
            TestConfiguration_SW_FW_Binding binding = tcf.getFwSwBinding().get(0);
            if(!(input.getFwName().equals(binding.getFirmware().getName()) &&
                    input.getFwVersion().equals(binding.getFirmware().getVersion()) &&
                    input.getSwName().equals(binding.getSoftware().getName()) &&
                    input.getSwVersion().equals(binding.getSoftware().getVersion()) )){

                TestConfigurationUpgradeInput upInput = new TestConfigurationUpgradeInput();
                upInput.setLotcode(input.getLotcode());
                upInput.setFwName(input.getFwName());
                upInput.setFwVersion(input.getFwVersion());
                upInput.setSwName(input.getSwName());
                upInput.setSwVersion(input.getSwVersion());
                TestConfiguration_SW_FW_Binding currentActived = this.mSwFwBindingMapper.getIsActivedByLotCode(input.getLotcode());
                this.mSwFwBindingMapper.changeIsActived(currentActived.getID(),0);
                TestConfiguration_SW_FW_Binding sw_fw_binding = this.mTestConfiguration_SW_FW_BindingMapper.get(upInput);

                if(sw_fw_binding == null){
                    sw_fw_binding = new TestConfiguration_SW_FW_Binding();
                    sw_fw_binding.setFirmware(fw);
                    sw_fw_binding.setSoftware(sw);
                    sw_fw_binding.setTestConfiguration(tcf);
                    sw_fw_binding.setIsActived(1);
                    this.mSwFwBindingMapper.insert(sw_fw_binding);
                }else{
                    sw_fw_binding.setIsActived(1);
                    this.mSwFwBindingMapper.update(sw_fw_binding);
                }

                // --- save change binding log
                TestConfiguration_SW_FW_Binding_Log log = new TestConfiguration_SW_FW_Binding_Log();
                log.setOPId(input.getOpId());
                log.setTestFlow(input.getTestFlow());
                log.setTestConfiguration_SW_FW_Binding(sw_fw_binding);
                mTestConfiguration_SW_FW_Binding_LogMapper.insert(log);

                // ------
                rm.setResult(true);
                rm.setCurrentStatus(tcf.getStatus().getName());
                rm.setSwName(sw.getName());
                rm.setSwVersion(sw.getVersion());
                rm.setFwName(fw.getName());
                rm.setFwVersion(fw.getVersion());
            }else{
                rm.setResult(true);
                rm.setCurrentStatus(tcf.getStatus().getName());
                rm.setSwName(binding.getSoftware().getName());
                rm.setSwVersion(binding.getSoftware().getVersion());
                rm.setFwName(binding.getFirmware().getName());
                rm.setFwVersion(binding.getFirmware().getVersion());
            }

            return rm;
        }



        //TODO --- dutname remove
//        if(input.getDutNames() != null){
//            if(input.getDutNames().length > 0){
//                for(String s : input.getDutNames()){
//                    DutDevice device = null;
//                    if((device = mDutMapper.getByHostNameAndProductDeviceAndGroupPC(s, input.getProductDeviceName(), input.getGroupPC())) == null){
//                        throw new RuntimeException("DutDevice By Name : " + s + " Is Not Exists !!");
//                    }
//                    duts.add(device);
//                }
//            }
//        }else{
//            throw new RuntimeException("DutDevices parameter is empty !!");
//        }

        if(input.getMacCount() > 0 &&
                input.getMacType() != null &&
                input.getMacDispatchType() != null &&
                input.getMacName() != null){
            if((macType = this.mMacTypeMapper.getByName(input.getMacType())) == null){
                throw new RuntimeException("Unknown Mac Type In DB: " + input.getMacType());
            }

            if((macStatus = this.mMacStatusMapper.getByName(Constant.MacStatus_UsePrepare)) == null){
                throw new RuntimeException("Unknown Mac Status In DB: " + Constant.MacStatus_UsePrepare);
            }

            if((dispatchType = this.mMacDispatchTypeMapper.getByName(input.getMacDispatchType())) == null){
                throw new RuntimeException("Unknown Mac Dispatch Type In DB: " + input.getMacDispatchType());
            }else{
                if(input.getMacDispatchType().toLowerCase().equals(Constant.EnumIncreateType_OddAndEvenRotateId.toLowerCase()) ||
                        input.getMacDispatchType().toLowerCase().equals(Constant.EnumIncreateType_OddAndEven.toLowerCase())){
                    if((input.getMacCount() & 1) != 0){
                        throw new RuntimeException("Mac Dispatch Type : OddAndEven , macCount must be even !!");
                    }
                }
            }

            List<String> macsplit = null;
            if(input.getMacStart() != null){
                if((macsplit = UtilFunc.CheckMacAddressFormat(input.getMacStart())) == null){
                    throw new RuntimeException("Unknown Mac Dispatch Type In DB: " + input.getMacStart());
                }
                startMacAddress = UtilFunc.MacStringToMacLong(macsplit);
            }

            isDoingUpdateMac = true;
        }

        JSONObject jObject = new JSONObject();
        jObject.put("type", getValueByStrObject(input.getMacDispatchType()));
        jObject.put("dutTestMode", getValueByStrObject(input.getTestDutMode()));
        jObject.put("macName", getValueByStrObject(input.getMacName()));
        jObject.put("macType", getValueByStrObject(input.getMacType()));
        jObject.put("macCount", input.getMacCount());
        jObject.put("macStart", getValueByStrObject(input.getMacStart()));
        jObject.put("macRotateStartId", input.getMacRotatIdStart());
        jObject.put("opId", input.getOpId());
        jObject.put("testLoadBoard", getValueByStrObject(input.getTestLoadBoard()));
        jObject.put("testMode", getValueByStrObject(input.getTestMode()));
//        jObject.put("testFlow", getValueByStrObject(input.getTestFlow()));
        TestConfiguration newTCfg = insertNewConfigurationForTesting(
                input.getLotcode(),
                input.getTestFlow(),
                input.getOpId(),
                productDevice,
                customer,
                sw, fw, owner, duts, dispatchType,
                input.getLogTitle(),
                input.getLogLimitUpper(),
                input.getLogLimitLower(),
                jObject, input.getPid(), input.getTrayMode());

        //  tray mode
        if(input.getTrayMode() > 0){
            if(input.getTrayData() == null){
                throw new RuntimeException("TrayData parameter is empty !!");
            }

            if(input.getTrayHeight() < 0 || input.getTrayWidth() < 0){
                throw new RuntimeException("TrayHeight or TrayWidth parameter is empty !!");
            }
            // check count and insert to db
            input.getTrayData().forEach((key, value) ->{
                TrayType currentTrayType = null;
                List<String> array = (List<String>) value;

                int currentTypeTrayTotalCount = array.size() * input.getTrayWidth() * input.getTrayHeight();

                if(currentTypeTrayTotalCount < input.getMacCount()){
                    throw new RuntimeException("TrayType : " + key + ", current total count : "
                            + currentTypeTrayTotalCount + " are not enough mac count : " + input.getMacCount());
                }

                if((currentTrayType = this.mTrayTypeMapper.getByName(key)) == null){
                    throw new RuntimeException("Unknown trayType : " + key);
                }

                for(int i = 0; i < array.size(); i++){
                    Tray newTray = new Tray();
                    newTray.setHeight(input.getTrayHeight());
                    newTray.setWidth(input.getTrayWidth());
                    newTray.setName(array.get(i));
                    newTray.setOrder(i);
                    newTray.setTrayType(currentTrayType);
                    newTray.setTestConfiguration(newTCfg);
                    this.mTrayMapper.insert(newTray);
                }
            });
        }

        if(isDoingUpdateMac){
            List<MacAddress> macAddresses =
                    getCreateiTestConfigurationMacs(startMacAddress, macStatus,
                            macType, input.getMacCount(), dispatchType);

            boolean isRotateId = false;
            boolean isEvenAndOdd = false;
            switch (dispatchType.getName()){
                case Constant.EnumIncreateType_NormalRotateId:
                case Constant.EnumIncreateType_EvenRotateId:
                case Constant.EnumIncreateType_OddRotateId:
                    isRotateId = true;
                    break;
                case Constant.EnumIncreateType_OddAndEvenRotateId:
                    isRotateId = true;
                    isEvenAndOdd = true;
                    break;
                default:
                    break;
            }

            List<MacAddress> adds = new ArrayList<>();
            int updateLoop = 0;
            int id = 0;
            int startId = input.getMacRotatIdStart() > 0 ? input.getMacRotatIdStart() : 1;
            for(MacAddress mac : macAddresses){
                if(isRotateId){
                    if(isEvenAndOdd){
                        id = updateLoop / 2;
                    }else{
                        id = updateLoop;
                    }
                    mac.setName(input.getMacName() + "_" + (startId + id));
                }else{
                    mac.setName(input.getMacName());
                }
                mac.setTestConfiguration(newTCfg);
                adds.add(mac);
                if(adds.size() >= MAC_UPDATE_BATCH_COUNT){
                    this.mMacAddressMapper.fastBatchUpdate(adds);
                    adds.clear();
                }
                updateLoop++;
            }

            if(adds.size() > 0){
                this.mMacAddressMapper.fastBatchUpdate(adds);
            }
        }

        ResponseCreateTestConfigurationModel rm = new ResponseCreateTestConfigurationModel();
        rm.setResult(true);
        rm.setCurrentStatus(Constant.TestConfigStatus_Testing);
        rm.setSwName(input.getSwName());
        rm.setSwVersion(input.getSwVersion());
        rm.setFwName(input.getFwName());
        rm.setFwVersion(input.getFwVersion());

        return rm;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public ResponseCreateTestConfigurationModel CreateTestConfigurationByAutogen(TestConfigurationCreationInput input){
        long startMacAddress = -1;
        boolean isDoingUpdateMac = false;

        MacDispatchType dispatchType = null;
        MacType macType = null;
        ProductFamily productFamily = null;
        MacStatus macStatus = null;
        ProductDevice productDevice = null;
        User owner = null;
        Customer customer = null;
        Software sw = null;
        Firmware fw = null;
        List<DutDevice> duts = new ArrayList<>();
        TestConfiguration tfg = null;

        // --- check owner
        if((owner = this.mUserMapper.getByEmployeeID(input.getOwnerEmplayeeId()) ) == null){
            throw new RuntimeException("Not Found User By EmplayeeId : " + input.getOwnerEmplayeeId());
        }

        if(input.getProductFamilyName() != null){
            if((productFamily = this.mProductFamilyMapper.getByName(input.getProductFamilyName()) ) == null){
                productFamily = new ProductFamily();
                productFamily.setName(input.getProductFamilyName());
                productFamily.setCreatedOwner(owner);
                productFamily.setRemark("");
                this.mProductFamilyMapper.insert(productFamily);
            }
        }else{
            throw new RuntimeException("ProductFamily parameter is empty !!");
        }

        if(input.getProductDeviceName() != null){
            if((productDevice = this.mProductDeviceMapper.getByProductDeviceName(input.getProductDeviceName()))  == null){
                productDevice = new ProductDevice();
                productDevice.setName(input.getProductDeviceName());
                productDevice.setCreatedOwner(owner);
                productDevice.setProductFamily(productFamily);
                this.mProductDeviceMapper.insert(productDevice);
            }else{
                // 2021/04/20 check already exists product device's family mapping
                if(!productDevice.getProductFamily().getName().equals(input.getProductFamilyName())){
                    throw new RuntimeException(
                            "ProductDevice : " + input.getProductDeviceName() + " Is Already Exists, But It's ProductFamily Is Not Mapping With Your Input  !! Current Input Family: " +
                            input.getProductFamilyName() + " , DB : " + productDevice.getProductFamily().getName());
                }
            }
        }else{
            throw new RuntimeException("ProductDevice parameter is empty !!");
        }

        if(input.getSwName() != null && input.getSwVersion() != null){
            if((sw = this.mSoftwareMapper.getByNameAndVersion(input.getSwName(), input.getSwVersion())) == null){
                sw = new Software();
                sw.setVersion(input.getSwVersion());
                sw.setName(input.getSwName());
                sw.setCreatedOwner(owner);
                mSoftwareMapper.insert(sw);
            }
        }else{
            throw new RuntimeException("Software parameter is empty !!");
        }

        if(input.getFwName() != null && input.getFwVersion() != null){
            if((fw = this.mFirmwareMapper.getByNameAndVersion(input.getFwName(), input.getFwVersion())) == null){
                fw = new Firmware();
                fw.setPath("");
                fw.setRemark("");
                fw.setMD5("");
                fw.setCreatedOwner(owner);
                fw.setVersion(input.getFwVersion());
                fw.setName(input.getFwName());
                this.mFirmwareMapper.insert(fw);
            }
        }else{
            throw new RuntimeException("Firmware parameter is empty !!");
        }

        if(input.getGroupPC() == null){
            throw new RuntimeException("GroupPC parameter is empty !!");
        }

        if(input.getCustomerName() != null ){
            if((customer = this.mCustomerMapper.getByName(input.getCustomerName())) == null){
                customer = new Customer();
                customer.setName(input.getCustomerName());
                customer.setPhone("");
                customer.setRemark("");
                customer.setCreatedOwner(owner);
                this.mCustomerMapper.insert(customer);
            }
        }else{
            throw new RuntimeException("Customer parameter is empty !!");
        }

        if((tfg = this.mMapper.getByProductDeviceIdAndLotCode( input.getLotcode())) != null){
            ResponseCreateTestConfigurationModel rm = new ResponseCreateTestConfigurationModel();
            // check product device
            String pfamily = "";
            String pDevice = "";

            if ((pDevice = this.mMapper.getProductDeviceByLotCode(input.getLotcode())) == null){
                throw new RuntimeException("TestConfiguration LotCode : " + input.getLotcode() +" Can't Get ProductDevice !!");
            }

            if(!input.getProductDeviceName().equals(pDevice.toString())){
                throw new RuntimeException("TestConfiguration LotCode : " + input.getLotcode() +
                        " ProductDevice Is Not Match With DB !! Current : " +
                        input.getProductDeviceName() + " , DB : " + pDevice);
            }

            // check product family
            if ((pfamily = this.mMapper.getProductFamilyByLotCode(input.getLotcode())) == null){
                throw new RuntimeException("TestConfiguration LotCode : " + input.getLotcode() +" Can't Get ProductFamily !!");
            }

            if(!input.getProductFamilyName().equals(pfamily.toString())){
                throw new RuntimeException("TestConfiguration LotCode : " + input.getLotcode() +
                        " ProductFamily Is Not Match With DB !! Current : " +
                        input.getProductFamilyName() + " , DB : " + pfamily);
            }


            // 2021.0304
            // --- check tool version --> if not match --> change active
            TestConfiguration_SW_FW_Binding binding = tfg.getFwSwBinding().get(0);
            if(!(input.getFwName().equals(binding.getFirmware().getName()) &&
                    input.getFwVersion().equals(binding.getFirmware().getVersion()) &&
                    input.getSwName().equals(binding.getSoftware().getName()) &&
                    input.getSwVersion().equals(binding.getSoftware().getVersion()) )){

                TestConfigurationUpgradeInput upInput = new TestConfigurationUpgradeInput();
                upInput.setLotcode(input.getLotcode());
                upInput.setFwName(input.getFwName());
                upInput.setFwVersion(input.getFwVersion());
                upInput.setSwName(input.getSwName());
                upInput.setSwVersion(input.getSwVersion());
                TestConfiguration_SW_FW_Binding currentActived = this.mSwFwBindingMapper.getIsActivedByLotCode(input.getLotcode());
                this.mSwFwBindingMapper.changeIsActived(currentActived.getID(),0);
                TestConfiguration_SW_FW_Binding sw_fw_binding = this.mTestConfiguration_SW_FW_BindingMapper.get(upInput);

                if(sw_fw_binding == null){
                    sw_fw_binding = new TestConfiguration_SW_FW_Binding();
                    sw_fw_binding.setFirmware(fw);
                    sw_fw_binding.setSoftware(sw);
                    sw_fw_binding.setTestConfiguration(tfg);
                    sw_fw_binding.setIsActived(1);
                    this.mSwFwBindingMapper.insert(sw_fw_binding);
                }else{
                    sw_fw_binding.setIsActived(1);
                    this.mSwFwBindingMapper.update(sw_fw_binding);
                }

                // --- save change binding log
                TestConfiguration_SW_FW_Binding_Log log = new TestConfiguration_SW_FW_Binding_Log();
                log.setOPId(input.getOpId());
                log.setTestFlow(input.getTestFlow());
                log.setTestConfiguration_SW_FW_Binding(sw_fw_binding);
                mTestConfiguration_SW_FW_Binding_LogMapper.insert(log);

                // ------
                rm.setResult(true);
                rm.setCurrentStatus(tfg.getStatus().getName());
                rm.setSwName(sw.getName());
                rm.setSwVersion(sw.getVersion());
                rm.setFwName(fw.getName());
                rm.setFwVersion(fw.getVersion());
            }else{
                rm.setResult(true);
                rm.setCurrentStatus(tfg.getStatus().getName());
                rm.setSwName(binding.getSoftware().getName());
                rm.setSwVersion(binding.getSoftware().getVersion());
                rm.setFwName(binding.getFirmware().getName());
                rm.setFwVersion(binding.getFirmware().getVersion());
            }

            return rm;
        }

        // TODO dutname
//        if(input.getDutNames() != null){
//            if(input.getDutNames().length > 0){
//                for(String s : input.getDutNames()){
//                    DutDevice device = null;
//                    if((device = this.mDutMapper.getByHostNameAndProductDeviceAndGroupPC(s, input.getProductDeviceName(), input.getGroupPC())) == null){
//                        device = new DutDevice();
//                        device.setHostName(s);
//                        device.setProductDevice(input.getProductDeviceName());
//                        device.setGroupPC(input.getGroupPC());
//                        device.setRemark("");
//                        device.setCreatedOwner(owner);
//                        this.mDutMapper.insert(device);
//                    }
//                    duts.add(device);
//                }
//            }
//        }else{
//            throw new RuntimeException("DutDevices parameter is empty !!");
//        }

        if(input.getMacCount() > 0 &&
                input.getMacType() != null &&
                input.getMacDispatchType() != null &&
                input.getMacName() != null){
            if((macType = this.mMacTypeMapper.getByName(input.getMacType())) == null){
                throw new RuntimeException("Unknown Mac Type In DB: " + input.getMacType());
            }

            if((macStatus = this.mMacStatusMapper.getByName(Constant.MacStatus_UsePrepare)) == null){
                throw new RuntimeException("Unknown Mac Status In DB: " + Constant.MacStatus_UsePrepare);
            }

            if((dispatchType = this.mMacDispatchTypeMapper.getByName(input.getMacDispatchType())) == null){
                throw new RuntimeException("Unknown Mac Dispatch Type In DB: " + input.getMacDispatchType());
            }else{
                if(input.getMacDispatchType().toLowerCase().equals(Constant.EnumIncreateType_OddAndEvenRotateId.toLowerCase()) ||
                        input.getMacDispatchType().toLowerCase().equals(Constant.EnumIncreateType_OddAndEven.toLowerCase())){
                    if((input.getMacCount() & 1) != 0){
                        throw new RuntimeException("Mac Dispatch Type : OddAndEven , macCount must be even !!");
                    }
                }
            }

            List<String> macsplit = null;
            if(input.getMacStart() != null){
                if((macsplit = UtilFunc.CheckMacAddressFormat(input.getMacStart())) == null){
                    throw new RuntimeException("Unknown Mac Dispatch Type In DB: " + input.getMacStart());
                }
                startMacAddress = UtilFunc.MacStringToMacLong(macsplit);
            }

            isDoingUpdateMac = true;
        }

        JSONObject jObject = new JSONObject();
        jObject.put("dutTestMode", getValueByStrObject(input.getTestDutMode()));
        jObject.put("type", getValueByStrObject(input.getMacDispatchType()));
        jObject.put("macName", getValueByStrObject(input.getMacName()));
        jObject.put("macType", getValueByStrObject(input.getMacType()));
        jObject.put("macCount", input.getMacCount());
        jObject.put("macStart", getValueByStrObject(input.getMacStart()));
        jObject.put("macRotateStartId", input.getMacRotatIdStart());
        jObject.put("opId", input.getOpId());
        jObject.put("testLoadBoard", getValueByStrObject(input.getTestLoadBoard()));
        jObject.put("testMode", getValueByStrObject(input.getTestMode()));
//        jObject.put("testFlow", getValueByStrObject(input.getTestFlow()));
        TestConfiguration newTCfg = insertNewConfigurationForTesting(
                input.getLotcode(),
                input.getTestFlow(),
                input.getOpId(),
                productDevice,
                customer,
                sw, fw, owner, duts, dispatchType,
                input.getLogTitle(),
                input.getLogLimitUpper(),
                input.getLogLimitLower(),
                jObject, input.getPid(), input.getTrayMode());

        //  tray mode
        if(input.getTrayMode() > 0){
            if(input.getTrayData() == null){
                throw new RuntimeException("TrayData parameter is empty !!");
            }

            if(input.getTrayHeight() < 0 || input.getTrayWidth() < 0){
                throw new RuntimeException("TrayHeight or TrayWidth parameter is empty !!");
            }
            // check count and insert to db
            input.getTrayData().forEach((key, value) ->{
                TrayType currentTrayType = null;
                List<String> array = (List<String>) value;

                int currentTypeTrayTotalCount = array.size() * input.getTrayWidth() * input.getTrayHeight();

                if(currentTypeTrayTotalCount < input.getMacCount()){
                    throw new RuntimeException("TrayType : " + key + ", current total count : "
                            + currentTypeTrayTotalCount + " are not enough mac count : " + input.getMacCount());
                }

                if((currentTrayType = this.mTrayTypeMapper.getByName(key)) == null){
                    throw new RuntimeException("Unknown trayType : " + key);
                }

                for(int i = 0; i < array.size(); i++){
                    Tray newTray = new Tray();
                    newTray.setHeight(input.getTrayHeight());
                    newTray.setWidth(input.getTrayWidth());
                    newTray.setName(array.get(i));
                    newTray.setOrder(i);
                    newTray.setTrayType(currentTrayType);
                    newTray.setTestConfiguration(newTCfg);
                    this.mTrayMapper.insert(newTray);
                }
            });
        }

        if(isDoingUpdateMac){
            List<MacAddress> macAddresses =
                    getCreateiTestConfigurationMacs(startMacAddress, macStatus,
                            macType, input.getMacCount(), dispatchType);

            boolean isRotateId = false;
            boolean isEvenAndOdd = false;
            switch (dispatchType.getName()){
                case Constant.EnumIncreateType_NormalRotateId:
                case Constant.EnumIncreateType_EvenRotateId:
                case Constant.EnumIncreateType_OddRotateId:
                    isRotateId = true;
                    break;
                case Constant.EnumIncreateType_OddAndEvenRotateId:
                    isRotateId = true;
                    isEvenAndOdd = true;
                    break;
                default:
                    break;
            }

            List<MacAddress> adds = new ArrayList<>();
            int updateLoop = 0;
            int id = 0;
            int startId = input.getMacRotatIdStart() > 0 ? input.getMacRotatIdStart() : 1;
            for(MacAddress mac : macAddresses){
                if(isRotateId){
                    if(isEvenAndOdd){
                        id = updateLoop / 2;
                    }else{
                        id = updateLoop;
                    }
                    mac.setName(input.getMacName() + "_" + (startId + id));
                }else{
                    mac.setName(input.getMacName());
                }
                mac.setTestConfiguration(newTCfg);
                adds.add(mac);
                if(adds.size() >= MAC_UPDATE_BATCH_COUNT){
                    this.mMacAddressMapper.fastBatchUpdate(adds);
                    adds.clear();
                }
                updateLoop++;
            }

            if(adds.size() > 0){
                this.mMacAddressMapper.fastBatchUpdate(adds);
            }
        }

        ResponseCreateTestConfigurationModel rm = new ResponseCreateTestConfigurationModel();
        rm.setResult(true);
        rm.setCurrentStatus(Constant.TestConfigStatus_Testing);
        rm.setSwName(input.getSwName());
        rm.setSwVersion(input.getSwVersion());
        rm.setFwName(input.getFwName());
        rm.setFwVersion(input.getFwVersion());

        return rm;

    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void UpdateTestConfiguration_SW_FW_Version(TestConfigurationUpgradeInput input){

        try{
            //先預設為01978
            User owner = new User();
            owner = this.mUserMapper.getByEmployeeID("01978");

            TestConfiguration_SW_FW_Binding oriTFC = new TestConfiguration_SW_FW_Binding();
            oriTFC = this.mTestConfiguration_SW_FW_BindingMapper.getIsActivedByLotCode(input.getLotcode());

            TestConfiguration_SW_FW_Binding tfc_s_f_b = new TestConfiguration_SW_FW_Binding();
            tfc_s_f_b = this.mTestConfiguration_SW_FW_BindingMapper.get(input);
            if( tfc_s_f_b != null )
            {
                this.mTestConfiguration_SW_FW_BindingMapper.changeIsActived(oriTFC.getID(),0);
                this.mTestConfiguration_SW_FW_BindingMapper.changeIsActived(tfc_s_f_b.getID(),1);
            }
            else
            {
                Firmware fw = new Firmware();
                Software sw = new Software();
                if( this.mSoftwareMapper.getCountByNameAndVersoin(input.getSwName(),input.getSwVersion()) == 0 )
                {

                    sw.setName(input.getSwName());
                    sw.setVersion(input.getSwVersion());
                    sw.setCreatedOwner(owner);
                    this.mSoftwareMapper.insert(sw);
                }
                if( this.mFirmwareMapper.getCountByNameAndVersoin(input.getFwName(),input.getFwVersion()) == 0 )
                {

                    fw.setName(input.getFwName());
                    fw.setVersion(input.getFwVersion());
                    fw.setCreatedOwner(owner);
                    this.mFirmwareMapper.insert(fw);
                }
                this.mTestConfiguration_SW_FW_BindingMapper.changeIsActived(oriTFC.getID(),0);
                TestConfiguration tfc = new TestConfiguration();
                tfc = this.mMapper.getByLotCode(input.getLotcode());
                sw = this.mSoftwareMapper.getByNameAndVersion(input.getSwName(),input.getSwVersion());
                fw = this.mFirmwareMapper.getByNameAndVersion(input.getFwName(),input.getFwVersion());
                TestConfiguration_SW_FW_Binding inserttfc = new TestConfiguration_SW_FW_Binding();
                inserttfc.setTestConfiguration(tfc);
                inserttfc.setFirmware(fw);
                inserttfc.setSoftware(sw);
                inserttfc.setIsActived(1);
                this.mTestConfiguration_SW_FW_BindingMapper.insert(inserttfc);

            }


        }
        catch (Exception e)
        {
            throw new QLException(e);
        }

    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void UpdateTestConfiguration(TestConfigurationUpgradeInput input){

        try{
            ProductDevice pt = null;
            TestConfiguration testConfiguration = null;
            User owner = null;

            if((owner = this.mUserMapper.getByEmployeeID(input.getOwnerEmplayeeId()) ) == null){
                throw new RuntimeException("Not Found User By EmplayeeId : " + input.getOwnerEmplayeeId());
            }


            // --- check product
            if(input.getProductDeviceName() != null && input.getProductFamilyName() != null ){
                if((pt = this.mProductDeviceMapper.getByNameStr(input.getProductDeviceName(), input.getProductFamilyName())) == null){
                    throw new RuntimeException("ProductDevice By Name : " + input.getProductDeviceName() + ", ProductFamilyName : " + input.getProductFamilyName() + " Is Not Exists !!");
                }
            }

            if(pt == null || (testConfiguration = this.mMapper.getByProductDeviceIdAndLotCode( input.getLotcode())) == null){
                throw new RuntimeException("You input TestConfiguration Lotcode is nonexistent ("+input.getLotcode()+")!!");
            }

            // ------
            if(input.getSwName() != null && input.getSwName() != null &&
                    input.getFwName() != null && input.getFwVersion() != null){

                if(input.getForceUpdate() == -1){
                    if(this.mMacAddressMapper.checkMacAddressesHaveUsing(testConfiguration.getID()) > 0){
                        throw new RuntimeException("Mac addresses have testing status, wait for all of test mac finish");
                    }
                }

                Software sw = this.mSoftwareMapper.getByNameAndVersion(input.getSwName(), input.getSwVersion());
                Firmware fw = this.mFirmwareMapper.getByNameAndVersion(input.getFwName(), input.getFwVersion());

                // isActived all become 0 可能有錯
                this.mSwFwBindingMapper.changeIsActived(testConfiguration.getID(),0);

                // add testActived
                TestConfiguration_SW_FW_Binding sw_fw_binding = new TestConfiguration_SW_FW_Binding();
                sw_fw_binding.setFirmware(fw);
                sw_fw_binding.setSoftware(sw);
                sw_fw_binding.setTestConfiguration(testConfiguration);
                sw_fw_binding.setIsActived(1);
                this.mSwFwBindingMapper.insert(sw_fw_binding);
            }

            if(input.getDutNames() != null){
                if(input.getDutNames().length > 0){
                    // current duts
                    List<DutDevice> findDuts = this.mDutBindingMapper.getDutsByConfigurationId(testConfiguration.getID());
                    // add
                    for(String s: input.getDutNames()){
                        boolean isMustAdd = true;
                        for(DutDevice dut : findDuts){
                            if(dut.getHostName().equals(s)){
                                isMustAdd = false;
                                break;
                            }
                        }
                        if(isMustAdd){
                            DutDevice device = null;
                            if((device = this.mDutMapper.getByHostNameAndProductDeviceAndGroupPC(s, input.getProductDeviceName(), input.getGroupPC())) == null){
                                device = new DutDevice();
                                device.setHostName(s);
                                device.setGroupPC(input.getGroupPC());
                                device.setProductDevice(input.getProductDeviceName());
                                device.setRemark("");
                                device.setCreatedOwner(owner);
                                this.mDutMapper.insert(device);
                            }
                            TestConfiguration_DutBinding newDutBinding = new TestConfiguration_DutBinding();
                            newDutBinding.setDutDevice_ID(device);
                            newDutBinding.setTestConfiguration_ID(testConfiguration);
                            mDutBindingMapper.insert(newDutBinding);
                        }
                    }

                    // remove
                    for(DutDevice dut : findDuts){
                        boolean isExists = false;
                        for(String s: input.getDutNames()){
                            if(dut.getHostName().equals(s)){
                                isExists = true;
                                break;
                            }
                        }
                        if(!isExists){
                            mDutBindingMapper.deleteByDutId(dut.getID());
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            throw new QLException(e);
        }

    }

//    @Transactional(isolation = Isolation.REPEATABLE_READ)
//    public void UpdateTestConfigurationStatus(TestConfigurationStatusUpdateInput input){
//        TestConfiguration cfg = null;
//        TestConfigurationStatus currentStatusObj = null;
//        User forceOwner = null;
//        User startOwner = null;
//        // 1 --> current prepare , next testing
//        // 2 --> current testing , next force stop
//        int updateState = 0;
//
//        if((cfg = this.mMapper.getByLotCode(input.getDevice())) == null){
//            throw new RuntimeException("TestConfiguration By LotCode : " + input.getDevice() + " Is Not Exists !!");
//        }
//
//        if((currentStatusObj = this.mTestStatusMapper.getByName(input.getStatus())) == null){
//            throw new RuntimeException("TestConfigurationStatus By Name : " + input.getStatus() + " Is Not Exists !!");
//        }
//
//        if(cfg.getStatus().getName().equals(input.getStatus())){
//            throw new RuntimeException("TestConfigurationStatus Is Already be " + input.getStatus());
//        }
//
//        if(cfg.getStatus().getName().equals(Constant.TestConfigStatus_TestPrepare) &&(
//                input.getStatus().equals(Constant.TestConfigStatus_Testing))) {
//            if(input.getStartEmplayeeId() == null){
//                throw new RuntimeException("You want to set testing, so you must input start employeeId !!");
//            }
//
//            if((startOwner = this.mUserMapper.getByEmployeeID(input.getStartEmplayeeId()) ) == null){
//                throw new RuntimeException("Not Found User By Start EmplayeeId : " + input.getStartEmplayeeId());
//            }
//        }
//
//        if(cfg.getStatus().getName().equals(Constant.TestConfigStatus_Testing) &&(
//                input.getStatus().equals(Constant.TestConfigStatus_TestFinish) ||
//                        input.getStatus().equals(Constant.TestConfigStatus_TestFail))) {
//            if(input.getForceStopEmplayeeId() == null || input.getForceStopReason() == null){
//                throw new RuntimeException("You want to force stop this test, so you must input reason and force employeeId !!");
//            }
//
//            if((forceOwner = this.mUserMapper.getByEmployeeID(input.getForceStopEmplayeeId()) ) == null){
//                throw new RuntimeException("Not Found User By stop EmplayeeId : " + input.getForceStopEmplayeeId());
//            }
//        }
//
//        // 1 --> current prepare , next testing
//        // 2 --> current testing , next force stop
//        switch (input.getStatus()){
//            case Constant.TestConfigStatus_TestPrepare:
//                throw new RuntimeException("TestConfigurationStatus Current Status:" +
//                        cfg.getStatus().getName() + " Can't Rollback To " + Constant.TestConfigStatus_TestPrepare);
//            case Constant.TestConfigStatus_Testing:
//                if(cfg.getStatus().getName().equals(Constant.TestConfigStatus_TestFail) ||
//                        cfg.getStatus().getName().equals(Constant.TestConfigStatus_TestFinish)){
//                    throw new RuntimeException("TestConfigurationStatus Current Status:" +
//                            cfg.getStatus().getName() + " Can't Rollback To " + Constant.TestConfigStatus_Testing);
//                }else{
//                    int macBindingCount = this.mMacAddressMapper.getCountByTestConfigurationId(cfg.getID());
//                    // --- check all binding status
//                    if(cfg.getCustomer() == null ||
//                            (cfg.getDutDevices() != null && cfg.getDutDevices().size() == 0) ||
//                            cfg.getSW() == null || cfg.getFW() == null || cfg.getLotCode() == null ||
//                            macBindingCount == 0){
//                        throw new RuntimeException("This TestConfiguration Is Not Binding Ready !!");
//                    }
//                    updateState = 1;
//                }
//                break;
//            case Constant.TestConfigStatus_TestFinish:
//            case Constant.TestConfigStatus_TestFail:
//                if(cfg.getStatus().getName().equals(Constant.TestConfigStatus_TestFail) ||
//                        cfg.getStatus().getName().equals(Constant.TestConfigStatus_TestFinish)){
//                    throw new RuntimeException("This TestConfigurationStatus Is Already Finished, You Can't Change");
//                }else if(cfg.getStatus().getName().equals(Constant.TestConfigStatus_TestPrepare)){
//                    throw new RuntimeException("This TestConfigurationStatus Status Is TestPrepare Now, So Next Status Is Testing !!");
//                }else{
//                    updateState = 2;
//                }
//                break;
//        }
//
////        TestConfigurationStatus currentStatusObj = this.mTestStatusMapper.getByName(input.getStatus());
////        TestConfigurationStatus currentStatusObj = statusList.stream()
////                .filter(item -> input.getStatus().equals(item.getName()))
////                .findAny()
////                .orElse(null);
//
//        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
//        // 1 --> current prepare , next testing
//        // 2 --> current testing , next force stop
//        if(updateState == 1 && currentStatusObj.getName().equals(Constant.TestConfigStatus_Testing)){
//            this.mMapper.updateToTestingStatusById(cfg.getID(), currentStatusObj.getID(), startOwner.getID(), currentTimestamp );
//        }else if(updateState == 2 &&
//                (currentStatusObj.getName().equals(Constant.TestConfigStatus_TestFinish)||
//                        currentStatusObj.getName().equals(Constant.TestConfigStatus_TestFail))){
//            // --- unbinding unused mac testconfiguration
//            this.mMapper.updateToTestEndStatusById(cfg.getID(), currentStatusObj.getID(),
//                    forceOwner.getID(), input.getForceStopReason(), currentTimestamp);
//            MacStatus unusedStatus = this.mMacStatusMapper.getByName(Constant.MacStatus_UsePrepare);
//            this.mMacAddressMapper.unbindingTestConfigIdByStateUnused(cfg.getID(), unusedStatus.getID());
//        }else{
//            throw new RuntimeException("Unknown updateState " + currentStatusObj.getName());
//        }
//    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public ResponseMacModel DispatchMac(MacDispatchInput input){
        TestConfiguration cfg = null;
        User owner = null;
        MacStatus unusedMacStatus = null;
        MacStatus usingMacStatus = null;
        ProductDevice productDevice = null;
        DutDevice dutDevice = null;


        if((productDevice = this.mProductDeviceMapper.getByNameStr(input.getProductDevice(), input.getProductFamily())) == null){
            throw new RuntimeException("ProductDevice By Name : " + input.getProductDevice() + ", ProductFamily : " + input.getProductFamily() + " Is Not Exists !!");
        }

        if((owner = this.mUserMapper.getByEmployeeID(input.getTesterEmplayeeId()) ) == null){
            throw new RuntimeException("Not Found User By Start EmplayeeId : " + input.getTesterEmplayeeId());
        }

        // TODO dut no check
//        if((dutDevice = this.mDutMapper.getByHostNameAndProductDeviceAndGroupPC(input.getDutPCName(), input.getProductDevice(), input.getGroupPC())) == null){
//            throw new RuntimeException("DutDevice By Name : " + input.getDutPCName() + ", ProductDevice : " + input.getProductDevice() + ", GroupPC : " + input.getGroupPC() + " Is Not Exists !!");
//        }

        if((dutDevice = this.mDutMapper.getByHostNameAndProductDeviceAndGroupPC(input.getDutPCName(), input.getProductDevice(), input.getGroupPC())) == null){
            //throw new RuntimeException("DutDevice By Name : " + input.getDutPCName() + ", ProductDevice : " + input.getProductDevice() + ", GroupPC : " + input.getGroupPC() + " Is Not Exists !!");
            dutDevice = new DutDevice();
            dutDevice.setHostName(input.getDutPCName());
            dutDevice.setProductDevice(input.getProductDevice());
            dutDevice.setGroupPC(input.getGroupPC());
            dutDevice.setRemark("");
            dutDevice.setCreatedOwner(owner);
            this.mDutMapper.insert(dutDevice);
        }

        if((cfg = this.mMapper.getByProductDeviceIdAndLotCode( input.getLotCode())) == null){
            throw new RuntimeException("TestConfiguration By LotCode : " + input.getLotCode() + " And ProductDevice : " + input.getProductDevice() + " Is Not Exists !!");
        }

        if(!cfg.getStatus().getName().equals(Constant.TestConfigStatus_Testing)){
            throw new RuntimeException("TestConfiguration Current Status : " + cfg.getStatus().getName() + " Can't Dispatch Mac Address !!");
        }

        if((unusedMacStatus = this.mMacStatusMapper.getByName(Constant.MacStatus_UsePrepare)) == null){
            throw new RuntimeException("Mac Status By Name : " + Constant.MacStatus_UsePrepare + " Is Not Exists !!");
        }

        if((usingMacStatus = this.mMacStatusMapper.getByName(Constant.MacStatus_Using)) == null){
            throw new RuntimeException("Mac Status By Name : " + Constant.MacStatus_Using + " Is Not Exists !!");
        }

        // ----
        MacAddress address = this.mMacAddressMapper.getByTestConfigIdAndStatusId(cfg.getID(), unusedMacStatus.getID());
        if(address == null ){
            //---
            List<Long> allLotcodeMacs = this.mMacAddressMapper.getTotalMACStatusByLotCode(input.getLotCode());
            int totalFinishMac = 0;
            for(Long item : allLotcodeMacs){
                if (item != usingMacStatus.getID() && item != unusedMacStatus.getID()){
                    totalFinishMac++;
                }
            }

            ResponseMacModel model = new ResponseMacModel();
            model.setMac("");
            model.setResult(true);
            model.setIsNoMac(true);
            model.setIsFinish((totalFinishMac == allLotcodeMacs.size()));
            model.setName("");
            return model;
        }

        this.mMacAddressMapper.updateTestUserIdAndStatusIdByAddress(address.getAddress(), usingMacStatus.getID(), owner.getID());
        MacAddress_ResultBinding mr =new MacAddress_ResultBinding();
        mr.setMac_ID(address);
        mr.setOPId(input.getOpId());
        mr.setTestFlow(input.getTestFlow());
        mr.setDutDevice(dutDevice);
        mr.setBarcode(input.getBarcode());
        mr.setBarcode_Vendor(input.getBarcode_vendor());
        mr.setTestConfiguration_ID(cfg);
        mr.setSipSerialName(input.getSipserialname());
        this.mMacAddress_ResultBindingMapper.insert(mr);

        ResponseMacModel model = new ResponseMacModel();
        model.setMac(address.getAddress());
        model.setResult(true);
        model.setIsNoMac(false);
        model.setName(address.getName());


        return model;
    }

    // --- save log file
    public void saveLogFileToDisk(Part attachmentPart, String fileName) throws IOException{
        if(!deleteFile(fileName)){
            throw new RuntimeException("Delete Upload File SaveName : " + fileName + " Fail !!");
        }

        File fwFile = this.getLogFilePath(fileName);
        String fullPath = fwFile.getPath();
        attachmentPart.write(fullPath);
    }

    public boolean deleteFile(String fileName) throws IOException {
        int loop = 0;
        boolean isOk = false;
        do{
            File fwFile = this.getLogFilePath(fileName);
            if(fwFile.exists()){
                isOk = fwFile.delete();
            }else{
                isOk = true;
                break;
            }
            loop++;
        }while(!isOk && loop < 5);
        return isOk;
    }

    public File getLogFilePath(String fileName) throws IOException {
        File directory = mResourceLoader.getResource("file:" + mLogPath).getFile();
        if(!directory.exists()){
            directory.mkdirs();
        }
        return new File(directory, fileName);
    }

    ///////////
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public ResponseTotalModel SyncMACResultWithoutLog(SyncIput input){
        long trayId = -1;
        int trayMatrix = -1;
        int trayX = -1;
        int trayY = -1;
        String trayName = null;
        TestConfiguration cfg = null;
        ProductDevice productDevice = null;

        if(input.getUploadLog() == -1){
            if(input.getLotcode() == null){
                throw new RuntimeException("lotcode field is empty !!");
            }
            return getDetailMacCountByMacAddressOrLotCode(null, input.getLotcode(), null, -1, -1);
        }

        if(input.getAddress() == null){
            throw new RuntimeException("address field is empty !!");
        }

        if(input.getResult() == null){
            throw new RuntimeException("result field is empty !!");
        }

        if(input.getResultSummary() == null){
            throw new RuntimeException("resultSummary field is empty !!");
        }

        if(input.getMacType() == null){
            throw new RuntimeException("macType field is empty !!");
        }

        if(input.getErrorCode() == null){
            throw new RuntimeException("errorCode field is empty !!");
        }

        if((productDevice = this.mProductDeviceMapper.getByNameStr(input.getProductDevice(), input.getProductFamily())) == null){
            throw new RuntimeException("ProductDevice By Name : " + input.getProductDevice() + ", ProductFamily : " + input.getProductFamily() + " Is Not Exists !!");
        }

        if((cfg = this.mMapper.getByProductDeviceIdAndLotCode( input.getLotcode())) == null){
            throw new RuntimeException("TestConfiguration By LotCode : " + input.getLotcode() + " And ProductDevice : " + input.getProductDevice() + " Is Not Exists !!");
        }

        MacAddress macAddress = null;
        MacType macType = null;
        MacStatus status = null;
        MacUpgradeInput model = null;
        String testresult = null;
        String searchStatus = "";
        TrayType trayType = null;
        MacAddress_ResultBinding mr = null;
        if((searchStatus = this.mMacAddressMapper.getMacAddressStatus(input.getAddress())) == null )
        {
            throw new RuntimeException("Not found mac address : " + input.getAddress());
        }

        if(!searchStatus.equals("Using")){
            throw new RuntimeException("Mac Status must be Using !! Current : " + searchStatus);
        }

        List<String> macSplits = null;
        if((macSplits = mMacAddressService._checkMacAddressFormat(input.getAddress())) == null){
            throw new RuntimeException("Mac Format Error !! Mac Format Must Match xx-xx-xx-xx-xx-xx");
        }

        if((macType = this.mMacTypeMapper.getByName(input.getMacType())) == null){
            throw new RuntimeException("Unknown Mac Type In DB: " + input.getMacType());
        }

        long macAddressDec = UtilFunc.MacStringToMacLong(macSplits);
        if ((macAddress = this.mMacAddressMapper.getByAddress(macAddressDec, macType.getID())) == null) {
            throw new RuntimeException("Not Found Mac Address : " + input.getAddress());
        }


        // --- fail --> delete result binding and init mac status
//        if(input.getResult().toLowerCase().equals("fail")){
//            testresult = "Unused";
//            if((status = this.mMacStatusMapper.getByName(testresult)) == null){
//                throw new RuntimeException("Not Found Mac Status Name : " + testresult);
//            }
//
//            this.mMacAddress_ResultBindingMapper.deleteByMacId(macAddress.getID());
//            // recovery mac status to using
//        }else{
//            if(input.getResult().toLowerCase().equals("pass"))
//            {
//                testresult = "Used";
//            }
//
//            if((status = this.mMacStatusMapper.getByName(testresult)) == null){
//                throw new RuntimeException("Not Found Mac Status Name : " + testresult);
//            }
//
//            mr =new MacAddress_ResultBinding();
//            mr.setID(this.mMacAddress_ResultBindingMapper.getIdByMacId(macAddress.getID()));
//            mr.setMac_ID(macAddress);
//            TestResultStatus resultStatus = mTestResultStatusMapper.getIDByResult(input.getResult());
//            mr.setResultStatus_ID(resultStatus);
//            mr.setResultSummary(input.getResultSummary());
//            mr.setPath("");
//            mr.setSipSerialName(input.getSipserialname());
//            this.mMacAddress_ResultBindingMapper.update(mr);
//        }
        //2021/03/08 fail and pass recoded to DB.
        testresult = "Used";
        if((status = this.mMacStatusMapper.getByName(testresult)) == null)
        {
            throw new RuntimeException("Not Found Mac Status Name : " + testresult);
        }
        mr =new MacAddress_ResultBinding();
        mr.setID(this.mMacAddress_ResultBindingMapper.getIdByMacId(macAddress.getID()));
        mr.setMac_ID(macAddress);
        TestResultStatus resultStatus = mTestResultStatusMapper.getIDByResult(input.getResult());
        mr.setResultStatus_ID(resultStatus);
        mr.setResultSummary(input.getResultSummary());
        mr.setPath("");
        mr.setSipSerialName(input.getSipserialname());
        this.mMacAddress_ResultBindingMapper.update(mr);

        model = new MacUpgradeInput();
        model.setNewStatusID(status.getID());
        this.mMacAddressMapper.updateData(model,macAddress.getID(),-1,-1);
        //2021-3-5:change mac database rm SipSerialName
//        this.mMacAddressMapper.updateSipSerialNameByAddress(input.getAddress(),input.getSipserialname());

        if(cfg.getTrayMode() > 0){
            String searchTrayType = Constant.TrayType_FailStart;
            if(input.getResult().toLowerCase().equals("pass"))
            {
                searchTrayType = Constant.TrayType_Pass;
            }else{
                String errorFirst = input.getErrorCode().trim().substring(0, 1);
                searchTrayType += errorFirst;
            }

            trayType = this.mTrayTypeMapper.getByName(searchTrayType);
            List<TrayCount> trayCounts = this.mTrayPositionMapper.getLastXYByTypeIdAndCfgId(trayType.getID(), cfg.getID());
            if(trayCounts == null || trayCounts.size() == 0){
                throw new RuntimeException("Search Tray Type : " + searchTrayType + " last x y null error !!" );
            }

            for(TrayCount trayCount : trayCounts){
                if(trayCount.getLastMatrix() != null &&
                        trayCount.getLastMatrix() >= (trayCount.getOneTrayWidth() * trayCount.getOneTrayHeight() - 1)){
                    continue;
                }
                trayName = trayCount.getTrayName();
                trayId = trayCount.getTrayId();
                trayX = 0;
                trayY = 0;
                trayMatrix = 0;
                if(trayCount.getLastMatrix() != null ){
                    trayMatrix = trayCount.getLastMatrix() + 1;
                    trayY = trayMatrix / trayCount.getOneTrayWidth();
                    trayX = trayMatrix - (trayY * trayCount.getOneTrayWidth());
                }

                Tray currentTray = new Tray();
                currentTray.setID(trayId);
                TrayPosition newPosition = new TrayPosition();
                newPosition.setErrorCode(input.getErrorCode());
                newPosition.setMatrixIndex(trayMatrix);
                newPosition.setTray(currentTray);
                newPosition.setResultBinding(mr);
                newPosition.setTestConfiguration(cfg);
                newPosition.setX(trayX);
                newPosition.setY(trayY);
                this.mTrayPositionMapper.insert(newPosition);
                break;
            }

        }

        return getDetailMacCountByMacAddressOrLotCode(input.getAddress(), null, trayName, trayX, trayY);
    }


    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public ResponseTotalModel SyncMACResult(Part part, SyncIput input, DataFetchingEnvironment env) throws IOException {
        long trayId = -1;
        int trayMatrix = -1;
        int trayX = -1;
        int trayY = -1;
        String trayName = null;
        TestConfiguration cfg = null;
        ProductDevice productDevice = null;

        if(input.getUploadLog() == -1){
            if(input.getLotcode() == null){
                throw new RuntimeException("lotcode field is empty !!");
            }
            return getDetailMacCountByMacAddressOrLotCode(null, input.getLotcode(), null, -1, -1);
        }

        if(input.getAddress() == null){
            throw new RuntimeException("address field is empty !!");
        }

        if(input.getResult() == null){
            throw new RuntimeException("result field is empty !!");
        }

        if(input.getResultSummary() == null){
            throw new RuntimeException("resultSummary field is empty !!");
        }

        if(input.getMacType() == null){
            throw new RuntimeException("macType field is empty !!");
        }

        if(input.getFilename() == null){
            throw new RuntimeException("filename field is empty !!");
        }

        if(input.getErrorCode() == null){
            throw new RuntimeException("errorCode field is empty !!");
        }

        if((productDevice = this.mProductDeviceMapper.getByNameStr(input.getProductDevice(), input.getProductFamily())) == null){
            throw new RuntimeException("ProductDevice By Name : " + input.getProductDevice() + ", ProductFamily : " + input.getProductFamily() + " Is Not Exists !!");
        }

        if((cfg = this.mMapper.getByProductDeviceIdAndLotCode( input.getLotcode())) == null){
            throw new RuntimeException("TestConfiguration By LotCode : " + input.getLotcode() + " And ProductDevice : " + input.getProductDevice() + " Is Not Exists !!");
        }

        MacAddress macAddress = null;
        MacType macType = null;
        MacStatus status = null;
        MacUpgradeInput model = null;
        String testresult = null;
        String searchStatus = "";
        TrayType trayType = null;
        if((searchStatus = this.mMacAddressMapper.getMacAddressStatus(input.getAddress())) == null )
        {
            throw new RuntimeException("Not found mac address : " + input.getAddress());
        }

        if(!searchStatus.equals("Using")){
            throw new RuntimeException("Mac Status must be Using !! Current : " + searchStatus);
        }

        Part attachmentPart= env.getArgument("file");
        if(attachmentPart.getInputStream().available() == 0)
        {
            throw new RuntimeException("Upload File Size Is Zero Fail !!");
        }
        String currentNewFileName = input.getFilename();
        saveLogFileToDisk(attachmentPart, currentNewFileName);

        List<String> macSplits = null;
        if((macSplits = mMacAddressService._checkMacAddressFormat(input.getAddress())) == null){
            throw new RuntimeException("Mac Format Error !! Mac Format Must Match xx-xx-xx-xx-xx-xx");
        }

        if((macType = this.mMacTypeMapper.getByName(input.getMacType())) == null){
            throw new RuntimeException("Unknown Mac Type In DB: " + input.getMacType());
        }

        long macAddressDec = UtilFunc.MacStringToMacLong(macSplits);
        if ((macAddress = this.mMacAddressMapper.getByAddress(macAddressDec, macType.getID())) == null) {
            throw new RuntimeException("Not Found Mac Address : " + input.getAddress());
        }

        String urlPath = Constant.LOG_URL + currentNewFileName;

        MacAddress_ResultBinding mr =new MacAddress_ResultBinding();
        mr.setID(this.mMacAddress_ResultBindingMapper.getIdByMacId(macAddress.getID()));
        mr.setMac_ID(macAddress);
        TestResultStatus resultStatus = mTestResultStatusMapper.getIDByResult(input.getResult());
        mr.setResultStatus_ID(resultStatus);
        mr.setResultSummary(input.getResultSummary());
        mr.setPath(urlPath);
        mr.setSipSerialName(input.getSipserialname());
        this.mMacAddress_ResultBindingMapper.update(mr);
//        this.mMacAddress_ResultBindingMapper.insert(mr);
        if(input.getResult().toLowerCase().equals("pass"))
        {
            testresult = "Used";
        }
        if(input.getResult().toLowerCase().equals("fail"))
        {
            testresult = "UsedFail";
        }
        if((status = this.mMacStatusMapper.getByName(testresult)) == null){
            throw new RuntimeException("Not Found Mac Status Name : " + testresult);
        }
        model = new MacUpgradeInput();
        model.setNewStatusID(status.getID());
        this.mMacAddressMapper.updateData(model,macAddress.getID(),-1,-1);

        if(cfg.getTrayMode() > 0){
            String searchTrayType = Constant.TrayType_FailStart;
            if(input.getResult().toLowerCase().equals("pass"))
            {
                searchTrayType = Constant.TrayType_Pass;
            }else{
                String errorFirst = input.getErrorCode().trim().substring(0, 1);
                searchTrayType += errorFirst;
            }

            trayType = this.mTrayTypeMapper.getByName(searchTrayType);
            List<TrayCount> trayCounts = this.mTrayPositionMapper.getLastXYByTypeIdAndCfgId(trayType.getID(), cfg.getID());
            if(trayCounts == null || trayCounts.size() == 0){
                throw new RuntimeException("Search Tray Type : " + searchTrayType + " last x y null error !!" );
            }

            for(TrayCount trayCount : trayCounts){
                if(trayCount.getLastMatrix() != null &&
                        trayCount.getLastMatrix() >= (trayCount.getOneTrayWidth() * trayCount.getOneTrayHeight() - 1)){
                    continue;
                }
                trayName = trayCount.getTrayName();
                trayId = trayCount.getTrayId();
                trayX = 0;
                trayY = 0;
                trayMatrix = 0;
                if(trayCount.getLastMatrix() != null ){
                    trayMatrix = trayCount.getLastMatrix() + 1;
                    trayY = trayMatrix / trayCount.getOneTrayWidth();
                    trayX = trayMatrix - (trayY * trayCount.getOneTrayWidth());
                }

                Tray currentTray = new Tray();
                currentTray.setID(trayId);
                TrayPosition newPosition = new TrayPosition();
                newPosition.setErrorCode(input.getErrorCode());
                newPosition.setMatrixIndex(trayMatrix);
                newPosition.setTray(currentTray);
                newPosition.setResultBinding(mr);
                newPosition.setTestConfiguration(cfg);
                newPosition.setX(trayX);
                newPosition.setY(trayY);
                this.mTrayPositionMapper.insert(newPosition);
                break;
            }

        }

        return getDetailMacCountByMacAddressOrLotCode(input.getAddress(), null, trayName, trayX, trayY);
    }

    private ResponseTotalModel getDetailMacCountByMacAddressOrLotCode(String address, String lotcode, String trayName, int trayX, int trayY){
        List<MacStatus> macstatus = this.mMacStatusMapper.getAll();
        Map<Long, String> macstatusMap =
                new HashMap<Long, String>();
        for (MacStatus s: macstatus){
            macstatusMap.put(s.getID(), s.getName());
        }

        List<Long> mac = null;
        MacAddress lastMacAddress = null;

        if(address != null){
            mac = this.mMacAddressMapper.getTotalMACStatusByMacAddress(address);
        }else if(lotcode != null){
            mac = this.mMacAddressMapper.getTotalMACStatusByLotCode(lotcode);
            lastMacAddress = this.mMacAddressMapper.getLastTestMacByLotcodeId(lotcode);
        }else{
            throw new RuntimeException("getDetailMacCountByMacAddressOrLotCode input fail null");
        }

        ResponseTotalModel rm = new ResponseTotalModel();
        int pass = 0;
        int fail = 0;
        int using = 0;
        int unused = 0;
        int total = mac.size();
        for (Long r: mac)
        {
            String currentStatus = macstatusMap.get(r);
            switch (currentStatus){
                case Constant.MacStatus_Used:
                    pass++;
                    break;

                case Constant.MacStatus_UsedFail:
                    fail++;
                    break;

                case Constant.MacStatus_Using:
                    using++;
                    break;

                default:
                    unused++;
            }
        }
        rm.setFail(fail);
        rm.setTotal(total);
        rm.setPass(pass);
        rm.setUnused(unused);
        rm.setUsing(using);

        if(trayName != null){
            rm.setTrayName(trayName);
            rm.setTrayX(trayX);
            rm.setTrayY(trayY);
        }

        if(lastMacAddress != null){
            rm.setLastMac(lastMacAddress.getAddress());
            rm.setLastMacName(lastMacAddress.getName());
        }

        return rm;
    }




    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public ResponseCreateTestConfigurationModel CreateProductTestConfigurationByAutogen(TestConfigurationCreationInput input){
        long startMacAddress = -1;
        boolean isDoingUpdateMac = false;

        MacDispatchType dispatchType = null;
        MacType macType = null;
        ProductFamily productFamily = null;
        MacStatus macStatus = null;
        ProductDevice productDevice = null;
        User owner = null;
        Customer customer = null;
        Software sw = null;
        Firmware fw = null;
        List<DutDevice> duts = new ArrayList<>();
        TestConfiguration tfg = null;

        // --- check owner
        if((owner = this.mUserMapper.getByEmployeeID(input.getOwnerEmplayeeId()) ) == null){
            throw new RuntimeException("Not Found User By EmplayeeId : " + input.getOwnerEmplayeeId());
        }

        if(input.getProductFamilyName() != null){
            if((productFamily = this.mProductFamilyMapper.getByName(input.getProductFamilyName()) ) == null){
                productFamily = new ProductFamily();
                productFamily.setName(input.getProductFamilyName());
                productFamily.setCreatedOwner(owner);
                productFamily.setRemark("");

                this.mProductFamilyMapper.insert(productFamily);
            }
        }else{
            throw new RuntimeException("ProductFamily parameter is empty !!");
        }

        if(input.getProductDeviceName() != null){
            if((productDevice = this.mProductDeviceMapper.getByNameStr(input.getProductDeviceName(), input.getProductFamilyName()))  == null){
                productDevice = new ProductDevice();
                productDevice.setName(input.getProductDeviceName());
                productDevice.setCreatedOwner(owner);
                productDevice.setProductFamily(productFamily);
                this.mProductDeviceMapper.insert(productDevice);
            }
        }else{
            throw new RuntimeException("ProductDevice parameter is empty !!");
        }

        if((tfg = this.mMapper.getByProductDeviceIdAndLotCode( input.getLotcode())) != null){
            // OK ~~~~
            ResponseCreateTestConfigurationModel rm = new ResponseCreateTestConfigurationModel();
            rm.setResult(true);
            rm.setCurrentStatus(tfg.getStatus().getName());
            TestConfiguration_SW_FW_Binding binding = tfg.getFwSwBinding().get(0);
            rm.setSwName(binding.getSoftware().getName());
            rm.setSwVersion(binding.getSoftware().getVersion());
            rm.setFwName(binding.getFirmware().getName());
            rm.setFwVersion(binding.getFirmware().getVersion());
            //  rm.setSwName(tfg.getFwSwBinding().);
            return rm;
        }

        if(input.getCustomerName() != null ){
            if((customer = this.mCustomerMapper.getByName(input.getCustomerName())) == null){
                customer = new Customer();
                customer.setName(input.getCustomerName());
                customer.setPhone("");
                customer.setRemark("");
                customer.setCreatedOwner(owner);
                this.mCustomerMapper.insert(customer);
            }
        }else{
            throw new RuntimeException("Customer parameter is empty !!");
        }

        if(input.getSwName() != null && input.getSwVersion() != null){
            if((sw = this.mSoftwareMapper.getByNameAndVersion(input.getSwName(), input.getSwVersion())) == null){
                sw = new Software();
                sw.setVersion(input.getSwVersion());
                sw.setName(input.getSwName());
                sw.setCreatedOwner(owner);
                mSoftwareMapper.insert(sw);
            }
        }else{
            throw new RuntimeException("Software parameter is empty !!");
        }

        if(input.getFwName() != null && input.getFwVersion() != null){
            if((fw = this.mFirmwareMapper.getByNameAndVersion(input.getFwName(), input.getFwVersion())) == null){
                fw = new Firmware();
                fw.setPath("");
                fw.setRemark("");
                fw.setMD5("");
                fw.setCreatedOwner(owner);
                fw.setVersion(input.getFwVersion());
                fw.setName(input.getFwName());
                this.mFirmwareMapper.insert(fw);
            }
        }else{
            throw new RuntimeException("Firmware parameter is empty !!");
        }

        if(input.getGroupPC() == null){
            throw new RuntimeException("GroupPC parameter is empty !!");
        }

        // TODO dutname
//        if(input.getDutNames() != null){
//            if(input.getDutNames().length > 0){
//                for(String s : input.getDutNames()){
//                    DutDevice device = null;
//                    if((device = this.mDutMapper.getByHostNameAndProductDeviceAndGroupPC(s, input.getProductDeviceName(), input.getGroupPC())) == null){
//                        device = new DutDevice();
//                        device.setHostName(s);
//                        device.setProductDevice(input.getProductDeviceName());
//                        device.setGroupPC(input.getGroupPC());
//                        device.setRemark("");
//                        device.setCreatedOwner(owner);
//                        this.mDutMapper.insert(device);
//                    }
//                    duts.add(device);
//                }
//            }
//        }else{
//            throw new RuntimeException("DutDevices parameter is empty !!");
//        }

        if(input.getMacCount() > 0 &&
                input.getMacType() != null &&
                input.getMacDispatchType() != null &&
                input.getMacName() != null){
            if((macType = this.mMacTypeMapper.getByName(input.getMacType())) == null){
                throw new RuntimeException("Unknown Mac Type In DB: " + input.getMacType());
            }

            if((macStatus = this.mMacStatusMapper.getByName(Constant.MacStatus_UsePrepare)) == null){
                throw new RuntimeException("Unknown Mac Status In DB: " + Constant.MacStatus_UsePrepare);
            }

            if((dispatchType = this.mMacDispatchTypeMapper.getByName(input.getMacDispatchType())) == null){
                throw new RuntimeException("Unknown Mac Dispatch Type In DB: " + input.getMacDispatchType());
            }else{
                if(input.getMacDispatchType().toLowerCase().equals(Constant.EnumIncreateType_OddAndEvenRotateId.toLowerCase()) ||
                        input.getMacDispatchType().toLowerCase().equals(Constant.EnumIncreateType_OddAndEven.toLowerCase())){
                    if((input.getMacCount() & 1) != 0){
                        throw new RuntimeException("Mac Dispatch Type : OddAndEven , macCount must be even !!");
                    }
                }
            }

            List<String> macsplit = null;
            if(input.getMacStart() != null){
                if((macsplit = UtilFunc.CheckMacAddressFormat(input.getMacStart())) == null){
                    throw new RuntimeException("Unknown Mac Dispatch Type In DB: " + input.getMacStart());
                }
                startMacAddress = UtilFunc.MacStringToMacLong(macsplit);
            }

            isDoingUpdateMac = true;
        }

        JSONObject jObject = new JSONObject();
        jObject.put("type", getValueByStrObject(input.getMacDispatchType()));
        jObject.put("macName", getValueByStrObject(input.getMacName()));
        jObject.put("macType", getValueByStrObject(input.getMacType()));
        jObject.put("macCount", input.getMacCount());
        jObject.put("macStart", getValueByStrObject(input.getMacStart()));
        jObject.put("macRotateStartId", input.getMacRotatIdStart());
        jObject.put("opId", input.getOpId());
        jObject.put("testLoadBoard", getValueByStrObject(input.getTestLoadBoard()));
        jObject.put("testMode", getValueByStrObject(input.getTestMode()));
//        jObject.put("testFlow", getValueByStrObject(input.getTestFlow()));
        TestConfiguration newTCfg = insertNewConfigurationForTesting(
                input.getLotcode(),
                input.getTestFlow(),
                input.getOpId(),
                productDevice,
                customer,
                sw, fw, owner, duts, dispatchType,
                input.getLogTitle(),
                input.getLogLimitUpper(),
                input.getLogLimitLower(),
                jObject, input.getPid(), input.getTrayMode());

        //  tray mode
        if(input.getTrayMode() > 0){
            if(input.getTrayData() == null){
                throw new RuntimeException("TrayData parameter is empty !!");
            }

            if(input.getTrayHeight() < 0 || input.getTrayWidth() < 0){
                throw new RuntimeException("TrayHeight or TrayWidth parameter is empty !!");
            }
            // check count and insert to db
            input.getTrayData().forEach((key, value) ->{
                TrayType currentTrayType = null;
                List<String> array = (List<String>) value;

                int currentTypeTrayTotalCount = array.size() * input.getTrayWidth() * input.getTrayHeight();

                if(currentTypeTrayTotalCount < input.getMacCount()){
                    throw new RuntimeException("TrayType : " + key + ", current total count : "
                            + currentTypeTrayTotalCount + " are not enough mac count : " + input.getMacCount());
                }

                if((currentTrayType = this.mTrayTypeMapper.getByName(key)) == null){
                    throw new RuntimeException("Unknown trayType : " + key);
                }

                for(int i = 0; i < array.size(); i++){
                    Tray newTray = new Tray();
                    newTray.setHeight(input.getTrayHeight());
                    newTray.setWidth(input.getTrayWidth());
                    newTray.setName(array.get(i));
                    newTray.setOrder(i);
                    newTray.setTrayType(currentTrayType);
                    newTray.setTestConfiguration(newTCfg);
                    this.mTrayMapper.insert(newTray);
                }
            });
        }

        if(isDoingUpdateMac){
            List<MacAddress> macAddresses =
                    getCreateiTestConfigurationMacs(startMacAddress, macStatus,
                            macType, input.getMacCount(), dispatchType);

            boolean isRotateId = false;
            boolean isEvenAndOdd = false;
            switch (dispatchType.getName()){
                case Constant.EnumIncreateType_NormalRotateId:
                case Constant.EnumIncreateType_EvenRotateId:
                case Constant.EnumIncreateType_OddRotateId:
                    isRotateId = true;
                    break;
                case Constant.EnumIncreateType_OddAndEvenRotateId:
                    isRotateId = true;
                    isEvenAndOdd = true;
                    break;
                default:
                    break;
            }

            List<MacAddress> adds = new ArrayList<>();
            int updateLoop = 0;
            int id = 0;
            int startId = input.getMacRotatIdStart() > 0 ? input.getMacRotatIdStart() : 1;
            for(MacAddress mac : macAddresses){
                if(isRotateId){
                    if(isEvenAndOdd){
                        id = updateLoop / 2;
                    }else{
                        id = updateLoop;
                    }
                    mac.setName(input.getMacName() + "_" + (startId + id));
                }else{
                    mac.setName(input.getMacName());
                }
                mac.setTestConfiguration(newTCfg);
                adds.add(mac);
                if(adds.size() >= MAC_UPDATE_BATCH_COUNT){
                    this.mMacAddressMapper.fastBatchUpdate(adds);
                    adds.clear();
                }
                updateLoop++;
            }

            if(adds.size() > 0){
                this.mMacAddressMapper.fastBatchUpdate(adds);
            }
        }

        ResponseCreateTestConfigurationModel rm = new ResponseCreateTestConfigurationModel();
        rm.setResult(true);
        rm.setCurrentStatus(Constant.TestConfigStatus_Testing);
        rm.setSwName(input.getSwName());
        rm.setSwVersion(input.getSwVersion());
        rm.setFwName(input.getFwName());
        rm.setFwVersion(input.getFwVersion());

        return rm;

    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public ResponseLotCodeModel QueryNewLotcode() {
        ResponseLotCodeModel rm = new ResponseLotCodeModel();
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss.SSS");
        Date current = new Date();
        if(this.mMapper.getByLotCode("corr_"+sdFormat.format(current)) ==null)
        {
            rm.setLotCode("corr_"+sdFormat.format(current));
        }
        else
        {
            int r = 0;
            r = (int)(Math.random()*100)+1;
            rm.setLotCode("corr_"+sdFormat.format(current)+"_"+r);
        }
        return rm;
    }


}
