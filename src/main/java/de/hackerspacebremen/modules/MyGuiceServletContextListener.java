package de.hackerspacebremen.modules;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class MyGuiceServletContextListener extends GuiceServletContextListener{

	/**{@inheritedDoc}*/
	@Override
	protected Injector getInjector() {
		return Guice.createInjector(new OSNModule(), new ServletsModule(), new DataModule(),
				new DomainModule());
	}

}
