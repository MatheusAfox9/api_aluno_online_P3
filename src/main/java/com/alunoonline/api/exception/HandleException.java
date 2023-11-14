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
    public ResponseEntity<String> handleidNaoEncontradoException(IdNaoEncontradoException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());

    }

    @ExceptionHandler(NenhumCampoAlteradoException.class)
    public ResponseEntity<String> handlenenhumCampoAlteradoException(NenhumCampoAlteradoException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(ValidacaoAlunoException.class)
    public ResponseEntity<String> handleValidacaoAlunoException(ValidacaoAlunoException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(ValidacaoProfessorException.class)
    public ResponseEntity<String> handleValidacaoProfessorException(ValidacaoProfessorException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(ValidacaoDisciplinaException.class)
    public ResponseEntity<String> handleValidacaoDisciplinaException(ValidacaoDisciplinaException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(ValidacaoMatriculaException.class)
    public ResponseEntity<String> handleValidacaoMatriculaException(ValidacaoMatriculaException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(InformacaoMatriculaDuplicadaException.class)
    public ResponseEntity<String> handleInformacaoMatriculaDuplicadaException(InformacaoMatriculaDuplicadaException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
    }







}
