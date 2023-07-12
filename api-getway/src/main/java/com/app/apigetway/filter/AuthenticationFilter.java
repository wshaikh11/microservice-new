package com.app.apigetway.filter;

import com.app.apigetway.util.JwtUtil;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private  RouteValidator routeValidator;

    @Autowired
    private JwtUtil jwtUtil;

//    @Autowired
//    private RestTemplate template;

    public AuthenticationFilter(RouteValidator routeValidator) {
        super(Config.class);
        this.routeValidator = routeValidator;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (((exchange, chain) -> {
            if(routeValidator.isSecured.test(exchange.getRequest())){
                if(!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                    throw new RuntimeException("Missing authorization header");
                }
                String authHeader = exchange.getRequest().getHeaders().get(org.springframework.http.HttpHeaders.AUTHORIZATION).get(0);
                if(authHeader !=null && authHeader.startsWith("Bearer ")){
                    authHeader = authHeader.substring(7);
                }
                try {
//                    template.getForObject("http://auth-server/validate?token"+authHeader,String.class);
                    jwtUtil.isTokenValid(authHeader);
                } catch (Exception e) {
                    throw new RuntimeException("un-authorised access to application");
                }
            }
            return chain.filter(exchange);
        }));
    }

    public static class Config {

    }
}
