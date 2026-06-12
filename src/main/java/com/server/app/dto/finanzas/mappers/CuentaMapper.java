package com.server.app.dto.finanzas.mappers;

import com.server.app.dto.finanzas.CuentaRequestDto;
import com.server.app.dto.finanzas.CuentaResponseDto;
import com.server.app.entities.Cuenta;
import org.springframework.stereotype.Component;

@Component
public class CuentaMapper {
    public CuentaResponseDto toDto(Cuenta cuenta){
        return CuentaResponseDto.builder()
                .alias(cuenta.getAlias())
                .tipo(cuenta.getTipo().toString())
                .moneda(cuenta.getMoneda().toString())
                .saldo(cuenta.getSaldo_base())
                .build();
    }

    public Cuenta toEntity(CuentaRequestDto dto){
        return Cuenta.builder()
                .alias(dto.getAlias())
                .tipo(dto.getTipo())
                .moneda(dto.getMoneda())
                .build();
    }

}
