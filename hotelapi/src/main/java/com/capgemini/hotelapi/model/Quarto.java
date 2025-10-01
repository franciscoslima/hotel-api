package com.capgemini.hotelapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "quartos")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Quarto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int numeracao;

    @Column(nullable = false, length = 120)
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private QuartoStatus status = QuartoStatus.DISPONIVEL;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "propriedade_id", nullable = false)
    private Propriedade propriedade;

    @OneToMany(mappedBy = "quarto")
    private List<Reserva> reservas;

    private Double valorDiaria;
}
