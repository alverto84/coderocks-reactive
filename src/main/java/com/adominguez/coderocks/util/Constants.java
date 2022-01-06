package com.adominguez.coderocks.util;

public class Constants {
    public static final long ACCESS_TOKEN_VALIDITY_SECONDS = (long) 5*60*60;
    public static final String SIGNING_KEY = "ThisIsSecretForJWTHS512SignatureAlgorithmThatMUSTHave64ByteLength";
    public static final String AUTHORITIES_KEY = "scopes";

    private Constants(){}
}
