package com.alunoonline.api.exception;

public class IdNaoEncontradoException extends RuntimeException{

        public IdNaoEncontradoException(Long id, String tipo) {
                super(tipo + " com ID " + id + " não encontrado.");
        }
}
