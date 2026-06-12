package com.server.app.entities;

import com.server.app.entities.enums.Moneda;
import com.server.app.entities.enums.TipoCuenta;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "cuenta")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    private String alias;

    @Column
    @Enumerated(EnumType.STRING)
    private Moneda moneda;

    @Column
    private BigDecimal saldo_base;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private TipoCuenta tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;
}
