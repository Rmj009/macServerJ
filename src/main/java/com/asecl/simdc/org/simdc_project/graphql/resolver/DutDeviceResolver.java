package com.asecl.simdc.org.simdc_project.graphql.resolver;


import com.asecl.simdc.org.simdc_project.db.entity.*;
import com.asecl.simdc.org.simdc_project.db.service.DutDeviceService;
import com.asecl.simdc.org.simdc_project.db.service.TestConfigurationService;
import com.asecl.simdc.org.simdc_project.db.service.TestConfiguration_DutBindingService;
import com.asecl.simdc.org.simdc_project.db.service.UserService;
import com.asecl.simdc.org.simdc_project.exception.QLException;
import com.asecl.simdc.org.simdc_project.graphql.entity.input.DutDeviceCreationInput;
import com.asecl.simdc.org.simdc_project.graphql.entity.input.DutDeviceDeleteInput;
import com.asecl.simdc.org.simdc_project.graphql.entity.input.DutDeviceQueryInput;
import com.asecl.simdc.org.simdc_project.graphql.entity.input.DutDeviceUpgradeInput;
import com.asecl.simdc.org.simdc_project.graphql.entity.out.ResponseModel;
import com.github.pagehelper.PageHelper;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DutDeviceResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    @Autowired
    private UserService mUserService;

    @Autowired
    private DutDeviceService mDutDeviceService;

//    @Autowired
//    private TestConfigurationService mTestConfigurationService;
//
    @Autowired
    private TestConfiguration_DutBindingService mTestConfiguration_Dut_BindingService;

    public ResponseModel CreateDutDevice(DutDeviceCreationInput input ){

        try{
            User owner = null;
           // TestConfiguration tc = null;
            if((owner = this.mUserService.getByEmployeeID(input.getOwnerEmplayeeId()) ) == null){
                throw new RuntimeException("Not Found User By EmplayeeId : " + input.getOwnerEmplayeeId());
            }

            if(this.mDutDeviceService.getByHostNameAndProductDeviceAndGroupPC(input.getHostname(), input.getProductDevice(), input.getGroupPc()) != null){
                throw new RuntimeException("DutDevice HostName : " + input.getHostname() + ", ProductDevice : " + input.getProductDevice() + ", GroupPC : " + input.getGroupPc() + " Is Already Exists !!");
            }

//            if(this.mDutDeviceService.getCountByLotCode(input.getHostname()) > 0){
//                throw new RuntimeException("DutDevice Name : " + input.getHostname() + " Is Already Exists !!");
//            }

            DutDevice d= new DutDevice();
            d.setGroupPC(input.getGroupPc());
            d.setHostName(input.getHostname());
            d.setProductDevice(input.getProductDevice());
            d.setRemark(input.getRemark());
            d.setCreatedOwner(owner);
            mDutDeviceService.insert(d);

//            if((tc = this.mTestConfigurationService.getByLotCode(model.getBinding_LotCode()) ) != null){
//
//                TestConfiguration_Binding tb = new TestConfiguration_Binding();
//                tb.setTestConfiguration_ID(this.mTestConfigurationService.getByLotCode(model.getBinding_LotCode()));
//                tb.setDutDevice_ID(this.mDutDeviceService.getByIP(model.getIp()));
//                mTestConfiguration_BindingService.insert(tb);
//            }

        }
        catch (Exception e)
        {
            throw new QLException(e);
        }

        ResponseModel rm = new ResponseModel();
        rm.setResult(true);
        return rm;
    }

    public ResponseModel UpdateDutDevice(DutDeviceUpgradeInput input){
        try{
            DutDevice d = null;
            if((d = this.mDutDeviceService.getByHostNameAndProductDeviceAndGroupPC(input.getOriginName(), input.getOriginProductDevice(), input.getOriginGroupPc())) == null){
                throw new RuntimeException("DutDevice HostName : " + input.getOriginName() + ", ProductDevice : " + input.getOriginProductDevice() + ", GroupPC : " + input.getOriginGroupPc() + " Is Not Exists !!");
            }

            if(this.mTestConfiguration_Dut_BindingService.getDutDevice_IDCount(d.getID()) > 0){
                throw new RuntimeException("DutDevice id is binding ， You need unbinding !!");
            }

            this.mDutDeviceService.updateData(input, d.getID());
        }
        catch (Exception e)
        {
            throw new QLException(e);
        }

        ResponseModel rm = new ResponseModel();
        rm.setResult(true);
        return rm;
    }

    public ResponseModel DeleteDutDevice(DutDeviceDeleteInput input){
        try{
            DutDevice d = null;
            if((d = this.mDutDeviceService.getByHostNameAndProductDeviceAndGroupPC(input.getHostname(), input.getProductDevice(), input.getGroupPc())) == null){
                throw new RuntimeException("DutDevice HostName : " + input.getHostname() + ", ProductDevice : " + input.getProductDevice() + ", GroupPC : " + input.getGroupPc() + " Is Not Exists !!");
            }

            if((this.mTestConfiguration_Dut_BindingService.getDutDevice_IDCount(d.getID()) > 0)){
                throw new RuntimeException("DutDevice id is binding ， You need unbinding !!");
            }

            this.mDutDeviceService.deleteByID(d.getID());
        }
        catch (Exception e)
        {
            throw new QLException(e);
        }

        ResponseModel rm = new ResponseModel();
        rm.setResult(true);
        return rm;
    }

    public List<DutDevice> QueryDutDevice(DutDeviceQueryInput input){
        List<DutDevice> duts = null;
        try{
            if(input == null){
                duts = this.mDutDeviceService.getAll();
            }else{
                if(input.getPageNumber() > 0 && input.getPageSize() > 0){
                    PageHelper.startPage(input.getPageNumber(), input.getPageSize());
                }
                duts = this.mDutDeviceService.get(input);
            }
        }catch(Exception ex){
            throw new QLException(ex);
        }
        return duts;
    }
}
