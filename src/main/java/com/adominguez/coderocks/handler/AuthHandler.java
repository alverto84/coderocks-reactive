package com.adominguez.coderocks.handler;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import com.adominguez.coderocks.model.ApiResponse;
import com.adominguez.coderocks.model.LoginRequest;
import com.adominguez.coderocks.model.LoginResponse;
import com.adominguez.coderocks.user.entity.User;
import com.adominguez.coderocks.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AuthHandler {

  @Autowired
  private TokenProvider tokenProvider;
  private final BCryptPasswordEncoder passwordEncoder;
  private final UserService userService;

  public Mono<ServerResponse> login(ServerRequest request) {
    Mono<LoginRequest> loginRequest = request.bodyToMono(LoginRequest.class);
    return loginRequest.flatMap(login -> userService.findByUsername(login.getUsername())
        .flatMap(user -> {
          if (passwordEncoder.matches(login.getPassword(), user.getPassword())) {
            return ServerResponse.ok().contentType(APPLICATION_JSON)
                .body(Mono.just(new LoginResponse(tokenProvider.generateToken(user))), LoginResponse.class);
          } else {
            return ServerResponse.badRequest()
                .body(Mono.just(new ApiResponse(400, "Invalid credentials", null)), ApiResponse.class);
          }
        }).switchIfEmpty(ServerResponse.badRequest()
            .body(Mono.just(new ApiResponse(400, "User does not exist", null)), ApiResponse.class)
        )
    );
  }

  public Mono<ServerResponse> signUp(ServerRequest request) {
    Mono<User> userMono = request.bodyToMono(User.class);
    return userMono.map(user -> {
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      return user;
    }).flatMap(user -> userService.findByUsername(user.getUsername())
        .flatMap(dbUser -> ServerResponse.badRequest()
            .body(Mono.just(new ApiResponse(400, "User already exist", null)), ApiResponse.class)
        ).switchIfEmpty(userService.save(user)
            .flatMap(savedUser -> ServerResponse.ok().contentType(APPLICATION_JSON)
                .body(Mono.just(savedUser), User.class)
            )
        )
    );
  }
}
