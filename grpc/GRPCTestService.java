package com.asecl.simdc.org.simdc_project.grpc;

import com.hans.grpcserver.grpc.GreeterGrpc;
import com.hans.grpcserver.grpc.HelloProto;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;

@GrpcService
public class GRPCTestService extends GreeterGrpc.GreeterImplBase {

    @Override
    public void sayHello(HelloProto.HelloRequest req, StreamObserver<HelloProto.HelloReply> responseObserver) {
        HelloProto.HelloReply reply = HelloProto.HelloReply.newBuilder().setMessage("Hello " + req.getName()).build();
        // 使用响应监视器的onNext方法返回HelloReply
        responseObserver.onNext(reply);
        // 使用onCompleted方法指定本次调用已经完成
        responseObserver.onCompleted();
    }
}
