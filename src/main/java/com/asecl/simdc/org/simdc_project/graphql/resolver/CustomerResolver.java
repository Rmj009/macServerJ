package com.asecl.simdc.org.simdc_project.graphql.resolver;

import com.asecl.simdc.org.simdc_project.db.entity.Customer;
import com.asecl.simdc.org.simdc_project.db.entity.User;
import com.asecl.simdc.org.simdc_project.db.service.CustomerService;
import com.asecl.simdc.org.simdc_project.db.service.UserService;
import com.asecl.simdc.org.simdc_project.exception.QLException;
import com.asecl.simdc.org.simdc_project.graphql.entity.input.*;
import com.asecl.simdc.org.simdc_project.graphql.entity.out.ResponseModel;
import com.github.pagehelper.PageHelper;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.ServletConfig;
import java.util.List;


@Component
public class CustomerResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    @Autowired
    private UserService mUserService;

    @Autowired
    private CustomerService mcCustomerService;

    public ResponseModel CreateCustomer(CustomerCreationInput model){
        try{
            User owner = null;
            if((owner = this.mUserService.getByEmployeeID(model.getOwnerEmplayeeId()) ) == null){
                throw new RuntimeException("Not Found User By EmplayeeId : " + model.getOwnerEmplayeeId());
            }

            if(this.mcCustomerService.getCountByName(model.getName()) > 0){
                throw new RuntimeException("Customer Name : " + model.getName() + " Is Already Exists !!");
            }
            Customer customer = new Customer();
            customer.setName(model.getName());
            customer.setPhone(model.getPhone());
            customer.setRemark(model.getPhone());
            customer.setCreatedOwner(owner);
            mcCustomerService.insert(customer);

        }
        catch (Exception e)
        {
            throw new QLException(e);
        }

        ResponseModel rm = new ResponseModel();
        rm.setResult(true);
        return rm;

    }

    public List<Customer> QueryCustomer(CustomerQueryInput input ){
        List<Customer> customer = null;
        try{

            if(input == null){
                customer = this.mcCustomerService.getAll();
            }else{
                if(input.getPageNumber() > 0 && input.getPageSize() > 0){
                    //把輸出的資料進行page切割
                    PageHelper.startPage(input.getPageNumber(), input.getPageSize());
                }
                customer = this.mcCustomerService.get(input);
            }

        }catch(Exception ex){
            throw new QLException(ex);
        }
        return customer;
    }

    public ResponseModel UpdateCustomer(CustomerUpgradeInput input){

        try{
            Customer c = null;
            if((c = this.mcCustomerService.getByName(input.getOriginName())) == null){
                throw new RuntimeException("Not Found Customer By OriginName : " + input.getOriginName());
            }

            if( this.mcCustomerService.getCountByNameInTestconfiguration(input.getOriginName()) > 0){
                throw new RuntimeException("Customer id is binding ， You need unbinding !!");
            }

            this.mcCustomerService.updateData(input,c.getID());
        }catch(Exception ex){
            throw new QLException(ex);
        }
        ResponseModel r = new ResponseModel();
        r.setResult(true);
        return r;
    }

    public ResponseModel DeleteCustomer(CustomerDeleteInput model){

        try{
            Customer c = null;
            if((this.mcCustomerService.getCountByName(model.getName())) == 0){
                throw new RuntimeException("Not Found Customer By Name : " + model.getName());
            }

            if( this.mcCustomerService.getCountByNameInTestconfiguration(model.getName()) > 0){
                throw new RuntimeException("Customer id is binding ， You need unbinding !!");
            }

            if((c = this.mcCustomerService.getByName(model.getName())) == null){
                throw new RuntimeException("Customer Name : " + model.getName()  + " Is Not Exists !!");
            }

            this.mcCustomerService.deleteByName(c.getName());
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
