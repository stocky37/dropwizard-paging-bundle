package com.github.stocky37.dropwizard.bundles.paging.config;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.github.stocky37.dropwizard.bundles.paging.OffsetPageFactory;
import com.github.stocky37.dropwizard.bundles.paging.PageFactory;

@JsonTypeName("offset")
public class OffsetPageFactoryFactory extends AbstractPageFactoryFactory {

	private String offsetParameterName = "offset";
	private String limitParameterName = "limit";
	private long defaultOffset = 0;
	private long defaultLimit = 25;

	public String getOffsetParameterName() {
		return offsetParameterName;
	}

	public OffsetPageFactoryFactory setOffsetParameterName(String offsetParameterName) {
		this.offsetParameterName = offsetParameterName;
		return this;
	}

	public String getLimitParameterName() {
		return limitParameterName;
	}

	public OffsetPageFactoryFactory setLimitParameterName(String limitParameterName) {
		this.limitParameterName = limitParameterName;
		return this;
	}

	public long getDefaultOffset() {
		return defaultOffset;
	}

	public OffsetPageFactoryFactory setDefaultOffset(long defaultOffset) {
		this.defaultOffset = defaultOffset;
		return this;
	}

	public long getDefaultLimit() {
		return defaultLimit;
	}

	public OffsetPageFactoryFactory setDefaultLimit(long defaultLimit) {
		this.defaultLimit = defaultLimit;
		return this;
	}

	@Override
	public PageFactory getPageFactory() {
		return new OffsetPageFactory(defaultOffset, defaultLimit, offsetParameterName, limitParameterName, getTotalHeader());
	}
}
