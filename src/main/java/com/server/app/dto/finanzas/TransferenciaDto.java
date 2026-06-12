package com.server.app.dto.finanzas;

import com.server.app.entities.Categoria;
import com.server.app.entities.enums.Moneda;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class TransferenciaDto {

    @NotNull
    private BigDecimal monto;

    @NotNull
    private UUID origen;

    @NotNull
    private UUID destino;

    @NotNull
    private Moneda moneda;

    @NotNull
    private BigDecimal tasa_de_cambio;

    @NotBlank
    private String description;

    @NotNull
    private Categoria categoria;
}
