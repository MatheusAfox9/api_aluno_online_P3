package com.alunoonline.api.exception;

public class InformacaoProfessorDuplicadaException extends RuntimeException{

        public InformacaoProfessorDuplicadaException(String nome, String email) {
            super("Professor com nome: " + nome + ", e-mail: " + email + " já existe.");
        }


}
