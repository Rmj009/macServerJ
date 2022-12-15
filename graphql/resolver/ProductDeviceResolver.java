package com.asecl.simdc.org.simdc_project.graphql.resolver;

import com.asecl.simdc.org.simdc_project.db.entity.ProductDevice;
import com.asecl.simdc.org.simdc_project.db.entity.ProductFamily;
import com.asecl.simdc.org.simdc_project.db.entity.User;
import com.asecl.simdc.org.simdc_project.db.service.ProductFamilyService;
import com.asecl.simdc.org.simdc_project.db.service.ProductDeviceService;
import com.asecl.simdc.org.simdc_project.db.service.UserService;
import com.asecl.simdc.org.simdc_project.exception.QLException;
import com.asecl.simdc.org.simdc_project.graphql.entity.input.*;
import com.asecl.simdc.org.simdc_project.graphql.entity.out.ResponseMacModel;
import com.asecl.simdc.org.simdc_project.graphql.entity.out.ResponseModel;
import com.asecl.simdc.org.simdc_project.util.ILockCallback;
import com.github.pagehelper.PageHelper;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductDeviceResolver implements GraphQLQueryResolver , GraphQLMutationResolver {

    @Autowired
    private UserService mUserService;

    @Autowired
    private ProductDeviceService mProductDeviceService;

    @Autowired
    private ProductFamilyService mProductFamilyService;

    public ResponseModel CreateProductDevice(ProductDeviceCreationInput model){
        try{
            User owner = null;
            long pfId = -1;
            if((owner = this.mUserService.getByEmployeeID(model.getOwnerEmplayeeId()) ) == null){
                throw new RuntimeException("Not Found User By EmplayeeId : " + model.getOwnerEmplayeeId());
            }

            if((this.mProductFamilyService.getCountByName(model.getFamilyName())) == 0){
                throw new RuntimeException("Not Found Product Family By FamilyName : " + model.getFamilyName());
            }

            pfId = this.mProductFamilyService.getIdByName(model.getFamilyName());

            if(this.mProductDeviceService.getCountByName(model.getName(), pfId) > 0){
                throw new RuntimeException("Product Name : " + model.getName() + " In Family : " + model.getFamilyName() + " Is Already Exists !!");
            }
            ProductFamily family = new ProductFamily();
            family.setName(model.getFamilyName());

            ProductDevice p = new ProductDevice();
            p.setRemark(model.getRemark());
            p.setName(model.getName());
            p.setCreatedOwner(owner);
            p.setProductFamily(family);
            mProductDeviceService.insert(p);
        }
        catch (Exception e)
        {
            throw new QLException(e);
        }

        ResponseModel rm = new ResponseModel();
        rm.setResult(true);
        return rm;

    }



    public List<ProductDevice> QueryProductDevice(ProductDeviceQueryInput input){
        List<ProductDevice> productDevices = null;
        try{
            if(input == null){
                productDevices = this.mProductDeviceService.getAll();
            }else{
                if(input.getPageNumber() > 0 && input.getPageSize() > 0){
                    PageHelper.startPage(input.getPageNumber(), input.getPageSize());
                }
                productDevices = this.mProductDeviceService.get(input);
            }

        }catch(Exception ex){
            throw new QLException(ex);
        }
        return productDevices;
    }

    public ResponseModel UpdateProductDevice(ProductDeviceUpgradeInput model){

        try{
            ProductDevice p = null;
            long srcPfId = -1;
            long newPfid = -1;
            if((this.mProductFamilyService.getCountByName(model.getOriginFamilyName())) == 0){
                throw new RuntimeException("Not Found Product Family By FamilyName : " + model.getOriginFamilyName());
            }
            srcPfId = this.mProductFamilyService.getIdByName(model.getOriginFamilyName());

            if((p = this.mProductDeviceService.getByName(model.getOriginName(), srcPfId)) == null){
                throw new RuntimeException("Product Name : " + model.getOriginName() + " In Family : " + model.getOriginFamilyName() + " Is Not Exists !!");
            }

            if(model.getFamilyName() != null){
                if((this.mProductFamilyService.getCountByName(model.getFamilyName())) == 0){
                    throw new RuntimeException("Not Found New Product Family By FamilyName : " + model.getFamilyName());
                }
                newPfid = this.mProductFamilyService.getIdByName(model.getFamilyName());
            }

            if(model.getRemark() == null){
                if(model.getFamilyName() == null && model.getName() == null){
                    throw new RuntimeException("You must input name or family name for updating !!");
                }
            }

            this.mProductDeviceService.updateData(model, p.getID(), srcPfId, newPfid);

        }
        catch (Exception e)
        {
            throw new QLException(e);
        }
        ResponseModel rm = new ResponseModel();
        rm.setResult(true);
        return rm;
    }

    public ResponseModel DeleteProductDevice(ProductDeviceDeleteInput model){

        try{
            ProductDevice p = null;
            long srcPfId = -1;
            if((this.mProductFamilyService.getCountByName(model.getFamilyName())) == 0){
                throw new RuntimeException("Not Found Product Family By FamilyName : " + model.getFamilyName());
            }
            srcPfId = this.mProductFamilyService.getIdByName(model.getFamilyName());

            if((p = this.mProductDeviceService.getByName(model.getName(), srcPfId)) == null){
                throw new RuntimeException("Product Name : " + model.getName() + " In Family : " + model.getFamilyName() + " Is Not Exists !!");
            }

            this.mProductDeviceService.deleteByID(p.getID());
        }
        catch (Exception e)
        {
            throw new QLException(e);
        }
        ResponseModel rm = new ResponseModel();
        rm.setResult(true);
        return rm;
    }
}
