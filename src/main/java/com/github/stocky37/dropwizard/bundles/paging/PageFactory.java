package com.github.stocky37.dropwizard.bundles.paging;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.UriBuilder;
import java.util.Set;

public interface PageFactory extends ContainerResponseFilter {
	Page build(ContainerRequestContext request) throws IllegalArgumentException;
	Set<Link> buildLinks(UriBuilder uriBuilder, Page currentPage, long total);
}
