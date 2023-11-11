package com.alunoonline.api.controller;



import com.alunoonline.api.dto.AlunoDTO;
import com.alunoonline.api.model.Aluno;
import com.alunoonline.api.services.AlunoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/aluno")


public class AlunoController {

    @Autowired
    AlunoService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Aluno> create(@RequestBody Aluno aluno){
        Aluno alunoCreated = service.create(aluno);

        return ResponseEntity.status(HttpStatus.CREATED).body(alunoCreated);

    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Aluno> findAll(){
        return service.findAll();

    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Aluno findById(@PathVariable Long id){
        return service.findById(id);
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Aluno> update(@PathVariable Long id, @RequestBody AlunoDTO aluno) {

        Aluno alunoAtualizado = service.update(id, aluno);

        return ResponseEntity.status(200).body(alunoAtualizado);
    }



    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        service.delete(id);
    }










}
