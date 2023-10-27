package com.alunoonline.api.services;

import com.alunoonline.api.model.MatriculaAluno;
import com.alunoonline.api.repository.MatriculaAlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatriculaAlunoService {

    @Autowired
    MatriculaAlunoRepository repository;

    public void create(MatriculaAluno matriculaAluno) {

        matriculaAluno.setStatus("MATRICULADO");
        repository.save(matriculaAluno);
    }




}
