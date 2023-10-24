package com.alunoonline.api.exception;

public class InformacaoDisciplinaDuplicadaException extends RuntimeException{

    public InformacaoDisciplinaDuplicadaException(String nomeDisciplina, String nomeProfessor) {
        super("A disciplina: " + nomeDisciplina + " já existe para o professor: " + nomeProfessor + ".");
    }


}



