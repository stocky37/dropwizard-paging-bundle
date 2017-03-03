package com.github.stocky37.dropwizard.bundles.paging;

import javax.inject.Inject;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class PagingFeature implements DynamicFeature {
	private final PageFactory pageFactory;

	@Inject
	public PagingFeature(PageFactory pageFactory) {
		this.pageFactory = pageFactory;
	}

	@Override
	public void configure(ResourceInfo resourceInfo, FeatureContext context) {
		if(shouldRegister(resourceInfo.getResourceMethod())) {
			context.register(pageFactory);
		}
	}

	private boolean shouldRegister(Method method) {
		if(!method.getReturnType().isAssignableFrom(PaginatedList.class)) {
			return false;
		}
		for(Parameter parameter : method.getParameters()) {
			if(parameter.isAnnotationPresent(PagingParam.class) && parameter.getType().isAssignableFrom(Page.class)) {
				return true;
			}
		}
		return false;
	}
}
