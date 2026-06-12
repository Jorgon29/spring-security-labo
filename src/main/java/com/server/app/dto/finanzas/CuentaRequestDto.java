package com.server.app.dto.finanzas;

import com.server.app.entities.enums.Moneda;
import com.server.app.entities.enums.TipoCuenta;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CuentaRequestDto {

    @NotBlank
    private String alias;

    @NotNull
    private Moneda moneda;

    @NotNull
    private TipoCuenta tipo;
}
