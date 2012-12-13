package edu.ucla.wise.admin.initializer;

import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import edu.ucla.wise.admin.studyspaceparameters.StudySpaceParametersHandler;
import edu.ucla.wise.commons.AdminInfo;

public class AdminApplicationInitializer implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		// TODO: Destroy the application cleanly

	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		try{
			// TODO: Initialize the application
			// 1. Create a class that stores all study spaces and their associated
			// parameters
			if(!StudySpaceParametersHandler.initialize()){
				System.out.println("The WISE Application did not start correctly");
			}
			// 2. Initialize the admin application
			String contextPath = servletContextEvent.getServletContext().getContextPath();
			System.out.println("The context path is "+contextPath);
			AdminInfo.check_init(contextPath);


		}catch(IOException e){
			System.out.println("ERROR: The admin application did not start");
			e.printStackTrace();
		}

	}
}
