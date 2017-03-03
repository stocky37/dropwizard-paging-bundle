package com.github.stocky37.dropwizard.bundles.paging;

import com.github.stocky37.dropwizard.bundles.paging.util.MoreMath;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.Priority;
import javax.ws.rs.Priorities;

@Priority(Priorities.HEADER_DECORATOR)
@ParametersAreNonnullByDefault
public class NumberedPageFactory extends AbstractPageFactory {
	public NumberedPageFactory(long defaultIndex, long defaultSize, String indexParam, String sizeParam, String totalHeader) {
		super(defaultIndex, defaultSize, indexParam, sizeParam, totalHeader);
	}

	@Override
	protected Page buildPage(long index, long size) {
		return new Page((index - 1) * size, size);
	}

	@Override
	protected long getIndexFromOffset(Page page) {
		return page.getOffset() > 0 ? MoreMath.ceilingDivide(page.getOffset(), page.getLimit()) + 1 : 1;
	}

	@Override
	protected long getSizeFromOffset(Page page) {
		return page.getLimit();
	}
}
