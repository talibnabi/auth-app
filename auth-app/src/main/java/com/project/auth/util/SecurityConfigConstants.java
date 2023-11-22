package com.project.auth.util;

public final class SecurityConfigConstants {
    private SecurityConfigConstants() {
    }

    public static final String ADMIN_ROLE = "ADMIN";

    public static final String USER_ROLE = "USER";

    public static final String[] ADMIN_MATCHER = new String[]{
            "/api/admin/**"
    };

    public static final String[] USER_MATCHER = new String[]{
            "/api/user/**"
    };

    public static final String[] OTHER_MATCHER = new String[]{
            "/api/auth/**", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/v2/api-docs/**", "/swagger-resources/**"
    };
}
