package com.alunoonline.api.exception;

public class NenhumCampoAlteradoException extends RuntimeException {

    public NenhumCampoAlteradoException(String nomeDisciplina) {
        super("Nenhum campo foi alterado para a disciplina: " + nomeDisciplina + ".");
    }

}
