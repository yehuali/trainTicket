package com.example.security.core.context;

import com.example.security.core.Authentication;

/**
 * 接口定义与当前线程关联的最小安全信息执行
 * 安全上下文存储在{@link SecurityContextHolder}.
 */
public interface SecurityContext {
    // ~ Methods
    // ========================================================================================================

    /**
     * Obtains the currently authenticated principal, or an authentication request token.
     *
     * @return the <code>Authentication</code> or <code>null</code> if no authentication
     * information is available
     */
    Authentication getAuthentication();

    /**
     * Changes the currently authenticated principal, or removes the authentication
     * information.
     *
     * @param authentication the new <code>Authentication</code> token, or
     * <code>null</code> if no further authentication information should be stored
     */
    void setAuthentication(Authentication authentication);
}
