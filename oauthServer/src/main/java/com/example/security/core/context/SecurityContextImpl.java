package com.example.security.core.context;

import com.example.security.core.Authentication;

public class SecurityContextImpl implements SecurityContext {

    private static final long serialVersionUID = 420L;

    // ~ Instance fields
    // ================================================================================================

    private Authentication authentication;

    // ~ Methods
    // ========================================================================================================

    public boolean equals(Object obj) {
        if (obj instanceof SecurityContextImpl) {
            SecurityContextImpl test = (SecurityContextImpl) obj;

            if ((this.getAuthentication() == null) && (test.getAuthentication() == null)) {
                return true;
            }

            if ((this.getAuthentication() != null) && (test.getAuthentication() != null)
                    && this.getAuthentication().equals(test.getAuthentication())) {
                return true;
            }
        }

        return false;
    }

    public Authentication getAuthentication() {
        return authentication;
    }

    public int hashCode() {
        if (this.authentication == null) {
            return -1;
        }
        else {
            return this.authentication.hashCode();
        }
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());

        if (this.authentication == null) {
            sb.append(": Null authentication");
        }
        else {
            sb.append(": Authentication: ").append(this.authentication);
        }

        return sb.toString();
    }
}
