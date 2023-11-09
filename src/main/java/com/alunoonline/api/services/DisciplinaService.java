package com.alunoonline.api.services;


import com.alunoonline.api.dto.DisciplinaDTO;
import com.alunoonline.api.exception.*;
import com.alunoonline.api.model.Aluno;
import com.alunoonline.api.model.Disciplina;
import com.alunoonline.api.model.Professor;
import com.alunoonline.api.repository.DisciplinaRepository;
import com.alunoonline.api.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DisciplinaService {

    @Autowired
    DisciplinaRepository repository;

    @Autowired
    ProfessorRepository professorRepository;

    public Disciplina create(Disciplina disciplina) {


        validateDisciplina(disciplina);

        Professor professor = professorRepository.findById(disciplina.getProfessor().getId())
                .orElseThrow(() -> new IdNaoEncontradoException(disciplina.getProfessor().getId(), "Professor"));


        boolean disciplinaExists = repository.existsByNomeAndProfessor(disciplina.getNome(), professor);
        if (disciplinaExists) {
            throw new InformacaoDisciplinaDuplicadaException(disciplina.getNome(), professor.getNome());
        }

        disciplina.setProfessor(professor);
        return repository.save(disciplina);

    }

    private void validateDisciplina(Disciplina disciplina) {
        List<String> missingFields = new ArrayList<>();

        if (isNullOrEmpty(disciplina.getNome())) {
            missingFields.add("nome");
        }
        if (disciplina.getProfessor() == null) {
            missingFields.add("professor");
        }


        if (!missingFields.isEmpty()) {
            throw new ValidacaoDisciplinaException(String.join(", ", missingFields));
        }
    }

    private boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    private void validateIdNotFoundDisciplina (Disciplina disciplina){

    }

    private void validateRegistrationDuplicationDisciplina (Disciplina disciplina){

    }



    public List<Disciplina> findAll() {
        repository.findAll();
        return repository.findAll();

    }

    public Disciplina findById(Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new IdNaoEncontradoException(id, "Disciplina"));

    }

    public Disciplina update(Long id, DisciplinaDTO disciplinaDTO) {
        Disciplina disciplinaAtual = repository.findById(id)
                .orElseThrow(() -> new IdNaoEncontradoException(id, "Disciplina"));

        boolean foiAlterado = false;

        if (disciplinaDTO.getNome() != null && !disciplinaDTO.getNome().equals(disciplinaAtual.getNome())){
            disciplinaAtual.setNome(disciplinaDTO.getNome());
            foiAlterado = true;
        }

        if (disciplinaDTO.getProfessor() != null && (disciplinaAtual.getProfessor() == null || !disciplinaDTO.getProfessor().getId().equals(disciplinaAtual.getProfessor().getId()))) {
            Professor professor = professorRepository.findById(disciplinaDTO.getProfessor().getId())
                    .orElseThrow(() -> new IdNaoEncontradoException(disciplinaDTO.getProfessor().getId(), "Professor"));
            disciplinaAtual.setProfessor(professor);
            foiAlterado = true;
        }


        if (!foiAlterado) {
            throw new NenhumCampoAlteradoException();
        }

        Disciplina disciplinaAtualizado = repository.save(disciplinaAtual);

        return disciplinaAtualizado;

    }


    public void delete(Long id) {

        if (!repository.existsById(id)) {
            throw new IdNaoEncontradoException(id, "Disciplina");
        }
        repository.deleteById(id);

    }

    public List<Disciplina> findByProfessorId(Long professorId){
        return repository.findByProfessorId(professorId);



    }



}
