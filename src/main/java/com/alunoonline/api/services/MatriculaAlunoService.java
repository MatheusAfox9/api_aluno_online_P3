package com.alunoonline.api.services;

import com.alunoonline.api.dto.PatchNotasRequest;
import com.alunoonline.api.enums.StatusMatricula;
import com.alunoonline.api.exception.IdNaoEncontradoException;
import com.alunoonline.api.exception.InformacaoMatriculaDuplicadaException;
import com.alunoonline.api.exception.ValidacaoMatriculaException;
import com.alunoonline.api.model.Aluno;
import com.alunoonline.api.model.Disciplina;
import com.alunoonline.api.model.MatriculaAluno;
import com.alunoonline.api.repository.AlunoRepository;
import com.alunoonline.api.repository.DisciplinaRepository;
import com.alunoonline.api.repository.MatriculaAlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MatriculaAlunoService {

    private final MatriculaAlunoRepository repository;
    private final AlunoRepository alunoRepository;
    private final DisciplinaRepository disciplinaRepository;

    @Autowired
    public MatriculaAlunoService(MatriculaAlunoRepository repository,
                                 AlunoRepository alunoRepository,
                                 DisciplinaRepository disciplinaRepository) {
        this.repository = repository;
        this.alunoRepository = alunoRepository;
        this.disciplinaRepository = disciplinaRepository;
    }

    public MatriculaAluno create(MatriculaAluno matriculaAluno) {

        validateMatriculaAluno(matriculaAluno);

        validateRegistrationDuplication(matriculaAluno);

        matriculaAluno.setStatus(StatusMatricula.MATRICULADO);
        return repository.save(matriculaAluno);
    }

    private void validateMatriculaAluno(MatriculaAluno matriculaAluno) {
        if (matriculaAluno.getAluno() == null || matriculaAluno.getAluno().getId() == null) {
            throw new ValidacaoMatriculaException("aluno");
        }
        Aluno aluno = alunoRepository.findById(matriculaAluno.getAluno().getId())
                .orElseThrow(() -> new IdNaoEncontradoException(matriculaAluno.getAluno().getId(), "Aluno"));

        if (matriculaAluno.getDisciplina() == null || matriculaAluno.getDisciplina().getId() == null) {
            throw new ValidacaoMatriculaException("disciplina");
        }
        Disciplina disciplina = disciplinaRepository.findById(matriculaAluno.getDisciplina().getId())
                .orElseThrow(() -> new IdNaoEncontradoException(matriculaAluno.getDisciplina().getId(), "Disciplina"));

        matriculaAluno.setAluno(aluno);
        matriculaAluno.setDisciplina(disciplina);
    }


    private void validateRegistrationDuplication(MatriculaAluno matriculaAluno) {
        boolean matriculaExists = repository.existsByAlunoAndDisciplina(
                matriculaAluno.getAluno(), matriculaAluno.getDisciplina());

        if (matriculaExists) {
            throw new InformacaoMatriculaDuplicadaException(matriculaAluno.getAluno().getId(), matriculaAluno.getAluno().getNome(), matriculaAluno.getDisciplina().getNome());
        }
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
