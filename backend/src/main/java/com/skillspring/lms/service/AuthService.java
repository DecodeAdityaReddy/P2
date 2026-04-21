package com.skillspring.lms.service;

import com.skillspring.lms.dto.AuthResponse;
import com.skillspring.lms.dto.LoginRequest;
import com.skillspring.lms.dto.SignupRequest;
import com.skillspring.lms.dto.UserDto;
import com.skillspring.lms.model.User;
import com.skillspring.lms.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Service
public class AuthService {
  private final DataStore dataStore;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;

  public AuthService(DataStore dataStore, PasswordEncoder passwordEncoder, JwtService jwtService) {
    this.dataStore = dataStore;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
  }

  public AuthResponse signup(SignupRequest request) {
    if (dataStore.findUserByEmail(request.getEmail()) != null) {
      throw new ResponseStatusException(BAD_REQUEST, "Email already exists");
    }

    User user = new User(
        null,
        request.getName(),
        request.getEmail(),
        passwordEncoder.encode(request.getPassword()),
        request.getRole()
    );
    dataStore.saveUser(user);
    return toAuthResponse(user);
  }

  public AuthResponse login(LoginRequest request) {
    User user = dataStore.findUserByEmail(request.getEmail());

    if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
      throw new ResponseStatusException(UNAUTHORIZED, "Invalid email or password");
    }

    return toAuthResponse(user);
  }

  private AuthResponse toAuthResponse(User user) {
    String token = jwtService.generateToken(user.getEmail(), user.getRole().name());
    return new AuthResponse(token, UserDto.from(user));
  }
}
