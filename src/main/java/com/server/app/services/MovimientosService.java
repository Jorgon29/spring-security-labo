package com.server.app.services;

import com.server.app.dto.finanzas.CategoriaResponseDto;
import com.server.app.dto.finanzas.MovimientoResponseDto;
import com.server.app.dto.finanzas.TransferenciaDto;
import com.server.app.dto.finanzas.mappers.CategoriaMapper;
import com.server.app.dto.finanzas.mappers.MovimientoMapper;
import com.server.app.dto.response.Pagination;
import com.server.app.dto.response.PaginationMeta;
import com.server.app.entities.Categoria;
import com.server.app.entities.Movimiento;
import com.server.app.entities.User;
import com.server.app.exceptions.BadRequestException;
import com.server.app.exceptions.UnauthorizedException;
import com.server.app.repositories.CategoriaRepository;
import com.server.app.repositories.CuentaRepository;
import com.server.app.repositories.MovimientoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovimientosService {
    private final MovimientoRepository movimientoRepository;
    private final CategoriaRepository categoriaRepository;
    private final MovimientoMapper movimientoMapper;
    private final CategoriaMapper categoriaMapper;

    public Pagination<MovimientoResponseDto> getByDate(User user, Date date, Pageable pageable) {
        Page<Movimiento> movimientoPage = movimientoRepository.findByUserAndFecha(user, date, pageable);

        List<MovimientoResponseDto> data = movimientoPage.getContent()
                .stream()
                .map(movimientoMapper::toDto)
                .collect(Collectors.toList());

        PaginationMeta meta = new PaginationMeta(
                movimientoPage.getNumber() + 1,
                movimientoPage.getSize(),
                movimientoPage.getTotalPages(),
                movimientoPage.getTotalElements()
        );

        return new Pagination<>(data, meta);
    }

    @Transactional
    public void transferencia(User user, TransferenciaDto dto){
        Movimiento movimiento = movimientoMapper.toEntity(dto);
        if (user.getId() != movimiento.getCuentaOrigen().getUser().getId()){
            throw new UnauthorizedException("Solo se hacen transferencias propias a alguien mas");
        }
        if (movimiento.getCuentaOrigen().getSaldo_base().compareTo(movimiento.getMonto()) < 0){
            throw new BadRequestException("Fondos insuficientes");
        }
        movimiento.setFecha(new Date(System.currentTimeMillis()));
        movimientoRepository.save(movimiento);
    }

    public Pagination<CategoriaResponseDto> getCategorias(Pageable pageable) {

        Page<Categoria> categoriaPage = categoriaRepository.findAll(pageable);

        List<CategoriaResponseDto> data = categoriaPage.getContent()
                .stream()
                .map(categoriaMapper::toDto)
                .collect(Collectors.toList());

        PaginationMeta meta = new PaginationMeta(
                categoriaPage.getNumber() + 1,
                categoriaPage.getSize(),
                categoriaPage.getTotalPages(),
                categoriaPage.getTotalElements()
        );

        return new Pagination<>(data, meta);
    }
}
