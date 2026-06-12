package com.server.app.repositories;

import com.server.app.entities.Movimiento;
import com.server.app.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.UUID;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, UUID> {
    @Query("SELECT m FROM Movimiento m WHERE " +
            "(m.cuentaOrigen.user = :user OR m.cuentaDestino.user = :user) " +
            "AND m.fecha = :date")
    Page<Movimiento> findByUserAndFecha(@Param("user") User user,
                                        @Param("date") Date date,
                                        Pageable pageable);
}
