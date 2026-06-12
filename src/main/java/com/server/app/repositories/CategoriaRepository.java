package com.server.app.repositories;

import com.server.app.dto.finanzas.CategoriaResponseDto;
import com.server.app.dto.response.Pagination;
import com.server.app.entities.Categoria;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, UUID> {
}
