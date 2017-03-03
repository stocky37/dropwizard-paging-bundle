package com.github.stocky37.dropwizard.bundles.paging;

import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.internal.inject.AbstractContainerRequestValueFactory;
import org.glassfish.jersey.server.internal.inject.AbstractValueFactoryProvider;
import org.glassfish.jersey.server.internal.inject.MultivaluedParameterExtractorProvider;
import org.glassfish.jersey.server.internal.inject.ParamInjectionResolver;
import org.glassfish.jersey.server.model.Parameter;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PageValueFactoryProvider extends AbstractValueFactoryProvider {

	private final PageFactory pageFactory;

	@Inject
	public PageValueFactoryProvider(
		MultivaluedParameterExtractorProvider mpep,
		ServiceLocator locators,
		PageFactory pageFactory
	) {
		super(mpep, locators, Parameter.Source.UNKNOWN);
		this.pageFactory = pageFactory;
	}

	@Override
	protected Factory<Page> createValueFactory(Parameter parameter) {
		return parameter.isAnnotationPresent(PagingParam.class) && parameter.getRawType().isAssignableFrom(Page.class)
			? new PageContainerRequestValueFactory(pageFactory)
			: null;
	}

	@Singleton
	static final class InjectionResolver extends ParamInjectionResolver<PagingParam> {
		public InjectionResolver() {
			super(PageValueFactoryProvider.class);
		}
	}

	static class PageContainerRequestValueFactory extends AbstractContainerRequestValueFactory<Page> {
		private final PageFactory pageFactory;

		PageContainerRequestValueFactory(PageFactory pageFactory) {
			this.pageFactory = pageFactory;
		}

		@Override
		public Page provide() {
			return pageFactory.build(getContainerRequest());
		}
	}
}
