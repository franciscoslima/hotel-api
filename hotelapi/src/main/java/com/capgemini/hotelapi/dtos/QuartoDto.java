package com.capgemini.hotelapi.dtos;

import com.capgemini.hotelapi.model.Propriedade;
import com.capgemini.hotelapi.model.QuartoStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class QuartoDto {

    @Schema(description = "Identificador do quarto", example = "1")
    private Long id;

    @Schema(description = "Número do quarto", example = "101")
    private int numeracao;

    @Schema(description = "Descrição do quarto", example = "Quarto Suíte com vista para o mar")
    @NotBlank(message = "Descrição é obrigatório")
    @Size(max = 120)
    private String descricao;

    @Schema(description = "Status do quarto", example = "DISPONIVEL")
    @NotNull(message = "Status é obrigatório")
    private QuartoStatus status = QuartoStatus.DISPONIVEL;

    @Schema(description = "Propriedade associada ao quarto")
    @NotNull(message = "Propriedade é obrigatório")
    private Propriedade propriedade;
}
