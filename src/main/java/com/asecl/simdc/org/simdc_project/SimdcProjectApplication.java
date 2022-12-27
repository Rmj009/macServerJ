package com.asecl.simdc.org.simdc_project;


import com.asecl.simdc.org.simdc_project.util.Constant;
import com.hans.grpcserver.grpc.GreeterGrpc;
import com.hans.grpcserver.grpc.HelloProto;
import graphql.schema.GraphQLEnumType;
import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;
import java.util.TimeZone;
import java.util.logging.Logger;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.asecl.simdc.org.simdc_project.db.mapper")
public class SimdcProjectApplication  {

	public static void main(String[] args) throws IOException, InterruptedException {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Taipei"));
		SpringApplication.run(SimdcProjectApplication.class, args);

	}

	@Bean
	public GraphQLEnumType EnumIncreateType(){
		GraphQLEnumType.Builder builder = GraphQLEnumType.newEnum().name(Constant.EnumIncreateType);
		builder.description(Constant.EnumIncreateType);
		builder.value(Constant.EnumIncreateType_Even);
		builder.value(Constant.EnumIncreateType_EvenRotateId);
		builder.value(Constant.EnumIncreateType_Odd);
		builder.value(Constant.EnumIncreateType_OddRotateId);
		builder.value(Constant.EnumIncreateType_OddAndEvenRotateId);
		builder.value(Constant.EnumIncreateType_Normal);
		builder.value(Constant.EnumIncreateType_NormalRotateId);
		return builder.build();
	}

	@Bean
	public GraphQLEnumType EnumMacType(){
		GraphQLEnumType.Builder builder = GraphQLEnumType.newEnum().name(Constant.EnumMacType);
		builder.description(Constant.EnumMacType);
		builder.value(Constant.EnumMacType_BT);
		builder.value(Constant.EnumMacType_Wifi);
		return builder.build();
	}

	

}
