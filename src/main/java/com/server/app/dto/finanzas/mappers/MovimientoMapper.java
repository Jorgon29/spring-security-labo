package com.server.app.dto.finanzas.mappers;

import com.server.app.dto.finanzas.MovimientoResponseDto;
import com.server.app.dto.finanzas.TransferenciaDto;
import com.server.app.entities.Cuenta;
import com.server.app.entities.Movimiento;
import com.server.app.exceptions.NotFoundException;
import com.server.app.repositories.CuentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MovimientoMapper {
    private final CuentaRepository cuentaRepository;

    public MovimientoResponseDto toDto(Movimiento movimiento){
        return MovimientoResponseDto.builder()
                .id(movimiento.getId())
                .fecha(movimiento.getFecha().toString())
                .monto(movimiento.getMonto())
                .destino(movimiento.getCuentaDestino().getId())
                .origen(movimiento.getCuentaOrigen().getId())
                .descripcion(movimiento.getDescripcion())
                .moneda_original(movimiento.getMoneda_original())
                .build();
    }

    public Movimiento toEntity(TransferenciaDto dto){
        Cuenta origen = cuentaRepository.findById(dto.getOrigen()).orElseThrow(() -> new NotFoundException("No encontró cuenta origen"));
        Cuenta destino = cuentaRepository.findById(dto.getDestino()).orElseThrow(() -> new NotFoundException("No encontró cuenta destino"));

        return Movimiento.builder()
                .categoria(dto.getCategoria())
                .cuentaDestino(destino)
                .cuentaOrigen(origen)
                .descripcion(dto.getDescription())
                .moneda_original(dto.getMoneda())
                .monto(dto.getMonto())
                .tasa_cambio(dto.getTasa_de_cambio())
                .build();
    }
}
