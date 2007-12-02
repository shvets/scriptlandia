//

package com.jweldin.gormtest;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import org.codehaus.groovy.grails.commons.*;
import org.codehaus.groovy.grails.orm.hibernate.cfg.*;
import org.hibernate.SessionFactory;
import org.springframework.core.io.*;
import org.springframework.core.io.support.*;

class Test
{
    public static void main(String[] args)
    {

        GrailsApplication grailsApplication = null;
        SessionFactory sessionFactory = null;

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        Resource[] groovyFiles = resolver.getResources("classpath*:**grails-app/**/*.groovy");

        grailsApplication = new DefaultGrailsApplication(groovyFiles, new MyInjectionOperation());

        DefaultGrailsDomainConfiguration config = new DefaultGrailsDomainConfiguration();
        config.setGrailsApplication(grailsApplication);

        Properties props = new Properties();
        props.put("hibernate.connection.username","sa");
        props.put("hibernate.connection.password","");
        props.put("hibernate.connection.url","jdbc:hsqldb:file:gorm/gorm");
        props.put("hibernate.connection.driver_class","org.hsqldb.jdbcDriver");
        props.put("hibernate.dialect","org.hibernate.dialect.HSQLDialect");
        props.put("hibernate.hbm2ddl.auto","create-drop");

        config.setProperties(props);

        sessionFactory = config.buildSessionFactory();

        GrailsHibernateUtil.configureDynamicMethods(sessionFactory, grailsApplication);

        GroovyClassLoader cl = grailsApplication.getClassLoader();

        println "\n-----------\nBefore Main\n-----------\n"

        def TestDomainScript = (GroovyObject)cl.loadClass("Main",false,true).newInstance()
        TestDomainScript.run()

        println "\n-----------\nAfter Main\n-----------\n"
        println "\nDone";

    }
}


import org.codehaus.groovy.grails.injection.GrailsInjectionOperation;
import org.codehaus.groovy.grails.injection.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.classgen.GeneratorContext;
import org.codehaus.groovy.control.CompilationFailedException;
import org.codehaus.groovy.control.CompilationUnit;
import org.codehaus.groovy.control.Phases;
import org.codehaus.groovy.control.SourceUnit;
import org.codehaus.groovy.grails.commons.GrailsResourceLoader;
import org.codehaus.groovy.grails.commons.GrailsResourceUtils;

public class MyInjectionOperation extends GrailsInjectionOperation 
{
	
        private static GrailsDomainClassInjector injector = new DefaultGrailsDomainClassInjector();;
	private GrailsResourceLoader resourceLoader;
	
	public void call(SourceUnit source, GeneratorContext context, ClassNode classNode) throws CompilationFailedException 
        {
		
                if(resourceLoader != null) 
                {
			try {
				URL url;
				if(GrailsResourceUtils.isGrailsPath(source.getName())) 
                                {
					url = resourceLoader.loadGroovySource(GrailsResourceUtils.getClassName(source.getName()));
				}
				else 
                                {
					url = resourceLoader.loadGroovySource(source.getName());
				}
				if(GrailsResourceUtils.isDomainClass(url)) 
                                {
                                        this.injector.performInjection(source,context,classNode);
				}
				
			} 
                        catch (MalformedURLException e) 
                        {
				println("Error loading URL during addition of compile time properties: " + e.getMessage(),e);
				throw new CompilationFailedException(Phases.CONVERSION,source,e);
			}			
		}
	}
	
	public void setResourceLoader(GrailsResourceLoader resourceLoader) 
        {
		this.resourceLoader = resourceLoader;
	}
}