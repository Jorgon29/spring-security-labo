package com.server.app.dto.finanzas;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Builder
@Data
public class CuentaResponseDto {
    private String alias;
    private String moneda;
    private BigDecimal saldo;
    private String tipo;
}
