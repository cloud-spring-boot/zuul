package com.max.zuul.filters;

import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class FilterUtils {

    private static final Logger LOG = LoggerFactory.getLogger(FilterUtils.class);

    static final String CORRELATION_ID_HEADER = "Correlation-Id";

    static final String PRE_FILTER_TYPE = "pre";
    static final String POST_FILTER_TYPE = "post";
    static final String ROUTE_FILTER_TYPE = "route";

    String getCorrelationId() {
        RequestContext ctx = RequestContext.getCurrentContext();

        if (ctx.getRequest().getHeader(CORRELATION_ID_HEADER) != null) {
            return ctx.getRequest().getHeader(CORRELATION_ID_HEADER);
        }

        return ctx.getZuulRequestHeaders().get(CORRELATION_ID_HEADER);
    }

    void setCorrelationId(String id) {
        RequestContext.getCurrentContext().addZuulRequestHeader(CORRELATION_ID_HEADER, id);
    }

}
