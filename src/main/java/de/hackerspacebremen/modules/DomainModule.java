package de.hackerspacebremen.modules;

import com.google.inject.AbstractModule;

import de.hackerspacebremen.domain.APNSDataServiceImpl;
import de.hackerspacebremen.domain.AtomService;
import de.hackerspacebremen.domain.AuthAttemptServiceImpl;
import de.hackerspacebremen.domain.BasicHTTPAuthenticationServiceImpl;
import de.hackerspacebremen.domain.GCMDataServiceImpl;
import de.hackerspacebremen.domain.PropertyServiceImpl;
import de.hackerspacebremen.domain.RSSService;
import de.hackerspacebremen.domain.SpaceStatusServiceImpl;
import de.hackerspacebremen.domain.api.APNSDataService;
import de.hackerspacebremen.domain.api.AuthAttemptService;
import de.hackerspacebremen.domain.api.AuthenticationService;
import de.hackerspacebremen.domain.api.FeedService;
import de.hackerspacebremen.domain.api.GCMDataService;
import de.hackerspacebremen.domain.api.PropertyService;
import de.hackerspacebremen.domain.api.SpaceStatusService;
import de.hackerspacebremen.domain.demo.AuthenticateServiceTester;
import de.hackerspacebremen.domain.val.AuthServiceValidation;
import de.hackerspacebremen.domain.val.GCMDataServiceValidation;
import de.hackerspacebremen.domain.val.PropertyServiceValidation;
import de.hackerspacebremen.domain.val.SpaceStatusServiceValidation;
import de.hackerspacebremen.modules.binding.annotations.Atom;
import de.hackerspacebremen.modules.binding.annotations.Demo;
import de.hackerspacebremen.modules.binding.annotations.Proxy;
import de.hackerspacebremen.modules.binding.annotations.RSS;

public class DomainModule extends AbstractModule{

	@Override
	protected void configure() {
		bind(SpaceStatusService.class).to(SpaceStatusServiceImpl.class);
		bind(SpaceStatusService.class).annotatedWith(Proxy.class).to(SpaceStatusServiceValidation.class);
		
		bind(AuthenticationService.class).to(BasicHTTPAuthenticationServiceImpl.class);
		bind(AuthenticationService.class).annotatedWith(Proxy.class).to(AuthServiceValidation.class);
		// used for events, i.e. maker faire, works only on the test server
		//bind(AuthenticationService.class).annotatedWith(Demo.class).to(AuthenticateServiceDemo.class);
		// used for test accounts
		bind(AuthenticationService.class).annotatedWith(Demo.class).to(AuthenticateServiceTester.class);
		
		
		bind(GCMDataService.class).to(GCMDataServiceImpl.class);
		bind(GCMDataService.class).annotatedWith(Proxy.class).to(GCMDataServiceValidation.class);
		
		bind(APNSDataService.class).to(APNSDataServiceImpl.class);
		bind(APNSDataService.class).annotatedWith(Proxy.class).to(APNSDataServiceImpl.class);
		
		bind(PropertyService.class).to(PropertyServiceImpl.class);
		bind(PropertyService.class).annotatedWith(Proxy.class).to(PropertyServiceValidation.class);
		
		bind(FeedService.class).annotatedWith(RSS.class).to(RSSService.class);
		bind(FeedService.class).annotatedWith(Atom.class).to(AtomService.class);
		
		bind(AuthAttemptService.class).to(AuthAttemptServiceImpl.class);
	}

}
