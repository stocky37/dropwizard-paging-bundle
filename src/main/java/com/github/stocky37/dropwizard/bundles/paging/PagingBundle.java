package com.github.stocky37.dropwizard.bundles.paging;

import io.dropwizard.Configuration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public abstract class PagingBundle<T extends Configuration> implements ConfiguredBundle<T> {
	@Override
	public void run(T configuration, Environment environment) throws Exception {
		environment.jersey().register(new AbstractBinder() {
			@Override
			protected void configure() {
				bind(getPageFactory(configuration)).to(PageFactory.class);
			}
		});
		environment.jersey().register(PageValueFactoryProvider.class);
		environment.jersey().register(PagingFeature.class);
	}

	protected abstract PageFactory getPageFactory(T config);

	@Override
	public void initialize(Bootstrap<?> bootstrap) {}
}
