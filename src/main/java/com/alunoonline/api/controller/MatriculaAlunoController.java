package com.alunoonline.api.controller;


import com.alunoonline.api.dto.StatusMatriculaDTO;
import com.alunoonline.api.model.MatriculaAluno;
import com.alunoonline.api.services.MatriculaAlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/matricula-aluno")
public class MatriculaAlunoController {

    @Autowired
    MatriculaAlunoService service;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody MatriculaAluno matriculaAluno){
        service.create(matriculaAluno);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateStatus(@PathVariable Long id, @RequestBody StatusMatriculaDTO statusDTO) {
        service.updateStatus(id, statusDTO.getStatus());
        return ResponseEntity.noContent().build();
    }



}
