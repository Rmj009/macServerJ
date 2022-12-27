package com.asecl.simdc.org.simdc_project.graphql.resolver;

import com.asecl.simdc.org.simdc_project.db.entity.*;
import com.asecl.simdc.org.simdc_project.db.service.FirmwareService;
import com.asecl.simdc.org.simdc_project.db.service.SoftwareService;
import com.asecl.simdc.org.simdc_project.db.service.UserService;
import com.asecl.simdc.org.simdc_project.exception.QLException;
import com.asecl.simdc.org.simdc_project.graphql.entity.input.*;
import com.asecl.simdc.org.simdc_project.graphql.entity.out.ResponseModel;
import com.github.pagehelper.PageHelper;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SoftwareResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    @Autowired
    private UserService mUserService;

    @Autowired
    private SoftwareService mSoftwareService;

    @Autowired
    private FirmwareService mFirmwareService;


    public ResponseModel CreateSoftware(SoftwareCreationInput input){
        try{
            User owner = null;
            if((owner = this.mUserService.getByEmployeeID(input.getOwnerEmplayeeId()) ) == null){
                throw new RuntimeException("Not Found User By EmplayeeId : " + input.getOwnerEmplayeeId());
            }

            if(this.mSoftwareService.getCountByNameAndVersoin(input.getName(), input.getVersion()) > 0){
                throw new RuntimeException("Software Name : " + input.getName() + " And Version : " + input.getVersion() + " Is Already Exists!!");
            }

            Software sw = new Software();
            sw.setCreatedOwner(owner);
            sw.setName(input.getName());
            sw.setVersion(input.getVersion());
            this.mSoftwareService.insert(sw);
        }
        catch (Exception e)
        {
            throw new QLException(e);
        }

        ResponseModel rm = new ResponseModel();
        rm.setResult(true);
        return rm;
    }

//    public ResponseModel UpdateSoftware(SoftwareUpgradeInput input){
//        try{
//            Software s = null;
//            if((s = this.mSoftwareService.getByNameAndVersoin(input.getOriginName(), input.getOriginVersion())) == null){
//                throw new RuntimeException("Software Name : " + input.getOriginName() + " And Version : " + input.getOriginVersion() + " Is Not Exists!!");
//            }
//
//            this.mSoftwareService.updateData(input, s.getID());
//        }
//        catch (Exception e)
//        {
//            throw new QLException(e);
//        }
//        ResponseModel rm = new ResponseModel();
//        rm.setResult(true);
//        return rm;
//    }

    public ResponseModel DeleteSoftware(SoftwareDeleteInput input){
        try{
            Software s = null;
            if((s = this.mSoftwareService.getByNameAndVersion(input.getName(), input.getVersion())) == null){
                throw new RuntimeException("Software Name : " + input.getName() + " And Version : " + input.getVersion() + " Is Not Exists!!");
            }
            if(this.mSoftwareService.getCountByNameInTestconfiguration(input.getName(), input.getVersion()) > 0)
            {
                throw new RuntimeException("Software id is binding ， You need unbinding !!");
            }
            this.mSoftwareService.deleteByID(s.getID());
        }
        catch (Exception e)
        {
            throw new QLException(e);
        }
        ResponseModel rm = new ResponseModel();
        rm.setResult(true);
        return rm;
    }

    public List<SoftwareInfo> QuerySoftware(SoftwareQueryInput input){
        List<SoftwareInfo> sw = null;
        try{
            if(input == null){
                sw = this.mSoftwareService.getAllInfo();
            }else{
                if(input.getPageNumber() > 0 && input.getPageSize() > 0){
                    PageHelper.startPage(input.getPageNumber(), input.getPageSize());
                }
                sw = this.mSoftwareService.get(input);
            }
        }catch(Exception ex){
            throw new QLException(ex);
        }
        return sw;
    }
}
