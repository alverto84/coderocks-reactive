package com.adominguez.coderocks.user.service;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import com.adominguez.coderocks.dto.UserEvent;
import com.adominguez.coderocks.user.entity.User;
import com.adominguez.coderocks.user.repository.UserRepository;
import java.time.Duration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserService {

  private UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public Mono<ServerResponse> createUser(ServerRequest request) {
    return ServerResponse.ok().contentType(APPLICATION_JSON)
        .body(request.bodyToMono(User.class).flatMap(user -> userRepository.save(user)), User.class);
  }

  public Mono<ServerResponse> listUser(ServerRequest request) {
    return ServerResponse.ok().contentType(APPLICATION_JSON).body(userRepository.findAll(), User.class);
  }

  public Mono<ServerResponse> getUserById(ServerRequest request) {
    return userRepository.findById(request.pathVariable("userId")).flatMap(user -> ServerResponse.ok()
            .contentType(APPLICATION_JSON)
            .body(Mono.just(user), User.class))
        .switchIfEmpty(ServerResponse.notFound().build());
  }

  public Mono<ServerResponse> deleteUser(ServerRequest request) {
    userRepository.deleteById(request.pathVariable("userId"));
    return ServerResponse.ok().build();
  }

  public Mono<User> findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  public Mono<User> save(User user) {
    return userRepository.save(user);
  }

  public Mono<ServerResponse> streamEvents() {
    return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(Flux.interval(Duration.ofSeconds(1))
        .map(val -> new UserEvent("" + val, "Devglan User Event")), UserEvent.class);
  }

}
