package com.example.filter;

import com.example.security.authentication.AuthenticationManager;
import com.example.security.authentication.InternalAuthenticationServiceException;
import com.example.security.core.Authentication;
import com.example.security.core.AuthenticationException;
import com.example.util.matcher.AntPathRequestMatcher;
import com.example.util.matcher.RequestMatcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.util.Assert;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 基于浏览器的基于http的身份验证请求的抽象处理器
 * 身份验证过程:
 * 1.要求设置AuthenticationManager
 *   -->处理身份验证请求令牌的实现类创建
 * 2.拦截请求并尝试从中执行身份验证
 *  -->如果请求匹配,则setRequiresAuthenticationRequestMatcher(RequestMatcher)
 * 3.身份验证：attemptAuthentication(HttpServletRequest, HttpServletResponse)
 *  -->必须由子类实现
 *
 * 认证成功:
 * 1.如果身份验证成功,生成的Authentication放置到当前线程的SecurityContext
 *  -->保证已由较早的过滤器创建
 * 2.之前配置的setAuthenticationSuccessHandler(AuthenticationSuccessHandler)
 *  -->将重定向到成功登入后的目的地
 *     -->实现默认行为:savedrequestawareauthenticationsuccessesshandler
 *          -->使用任何DefaultSavedRequest由ExceptionTranslationFilter将用户重定向到其中包含的URL
 *                -->否则它将重定向到webapp root
 *      2.1 注入不同配置的这个类的实例，或者通过使用不同的实现
 *
 * 认证失败:
 * 1.AuthenticationFailureHandler以允许向其传递失败信息客户端
 *   --->默认实现是SimpleUrlAuthenticationFailureHandler
 *        --->向客户端发送401错误码
 *
 * 事件发表：
 * 1.如果身份验证成功，InteractiveAuthenticationSuccessEvent将执行通过应用程序上下文发布
 * 2.如果身份验证失败则不发布
 *    -->通过AuthenticationManager特定于应用程序的事件
 *
 * 会话认证:
 * 1.在成功调用attemptAuthentication(),该类有一个可选的SessionAuthenticationStrategy将被调用
 * 2.不同的实现setSessionAuthenticationStrategy(SessionAuthenticationStrategy) 可以注入
 *    --->以使诸如会话固定攻击预防或控制一个主体可能同时拥有的会话数
 */
@Slf4j
public abstract class AbstractAuthenticationProcessingFilter extends GenericFilterBean implements ApplicationEventPublisherAware, MessageSourceAware {

    private RequestMatcher requiresAuthenticationRequestMatcher;
    private AuthenticationManager authenticationManager;

    // ~ Constructors
    // ===================================================================================================
    protected AbstractAuthenticationProcessingFilter(String defaultFilterProcessesUrl) {
        setFilterProcessesUrl(defaultFilterProcessesUrl);
    }

    //设置确定是否需要身份验证的URL
    public void setFilterProcessesUrl(String filterProcessesUrl) {
        setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(
                filterProcessesUrl));
    }

    public final void setRequiresAuthenticationRequestMatcher(
            RequestMatcher requestMatcher) {
        Assert.notNull(requestMatcher, "requestMatcher cannot be null");
        this.requiresAuthenticationRequestMatcher = requestMatcher;
    }

    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        //待完成
    }

    public void setMessageSource(MessageSource messageSource) {
        //待完成
    }
    /**
     * 调用requiresAuthentication(HttpServletRequest, HttpServletResponse)方法来确定请求是否为认证，应该由这个过滤器处理
     * 如果是身份验证请求,则attemptAuthentication(HttpServletRequest, HttpServletResponse)来执行身份验证
     * @param req
     * @param res
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        if (!requiresAuthentication(request, response)) {
            chain.doFilter(request, response);
            return;
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Request is to process authentication");
        }
        Authentication authResult;
        try {
            authResult = attemptAuthentication(request, response);
            if (authResult == null) {
                // return immediately as subclass has indicated that it hasn't completed
                // authentication
                return;
            }
//待完成
        }
        catch (InternalAuthenticationServiceException failed) {
            logger.error(
                    "An internal error occurred while trying to authenticate the user.",
                    failed);
//            unsuccessfulAuthentication(request, response, failed);
//待完成
            return;
        }

    }

    /**
     * 从请求URL的“path”部分
     * 在匹配<code>filterProcessesUrl</code>属性之前,子类可以覆盖特殊的需求
     * @param request
     * @param response
     * @return
     */
    protected boolean requiresAuthentication(HttpServletRequest request,
                                             HttpServletResponse response) {
        return requiresAuthenticationRequestMatcher.matches(request);
    }

    protected AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }


    public abstract Authentication attemptAuthentication(HttpServletRequest request,
                                                         HttpServletResponse response) throws AuthenticationException, IOException,
            ServletException;
}
