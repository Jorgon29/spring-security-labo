package com.server.app.entities;

import com.server.app.entities.enums.Moneda;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.UUID;

@Entity
@Table(name = "movimiento")
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Movimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private BigDecimal monto;

    @Column
    private Moneda moneda_original;

    @Column
    private BigDecimal tasa_cambio;

    @Column
    private Date fecha;

    @Column
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cuenta_origen_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Cuenta cuentaOrigen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cuenta_destino_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Cuenta cuentaDestino;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Categoria categoria;
}
