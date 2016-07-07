package com.prodyna.pac.web;
import com.netflix.client.ClientException;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.ProxyRequestHelper;
import org.springframework.cloud.netflix.zuul.filters.route.RibbonCommandFactory;
import org.springframework.cloud.netflix.zuul.filters.route.RibbonRoutingFilter;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

/**
 * Created by bjoern on 05.07.16.
 */
@Component
public class AuthAwareRoutingFilter extends ZuulFilter {
    private static final Logger log = LoggerFactory.getLogger(AuthAwareRoutingFilter.class);
    ProxyRequestHelper helper = new ProxyRequestHelper();
    RibbonRoutingFilter delegateFilter;

    @Autowired
    RibbonCommandFactory<?> ribbonCommandFactory;

    @Value("${auth.serviceId:support-auth}")
    String authServiceId = "";

    @Value("${auth.authenticated:authenticated}")
    String authAttributeName;

    @Value("${auth.authenticationRequired:true}")
    boolean isAuthenticationRequired;

    @PostConstruct
    void initDelegateFilter() {

        delegateFilter = new RibbonRoutingFilter(helper, ribbonCommandFactory);
    }

    @Override
    public String filterType() {

        return "route";
    }

    @Override
    public int filterOrder() {

        return delegateFilter.filterOrder();
    }

    public boolean shouldFilter() {
        return delegateFilter.shouldFilter();
    }

    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();

        if (!isAuthenticationRequired(context)) {
            return delegateFilter.run();
        }

        log.info("Initiating authentication for request uri: {}.", getRequestUri(context));

        try {
            String originalServiceId = (String) context.get("serviceId");

            context.set("serviceId", authServiceId);
            ClientHttpResponse authResponse = (ClientHttpResponse) delegateFilter.run();
            context.set("serviceId", originalServiceId);

            if (isAuthenticationSuccessful(authResponse)) {
                return delegateFilter.run();
            }

            setResponse(authResponse);

            return authResponse;
        } catch (Exception e) {
            context.set("error.status_code", SC_INTERNAL_SERVER_ERROR);
            context.set("error.exception", e);
        }

        return null;
    }

    boolean isAuthenticationSuccessful(ClientHttpResponse response) throws IOException {
        return response != null && response.getStatusCode().is2xxSuccessful();
    }

    void setResponse(ClientHttpResponse resp) throws ClientException, IOException {
        helper.setResponse(resp.getStatusCode().value(),
                resp.getBody() == null ? null : resp.getBody(), resp.getHeaders());
    }

    boolean isAuthenticationRequired(RequestContext requestContext) {
        return isAuthenticationRequired && requestContext.getRequest().getAttribute(authAttributeName) == null;
    }

    String getRequestUri(RequestContext requestContext) {

        return (String) requestContext.get("requestURI");
    }
}