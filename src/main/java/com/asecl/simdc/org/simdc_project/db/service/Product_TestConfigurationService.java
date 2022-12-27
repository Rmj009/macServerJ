package com.asecl.simdc.org.simdc_project.db.service;

import com.alibaba.fastjson.JSONObject;
import com.asecl.simdc.org.simdc_project.db.entity.*;
import com.asecl.simdc.org.simdc_project.db.mapper.*;
import com.asecl.simdc.org.simdc_project.exception.QLException;
import com.asecl.simdc.org.simdc_project.graphql.entity.input.*;
import com.asecl.simdc.org.simdc_project.graphql.entity.out.ResponseCreateProductTestConfigurationModel;
import com.asecl.simdc.org.simdc_project.graphql.entity.out.ResponseCreateTestConfigurationModel;
import com.asecl.simdc.org.simdc_project.graphql.entity.out.ResponseMacModel;
import com.asecl.simdc.org.simdc_project.graphql.entity.out.ResponseTotalModel;
import com.asecl.simdc.org.simdc_project.util.Constant;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class Product_TestConfigurationService {
    @Autowired
    private ResourceLoader mResourceLoader;

    @Autowired
    private Product_TestConfigurationMapper mMapper;

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
    private Product_TestConfiguration_DutBindingMapper mDutBindingMapper;

    @Autowired
    @Lazy
    private TestConfigurationStatusMapper mTestStatusMapper;

    @Autowired
    @Lazy
    private Product_MacAddressMapper mProduct_MacAddressMapper;


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
    private Product_MacAddressService mMacAddressService;

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
    private Product_MacAddress_ResultBindingMapper mProduct_MacAddress_ResultBindingMapper;

    @Autowired
    @Lazy
    private MacStatusService mMacStatusService;

    @Autowired
    @Lazy
    private Product_TestConfiguration_SW_FW_BindingMapper mSwFwBindingMapper;

    @Autowired
    @Lazy
    private Product_TestConfiguration_SW_FW_Binding_LogMapper mSwFwBindingLogMapper;

    @Value("${mac.update.sql_batch_count}")
    private long MAC_UPDATE_BATCH_COUNT;

    @Autowired
    @Lazy
    private TrayTypeMapper mTrayTypeMapper;

    @Autowired
    @Lazy
    private Product_TrayMapper mTrayMapper;

    @Autowired
    @Lazy
    private TestResultStatusMapper mTestResultStatusMapper;

    @Autowired
    @Lazy
    private Product_TrayPositionMapper mProduct_TrayPositionMapper;

    @Autowired
    @Lazy
    private Product_TestConfiguration_SW_FW_BindingMapper mProduct_TestConfiguration_SW_FW_BindingMapper;

    //------------

    @Value("${log.upload.filePath}")
    private String mLogPath;

    @Transactional
    public void insert(Product_TestConfiguration type){
        this.mMapper.insert(type);
    }

    @Transactional
    public int getCountBylotCode(String LotCode){
        return this.mMapper.getCountByLotCode(LotCode);
    }

    @Transactional
    public List<Product_TestConfiguration> getAll(){
        return this.mMapper.getAll();
    }

    @Transactional
    public int getTotalCount(){
        return this.mMapper.getTotalCount();
    }

//    @Transactional(rollbackFor = Exception.class)
//    public List<Product_TestConfiguration> get(TestConfigurationQueryInput input){
//        if (input == null){
//            return this.mMapper.getAll();
//        }
//        return this.mMapper.get(input);
//    }

    @Transactional
    public int getCountByTFCBinding(String Device){
        return this.mMapper.getCountByTFCBinding(Device);
    }

    @Transactional
    public int getCountByTFCStatus(String Device){
        return this.mMapper.getCountByTFCStatus(Device);
    }

    @Transactional
    public void update(Product_TestConfiguration type){
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



    private String getValueByStrObject(String value){
        return value == null ? "" : value;
    }

    @Transactional(rollbackFor = Exception.class)
    public List<Product_TestConfiguration> get(Product_TestConfigurationQueryInput input){
        if (input == null){
            return this.mMapper.getAll();
        }
        return this.mMapper.get(input);
    }


    private Product_TestConfiguration insertNewProductConfigurationForTesting(String lotCode,
                                                               String po,
                                                               String opId,
                                                               String testFlow,
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
        Product_TestConfiguration newTCfg = new Product_TestConfiguration();
        newTCfg.setLotCode(lotCode);
        newTCfg.setPO(po);
//        newTCfg.setCreatedOwner(owner);
        newTCfg.setCustomer(c);
        newTCfg.setProductDevice(p);
        newTCfg.setStatus(status);
//        newTCfg.setMacDispatchType(dispatchType);
        newTCfg.setLogTitle(logTitle);
        newTCfg.setLogLimitUpper(logUpper);
        newTCfg.setLogLimitLower(logLower);
        newTCfg.setExtraJson(extraJson);
        newTCfg.setTrayMode(tryMode);
        newTCfg.setPID(pid);
        this.mMapper.insert(newTCfg);
        

        Product_TestConfiguration_SW_FW_Binding sw_fw_binding = new Product_TestConfiguration_SW_FW_Binding();
        sw_fw_binding.setFirmware(fw);
        sw_fw_binding.setSoftware(sw);
        sw_fw_binding.setProduct_TestConfiguration(newTCfg);
        sw_fw_binding.setIsActived(1);
        this.mSwFwBindingMapper.insert(sw_fw_binding);

        // --- save change binding log
        Product_TestConfiguration_SW_FW_Binding_Log log = new Product_TestConfiguration_SW_FW_Binding_Log();
        log.setOPId(opId);
        log.setTestFlow(testFlow);
        log.setProduct_TestConfiguration_SW_FW_Binding(sw_fw_binding);
        mSwFwBindingLogMapper.insert(log);

        // ------

        if(dts != null){
            for(DutDevice dut : dts){
                Product_TestConfiguration_DutBinding newDutBinding = new Product_TestConfiguration_DutBinding();
                newDutBinding.setDutDevice_ID(dut);
                newDutBinding.setProduct_TestConfiguration_ID(newTCfg);
                mDutBindingMapper.insert(newDutBinding);
            }
        }

        return newTCfg;
    }

    private List<Product_MacAddress> getCreateProductTestConfigurationMacs( String po, String lotCode, long statusId, int count){
        List<Product_MacAddress> addresses = this.mProduct_MacAddressMapper.getDispatchMacAddress(statusId, lotCode, po, count);
        if(addresses.size() == 0){
            throw new RuntimeException("Mac Dispatch Count Is Not Enough !! ");
        }
        return addresses;
    }


    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void UpdateProductTestConfiguration_SW_FW_Version(Product_TestConfigurationUpgradeInput input){

        try{
            //?��?設為01978
            User owner = new User();
            owner = this.mUserMapper.getByEmployeeID("01978");

            Product_TestConfiguration_SW_FW_Binding oriTFC = new Product_TestConfiguration_SW_FW_Binding();
            oriTFC = this.mProduct_TestConfiguration_SW_FW_BindingMapper.getIsActivedByLotCode(input.getLotcode());

            Product_TestConfiguration_SW_FW_Binding tfc_s_f_b = new Product_TestConfiguration_SW_FW_Binding();
            tfc_s_f_b = this.mProduct_TestConfiguration_SW_FW_BindingMapper.get(input);
            if( tfc_s_f_b != null )
            {
                this.mProduct_TestConfiguration_SW_FW_BindingMapper.changeIsActived(oriTFC.getID(),0);
                this.mProduct_TestConfiguration_SW_FW_BindingMapper.changeIsActived(tfc_s_f_b.getID(),1);
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
                this.mProduct_TestConfiguration_SW_FW_BindingMapper.changeIsActived(oriTFC.getID(),0);
                Product_TestConfiguration tfc = new Product_TestConfiguration();
                tfc = this.mMapper.getByLotCode(input.getLotcode());
                sw = this.mSoftwareMapper.getByNameAndVersion(input.getSwName(),input.getSwVersion());
                fw = this.mFirmwareMapper.getByNameAndVersion(input.getFwName(),input.getFwVersion());
                Product_TestConfiguration_SW_FW_Binding inserttfc = new Product_TestConfiguration_SW_FW_Binding();
                inserttfc.setProduct_TestConfiguration(tfc);
                inserttfc.setFirmware(fw);
                inserttfc.setSoftware(sw);
                inserttfc.setIsActived(1);
                this.mProduct_TestConfiguration_SW_FW_BindingMapper.insert(inserttfc);

            }


        }
        catch (Exception e)
        {
            throw new QLException(e);
        }

    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public ResponseCreateTestConfigurationModel CreateProductTestConfiguration(Product_TestConfigurationCreationInput input){
        long startMacAddress = -1;
        boolean isDoingUpdateMac = true;

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
        Product_TestConfiguration tcf = null;

        // --- check owner
//        if((owner = this.mUserMapper.getByEmployeeID(input.getOwnerEmplayeeId()) ) == null){
//            throw new RuntimeException("Not Found User By EmplayeeId : " + input.getOwnerEmplayeeId());
//        }

        //---- check mac address is already insert
        Product_MacAddress macAddress = this.mProduct_MacAddressMapper.getFirstMacByLotcodeAndPO(input.getLotcode(), input.getPo());
        if(macAddress != null){
            throw new RuntimeException("Product Mac Lotcode : " + input.getLotcode() + ", PO : " + input.getPo() + " haven't been inserted !!");
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

        if((tcf = this.mMapper.getByProductDeviceIdAndLotCode( input.getLotcode())) != null){
            // OK ~~~~
            ResponseCreateTestConfigurationModel rm = new ResponseCreateTestConfigurationModel();
            rm.setResult(true);
            rm.setCurrentStatus(tcf.getStatus().getName());
            Product_TestConfiguration_SW_FW_Binding binding = tcf.getFwSwBinding().get(0);
            rm.setSwName(binding.getSoftware().getName());
            rm.setSwVersion(binding.getSoftware().getVersion());
            rm.setFwName(binding.getFirmware().getName());
            rm.setFwVersion(binding.getFirmware().getVersion());
            //  rm.setSwName(tfg.getFwSwBinding().);
            return rm;
        }



        //-----

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
        Product_TestConfiguration newTCfg = insertNewProductConfigurationForTesting(
                input.getLotcode(),
                input.getPo(),
                input.getOpId(),
                input.getTestFlow(),
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
                    Product_Tray newTray = new Product_Tray();
                    newTray.setHeight(input.getTrayHeight());
                    newTray.setWidth(input.getTrayWidth());
                    newTray.setName(array.get(i));
                    newTray.setTOrder(i);
                    newTray.setTrayType(currentTrayType);
                    newTray.setProduct_TestConfiguration(newTCfg);
                    this.mTrayMapper.insert(newTray);
                }
            });
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

        if((macStatus = this.mMacStatusMapper.getByName(Constant.MacStatus_UsePrepare)) == null){
            throw new RuntimeException("Unknown Mac Status In DB: " + Constant.MacStatus_UsePrepare);
        }

        List<Product_MacAddress> macAddresses =
                getCreateProductTestConfigurationMacs(input.getPo(), input.getLotcode(), macStatus.getID(), input.getMacCount() );

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

        List<Product_MacAddress> adds = new ArrayList<>();
        int updateLoop = 0;
        int id = 0;
        int startId = input.getMacRotatIdStart() > 0 ? input.getMacRotatIdStart() : 1;
        for(Product_MacAddress mac : macAddresses){
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
            mac.setProduct_TestConfiguration(newTCfg);
            adds.add(mac);
            if(adds.size() >= MAC_UPDATE_BATCH_COUNT){
                this.mProduct_MacAddressMapper.fastBatchUpdate(adds);
                adds.clear();
            }
            updateLoop++;
        }

        if(adds.size() > 0){
            this.mProduct_MacAddressMapper.fastBatchUpdate(adds);
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
    public ResponseCreateTestConfigurationModel CreateProductTestConfigurationByAutogen(Product_TestConfigurationCreationInput input){
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
        Product_TestConfiguration tcf = null;

        // --- check owner
//        if((owner = this.mUserMapper.getByEmployeeID(input.getOwnerEmplayeeId()) ) == null){
//            throw new RuntimeException("Not Found User By EmplayeeId : " + input.getOwnerEmplayeeId());
//        }
        //---- check mac address is already insert
        Product_MacAddress macAddress = this.mProduct_MacAddressMapper.getFirstMacByLotcodeAndPO(input.getLotcode(), input.getPo());
        if(macAddress == null){
            throw new RuntimeException("Product Mac Lotcode : " + input.getLotcode() + ", PO : " + input.getPo() + " haven't been inserted !!");
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


        if((tcf = this.mMapper.getByProductDeviceIdAndLotCode(input.getLotcode())) != null){
            // OK ~~~~
            ResponseCreateTestConfigurationModel rm = new ResponseCreateTestConfigurationModel();
            // check product device
            String pfamily = "";
            String pDevice = "";

            if ((pDevice = this.mMapper.getProductDeviceByLotCode(input.getLotcode())) == null){
                throw new RuntimeException("Product TestConfiguration LotCode : " + input.getLotcode() +" Can't Get ProductDevice !!");
            }

            if(!input.getProductDeviceName().equals(pDevice.toString())){
                throw new RuntimeException("Product TestConfiguration LotCode : " + input.getLotcode() +
                        " ProductDevice Is Not Match With DB !! Current : " +
                        input.getProductDeviceName() + " , DB : " + pDevice);
            }

            // check product family
            if ((pfamily = this.mMapper.getProductFamilyByLotCode(input.getLotcode())) == null){
                throw new RuntimeException("Product TestConfiguration LotCode : " + input.getLotcode() +" Can't Get ProductFamily !!");
            }

            if(!input.getProductFamilyName().equals(pfamily.toString())){
                throw new RuntimeException("Product TestConfiguration LotCode : " + input.getLotcode() +
                        " ProductFamily Is Not Match With DB !! Current : " +
                        input.getProductFamilyName() + " , DB : " + pfamily);
            }

            // 2021.0304
            // --- check tool version --> if not match --> change active
            Product_TestConfiguration_SW_FW_Binding binding = tcf.getFwSwBinding().get(0);
            if(!(input.getFwName().equals(binding.getFirmware().getName()) &&
                    input.getFwVersion().equals(binding.getFirmware().getVersion()) &&
                    input.getSwName().equals(binding.getSoftware().getName()) &&
                    input.getSwVersion().equals(binding.getSoftware().getVersion()) )){

                Product_TestConfigurationUpgradeInput upInput = new Product_TestConfigurationUpgradeInput();
                upInput.setLotcode(input.getLotcode());
                upInput.setFwName(input.getFwName());
                upInput.setFwVersion(input.getFwVersion());
                upInput.setSwName(input.getSwName());
                upInput.setSwVersion(input.getSwVersion());
                Product_TestConfiguration_SW_FW_Binding currentActived = this.mSwFwBindingMapper.getIsActivedByLotCode(input.getLotcode());
                this.mSwFwBindingMapper.changeIsActived(currentActived.getID(),0);
                Product_TestConfiguration_SW_FW_Binding sw_fw_binding = this.mProduct_TestConfiguration_SW_FW_BindingMapper.get(upInput);

                if(sw_fw_binding == null){
                    sw_fw_binding = new Product_TestConfiguration_SW_FW_Binding();
                    sw_fw_binding.setFirmware(fw);
                    sw_fw_binding.setSoftware(sw);
                    sw_fw_binding.setProduct_TestConfiguration(tcf);
                    sw_fw_binding.setIsActived(1);
                    this.mSwFwBindingMapper.insert(sw_fw_binding);
                }else{
                    sw_fw_binding.setIsActived(1);
                    this.mSwFwBindingMapper.update(sw_fw_binding);
                }

                // --- save change binding log
                Product_TestConfiguration_SW_FW_Binding_Log log = new Product_TestConfiguration_SW_FW_Binding_Log();
                log.setOPId(input.getOpId());
                log.setTestFlow(input.getTestFlow());
                log.setProduct_TestConfiguration_SW_FW_Binding(sw_fw_binding);
                mSwFwBindingLogMapper.insert(log);

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
//            ResponseCreateTestConfigurationModel rm = new ResponseCreateTestConfigurationModel();
//            rm.setResult(true);
//            rm.setCurrentStatus(tcf.getStatus().getName());
//            Product_TestConfiguration_SW_FW_Binding binding = tcf.getFwSwBinding().get(0);
//            rm.setSwName(binding.getSoftware().getName());
//            rm.setSwVersion(binding.getSoftware().getVersion());
//            rm.setFwName(binding.getFirmware().getName());
//            rm.setFwVersion(binding.getFirmware().getVersion());
//            //  rm.setSwName(tfg.getFwSwBinding().);
//            return rm;
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

        Integer testCount = this.mProduct_MacAddressMapper.getDispatchMacCountByPOAndLotCode(input.getPo(), input.getLotcode());
//        if(testCount.intValue() < input.getMacCount()){
//            throw new RuntimeException("Mac Dispatch Count Is Not Enough !! ");
//        }

        if(testCount.intValue() <= 0){
            throw new RuntimeException("Mac Dispatch Count must over 0 !! ");
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
        Product_TestConfiguration newTCfg = insertNewProductConfigurationForTesting(
                input.getLotcode(),
                input.getPo(),
                input.getOpId(),
                input.getTestFlow(),
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
                    Product_Tray newTray = new Product_Tray();
                    newTray.setHeight(input.getTrayHeight());
                    newTray.setWidth(input.getTrayWidth());
                    newTray.setName(array.get(i));
                    newTray.setTOrder(i);
                    newTray.setTrayType(currentTrayType);
                    newTray.setProduct_TestConfiguration(newTCfg);
                    this.mTrayMapper.insert(newTray);
                }
            });
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

        if((macStatus = this.mMacStatusMapper.getByName(Constant.MacStatus_UsePrepare)) == null){
            throw new RuntimeException("Unknown Mac Status In DB: " + Constant.MacStatus_UsePrepare);
        }

        List<Product_MacAddress> macAddresses =
                getCreateProductTestConfigurationMacs(input.getPo(), input.getLotcode(), macStatus.getID(), input.getMacCount() );

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

        List<Product_MacAddress> adds = new ArrayList<>();
        int updateLoop = 0;
        int id = 0;
        int startId = input.getMacRotatIdStart() > 0 ? input.getMacRotatIdStart() : 1;
        for(Product_MacAddress mac : macAddresses){
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
            mac.setProduct_TestConfiguration(newTCfg);
            adds.add(mac);
            if(adds.size() >= MAC_UPDATE_BATCH_COUNT){
                this.mProduct_MacAddressMapper.fastBatchUpdate(adds);
                adds.clear();
            }
            updateLoop++;
        }

        if(adds.size() > 0){
            this.mProduct_MacAddressMapper.fastBatchUpdate(adds);
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

    private ResponseTotalModel getDetailMacCountByMacAddressOrLotCode(String address, String lotcode, String trayName, int trayX, int trayY){
        List<MacStatus> macstatus = this.mMacStatusMapper.getAll();
        Map<Long, String> macstatusMap =
                new HashMap<Long, String>();
        for (MacStatus s: macstatus){
            macstatusMap.put(s.getID(), s.getName());
        }

        List<Long> mac = null;
        Product_MacAddress lastMacAddress = null;

        if(address != null){
            mac = this.mProduct_MacAddressMapper.getTotalMACStatusByMacAddress(address);
        }else if(lotcode != null){
            mac = this.mProduct_MacAddressMapper.getTotalMACStatusByLotCode(lotcode);
            lastMacAddress = this.mProduct_MacAddressMapper.getLastTestMacByLotcodeId(lotcode);
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
    public ResponseTotalModel SyncMACResultWithoutLog(SyncIput input){
        long trayId = -1;
        int trayMatrix = -1;
        int trayX = -1;
        int trayY = -1;
        String trayName = null;
        Product_TestConfiguration cfg = null;
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

        if(input.isUploadLicense()){
            if(input.getLicense() == null){
                throw new RuntimeException("license field is empty !!");
            }
        }

        if((productDevice = this.mProductDeviceMapper.getByNameStr(input.getProductDevice(), input.getProductFamily())) == null){
            throw new RuntimeException("ProductDevice By Name : " + input.getProductDevice() + ", ProductFamily : " + input.getProductFamily() + " Is Not Exists !!");
        }

        if((cfg = this.mMapper.getByProductDeviceIdAndLotCode( input.getLotcode())) == null){
            throw new RuntimeException("TestConfiguration By LotCode : " + input.getLotcode() + " And ProductDevice : " + input.getProductDevice() + " Is Not Exists !!");
        }



        Product_MacAddress macAddress = null;
        MacType macType = null;
        MacStatus status = null;
        MacUpgradeInput model = null;
        String testresult = null;
        String searchStatus = "";
        TrayType trayType = null;
        Product_MacAddress_ResultBinding mr = null;
        if((searchStatus = this.mProduct_MacAddressMapper.getMacAddressStatus(input.getAddress())) == null )
        {
            throw new RuntimeException("Not found mac address : " + input.getAddress());
        }
//2021/3/8 remove check product mac status

        if(!searchStatus.equals("Using") && !searchStatus.equals("Used")){
            throw new RuntimeException("Mac Status must be Using or Used !! Current : " + searchStatus);
        }

        List<String> macSplits = null;
        if((macSplits = mMacAddressService._checkMacAddressFormat(input.getAddress())) == null){
            throw new RuntimeException("Mac Format Error !! Mac Format Must Match xx-xx-xx-xx-xx-xx");
        }

        if((macType = this.mMacTypeMapper.getByName(input.getMacType())) == null){
            throw new RuntimeException("Unknown Mac Type In DB: " + input.getMacType());
        }

        long macAddressDec = UtilFunc.MacStringToMacLong(macSplits);
        if ((macAddress = this.mProduct_MacAddressMapper.getByAddress(macAddressDec, macType.getID())) == null) {
            throw new RuntimeException("Not Found Mac Address : " + input.getAddress());
        }

        //---
        // --- fail --> delete result binding and init mac status
        if(input.getResult().toLowerCase().equals("fail")){
            testresult = "Unused";
            if((status = this.mMacStatusMapper.getByName(testresult)) == null){
                throw new RuntimeException("Not Found Mac Status Name : " + testresult);
            }

            if(macAddress.getProduct_TestConfiguration().getID() == cfg.getID())
            {
                this.mProduct_MacAddressMapper.updateTestUserIdAndStatusIdByAddress(macAddress.getAddress(),this.mMacStatusMapper.getByName(testresult).getID());
                this.mProduct_MacAddressMapper.updateSipSerialNameByAddress(macAddress.getAddress(),null);
                this.mProduct_MacAddress_ResultBindingMapper.deleteByMacId(macAddress.getID(),macAddress.getProduct_TestConfiguration().getID());
            }
            else
            {
                this.mProduct_MacAddress_ResultBindingMapper.deleteByMacId(macAddress.getID(),cfg.getID());
            }
            // recovery mac status to using
        }else{
            if(input.getResult().toLowerCase().equals("pass"))
            {
                testresult = "Used";
            }
            if((status = this.mMacStatusMapper.getByName(testresult)) == null){
                throw new RuntimeException("Not Found Mac Status Name : " + testresult);
            }

            mr =new Product_MacAddress_ResultBinding();
            mr.setID(this.mProduct_MacAddress_ResultBindingMapper.getIdByMacId(macAddress.getID()));
            mr.setMac_ID(macAddress);
            TestResultStatus resultStatus = mTestResultStatusMapper.getIDByResult(input.getResult());
            mr.setResultStatus_ID(resultStatus);
            mr.setResultSummary(input.getResultSummary());
            mr.setPath("");
            this.mProduct_MacAddress_ResultBindingMapper.update(mr);

            //if mac status is Using need change  and update UUID 2021/3/10
            if(macAddress.getMacStatus().getName().equals("Using"))
            {
                model = new MacUpgradeInput();
                model.setNewStatusID(status.getID());
                this.mProduct_MacAddressMapper.updateData(model,macAddress.getID(),-1,-1);
                this.mProduct_MacAddressMapper.updateSipSerialNameByAddress(input.getAddress(),input.getSipserialname());
                if(input.isUploadLicense()){
                    this.mProduct_MacAddressMapper.updateSipLicenseByAddress(input.getAddress(), input.getLicense());
                }
            }
        }



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
            List<TrayCount> trayCounts = this.mProduct_TrayPositionMapper.getLastXYByTypeIdAndCfgId(trayType.getID(), cfg.getID());
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

                Product_Tray currentTray = new Product_Tray();
                currentTray.setID(trayId);
                Product_TrayPosition newPosition = new Product_TrayPosition();
                newPosition.setErrorCode(input.getErrorCode());
                newPosition.setMatrixIndex(trayMatrix);
                newPosition.setProduct_Tray(currentTray);
                newPosition.setProduct_MacAddress_ResultBinding(mr);
                newPosition.setProduct_TestConfiguration(cfg);
                newPosition.setX(trayX);
                newPosition.setY(trayY);
                this.mProduct_TrayPositionMapper.insert(newPosition);
                break;
            }

        }

        return getDetailMacCountByMacAddressOrLotCode(input.getAddress(), null, trayName, trayX, trayY);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public ResponseMacModel DispatchProductMac(MacDispatchInput input){
        Product_TestConfiguration cfg = null;
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

        Product_TestConfiguration pt = this.mMapper.getByLotCode(input.getLotCode());

        ResponseMacModel model = new ResponseMacModel();
        Product_MacAddress havemac;

        //2021/3/8 check product_mac has been used.
        if((havemac = this.mProduct_MacAddressMapper.getMACBySipSerialName(input.getSipserialname())) == null)
        {
            // ----
            Product_MacAddress address = this.mProduct_MacAddressMapper.getByTestConfigIdAndStatusId(cfg.getID(), unusedMacStatus.getID());
            if(address == null ){
                List<Long> allLotcodeMacs = this.mProduct_MacAddressMapper.getTotalMACStatusByConfigurationID(cfg.getID());

                int totalFinishMac = 0;
                for(Long item : allLotcodeMacs){
                    if (item != usingMacStatus.getID() && item != unusedMacStatus.getID()){
                        totalFinishMac++;
                    }
                }

                ResponseMacModel model_n = new ResponseMacModel();
                model_n.setMac("");
                model_n.setResult(true);
                model_n.setIsNoMac(true);
                model_n.setIsFinish((totalFinishMac == allLotcodeMacs.size()));
                model_n.setName("");
                model_n.setSipLicense("");
                return model_n;
            }

            this.mProduct_MacAddressMapper.updateTestUserIdAndStatusIdByAddress(address.getAddress(), usingMacStatus.getID());
            Product_MacAddress_ResultBinding mr =new Product_MacAddress_ResultBinding();
            mr.setMac_ID(address);
            mr.setOPId(input.getOpId());
            mr.setTestFlow(input.getTestFlow());
            mr.setDutDevice(dutDevice);
            mr.setBarcode(input.getBarcode());
            mr.setBarcode_Vendor(input.getBarcode_vendor());
            mr.setProduct_TestConfiguration_ID(pt);
            this.mProduct_MacAddress_ResultBindingMapper.insert(mr);


            if( address.getSipLicense() == null)
            {
                model.setSipLicense("");
            }
            else
            {
                model.setSipLicense(address.getSipLicense());
            }

            model.setMac(address.getAddress());
            model.setResult(true);
            model.setIsNoMac(false);
            model.setIsFinish(false);
            model.setName(address.getName());
        }
        else
        {
            Product_MacAddress_ResultBinding mr =new Product_MacAddress_ResultBinding();
            mr.setMac_ID(havemac);
            mr.setOPId(input.getOpId());
            mr.setTestFlow(input.getTestFlow());
            mr.setDutDevice(dutDevice);
            mr.setBarcode(input.getBarcode());
            mr.setBarcode(input.getBarcode());
            mr.setProduct_TestConfiguration_ID(pt);
            this.mProduct_MacAddress_ResultBindingMapper.insert(mr);

            if(havemac.getSipLicense() == null){
                model.setSipLicense("");
            }else{
                model.setSipLicense(havemac.getSipLicense());
            }

            model.setMac(havemac.getAddress());
            model.setResult(true);
            model.setIsNoMac(false);
            model.setIsFinish(false);
            model.setName(havemac.getName());
        }



        return model;
    }


//    @Transactional(isolation = Isolation.REPEATABLE_READ)
//    public void UpdateTestConfiguration_SW_FW_Version(TestConfigurationUpgradeInput input){
//
//
//
//    }
//
//    @Transactional(isolation = Isolation.REPEATABLE_READ)
//    public void UpdateTestConfiguration(TestConfigurationUpgradeInput input){
//
//
//
//    }




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
    public ResponseTotalModel SyncProductMACResultWithoutLog(SyncIput input){
        long trayId = -1;
        int trayMatrix = -1;
        int trayX = -1;
        int trayY = -1;
        String trayName = null;
        Product_TestConfiguration cfg = null;
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

        Product_MacAddress macAddress = null;
        MacType macType = null;
        MacStatus status = null;
        MacUpgradeInput model = null;
        String testresult = null;
        String searchStatus = "";
        TrayType trayType = null;
        if((searchStatus = this.mProduct_MacAddressMapper.getMacAddressStatus(input.getAddress())) == null )
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
        if ((macAddress = this.mProduct_MacAddressMapper.getByAddress(macAddressDec, macType.getID())) == null) {
            throw new RuntimeException("Not Found Mac Address : " + input.getAddress());
        }

        Product_MacAddress_ResultBinding mr =new Product_MacAddress_ResultBinding();
        mr.setID(this.mProduct_MacAddress_ResultBindingMapper.getIdByMacId(macAddress.getID()));
        mr.setMac_ID(macAddress);
        TestResultStatus resultStatus = mTestResultStatusMapper.getIDByResult(input.getResult());
        mr.setResultStatus_ID(resultStatus);
        mr.setResultSummary(input.getResultSummary());
        mr.setPath("");
        this.mProduct_MacAddress_ResultBindingMapper.update(mr);
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
        this.mProduct_MacAddressMapper.updateData(model,macAddress.getID(),-1,-1);

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
            List<TrayCount> trayCounts = this.mProduct_TrayPositionMapper.getLastXYByTypeIdAndCfgId(trayType.getID(), cfg.getID());
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

                Product_Tray currentTray = new Product_Tray();
                currentTray.setID(trayId);
                Product_TrayPosition newPosition = new Product_TrayPosition();
                newPosition.setErrorCode(input.getErrorCode());
                newPosition.setMatrixIndex(trayMatrix);
                newPosition.setProduct_Tray(currentTray);
                newPosition.setProduct_MacAddress_ResultBinding(mr);
                newPosition.setProduct_TestConfiguration(cfg);
                newPosition.setX(trayX);
                newPosition.setY(trayY);
                this.mProduct_TrayPositionMapper.insert(newPosition);
                break;
            }

        }

        return getDetailMacCountByMacAddressOrLotCode(input.getAddress(), null, trayName, trayX, trayY);
    }


    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public ResponseTotalModel SyncProductMACResult(Part part, SyncIput input, DataFetchingEnvironment env) throws IOException {
        long trayId = -1;
        int trayMatrix = -1;
        int trayX = -1;
        int trayY = -1;
        String trayName = null;
        Product_TestConfiguration cfg = null;
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

        Product_MacAddress macAddress = null;
        MacType macType = null;
        MacStatus status = null;
        MacUpgradeInput model = null;
        String testresult = null;
        String searchStatus = "";
        TrayType trayType = null;
        if((searchStatus = this.mProduct_MacAddressMapper.getMacAddressStatus(input.getAddress())) == null )
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
        if ((macAddress = this.mProduct_MacAddressMapper.getByAddress(macAddressDec, macType.getID())) == null) {
            throw new RuntimeException("Not Found Mac Address : " + input.getAddress());
        }

        String urlPath = Constant.LOG_URL + currentNewFileName;

        Product_MacAddress_ResultBinding mr =new Product_MacAddress_ResultBinding();
        mr.setID(this.mProduct_MacAddress_ResultBindingMapper.getIdByMacId(macAddress.getID()));
        mr.setMac_ID(macAddress);
        TestResultStatus resultStatus = mTestResultStatusMapper.getIDByResult(input.getResult());
        mr.setResultStatus_ID(resultStatus);
        mr.setResultSummary(input.getResultSummary());
        mr.setPath(urlPath);
        this.mProduct_MacAddress_ResultBindingMapper.update(mr);
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
        this.mProduct_MacAddressMapper.updateData(model,macAddress.getID(),-1,-1);

        if(cfg.getTrayMode() > 0) {
            String searchTrayType = Constant.TrayType_FailStart;
            if (input.getResult().toLowerCase().equals("pass")) {
                searchTrayType = Constant.TrayType_Pass;
            } else {
                String errorFirst = input.getErrorCode().trim().substring(0, 1);
                searchTrayType += errorFirst;
            }

            trayType = this.mTrayTypeMapper.getByName(searchTrayType);
            List<TrayCount> trayCounts = this.mProduct_TrayPositionMapper.getLastXYByTypeIdAndCfgId(trayType.getID(), cfg.getID());
            if (trayCounts == null || trayCounts.size() == 0) {
                throw new RuntimeException("Search Tray Type : " + searchTrayType + " last x y null error !!");
            }

            for (TrayCount trayCount : trayCounts) {
                if (trayCount.getLastMatrix() != null &&
                        trayCount.getLastMatrix() >= (trayCount.getOneTrayWidth() * trayCount.getOneTrayHeight() - 1)) {
                    continue;
                }
                trayName = trayCount.getTrayName();
                trayId = trayCount.getTrayId();
                trayX = 0;
                trayY = 0;
                trayMatrix = 0;
                if (trayCount.getLastMatrix() != null) {
                    trayMatrix = trayCount.getLastMatrix() + 1;
                    trayY = trayMatrix / trayCount.getOneTrayWidth();
                    trayX = trayMatrix - (trayY * trayCount.getOneTrayWidth());
                }

                Product_Tray currentTray = new Product_Tray();
                currentTray.setID(trayId);
                Product_TrayPosition newPosition = new Product_TrayPosition();
                newPosition.setErrorCode(input.getErrorCode());
                newPosition.setMatrixIndex(trayMatrix);
                newPosition.setProduct_Tray(currentTray);
                newPosition.setProduct_MacAddress_ResultBinding(mr);
                newPosition.setProduct_TestConfiguration(cfg);
                newPosition.setX(trayX);
                newPosition.setY(trayY);
                this.mProduct_TrayPositionMapper.insert(newPosition);
                break;


            }
        }

        return getDetailMacCountByMacAddressOrLotCode(input.getAddress(), null, trayName, trayX, trayY);
    }




//    @Transactional(isolation = Isolation.REPEATABLE_READ)
//    public ResponseCreateTestConfigurationModel CreateProductTestConfigurationByAutogen(TestConfigurationCreationInput input){
//        long startMacAddress = -1;
//        boolean isDoingUpdateMac = false;
//
//        MacDispatchType dispatchType = null;
//        MacType macType = null;
//        ProductFamily productFamily = null;
//        MacStatus macStatus = null;
//        ProductDevice productDevice = null;
//        User owner = null;
//        Customer customer = null;
//        Software sw = null;
//        Firmware fw = null;
//        List<DutDevice> duts = new ArrayList<>();
//        TestConfiguration tfg = null;
//
//        // --- check owner
//        if((owner = this.mUserMapper.getByEmployeeID(input.getOwnerEmplayeeId()) ) == null){
//            throw new RuntimeException("Not Found User By EmplayeeId : " + input.getOwnerEmplayeeId());
//        }
//
//        if(input.getProductFamilyName() != null){
//            if((productFamily = this.mProductFamilyMapper.getByName(input.getProductFamilyName()) ) == null){
//                productFamily = new ProductFamily();
//                productFamily.setName(input.getProductFamilyName());
//                productFamily.setCreatedOwner(owner);
//                productFamily.setRemark("");
//
//                this.mProductFamilyMapper.insert(productFamily);
//            }
//        }else{
//            throw new RuntimeException("ProductFamily parameter is empty !!");
//        }
//
//        if(input.getProductDeviceName() != null){
//            if((productDevice = this.mProductDeviceMapper.getByNameStr(input.getProductDeviceName(), input.getProductFamilyName()))  == null){
//                productDevice = new ProductDevice();
//                productDevice.setName(input.getProductDeviceName());
//                productDevice.setCreatedOwner(owner);
//                productDevice.setProductFamily(productFamily);
//                this.mProductDeviceMapper.insert(productDevice);
//            }
//        }else{
//            throw new RuntimeException("ProductDevice parameter is empty !!");
//        }
//
//        if((tfg = this.mMapper.getByProductDeviceIdAndLotCode( input.getLotcode())) != null){
//            // OK ~~~~
//            ResponseCreateTestConfigurationModel rm = new ResponseCreateTestConfigurationModel();
//            rm.setResult(true);
//            rm.setCurrentStatus(tfg.getStatus().getName());
//            TestConfiguration_SW_FW_Binding binding = tfg.getFwSwBinding().get(0);
//            rm.setSwName(binding.getSoftware().getName());
//            rm.setSwVersion(binding.getSoftware().getVersion());
//            rm.setFwName(binding.getFirmware().getName());
//            rm.setFwVersion(binding.getFirmware().getVersion());
//            //  rm.setSwName(tfg.getFwSwBinding().);
//            return rm;
//        }
//
//        if(input.getCustomerName() != null ){
//            if((customer = this.mCustomerMapper.getByName(input.getCustomerName())) == null){
//                customer = new Customer();
//                customer.setName(input.getCustomerName());
//                customer.setPhone("");
//                customer.setRemark("");
//                customer.setCreatedOwner(owner);
//                this.mCustomerMapper.insert(customer);
//            }
//        }else{
//            throw new RuntimeException("Customer parameter is empty !!");
//        }
//
//        if(input.getSwName() != null && input.getSwVersion() != null){
//            if((sw = this.mSoftwareMapper.getByNameAndVersion(input.getSwName(), input.getSwVersion())) == null){
//                sw = new Software();
//                sw.setVersion(input.getSwVersion());
//                sw.setName(input.getSwName());
//                sw.setCreatedOwner(owner);
//                mSoftwareMapper.insert(sw);
//            }
//        }else{
//            throw new RuntimeException("Software parameter is empty !!");
//        }
//
//        if(input.getFwName() != null && input.getFwVersion() != null){
//            if((fw = this.mFirmwareMapper.getByNameAndVersion(input.getFwName(), input.getFwVersion())) == null){
//                fw = new Firmware();
//                fw.setPath("");
//                fw.setRemark("");
//                fw.setMD5("");
//                fw.setCreatedOwner(owner);
//                fw.setVersion(input.getFwVersion());
//                fw.setName(input.getFwName());
//                this.mFirmwareMapper.insert(fw);
//            }
//        }else{
//            throw new RuntimeException("Firmware parameter is empty !!");
//        }
//
//        if(input.getGroupPC() == null){
//            throw new RuntimeException("GroupPC parameter is empty !!");
//        }
//
//        // TODO dutname
////        if(input.getDutNames() != null){
////            if(input.getDutNames().length > 0){
////                for(String s : input.getDutNames()){
////                    DutDevice device = null;
////                    if((device = this.mDutMapper.getByHostNameAndProductDeviceAndGroupPC(s, input.getProductDeviceName(), input.getGroupPC())) == null){
////                        device = new DutDevice();
////                        device.setHostName(s);
////                        device.setProductDevice(input.getProductDeviceName());
////                        device.setGroupPC(input.getGroupPC());
////                        device.setRemark("");
////                        device.setCreatedOwner(owner);
////                        this.mDutMapper.insert(device);
////                    }
////                    duts.add(device);
////                }
////            }
////        }else{
////            throw new RuntimeException("DutDevices parameter is empty !!");
////        }
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
//        jObject.put("testMode", getValueByStrObject(input.getTestMode()));
////        jObject.put("testFlow", getValueByStrObject(input.getTestFlow()));
//        TestConfiguration newTCfg = insertNewConfigurationForTesting(
//                input.getLotcode(),
//                productDevice,
//                customer,
//                sw, fw, owner, duts, dispatchType,
//                input.getLogTitle(),
//                input.getLogLimitUpper(),
//                input.getLogLimitLower(),
//                jObject, input.getPid(), input.getTrayMode());
//
//        //  tray mode
//        if(input.getTrayMode() > 0){
//            if(input.getTrayData() == null){
//                throw new RuntimeException("TrayData parameter is empty !!");
//            }
//
//            if(input.getTrayHeight() < 0 || input.getTrayWidth() < 0){
//                throw new RuntimeException("TrayHeight or TrayWidth parameter is empty !!");
//            }
//            // check count and insert to db
//            input.getTrayData().forEach((key, value) ->{
//                TrayType currentTrayType = null;
//                List<String> array = (List<String>) value;
//
//                int currentTypeTrayTotalCount = array.size() * input.getTrayWidth() * input.getTrayHeight();
//
//                if(currentTypeTrayTotalCount < input.getMacCount()){
//                    throw new RuntimeException("TrayType : " + key + ", current total count : "
//                            + currentTypeTrayTotalCount + " are not enough mac count : " + input.getMacCount());
//                }
//
//                if((currentTrayType = this.mTrayTypeMapper.getByName(key)) == null){
//                    throw new RuntimeException("Unknown trayType : " + key);
//                }
//
//                for(int i = 0; i < array.size(); i++){
//                    Tray newTray = new Tray();
//                    newTray.setHeight(input.getTrayHeight());
//                    newTray.setWidth(input.getTrayWidth());
//                    newTray.setName(array.get(i));
//                    newTray.setOrder(i);
//                    newTray.setTrayType(currentTrayType);
//                    newTray.setTestConfiguration(newTCfg);
//                    this.mTrayMapper.insert(newTray);
//                }
//            });
//        }
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
//
//        ResponseCreateTestConfigurationModel rm = new ResponseCreateTestConfigurationModel();
//        rm.setResult(true);
//        rm.setCurrentStatus(Constant.TestConfigStatus_Testing);
//        rm.setSwName(input.getSwName());
//        rm.setSwVersion(input.getSwVersion());
//        rm.setFwName(input.getFwName());
//        rm.setFwVersion(input.getFwVersion());
//
//        return rm;
//
//    }

}

