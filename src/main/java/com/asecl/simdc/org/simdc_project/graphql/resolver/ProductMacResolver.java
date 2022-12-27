package com.asecl.simdc.org.simdc_project.graphql.resolver;

import com.asecl.simdc.org.simdc_project.db.entity.Firmware;
import com.asecl.simdc.org.simdc_project.db.entity.Product_MacAddress;
import com.asecl.simdc.org.simdc_project.db.entity.User;
import com.asecl.simdc.org.simdc_project.db.mapper.Product_MacAddressMapper;
import com.asecl.simdc.org.simdc_project.db.service.FirmwareService;
import com.asecl.simdc.org.simdc_project.db.service.Product_MacAddressService;
import com.asecl.simdc.org.simdc_project.exception.QLException;
import com.asecl.simdc.org.simdc_project.ftp.FTPUploadUtils;
import com.asecl.simdc.org.simdc_project.graphql.entity.input.FirmwareCreationInput;
import com.asecl.simdc.org.simdc_project.graphql.entity.input.SyncIput;
import com.asecl.simdc.org.simdc_project.graphql.entity.input.SyncPoInput;
import com.asecl.simdc.org.simdc_project.graphql.entity.out.ResponseModel;
import com.asecl.simdc.org.simdc_project.http.HttpService;
import com.asecl.simdc.org.simdc_project.util.Constant;
import com.asecl.simdc.org.simdc_project.util.ILockCallback;
import com.asecl.simdc.org.simdc_project.util.LockManager;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Part;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


@Component
public class ProductMacResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    @Value("${mac.insert.max-size}")
    private long MAC_INSERT_MAX;

    @Value("${mac.lock.time-out-sec}")
    private int TRY_LOCK_TIMEOUT;

    @Autowired
    private FirmwareService mFirmwareService;

    @Autowired
    private LockManager mLock;

    @Autowired
    @Lazy
    private Product_MacAddressService mProduct_MacAddressService;

    @Autowired
    @Lazy
    private Product_MacAddressMapper mProduct_MacAddressMapper;

    @Autowired
    @Lazy
    private HttpService mHttpService;

    @Autowired
    private FTPUploadUtils mFTPUploadutils;

    @Autowired
    private ResourceLoader mResourceLoader;

    public ResponseModel SyncPo(SyncPoInput model ){
        ResponseModel result = null;
        if(model.getCount() == -1)
        {
            throw new RuntimeException("Unknown Input Count: " + model.getCount());
        }
        if(model.getLotcode() == null)
        {
            throw new RuntimeException("Unknown Input Lotcode: " + model.getLotcode());
        }
        if(model.getPo() == null)
        {
            throw new RuntimeException("Unknown Input Po: " + model.getPo());
        }

        try{
            Product_MacAddress macAddress = this.mProduct_MacAddressMapper.getFirstMacByLotcodeAndPO(model.getLotcode(), model.getPo());
            if(macAddress != null){
                result = new ResponseModel();
                result.setResult(true);
            }else{
//                if(this.mHttpService.sendPostPOFromITToGetXML(model.getPo(),model.getLotcode(),model.getCount()) > 300)
//                {
//                    throw new RuntimeException("HttpClient From IT server Fail :" + this.mHttpService.sendPostPOFromITToGetXML(model.getPo(),model.getLotcode(),model.getCount()));
//                }
//                this.mFTPUploadutils.getFileByFtp("/Policy.xml","D:/Policy.xml");
                InputStream stream = ProductMacResolver.this.mProduct_MacAddressService.GetFileStreamFromITServer(model);
                result = mLock.TryLock("CreateProductMacAddress", new ILockCallback<ResponseModel>() {
                    @Override
                    public ResponseModel exec() throws JAXBException {
                        return ProductMacResolver.this.mProduct_MacAddressService.InsertMacAddressFromITFile(stream,model);
                    }
                });

            }
        }catch(Exception e){
            throw new QLException(e);
        }
        return result;
    }

