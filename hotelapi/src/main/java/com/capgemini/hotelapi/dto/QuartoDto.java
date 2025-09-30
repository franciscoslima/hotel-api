package com.capgemini.hotelapi.dto;

import com.capgemini.hotelapi.model.Propriedade;
import com.capgemini.hotelapi.model.QuartoStatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class QuartoDto {

    private Long id;


    private int numeracao;

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 120)
    private String descricao;

    @NotBlank(message = "Status é obrigatório")
    private QuartoStatusEnum status = QuartoStatusEnum.DISPONIVEL;

    @NotBlank(message = "Propriedade é obrigatório")
    private Propriedade propriedade;
}
