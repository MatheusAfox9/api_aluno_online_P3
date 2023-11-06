package com.alunoonline.api.services;

import com.alunoonline.api.enums.StatusMatricula;
import com.alunoonline.api.model.MatriculaAluno;
import com.alunoonline.api.repository.MatriculaAlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatriculaAlunoService {

    @Autowired
    MatriculaAlunoRepository repository;

    public void create(MatriculaAluno matriculaAluno) {

        boolean matriculaAlunoExists = repository

        matriculaAluno.setStatus(StatusMatricula.MATRICULADO);

        repository.save(matriculaAluno);
    }

    public void updateStatus(Long id, StatusMatricula newStatus) {
        MatriculaAluno matricula = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Matrícula não encontrada!"));

        matricula.setStatus(newStatus);
        repository.save(matricula);
    }





}
