package com.asecl.simdc.org.simdc_project.graphql.resolver;

import com.asecl.simdc.org.simdc_project.db.entity.*;
import com.asecl.simdc.org.simdc_project.db.service.*;
import com.asecl.simdc.org.simdc_project.exception.QLException;
import com.asecl.simdc.org.simdc_project.graphql.entity.input.*;
import com.asecl.simdc.org.simdc_project.graphql.entity.out.*;
import com.asecl.simdc.org.simdc_project.util.Constant;
import com.asecl.simdc.org.simdc_project.util.ILockCallback;
import com.asecl.simdc.org.simdc_project.util.LockManager;
import com.asecl.simdc.org.simdc_project.util.UtilFunc;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;


import javax.servlet.http.Part;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Component
public class TestconfigurationResolver implements GraphQLQueryResolver , GraphQLMutationResolver {

    @Autowired
    @Lazy
    private UserService mUserService;

    @Autowired
    private TestConfigurationService mTestConfigurationService;

    @Autowired
    @Lazy
    private TestConfiguration_DutBindingService mTestBindingService;

    @Autowired
    @Lazy
    private TestConfigurationStatusService mTestStatusService;

    @Autowired
    @Lazy
    private ProductDeviceService mProductService;

    @Autowired
    @Lazy
    private CustomerService mCustomerService;

    @Autowired
    @Lazy
    private SoftwareService mSoftwareService;

    @Autowired
    @Lazy
    private FirmwareService mFirmwareService;

    @Autowired
    @Lazy
    private DutDeviceService mDutService;

    @Autowired
    @Lazy
    private MacDispatchTypeService mMacDispatchTypeService;

    @Autowired
    @Lazy
    private MacTypeService mMacTypeService;

    @Autowired
    @Lazy
    private MacStatusService mMacStatusService;

    @Autowired
    @Lazy
    private MacAddressService mMacAddressService;

    @Autowired
    @Lazy
    private TestConfiguration_DutBindingService mDutBindingService;

    @Autowired
    private LockManager mLock;

    @Value("${mac.lock.time-out-sec}")
    private int TRY_LOCK_TIMEOUT;

    public ResponseCreateTestConfigurationModel CreateTestConfiguration(TestConfigurationCreationInput input){
//        boolean isLockOK = false;
        ResponseCreateTestConfigurationModel result = null;
        try{
            result = mLock.TryLock("CreateTestConfiguration", new ILockCallback<ResponseCreateTestConfigurationModel>() {
                @Override
                public ResponseCreateTestConfigurationModel exec() {
                    if(input.getAutogen() == 1){
                        return TestconfigurationResolver.this.mTestConfigurationService.CreateTestConfigurationByAutogen(input);
                    }else{
                        return TestconfigurationResolver.this.mTestConfigurationService.CreateTestConfiguration(input);
                    }

                }
            });
        }catch(Exception e){
            throw new QLException(e);
        }
        return result;
    }

    public ResponseModel UpdateTestConfiguration(TestConfigurationUpgradeInput input){
        try{
            mLock.TryLock(input.getLotcode(), new ILockCallback<Boolean>() {
                @Override
                public Boolean exec() {
                    TestconfigurationResolver.this.mTestConfigurationService.UpdateTestConfiguration_SW_FW_Version(input);
                    return true;
                }
            });
        }catch(Exception e){
            throw new QLException(e);
        }
        ResponseModel rm = new ResponseModel();
        rm.setResult(true);
        return rm;
    }

//    public ResponseModel DeleteTestConfiguration(TestConfigurationDeleteInput input){
//
//        try{
//            //?�斷testconfigurationstatus?�否存在
//            if (this.mTestConfigurationService.getByLotCode(input.getDevice()) != null )
//            {
//                //?�斷 裡�?testconfigurationstatus ?�否??TestPrepare
//                if(this.mTestConfigurationService.getCountByTFCBinding(input.getDevice()) == 1 )
//                {
//                    //?�斷testconfiguration 裡�?mac?�否?�被使用
//                    if(this.mTestConfigurationService.getCountByTFCStatus(input.getDevice()) == 0 )
//                    {
//                        this.mTestConfigurationService.deleteByLotCode(input.getDevice());
//                    }
//                    else
//                    {
//                        throw new RuntimeException("TestConfiguration's mac address id is binding �?you need unbinding (MacStatus is not Unused)!!");
//                    }
//
//                }
//                else
//                {
//                    throw new RuntimeException("TestConfiguration id is binding �?you need unbinding (TestConfigurationStatus is not TestPrepare)!!");
//                }
//
//            }
//            else
//            {
//                throw new RuntimeException("Your input TestConfiguration Lotcode is nonexistent ("+input.getDevice()+")!!");
//            }
//        }
//        catch (Exception e)
//        {
//            throw new QLException(e);
//        }
//        ResponseModel rm = new ResponseModel();
//        rm.setResult(true);
//        return rm;
//    }

//    public ResponseModel UpdateTestConfigurationStatus(TestConfigurationStatusUpdateInput input){
//        // --- check lotcode
//        boolean isLockOK = false;
//        try{
//            if(Constant.MAC_OP_LOCK.tryLock(TRY_LOCK_TIMEOUT, TimeUnit.SECONDS)){
//                isLockOK = true;
//                this.mTestConfigurationService.UpdateTestConfigurationStatus(input);
//
//            }else{
//                throw new RuntimeException("Mac Table Is Already Used By Other People...");
//            }
//        }
//        catch (Exception e)
//        {
//            throw new QLException(e);
//        }finally {
//            if(isLockOK){
//                Constant.MAC_OP_LOCK.unlock();
//            }
//        }
//        ResponseModel rm = new ResponseModel();
//        rm.setResult(true);
//        return rm;
//    }

