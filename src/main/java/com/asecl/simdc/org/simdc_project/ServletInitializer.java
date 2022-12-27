package com.asecl.simdc.org.simdc_project;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.context.request.RequestContextListener;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SimdcProjectApplication.class);
	}

	@Override
	public void onStartup( ServletContext servletContext ) throws ServletException {
		super.onStartup( servletContext );
//		servletContext.addListener( new RequestContextListener());
	}

}
