package com.github.stocky37.dropwizard.bundles.paging;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.Priority;
import javax.ws.rs.Priorities;

@Priority(Priorities.HEADER_DECORATOR)
@ParametersAreNonnullByDefault
public final class OffsetPageFactory extends AbstractPageFactory {

	public OffsetPageFactory(long defaultIndex, long defaultSize, String indexParam, String sizeParam, String totalHeader) {
		super(defaultIndex, defaultSize, indexParam, sizeParam, totalHeader);
	}

	@Override
	protected Page buildPage(long offset, long limit) {
		return new Page(offset, limit);
	}

	@Override
	protected long getIndexFromOffset(Page page) {
		return page.getOffset();
	}

	@Override
	protected long getSizeFromOffset(Page page) {
		return page.getLimit();
	}
}
