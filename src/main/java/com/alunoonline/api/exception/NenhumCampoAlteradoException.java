package com.alunoonline.api.exception;

public class NenhumCampoAlteradoException extends RuntimeException {


    public NenhumCampoAlteradoException() {
        super("Nenhum campo foi alterado. Por favor, forneça pelo menos uma alteração.");
    }

}
