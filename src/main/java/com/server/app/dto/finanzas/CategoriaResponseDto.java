package com.server.app.dto.finanzas;

import com.server.app.entities.enums.TipoCategoria;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CategoriaResponseDto {
    private UUID id;
    private String name;
    private TipoCategoria tipo;
}
