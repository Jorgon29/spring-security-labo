package com.server.app.dto.finanzas.mappers;

import com.server.app.dto.finanzas.CategoriaResponseDto;
import com.server.app.entities.Categoria;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {
    public CategoriaResponseDto toDto(Categoria categoria){
        return CategoriaResponseDto.builder()
                .id(categoria.getId())
                .name(categoria.getNombre())
                .tipo(categoria.getTipo()).build();
    }
}
