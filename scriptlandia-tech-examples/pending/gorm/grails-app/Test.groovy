//

//package com.jweldin.gormtest;

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


