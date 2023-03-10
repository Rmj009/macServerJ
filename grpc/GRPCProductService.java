package com.asecl.simdc.org.simdc_project.grpc;

//import com.asecl.simdc.org.simdc_project.db.service.ProductITService;
import com.asecl.simdc.org.simdc_project.security.LockManager;
import com.simdc_project.productmacfromit.grpc.ProductMacFromITProto;
import com.simdc_project.productmacfromit.grpc.ProductMacServiceGrpc;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

@GrpcService
public class GRPCProductService extends ProductMacServiceGrpc.ProductMacServiceImplBase {

//    @Autowired
//    private ProductITService mITService;

    @Autowired
    private LockManager mLock;

    @Override
    public void sendNewMacInfo(com.simdc_project.productmacfromit.grpc.ProductMacFromITProto.ProductInfoRequest request,
                               io.grpc.stub.StreamObserver<com.simdc_project.productmacfromit.grpc.ProductMacFromITProto.ProductInfoReply> responseObserver) {


        List<ProductMacFromITProto.ProductMacInfos> items = request.getProductInfosList();
        boolean isOK = true;
        String errorMsg = "";
      //  boolean result = false;
        String key = "CreateMacProduct_";
        try{
//            if(items.size() > 0){
//                key += items.get(0).getPid() + "_" + items.get(0).getLotCode();
//                mLock.TryLock(key, new ILockCallback<Boolean>() {
//                    @Override
//                    public Boolean exec() {
//                        return mITService.CreateProductMacAddress(items, items.get(0).getLotCode(), items.get(0).getPid());
//                    }
//                });
//            }else{
//                throw new RuntimeException("Empty item error !!");
//            }
        }catch(Exception ex){
            isOK = false;
//            errorMsg = Arrays.toString(ex.getStackTrace());
            errorMsg =  ex.toString();
        }

        ProductMacFromITProto.ProductInfoReply reply = ProductMacFromITProto.ProductInfoReply.newBuilder().setResult(isOK).setErrMsg(errorMsg).build();
        // ????????????????????????onNext????????????HelloReply
        responseObserver.onNext(reply);
        // ??????onCompleted????????????????????????????????????
        responseObserver.onCompleted();
    }
}
