package com.github.stocky37.dropwizard.bundles.paging;

import com.google.common.collect.ImmutableSet;
import io.dropwizard.jersey.params.LongParam;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.util.Optional;
import java.util.Set;

import static org.apache.commons.lang3.StringUtils.isBlank;

@ParametersAreNonnullByDefault
public abstract class AbstractPageFactory implements PageFactory {
	protected final String indexParam;
	protected final String sizeParam;
	protected final long defaultIndex;
	protected final long defaultSize;
	protected final String totalHeader;

	public AbstractPageFactory(long defaultIndex, long defaultSize, String indexParam, String sizeParam, String totalHeader) {
		this.defaultIndex = defaultIndex;
		this.defaultSize = defaultSize;
		this.indexParam = indexParam;
		this.sizeParam = sizeParam;
		this.totalHeader = totalHeader;
	}

	@Override
	public void filter(ContainerRequestContext request, ContainerResponseContext response) {
		if(response.getStatusInfo().getFamily().equals(Response.Status.Family.SUCCESSFUL)) {
			final PaginatedList<?> results = (PaginatedList<?>)response.getEntity();
			final Page currentPage = build(request);

			response.getHeaders().putSingle(totalHeader, results.getTotal());
			response.getHeaders().add(
				HttpHeaders.LINK, buildLinks(request.getUriInfo().getAbsolutePathBuilder(), currentPage, results.getTotal())
			);
		}
	}

	@Override
	public Page build(ContainerRequestContext request) throws IllegalArgumentException {
		final long index = getLongParamValue(request, indexParam).orElse(defaultIndex);
		final long size = getLongParamValue(request, sizeParam).orElse(defaultSize);
		return buildPage(index, size);
	}

	public Set<Link> buildLinks(UriBuilder uriBuilder, Page currentPage, long total) {
		final ImmutableSet.Builder<Link> builder = ImmutableSet.builder();
		builder.add(link(uriBuilder, currentPage.getFirstPage(), "first"));
		builder.add(link(uriBuilder, currentPage.getLastPage(total), "last"));
		currentPage.getPreviousPage().ifPresent(p -> builder.add(link(uriBuilder, p, "prev")));
		currentPage.getNextPage(total).ifPresent(p -> builder.add(link(uriBuilder, p, "next")));
		return builder.build();
	}

	protected Optional<Long> getLongParamValue(ContainerRequestContext request, String param) {
		final String value = request.getUriInfo().getQueryParameters().getFirst(param);
		return isBlank(value) ? Optional.empty() : Optional.of(new LongParam(value).get());
	}

	private Link link(UriBuilder uriBuilder, Page page, String rel) {
		return Link.fromUriBuilder(uriBuilder
			.replaceQueryParam(indexParam, getIndexFromOffset(page))
			.replaceQueryParam(sizeParam, getSizeFromOffset(page))
		).rel(rel).build();
	}

	protected abstract Page buildPage(long index, long size) throws IllegalArgumentException;
	protected abstract long getIndexFromOffset(Page page);
	protected abstract long getSizeFromOffset(Page page);
}