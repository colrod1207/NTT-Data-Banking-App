package org.example.banking.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/health")
@Tag(name = "Health", description = "Endpoints de verificación")
public class HealthController {

    @GetMapping("/ping")
    @Operation(summary = "Ping", description = "Devuelve estado OK para probar la API")
    public Map<String, String> ping() {
        return Map.of("status", "ok");
    }
}
