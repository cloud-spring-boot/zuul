package com.max.zuul.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Component;

@Component
public class CorrelationIdReponseFilter extends ZuulFilter {

    private static final String CORRELATION_ID_HEADER = "tmx-correlation-id";

    private static final int FILTER_ORDER = 1;
    private static final boolean SHOULD_FILTER = true;

    private final Tracer tracer;

    @Autowired
    public CorrelationIdReponseFilter(Tracer tracer) {
        this.tracer = tracer;
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
        ctx.getResponse().addHeader(CORRELATION_ID_HEADER, tracer.getCurrentSpan().traceIdString());
        return null;
    }
}
