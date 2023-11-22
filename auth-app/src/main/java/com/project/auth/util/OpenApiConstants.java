package com.project.auth.util;

public final class OpenApiConstants {
    private OpenApiConstants() {
    }

    public static final String SECURITY_SCHEME_NAME = "BearerAuth";

    public static final String SCHEME = "bearer";

    public static final String BEARER_FORMAT = "JWT";

    public static final String DESCRIPTION = "A robust Spring Boot authentication application that provides user registration," +
            " login, password reset, and email confirmation functionalities." +
            " This application is designed to be easily integrated into your projects, " +
            "offering a secure and seamless authentication process. ";

    public static final String VERSION = "1.0";

    public static final String TITLE = "Auth App";
}
