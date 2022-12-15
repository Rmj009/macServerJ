package com.asecl.simdc.org.simdc_project.graphql.resolver;

import com.asecl.simdc.org.simdc_project.db.entity.Product_TestConfiguration;
import com.asecl.simdc.org.simdc_project.db.service.*;
import com.asecl.simdc.org.simdc_project.exception.QLException;
import com.asecl.simdc.org.simdc_project.graphql.entity.input.*;
import com.asecl.simdc.org.simdc_project.graphql.entity.out.*;
import com.asecl.simdc.org.simdc_project.util.ILockCallback;
import com.asecl.simdc.org.simdc_project.security.LockManager;
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


@Component
public class ProductTestconfigurationResolver implements GraphQLQueryResolver , GraphQLMutationResolver {

    @Autowired
    @Lazy
    private UserService mUserService;

    @Autowired
    @Lazy
    private Product_TestConfigurationService mProduct_TestConfigurationService;

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

    public ResponseCreateTestConfigurationModel CreateProductTestConfiguration(Product_TestConfigurationCreationInput input){
//        boolean isLockOK = false;
        ResponseCreateTestConfigurationModel result = null;
        Product_TestConfiguration cfg = null;
        try{
            result = mLock.TryLock("CreateProductTestConfiguration", new ILockCallback<ResponseCreateTestConfigurationModel>() {
                @Override
                public ResponseCreateTestConfigurationModel exec() {
                    return ProductTestconfigurationResolver.this.mProduct_TestConfigurationService.CreateProductTestConfigurationByAutogen(input);
                }
            });
        }catch(Exception e){
            throw new QLException(e);
        }
        return result;
    }

    public ResponseModel UpdateProductTestConfiguration(Product_TestConfigurationUpgradeInput input){
        try{
            mLock.TryLock(input.getLotcode(), new ILockCallback<Boolean>() {
                @Override
                public Boolean exec() {
                    ProductTestconfigurationResolver.this.mProduct_TestConfigurationService.UpdateProductTestConfiguration_SW_FW_Version(input);
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

    public ProductTestConfigurationQueryResponse QueryProductTestConfiguration(Product_TestConfigurationQueryInput input){
        ProductTestConfigurationQueryResponse res = null;
        Page<Product_TestConfiguration> page = null;

        try{
            if(input == null){

                page = PageHelper.startPage(1, 10).doSelectPage(()-> this.mProduct_TestConfigurationService.getAll());

            }else{
                page = PageHelper.startPage(input.getPageNumber(), input.getPageSize()).doSelectPage(()-> this.mProduct_TestConfigurationService.get(input));
            }
            PageInfo<Product_TestConfiguration> pageInfo = page.toPageInfo();
            res = new ProductTestConfigurationQueryResponse();
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

    public ResponseMacModel DispatchProductMac(MacDispatchInput input){
        ResponseMacModel rm = null;
        try{
            rm = mLock.TryLock("Product_Dispatch_" + input.getLotCode(), new ILockCallback<ResponseMacModel>() {
                @Override
                public ResponseMacModel exec() {
                    return ProductTestconfigurationResolver.this.mProduct_TestConfigurationService.DispatchProductMac(input);
                }
            });
        }catch(Exception e){
            throw new QLException(e);
        }
        return rm;
    }

    public ResponseTotalModel SyncProductMACResultWithoutLog(SyncIput input){
        ResponseTotalModel rm = null;
        try{
//            rm = mLock.TryLock("Product_Dispatch_" + input.getLotcode(), new ILockCallback<ResponseTotalModel>() {
//                @Override
//                public ResponseTotalModel exec() {
//                    return ProductTestconfigurationResolver.this.mProduct_TestConfigurationService.SyncMACResultWithoutLog(input);
//                }
//            });

            rm = ProductTestconfigurationResolver.this.mProduct_TestConfigurationService.SyncMACResultWithoutLog(input);
        }catch(Exception e){
            throw new QLException(e);
        }
        return rm;
    }

    public ResponseTotalModel SyncProductMACResult(Part part, SyncIput input, DataFetchingEnvironment env){
        ResponseTotalModel rm = null;
        try{
            rm = mLock.TryLock(input.getLotcode(), new ILockCallback<ResponseTotalModel>() {
                @Override
                public ResponseTotalModel exec() throws IOException {
                    return ProductTestconfigurationResolver.this.mProduct_TestConfigurationService.SyncProductMACResult(part, input, env);
                }
            });
        }catch(Exception e){
            throw new QLException(e);
        }
        return rm;
    }

//    public ResponseModel PingServer(PingInput input){
//        System.out.println("PC Name : " + input.getPcName());
//        ResponseModel model = new ResponseModel();
//        model.setResult(true);
//        return model;
//    }

}
