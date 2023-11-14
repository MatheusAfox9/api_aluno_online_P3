package com.alunoonline.api.exception;

public class ValidacaoMatriculaException extends RuntimeException {

    public ValidacaoMatriculaException (String missingFields) {
        super("Os seguintes campos são obrigatórios: " + String.join(", ", missingFields) + ".");
    }


}
