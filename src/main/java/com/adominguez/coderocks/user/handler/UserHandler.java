package com.adominguez.coderocks.user.handler;

import com.adominguez.coderocks.user.service.UserService;
import lombok.Builder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@Builder
public class UserHandler {

  private final UserService userService;

  public UserHandler(UserService userService) {
    this.userService = userService;
  }


  public Mono<ServerResponse> createUser(ServerRequest request) {
    return userService.createUser(request);
  }

  public Mono<ServerResponse> listUser(ServerRequest request) {
    return userService.listUser(request);
  }

  public Mono<ServerResponse> getUserById(ServerRequest request) {
    return userService.getUserById(request);
  }

  public Mono<ServerResponse> deleteUser(ServerRequest request) {
    return userService.deleteUser(request);
  }
}
