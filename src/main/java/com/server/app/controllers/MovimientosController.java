package com.server.app.controllers;

import com.server.app.dto.finanzas.CategoriaResponseDto;
import com.server.app.dto.finanzas.DateDto;
import com.server.app.dto.finanzas.MovimientoResponseDto;
import com.server.app.dto.finanzas.TransferenciaDto;
import com.server.app.dto.response.Pagination;
import com.server.app.entities.User;
import com.server.app.services.MovimientosService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/finanzas")
@RequiredArgsConstructor
public class MovimientosController {
    private final MovimientosService movimientosService;

    @GetMapping("movimientos")
    public ResponseEntity<Pagination<MovimientoResponseDto>> getByDate(@AuthenticationPrincipal User user, @RequestBody @Valid DateDto date, Pageable pageable){
        return ResponseEntity.ok(movimientosService.getByDate(user, date.getDate(), pageable));
    }

    @PostMapping("transferencias")
    public ResponseEntity<Void> transferencia(@AuthenticationPrincipal User user, @RequestBody @Valid TransferenciaDto dto){
        movimientosService.transferencia(user, dto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("categorias")
    public ResponseEntity<Pagination<CategoriaResponseDto>> getCategorias(Pageable pageable){
        return ResponseEntity.ok(movimientosService.getCategorias(pageable));
    }

}
