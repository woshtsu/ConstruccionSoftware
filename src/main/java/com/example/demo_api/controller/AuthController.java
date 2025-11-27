package com.example.demo_api.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final com.example.demo_api.service.AuthService authService;

    public AuthController(com.example.demo_api.service.AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<com.example.demo_api.dto.LoginResponse> login(@RequestBody com.example.demo_api.dto.LoginRequest request) {
        if (request.getEmail() == null || request.getEmail().isBlank() || request.getPassword() == null || request.getPassword().isBlank()) {
            return ResponseEntity.badRequest().body(new com.example.demo_api.dto.LoginResponse("Error", "email y password son requeridos", null, null));
        }
        var resp = authService.login(request);
        if (resp != null && "Exito".equalsIgnoreCase(resp.getEstado())) {
            return ResponseEntity.ok(resp);
        }
        return ResponseEntity.status(401).body(resp);
    }

    @PostMapping(path = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<java.util.Map<String, String>> logout(@RequestHeader(value = "Authorization", required = false) String authorization) {
        String token = parseBearer(authorization);
        if (token == null) {
            return ResponseEntity.badRequest().body(java.util.Map.of("estado", "Error", "mensaje", "Authorization Bearer requerido"));
        }
        boolean ok = authService.logout(token);
        if (ok) return ResponseEntity.ok(java.util.Map.of("estado", "Exito"));
        return ResponseEntity.badRequest().body(java.util.Map.of("estado", "Error", "mensaje", "Token inválido"));
    }

    private String parseBearer(String authorization) {
        if (authorization == null) return null;
        String prefix = "Bearer ";
        if (authorization.startsWith(prefix)) return authorization.substring(prefix.length());
        return null;
    }
}
