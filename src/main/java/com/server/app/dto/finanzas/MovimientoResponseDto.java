package com.server.app.dto.finanzas;

import com.server.app.entities.enums.Moneda;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.UUID;

@Data
@Builder
public class MovimientoResponseDto {

    private UUID id;

    private BigDecimal monto;

    private Moneda moneda_original;

    private String fecha;

    private String descripcion;

    private UUID destino;
    private UUID origen;
}
