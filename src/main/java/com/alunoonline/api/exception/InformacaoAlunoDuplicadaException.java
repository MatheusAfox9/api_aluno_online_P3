package com.alunoonline.api.exception;

public class InformacaoAlunoDuplicadaException extends RuntimeException{

    public InformacaoAlunoDuplicadaException (String nome, String email, String curso) {
        super("Aluno com nome: " +  nome + ", e-mail: " + email + " e curso: " + curso + " já existe.");
    }



}
