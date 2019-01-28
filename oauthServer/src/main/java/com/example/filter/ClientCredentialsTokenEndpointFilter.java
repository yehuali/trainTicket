package com.example.filter;

import com.example.security.authentication.BadCredentialsException;
import com.example.security.authentication.UsernamePasswordAuthenticationToken;
import com.example.security.core.Authentication;
import com.example.security.core.AuthenticationException;
import com.example.security.core.context.SecurityContextHolder;
import com.example.util.matcher.RequestMatcher;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ClientCredentialsTokenEndpointFilter extends AbstractAuthenticationProcessingFilter {


    private boolean allowOnlyPost = false;

    public ClientCredentialsTokenEndpointFilter() {
        this("/oauth/token");
    }

    public ClientCredentialsTokenEndpointFilter(String path) {
        super(path);
        setRequiresAuthenticationRequestMatcher(new ClientCredentialsRequestMatcher(path));
        // If authentication fails the type is "Form"
//        ((OAuth2AuthenticationEntryPoint) authenticationEntryPoint).setTypeName("Form");
    }

    public void setAllowOnlyPost(boolean allowOnlyPost) {
        this.allowOnlyPost = allowOnlyPost;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        if (allowOnlyPost && !"POST".equalsIgnoreCase(request.getMethod())) {
            throw new HttpRequestMethodNotSupportedException(request.getMethod(), new String[] { "POST" });
        }

        String clientId = request.getParameter("client_id");
        String clientSecret = request.getParameter("client_secret");
        //如果请求已经通过身份验证,不需要过滤器
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication;
        }
        if (clientId == null) {
            throw new BadCredentialsException("No client credentials presented");
        }
        if (clientSecret == null) {
            clientSecret = "";
        }
        clientId = clientId.trim();
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(clientId,
                clientSecret);

        return this.getAuthenticationManager().authenticate(authRequest);

    }


    protected static class ClientCredentialsRequestMatcher implements RequestMatcher {

        private String path;

        public ClientCredentialsRequestMatcher(String path) {
            this.path = path;

        }

        @Override
        public boolean matches(HttpServletRequest request) {
            String uri = request.getRequestURI();
            int pathParamIndex = uri.indexOf(';');

            if (pathParamIndex > 0) {
                // strip everything after the first semi-colon
                uri = uri.substring(0, pathParamIndex);
            }

            String clientId = request.getParameter("client_id");

            if (clientId == null) {
                // Give basic auth a chance to work instead (it's preferred anyway)
                return false;
            }

            if ("".equals(request.getContextPath())) {
                return uri.endsWith(path);
            }

            return uri.endsWith(request.getContextPath() + path);
        }
    }
}
