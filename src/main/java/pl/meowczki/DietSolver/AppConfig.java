package pl.meowczki.DietSolver;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@org.springframework.boot.autoconfigure.domain.EntityScan("pl.meowczki")   
@EnableJpaRepositories("pl.meowczki")
@ComponentScan(basePackages="pl.meowczki")
@EnableWebMvc
@EnableTransactionManagement

public class AppConfig   implements  WebMvcConfigurer {

	
	
	
	    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
	            "classpath:/META-INF/resources/", "classpath:/resources/",
	            "classpath:/static/", "classpath:/public/" };

	    @Override
	    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	        registry.addResourceHandler("/**")
	            .addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
	    }
	
	  
	 
	 
	
}
