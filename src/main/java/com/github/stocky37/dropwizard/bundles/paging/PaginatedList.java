package com.github.stocky37.dropwizard.bundles.paging;

import com.google.common.collect.ForwardingList;
import com.google.common.collect.ImmutableList;

import java.util.Collection;
import java.util.List;

public class PaginatedList<T> extends ForwardingList<T> {
	private final List<T> delegate;
	private final long total;

	public static <T> PaginatedList<T> from(Collection<? extends T> original, long total) {
		return new PaginatedList<>(ImmutableList.copyOf(original), total);
	}

	private PaginatedList(List<T> delegate, long total) {
		this.delegate = delegate;
		this.total = total;
	}

	public List<T> getDelegate() {
		return delegate;
	}

	public long getTotal() {
		return total;
	}

	@Override
	protected List<T> delegate() {
		return getDelegate();
	}
}
