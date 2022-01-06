package com.adominguez.coderocks.auth.handler;

import com.adominguez.coderocks.auth.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class AuthHandler {

  private final AuthService authService;

  public Mono<ServerResponse> signUp(ServerRequest request) {
    return authService.signUp(request);
  }

  public Mono<ServerResponse> login(ServerRequest request) {
    return authService.login(request);
  }
}
