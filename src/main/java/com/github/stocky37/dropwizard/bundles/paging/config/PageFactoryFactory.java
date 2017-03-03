package com.github.stocky37.dropwizard.bundles.paging.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.github.stocky37.dropwizard.bundles.paging.PageFactory;
import io.dropwizard.jackson.Discoverable;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", defaultImpl = OffsetPageFactoryFactory.class)
public interface PageFactoryFactory extends Discoverable {
	PageFactory getPageFactory();
}
