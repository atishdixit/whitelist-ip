package com.ext.whitelist.filter;

import com.ext.whitelist.domain.HostDetails;
import com.ext.whitelist.domain.HostRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;
//https://github.com/gavinklfong/servlet-spring-forex-trade/blob/master/src/main/java/space/gavinklfong/forex/config/FilterConfig.java

@NoArgsConstructor
@Order(1)
@Component
public class WhiteListedIPFilter implements Filter {

    private static final String LOCALHOST_V6 = "0:0:0:0:0:0:0:1";
    private static final String LOCALHOST_V4 = "127.0.0.1";

    @Autowired
    private HostRepository hostRepository;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        String clientIpAddress = getClientIpAddress(servletRequest);

        Optional<HostDetails> hostDetails = hostRepository.findByIpAddress(clientIpAddress);
        if (hostDetails.isPresent()) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            FilterResponse filterResponse = new FilterResponse(HttpStatus.UNAUTHORIZED.value(),
                    "This IP address is not whiteListed...");
            ObjectMapper mapper = new ObjectMapper();
            httpResponse.getWriter().write(mapper.writeValueAsString(filterResponse));
            return;
        }
    }

    record FilterResponse(int status, String message) {
    }

    private String getClientIpAddress(ServletRequest request) {
        if (LOCALHOST_V6.equals(request.getRemoteAddr())) {
            return LOCALHOST_V4;
        } else {
            return request.getRemoteAddr();
        }
    }
}
