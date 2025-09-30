package com.capgemini.hotelapi.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
    private QuartoStatusEnum status = QuartoStatusEnum.DISPONIVEL;

    private Propriedade propriedade;
}
