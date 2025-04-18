package com.work.motel.infrastructure.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.server.ResponseStatusException;

import com.work.motel.application.service.ManagerService;
import com.work.motel.application.util.JwtUtil;
import com.work.motel.domain.entity.Manager;

import jakarta.servlet.http.HttpServletRequest;

public class PrivateController {
  @Autowired
  protected JwtUtil jwtUtil = new JwtUtil();

  @Autowired
  private ManagerService service;

  @ModelAttribute
  public void privateMiddleware(HttpServletRequest request) {
    String authHeader = request.getHeader("Authorization");
    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      String token = authHeader.substring(7);

      jwtUtil.init();
      Boolean isJwtValid = jwtUtil.verificarToken(token);

      if(isJwtValid) {
        Integer id = (Integer) jwtUtil.decodeJWT(token).get("id");
        
  
        Optional<Manager> manager = service.getById(id);
  
        if(manager == null) {
          throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
      }
      else {
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
      }
    }
    else {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
  }
}