    public TestConfigurationQueryResponse QueryTestConfiguration(TestConfigurationQueryInput input){
        TestConfigurationQueryResponse res = null;
        Page<TestConfiguration> page = null;

        try{
            if(input == null){
                page = PageHelper.startPage(1, 10).doSelectPage(()-> this.mTestConfigurationService.getAll());

            }else{
                page = PageHelper.startPage(input.getPageNumber(), input.getPageSize()).doSelectPage(()-> this.mTestConfigurationService.get(input));
            }
            PageInfo<TestConfiguration> pageInfo = page.toPageInfo();
            res = new TestConfigurationQueryResponse();
            res.setDatas(page.getResult());
            res.setTotalPage(pageInfo.getPages());
            res.setPageSize(pageInfo.getSize());
            res.setPageNumber(pageInfo.getPageNum());
            if(input == null)
            {
                res.setTotalSize(1);
            }
            else
            {
                res.setTotalSize((int)pageInfo.getTotal());
            }

        }catch(Exception ex){
            throw new QLException(ex);
        }
        return res;
    }

    public ResponseMacModel DispatchMac(MacDispatchInput input){
        ResponseMacModel rm = null;
        try{
            rm = mLock.TryLock(input.getLotCode(), new ILockCallback<ResponseMacModel>() {
                @Override
                public ResponseMacModel exec() {
                    return TestconfigurationResolver.this.mTestConfigurationService.DispatchMac(input);
                }
            });
        }catch(Exception e){
            throw new QLException(e);
        }
        return rm;
    }

    public ResponseTotalModel SyncMACResultWithoutLog(SyncIput input){
        ResponseTotalModel rm = null;
        try{
//            rm = mLock.TryLock(input.getLotcode(), new ILockCallback<ResponseTotalModel>() {
//                @Override
//                public ResponseTotalModel exec() {
//                    return TestconfigurationResolver.this.mTestConfigurationService.SyncMACResultWithoutLog(input);
//                }
//            });
            rm = TestconfigurationResolver.this.mTestConfigurationService.SyncMACResultWithoutLog(input);
        }catch(Exception e){
            throw new QLException(e);
        }
        return rm;
    }

    public ResponseTotalModel SyncMACResult(Part part, SyncIput input, DataFetchingEnvironment env){
        ResponseTotalModel rm = null;
        try{
            rm = mLock.TryLock(input.getLotcode(), new ILockCallback<ResponseTotalModel>() {
                @Override
                public ResponseTotalModel exec() throws IOException {
                    return TestconfigurationResolver.this.mTestConfigurationService.SyncMACResult(part, input, env);
                }
            });
        }catch(Exception e){
            throw new QLException(e);
        }
        return rm;
    }

    public ResponseModel PingServer(PingInput input){
        System.out.println("PC Name : " + input.getPcName());
        ResponseModel model = new ResponseModel();
        model.setResult(true);
        return model;
    }

    public ResponseLotCodeModel QueryNewLotcode()
    {
        ResponseLotCodeModel rm = new ResponseLotCodeModel();
        rm=this.mTestConfigurationService.QueryNewLotcode();
        //rm.setLotCode("");
        return rm;
    }

    //----------------------------
//    public ResponseCreateTestConfigurationModel CreateProductTestConfiguration(TestConfigurationCreationInput input){
////        boolean isLockOK = false;
//        ResponseCreateTestConfigurationModel result = null;
//        try{
//            result = mLock.TryLock("CreateTestConfiguration", new ILockCallback<ResponseCreateTestConfigurationModel>() {
//                @Override
//                public ResponseCreateTestConfigurationModel exec() {
//                    if(input.getAutogen() == 1){
//                        return TestconfigurationResolver.this.mTestConfigurationService.CreateTestConfigurationByAutogen(input);
//                    }else{
//                        return TestconfigurationResolver.this.mTestConfigurationService.CreateTestConfiguration(input);
//                    }
//
//                }
//            });
//        }catch(Exception e){
//            throw new QLException(e);
//        }
//        return result;
//    }
}
