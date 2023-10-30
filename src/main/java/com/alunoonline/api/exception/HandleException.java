package com.alunoonline.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Component
public class HandleException {


    @ExceptionHandler(InformacaoAlunoDuplicadaException.class)
    public ResponseEntity<String> handleInformacaoAlunoDuplicadaException(InformacaoAlunoDuplicadaException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }


    @ExceptionHandler(InformacaoProfessorDuplicadaException.class)
    public ResponseEntity<String> handleInformacaoProfessorDuplicadaException(InformacaoProfessorDuplicadaException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(InformacaoDisciplinaDuplicadaException.class)
    public ResponseEntity<String> handleInformacaoDisciplinaDuplicadaException(InformacaoDisciplinaDuplicadaException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }

    @ExceptionHandler(IdNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> idNaoEncontradoException(IdNaoEncontradoException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());

    }

    @ExceptionHandler(NenhumCampoAlteradoException.class)
    public ResponseEntity<String> nenhumCampoAlteradoException(NenhumCampoAlteradoException exception) {
        return ResponseEntity.ok(exception.getMessage());
    }





}
