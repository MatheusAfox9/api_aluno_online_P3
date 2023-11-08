package com.alunoonline.api.services;

import com.alunoonline.api.dto.PatchNotasRequest;
import com.alunoonline.api.enums.StatusMatricula;
import com.alunoonline.api.model.MatriculaAluno;
import com.alunoonline.api.repository.MatriculaAlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MatriculaAlunoService {

    @Autowired
    MatriculaAlunoRepository repository;

    public void create(MatriculaAluno matriculaAluno) {

        matriculaAluno.setStatus(StatusMatricula.MATRICULADO);

        repository.save(matriculaAluno);
    }

    public void updateStatus(Long id, StatusMatricula newStatus) {
        MatriculaAluno matricula = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Matrícula não encontrada!"));

        matricula.setStatus(newStatus);
        repository.save(matricula);
    }

    public void updateGrades(Long id, PatchNotasRequest patchNotasRequest){

        Optional<MatriculaAluno> matriculaAluno = repository.findById(id);

        MatriculaAluno matriculaUpdated = matriculaAluno.get();

        matriculaUpdated.setNota1(patchNotasRequest.getNota1());
        matriculaUpdated.setNota2(patchNotasRequest.getNota2());

        Double media = matriculaUpdated.getNota1() + matriculaUpdated.getNota2() / 2;

        Double mediaaParaAprovacao = 7.0;

        if (media >= mediaaParaAprovacao){
            matriculaUpdated.setStatus(StatusMatricula.valueOf("APROVADO"));
        } else {
            matriculaUpdated.setStatus(StatusMatricula.valueOf("REPROVADO"));
        }

        repository.save(matriculaUpdated);





    }






}
