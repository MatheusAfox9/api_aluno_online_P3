package com.alunoonline.api.exception;

public class InformacaoMatriculaDuplicadaException extends RuntimeException {

    public InformacaoMatriculaDuplicadaException(Long idAluno, String nomeAluno, String nomeDisciplina) {
        super("Identificamos uma matrícula já existente para o aluno com ID: " + idAluno + ", aluno: " + nomeAluno + " e disciplina: " + nomeDisciplina + ".");
    }
}
