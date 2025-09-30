package com.capgemini.hotelapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "properties")
@Data
@NoArgsConstructor
public class Propriedade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoPropriedade tipo;

    @Embedded
    private Endereco endereco;

    @OneToMany(mappedBy = "propriedade", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Quarto> quartos = new ArrayList<>();

//    public void adicionarQuarto(Quarto quarto) {
//        this.quartos.add(quarto);
//        quarto.setPropriedade(this);
//    }
}
