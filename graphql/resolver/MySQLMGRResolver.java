package com.asecl.simdc.org.simdc_project.graphql.resolver;

import com.asecl.simdc.org.simdc_project.db.entity.MySQLMGR;
import com.asecl.simdc.org.simdc_project.db.service.MySQLMGRService;
import com.asecl.simdc.org.simdc_project.exception.QLException;
import com.asecl.simdc.org.simdc_project.graphql.entity.out.ResponseMGRStatus;
import com.asecl.simdc.org.simdc_project.util.ILockCallback;
import com.asecl.simdc.org.simdc_project.security.LockManager;
import graphql.kickstart.tools.GraphQLMutationResolver;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MySQLMGRResolver implements GraphQLQueryResolver, GraphQLMutationResolver {

    @Autowired
    private MySQLMGRService mService;

    @Autowired
    private LockManager mLock;

    public List<ResponseMGRStatus> QueryMySQLMGR(){
        List<ResponseMGRStatus> statuses = new ArrayList<>();
        try{
            List<MySQLMGR> mgrs =  this.mService.getAll();
            for(int i=0; i < mgrs.size(); i++) {
                ResponseMGRStatus model = new ResponseMGRStatus();
                model.setCHANNEL_NAME(mgrs.get(i).getCHANNEL_NAME());
                model.setMEMBER_ID(mgrs.get(i).getMEMBER_ID());
                model.setMEMBER_HOST(mgrs.get(i).getMEMBER_HOST());
                model.setMEMBER_PORT(mgrs.get(i).getMEMBER_PORT());
                model.setMEMBER_STATE(mgrs.get(i).getMEMBER_STATE());
                model.setMEMBER_ROLE(mgrs.get(i).getMEMBER_ROLE());
                model.setMEMBER_VERSION(mgrs.get(i).getMEMBER_VERSION());
                statuses.add(model);
            }
        }catch(Exception ex){
            throw new QLException(ex);
        }
        return statuses;
    }

    public List<ResponseMGRStatus> MYSQL_MGR_Force_Single_Member_For_This_Host(){
        List<ResponseMGRStatus> statuses = new ArrayList<>();
        try{
            List<MySQLMGR> result =  new ArrayList<>();

            result = mLock.TryLock("MySQLMGRLock", new ILockCallback<List<MySQLMGR>>() {
                @Override
                public List<MySQLMGR> exec() {
                   return mService.forceToBecomeSingleMember();
                }
            });

            for(int i=0; i < result.size(); i++) {
                ResponseMGRStatus model = new ResponseMGRStatus();
                model.setCHANNEL_NAME(result.get(i).getCHANNEL_NAME());
                model.setMEMBER_ID(result.get(i).getMEMBER_ID());
                model.setMEMBER_HOST(result.get(i).getMEMBER_HOST());
                model.setMEMBER_PORT(result.get(i).getMEMBER_PORT());
                model.setMEMBER_STATE(result.get(i).getMEMBER_STATE());
                model.setMEMBER_ROLE(result.get(i).getMEMBER_ROLE());
                model.setMEMBER_VERSION(result.get(i).getMEMBER_VERSION());
                statuses.add(model);
            }
        }catch(Exception ex){
            throw new QLException(ex);
        }
        return statuses;
    }
}
