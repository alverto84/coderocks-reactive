package com.adominguez.coderocks.config;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import com.adominguez.coderocks.handler.AuthHandler;
import com.adominguez.coderocks.user.handler.UserHandler;
import com.adominguez.coderocks.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;

@Configuration
public class RoutesConfig {

  private UserService userService;
  private AuthHandler authHandler;

  @Autowired
  RoutesConfig(UserService userService, AuthHandler authHandler) {
    this.userService = userService;
    this.authHandler = authHandler;
  }

  @Bean
  public RouterFunction userRoute() {
    UserHandler.builder().userService(userService).build();
    return RouterFunctions
        .route(POST("/users").and(accept(APPLICATION_JSON)), userService::createUser)
        .andRoute(GET("/users").and(accept(APPLICATION_JSON)), userService::listUser)
        .andRoute(GET("/users/{userId}").and(accept(APPLICATION_JSON)), userService::getUserById)
        .andRoute(PUT("/users").and(accept(APPLICATION_JSON)), userService::createUser)
        .andRoute(DELETE("/users/{userId}").and(accept(APPLICATION_JSON)), userService::deleteUser);
  }

  @Bean
  public RouterFunction authRoute() {
    return RouterFunctions
        .route(POST("/auth/login").and(accept(APPLICATION_JSON)), authHandler::login)
        .andRoute(POST("/auth/signup").and(accept(APPLICATION_JSON)), authHandler::signUp);
  }
}
