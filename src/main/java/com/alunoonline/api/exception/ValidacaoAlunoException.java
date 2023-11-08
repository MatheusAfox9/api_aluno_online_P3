package com.alunoonline.api.exception;

public class ValidacaoAlunoException extends RuntimeException {

    public ValidacaoAlunoException(String missingFields) {
        super("Os seguintes campos são obrigatórios: " + String.join(", ", missingFields) + ".");
    }
}
