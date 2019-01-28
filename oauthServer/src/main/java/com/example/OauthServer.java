package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * http://blog.didispace.com/spring-security-oauth2-xjf-2/
 */
@SpringBootApplication
public class OauthServer
{
    public static void main( String[] args )
    {
        SpringApplication.run(OauthServer.class, args);
    }
}
