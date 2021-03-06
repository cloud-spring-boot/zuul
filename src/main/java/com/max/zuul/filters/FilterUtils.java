package com.max.zuul.filters;

import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

@Component
public class FilterUtils {

    static final String CORRELATION_ID_HEADER = "correlation-id";

    static final String PRE_FILTER_TYPE = "pre";
    static final String POST_FILTER_TYPE = "post";
    static final String ROUTE_FILTER_TYPE = "route";

    String getCorrelationId() {
        RequestContext ctx = RequestContext.getCurrentContext();

        if (ctx.getRequest().getHeader(CORRELATION_ID_HEADER) != null) {
            return ctx.getRequest().getHeader(CORRELATION_ID_HEADER);
        }
        else {
            return ctx.getZuulRequestHeaders().get(CORRELATION_ID_HEADER);
        }
    }

    void setCorrelationId(String id) {
        RequestContext ctx = RequestContext.getCurrentContext();
        ctx.addZuulRequestHeader(CORRELATION_ID_HEADER, id);
    }

}
