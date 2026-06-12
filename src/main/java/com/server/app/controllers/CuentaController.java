package com.server.app.controllers;

import com.server.app.dto.finanzas.CuentaRequestDto;
import com.server.app.dto.finanzas.CuentaResponseDto;
import com.server.app.dto.response.Pagination;
import com.server.app.entities.User;
import com.server.app.services.CuentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/finanzas/cuentas")
@RequiredArgsConstructor
public class CuentaController {
    private final CuentaService cuentaService;

    @GetMapping
    public ResponseEntity<Pagination<CuentaResponseDto>> getAccounts(@AuthenticationPrincipal User user, Pageable pageable){
        return ResponseEntity.ok(
            cuentaService.getCuentas(user, pageable)
        );
    }

    @PostMapping
    public ResponseEntity<Void> createAccount(@AuthenticationPrincipal User user, @RequestBody @Valid CuentaRequestDto dto){
        cuentaService.createCuenta(user, dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
