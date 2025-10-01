package com.capgemini.hotelapi.model;

public enum QuartoStatus {
    DISPONIVEL("Disponivel","O quarto está livre para reserva."),
    RESERVADO("","Alguém fez uma reserva, mas ainda não está hospedado."),
    OCUPADO("","O hóspede está atualmente no quarto."),
    MANUTENCAO("","O quarto está indisponível por manutenção ou limpeza."),
    INDISPONIVEL("","O quarto está fora de operação por tempo indeterminado.");

    private final String nome;
    private final String descricao;

    QuartoStatus(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }
}
