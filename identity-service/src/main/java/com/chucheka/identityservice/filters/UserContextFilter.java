//package com.chucheka.identityservice.filters;
//
//import jakarta.servlet.*;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//@Component
//public class UserContextFilter implements Filter {
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        Filter.super.init(filterConfig);
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
//
//        UserContextHolder.getContext().setCorrelationId(httpServletRequest.getHeader(UserContext.CORRELATION_ID) );
//
//        UserContextHolder.getContext().setUserId(
//                httpServletRequest.getHeader(UserContext.USER_ID));
//        UserContextHolder.getContext().setAuthToken(
//                httpServletRequest.getHeader(UserContext.AUTH_TOKEN));
//        UserContextHolder.getContext().setOrganizationId(
//                httpServletRequest.getHeader(UserContext.ORGANIZATION_ID));
//        logger.debug("UserContextFilter Correlation id: {}",
//                UserContextHolder.getContext().getCorrelationId());
//        filterChain.doFilter(httpServletRequest, servletResponse);
//    }
//    // Not showing the empty init and destroy methods
//    }
//
//    @Override
//    public void destroy() {
//        Filter.super.destroy();
//    }
//}
