package com.server.app.services;

import com.server.app.dto.finanzas.CuentaRequestDto;
import com.server.app.dto.finanzas.CuentaResponseDto;
import com.server.app.dto.finanzas.mappers.CuentaMapper;
import com.server.app.dto.response.Pagination;
import com.server.app.dto.response.PaginationMeta;
import com.server.app.entities.Cuenta;
import com.server.app.entities.User;
import com.server.app.repositories.CuentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CuentaService {
    private final CuentaRepository cuentaRepository;
    private final CuentaMapper cuentaMapper;

    public Pagination<CuentaResponseDto> getCuentas(User user, Pageable pageable) {
        Page<Cuenta> cuentaPage = cuentaRepository.getByUserId(user.getId(), pageable);

        List<CuentaResponseDto> data = cuentaPage.getContent()
                .stream()
                .map(cuentaMapper::toDto)
                .collect(Collectors.toList());

        PaginationMeta meta = new PaginationMeta(
                cuentaPage.getNumber() + 1,
                cuentaPage.getSize(),
                cuentaPage.getTotalPages(),
                cuentaPage.getTotalElements()
        );

        return new Pagination<>(data, meta);
    }

    public void createCuenta(User user, CuentaRequestDto dto){
        Cuenta cuenta = cuentaMapper.toEntity(dto);
        cuenta.setSaldo_base(BigDecimal.ZERO);
        cuenta.setUser(user);
        cuentaRepository.save(cuenta);
    }
}
