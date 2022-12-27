package com.asecl.simdc.org.simdc_project.graphql.resolver;

import com.asecl.simdc.org.simdc_project.db.entity.*;
import com.asecl.simdc.org.simdc_project.db.service.ProductFamilyService;
import com.asecl.simdc.org.simdc_project.db.service.UserService;
import com.asecl.simdc.org.simdc_project.exception.QLException;
import com.asecl.simdc.org.simdc_project.graphql.entity.input.ProductFamilyDeleteInput;
import com.asecl.simdc.org.simdc_project.graphql.entity.input.ProductFamilyUpgradeInput;
import com.asecl.simdc.org.simdc_project.graphql.entity.out.ResponseModel;
import com.asecl.simdc.org.simdc_project.graphql.entity.input.ProductFamilyCreationInput;
import com.asecl.simdc.org.simdc_project.graphql.entity.input.ProductFamilyQueryInput;
import com.asecl.simdc.org.simdc_project.util.UtilFunc;
import com.github.pagehelper.PageHelper;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductFamilyResolver implements GraphQLQueryResolver, GraphQLMutationResolver {
    @Autowired
    private UserService mUserService;

    @Autowired
    private ProductFamilyService mProductFamilyService;

    public ResponseModel CreateProductFamily(ProductFamilyCreationInput input ){
        try{
            User owner = null;
            if((owner = this.mUserService.getByEmployeeID(input.getOwnerEmplayeeId()) ) == null){
                throw new RuntimeException("Not Found User By EmplayeeId : " + input.getOwnerEmplayeeId());
            }

            if(this.mProductFamilyService.getCountByName(input.getName()) > 0){
                throw new RuntimeException("ProductFamily Name : " + input.getName() + " Is Already Exists !!");
            }

            ProductFamily newFamily = new ProductFamily();
            newFamily.setCreatedOwner(owner);
            newFamily.setName(input.getName());
            newFamily.setRemark(input.getRemark());
            this.mProductFamilyService.insert(newFamily);
        }catch(Exception ex){
            throw new QLException(ex);
        }

        ResponseModel r = new ResponseModel();
        r.setResult(true);
        return r;
    }

    public List<ProductFamily> QueryProductFamily(ProductFamilyQueryInput input){
        List<ProductFamily> families = null;
        try{
            if(input == null){
                families = this.mProductFamilyService.getAll();
            }else{
                if(input.getPageNumber() > 0 && input.getPageSize() > 0){
                    PageHelper.startPage(input.getPageNumber(), input.getPageSize());
                }
                families = this.mProductFamilyService.get(input);
            }
        }catch(Exception ex){
            throw new QLException(ex);
        }
        return families;
    }

    public ResponseModel UpdateProductFamily(ProductFamilyUpgradeInput input){
        try{
            ProductFamily family = null;
            if((family = this.mProductFamilyService.getByName(input.getOriginName())) == null){
                throw new RuntimeException("Not Found Product Family By FamilyName : " + input.getOriginName());
            }

            if(input.getRemark() == null){
                if(input.getName() == null){
                    throw new RuntimeException("You must input name for updating !!");
                }
            }


            this.mProductFamilyService.updateData(input, family.getID());
        }catch(Exception ex){
            throw new QLException(ex);
        }
        ResponseModel r = new ResponseModel();
        r.setResult(true);
        return r;
    }

    public ResponseModel DeleteProductFamily(ProductFamilyDeleteInput input){
        try{
            ProductFamily family = null;
            if((family = this.mProductFamilyService.getByName(input.getName())) == null){
                throw new RuntimeException("Not Found Product Family By FamilyName : " + input.getName());
            }
            this.mProductFamilyService.deleteByID(family.getID());
        }catch (Exception ex){
            throw new QLException(ex);
        }
        ResponseModel r = new ResponseModel();
        r.setResult(true);
        return r;
    }
}
