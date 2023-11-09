package com.alunoonline.api.exception;

public class ValidacaoDisciplinaException extends  RuntimeException {

    public ValidacaoDisciplinaException (String missingFields) {
        super("Os seguintes campos são obrigatórios: " + String.join(", ", missingFields) + ".");
    }
}
