package com.max.zuul.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CorrelationIdFilter extends ZuulFilter {

    private static final Logger LOG = LoggerFactory.getLogger(CorrelationIdFilter.class);

    private static final int FILTER_ORDER = 1;
    private static final boolean SHOULD_FILTER = true;

    private final FilterUtils filterUtils;

    @Autowired
    public CorrelationIdFilter(FilterUtils filterUtils) {
        this.filterUtils = filterUtils;
    }

    @Override
    public String filterType() {
        return FilterUtils.PRE_FILTER_TYPE;
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
        if (isCorrelationIdPresent()) {
            MDC.put("correlation-id", filterUtils.getCorrelationId());
            LOG.info("PASSING for {}", getRequestUri());
        }
        else {
            // generate correlation id
            filterUtils.setCorrelationId(UUID.randomUUID().toString());

            MDC.put("correlation-id", filterUtils.getCorrelationId());
            LOG.info("START for {}", getRequestUri());
        }

        return null;
    }

    private static String getRequestUri(){
        return RequestContext.getCurrentContext().getRequest().getRequestURI();
    }

    private boolean isCorrelationIdPresent() {
        return filterUtils.getCorrelationId() != null;
    }
}
