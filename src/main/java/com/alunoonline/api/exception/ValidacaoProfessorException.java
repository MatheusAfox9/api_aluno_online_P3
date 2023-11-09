package com.alunoonline.api.exception;

public class ValidacaoProfessorException extends RuntimeException{

    public ValidacaoProfessorException (String missingFields) {
        super("Os seguintes campos são obrigatórios: " + String.join(", ", missingFields) + ".");
    }

}
