package com.adominguez.coderocks.auth.service;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface AuthService {
  Mono<ServerResponse> signUp(ServerRequest request);
  Mono<ServerResponse> login(ServerRequest request);
}
