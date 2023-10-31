package com.alunoonline.api.enums;

import lombok.Getter;

@Getter
public enum StatusMatricula {

    MATRICULADO("Matriculado"),
    TRANCADO("Trancado de Matrícula");

    private final String descricao;

    StatusMatricula(String descricao) {
        this.descricao = descricao;
    }



}
