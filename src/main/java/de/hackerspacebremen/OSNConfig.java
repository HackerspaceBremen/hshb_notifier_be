package de.hackerspacebremen;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.googlecode.objectify.ObjectifyService;

import de.hackerspacebremen.data.entities.ApnsData;
import de.hackerspacebremen.data.entities.GcmData;
import de.hackerspacebremen.data.entities.Property;
import de.hackerspacebremen.data.entities.SpaceStatus;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages="de.hackerspacebremen")
public class OSNConfig extends WebMvcConfigurerAdapter{

	@PostConstruct
	public void init(){
		ObjectifyService.register(SpaceStatus.class);
        ObjectifyService.register(GcmData.class);
        ObjectifyService.register(ApnsData.class);
        ObjectifyService.register(Property.class);
	}
}
