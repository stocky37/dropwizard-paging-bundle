package com.github.stocky37.dropwizard.bundles.paging.config;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.github.stocky37.dropwizard.bundles.paging.NumberedPageFactory;
import com.github.stocky37.dropwizard.bundles.paging.PageFactory;

@JsonTypeName("numbered")
public class NumberedPageFactoryFactory extends AbstractPageFactoryFactory {
	private String pageNumberParamName = "page";
	private String pageSizeParamName = "pageSize";
	private long defaultPage = 1;
	private long defaultPageSize = 25;

	public String getPageNumberParamName() {
		return pageNumberParamName;
	}

	public NumberedPageFactoryFactory setPageNumberParamName(String pageNumberParamName) {
		this.pageNumberParamName = pageNumberParamName;
		return this;
	}

	public String getPageSizeParamName() {
		return pageSizeParamName;
	}

	public NumberedPageFactoryFactory setPageSizeParamName(String pageSizeParamName) {
		this.pageSizeParamName = pageSizeParamName;
		return this;
	}

	public long getDefaultPage() {
		return defaultPage;
	}

	public NumberedPageFactoryFactory setDefaultPage(long defaultPage) {
		this.defaultPage = defaultPage;
		return this;
	}

	public long getDefaultPageSize() {
		return defaultPageSize;
	}

	public NumberedPageFactoryFactory setDefaultPageSize(long defaultPageSize) {
		this.defaultPageSize = defaultPageSize;
		return this;
	}

	@Override
	public PageFactory getPageFactory() {
		return new NumberedPageFactory(defaultPage, defaultPageSize, pageNumberParamName, pageSizeParamName, getTotalHeader());
	}
}
