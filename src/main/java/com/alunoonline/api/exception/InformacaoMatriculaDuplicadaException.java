package com.alunoonline.api.exception;

public class InformacaoMatriculaDuplicadaException extends RuntimeException {

    public InformacaoMatriculaDuplicadaException(Long idAluno, String nomeAluno, String nomeDisciplina) {
        super("Matrícula duplicada encontrada para o aluno com ID: " + idAluno + ", nome: " + nomeAluno + " e disciplina: " + nomeDisciplina + ".");
    }
}
