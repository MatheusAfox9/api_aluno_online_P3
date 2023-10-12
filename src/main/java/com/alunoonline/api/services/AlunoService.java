package com.alunoonline.api.services;


import com.alunoonline.api.model.Aluno;
import com.alunoonline.api.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

//Preciso informar para o spring que é um service - usa a anotação service
@Service
public class AlunoService {

    //Os metodos são o CRUD - Create, Read, Update e Delete
    //Tenho que buscar o aluno no pacote model

    //Injeção do repository
    @Autowired
    AlunoRepository repository;

    public Aluno create(Aluno aluno) {
        return repository.save(aluno);


    }

    public List<Aluno> findAll() {
        repository.findAll();
        return repository.findAll();

    }

    public Optional<Aluno> findById(Long Id) {

        return repository.findById(Id);

    }


    public void delete(Long id) {
        repository.deleteById(id);

    }



}














