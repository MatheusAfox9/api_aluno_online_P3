package com.alunoonline.api.services;

import com.alunoonline.api.model.Aluno;
import com.alunoonline.api.model.Professor;
import com.alunoonline.api.repository.AlunoRepository;
import com.alunoonline.api.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {

    @Autowired
    ProfessorRepository repository;

    public Professor create(Professor professor) {
        return repository.save(professor);

    }

    public List<Professor> findAll() {
        repository.findAll();
        return repository.findAll();

    }

    public Optional<Professor> findById(Long Id) {

        return repository.findById(Id);

    }

    public void delete(Long id) {
        repository.deleteById(id);

    }

}
