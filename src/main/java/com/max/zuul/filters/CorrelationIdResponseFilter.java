package com.max.zuul.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Add correlation-id header back to response for a client.
 */
@Component
public class CorrelationIdResponseFilter extends ZuulFilter {

    private static final Logger LOG = LoggerFactory.getLogger(CorrelationIdResponseFilter.class);

    private static final int FILTER_ORDER = 1;
    private static final boolean SHOULD_FILTER = true;

    private final FilterUtils filterUtils;

    @Autowired
    public CorrelationIdResponseFilter(FilterUtils filterUtils) {
        this.filterUtils = filterUtils;
    }

    @Override
    public String filterType() {
        return FilterUtils.POST_FILTER_TYPE;
    }

    @Override
    public int filterOrder() {
        return FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return SHOULD_FILTER;
    }

    @Override
    public Object run() {

        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.getResponse().addHeader(FilterUtils.CORRELATION_ID_HEADER, filterUtils.getCorrelationId());

        LOG.info("Request {} with correlation-id {} completed",
                ctx.getRequest().getRequestURI(),
                filterUtils.getCorrelationId());

        return null;
    }
}
