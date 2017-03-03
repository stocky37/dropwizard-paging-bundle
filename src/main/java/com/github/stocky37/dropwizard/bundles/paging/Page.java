package com.github.stocky37.dropwizard.bundles.paging;

import com.github.stocky37.dropwizard.bundles.paging.util.MoreMath;
import com.google.common.base.MoreObjects;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;

@ParametersAreNonnullByDefault
public final class Page {
	private final long offset;
	private final long limit;

	public Page(long offset, long limit) {
		checkArgument(offset >= 0);
		checkArgument(limit > 0);
		this.offset = offset;
		this.limit = limit;
	}

	public long getOffset() {
		return offset;
	}

	public long getLimit() {
		return limit;
	}

	public Optional<Page> getNextPage(long total) {
		final long newOffset = offset + limit;
		return Optional.ofNullable(newOffset >= total ? null : new Page(newOffset, limit));
	}

	public Optional<Page> getPreviousPage() {
		if(offset == 0) {
			return Optional.empty();
		}
		return Optional.of(new Page(Math.max(0, offset - limit), limit));
	}

	public Page getFirstPage() {
		return new Page(0, limit);
	}

	public Page getLastPage(long total) {
		return new Page((getLastPageNumber(total) - 1) * limit, limit);
	}

	private long getLastPageNumber(long total) {
		return MoreMath.ceilingDivide(total, limit);
	}

	@Override
	public int hashCode() {
		return Objects.hash(offset, limit);
	}

	@Override
	public boolean equals(@Nullable Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;
		Page that = (Page)o;
		return that.canEqual(this)
			&& offset == that.offset
			&& limit == that.limit;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("offset", offset)
			.add("limit", limit)
			.toString();
	}

	/**
	 * @param other the other object to check whether this object is allowed to be equal to it
	 * @return true if the other instance is an instance of Page
	 * @see <a href="http://www.artima.com/lejava/articles/equality.html">How to Write an Equality Method in Java</a>
	 **/
	protected boolean canEqual(Object other) {
		return other instanceof Page;
	}
}
