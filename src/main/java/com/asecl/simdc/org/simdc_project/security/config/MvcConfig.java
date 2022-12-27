package com.asecl.simdc.org.simdc_project.security.config;

import com.asecl.simdc.org.simdc_project.util.Constant;
import graphql.execution.ExecutionStrategy;
import graphql.schema.GraphQLSchema;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class MvcConfig implements WebMvcConfigurer {
//    @Value("${fw.upload.filePath}")
//    private String mFwSaveFilePath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        Constant.FW_DIR_PATH = "file:" + mFwSaveFilePath;

        // graphiql used
        registry
                .addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/public/")
                .addResourceLocations("classpath:/resources/")
                .addResourceLocations("classpath:/META-INF/resources/");

//        registry
//                .addResourceHandler(mFwStaticUrlPath)
//                .addResourceLocations(Constant.FW_DIR_PATH);
    }

    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }
}
