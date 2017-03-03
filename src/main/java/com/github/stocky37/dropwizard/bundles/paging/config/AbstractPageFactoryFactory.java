package com.github.stocky37.dropwizard.bundles.paging.config;

public abstract class AbstractPageFactoryFactory implements PageFactoryFactory {
	private String totalHeader = "X-Total-Count";

	public String getTotalHeader() {
		return totalHeader;
	}

	public AbstractPageFactoryFactory setTotalHeader(String totalHeader) {
		this.totalHeader = totalHeader;
		return this;
	}
}
