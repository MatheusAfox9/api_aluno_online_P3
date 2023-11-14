package com.alunoonline.api.exception;

public class InformacaoDisciplinaDuplicadaException extends RuntimeException{

    public InformacaoDisciplinaDuplicadaException(String nomeDisciplina, String nomeProfessor) {
        super("A disciplina: " + nomeDisciplina + " já existe para o professor: " + nomeProfessor + ".");
    }

    public InformacaoDisciplinaDuplicadaException(Long id, String nomeDisciplina, String nomeProfessor) {
        super("Disciplina com ID: " + id + ", nome: " + nomeDisciplina + " e professor: " + nomeProfessor + " já existe.");
    }


}



