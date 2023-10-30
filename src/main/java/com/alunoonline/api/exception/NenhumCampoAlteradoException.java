package com.alunoonline.api.exception;

public class NenhumCampoAlteradoException extends RuntimeException {


    public NenhumCampoAlteradoException(String disciplinaNome) {
        super("Nenhum campo foi alterado para a disciplina: " + (disciplinaNome != null ? disciplinaNome : "Desconhecida") + ".");
    }

}