//    public List<MacAddress> QueryMacAddress(MacQueryInput input){
//        List<MacAddress> macs = null;
//        try{
//            if(input == null){
//                macs = this.mMacAddressService.getAll();
//            }else{
//                if(input.getMacType() != null){
//                    if(!this.mMacAddressService._checkMacTypeInput(input.getMacType())){
//                        throw new RuntimeException("Unknown Input Mac Type: " + input.getMacType());
//                    }
//                }
//                if(input.getPageNumber() > 0 && input.getPageSize() > 0){
//                    PageHelper.startPage(input.getPageNumber(), input.getPageSize());
//                }
//                macs = this.mMacAddressService.get(input);
//            }
//        }catch(Exception ex){
//            throw new QLException(ex);
//        }
//        return macs;
//    }
//
//    public MacAddress QueryLastBTMacAddressInMP(MacQueryInput input){
//        MacAddress macs = null;
//        try{
//            macs = this.mMacAddressService.getLastBTMacAddress();
//        }catch(Exception ex){
//            throw new QLException(ex);
//        }
//        return macs;
//    }

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

//    public ResponseModel UpdateDispatchMacStatus(MacDispatchUpdateStatusInput model){
//        String str = "Used";
//        String str1 = "UsedFail";
//        try{
//
//            if(this.mMacDispatchType.getCountByUsing(model.getLotCode(),model.getMac()) != 1)
//            {
//                throw new RuntimeException("Mac Status is not in testing !!!");
//            }
//
//            if(model.getStatus().equals(str) || model.getStatus().equals(str1))
//            {
//                this.mMacDispatchType.updateMacStatus(model.getMac(),model.getStatus(),model.getLotCode());
//            }
//            else
//            {
//                throw new RuntimeException("Your input is error !! " + model.getStatus());
//            }
//
//            if(this.mMacDispatchType.getCountByStatus_ID(model.getLotCode(),"Unused") == 0 )
//            {
//                if( this.mMacDispatchType.getCountByStatus_ID(model.getLotCode(),"Using") == 0 && this.mMacDispatchType.getCountByStatus_ID(model.getLotCode(),str1) > 0 )
//                {
//                    this.mTestConfigurationService.updateTestStatusByLotCode("TestFail",model.getLotCode());
//                }
//
//                if( this.mMacDispatchType.getCountByStatus_ID(model.getLotCode(),"Using") == 0 && this.mMacDispatchType.getCountByStatus_ID(model.getLotCode(),str1) == 0 )
//                {
//                    this.mTestConfigurationService.updateTestStatusByLotCode("TestFinish",model.getLotCode());
//                }
//            }
//
//        }catch(Exception ex){
//            throw new QLException(ex);
//        }
//        ResponseModel r = new ResponseModel();
//        r.setResult(true);
//        return r;
//    }


    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public ResponseModel Upload_MAC_XML(Part part, SyncIput input, DataFetchingEnvironment env)throws IOException{

//            暫時註解調檔案上傳功能 2020/6/18 start
        if(input.getFilename() == null){
            throw new RuntimeException("FileName can not null !!");
        }
        if(input.getLotcode() == null){
            throw new RuntimeException("Lotcode can not null !!");
        }
        Part attachmentPart= env.getArgument("file");
        if(attachmentPart.getInputStream().available() == 0){
                throw new RuntimeException("Upload File Size Is Zero Fail !!");
        }
//            END

//            暫時註解調檔案上傳功能 2020/6/18 start
        String currentNewFileName =input.getFilename();
        mFirmwareService.saveFwFileToDisk(attachmentPart, currentNewFileName);


        ResponseModel rm = new ResponseModel();
        rm.setResult(true);
        return rm;
    }

//    //    把fun移動到servive start
//    private File getNewFwFile(String fileName) throws IOException{
//        File directory = mResourceLoader.getResource(Constant.FW_DIR_PATH).getFile();
//        return new File(directory, fileName);
//    }
//
//    private void deleteFwFileFromDisk(String fileName) throws IOException {
//        File fwFile = this.getNewFwFile(fileName);
//        if(fwFile.exists()){
//            fwFile.delete();
//        }
////        deleteFile(fileName);
//    }

//    private boolean deleteFile(String fileName) throws IOException {
//        int loop = 0;
//        boolean isOk = false;
//        do{
//            File fwFile = this.getNewFwFile(fileName);
//            if(fwFile.exists()){
//                isOk = fwFile.delete();
//            }else{
//                isOk = true;
//                break;
//            }
//            loop++;
//        }while(!isOk && loop < 5);
//        return isOk;
//    }
}

