//

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
