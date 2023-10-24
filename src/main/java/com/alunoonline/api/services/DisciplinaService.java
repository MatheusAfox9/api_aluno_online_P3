package com.alunoonline.api.services;


import com.alunoonline.api.exception.IdNaoEncontradoException;
import com.alunoonline.api.exception.InformacaoDisciplinaDuplicadaException;
import com.alunoonline.api.model.Aluno;
import com.alunoonline.api.model.Disciplina;
import com.alunoonline.api.model.Professor;
import com.alunoonline.api.repository.DisciplinaRepository;
import com.alunoonline.api.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DisciplinaService {

    @Autowired
    DisciplinaRepository repository;

    @Autowired
    ProfessorRepository professorRepository;

    public Disciplina create(Disciplina disciplina) {

        Professor professor = professorRepository.findById(disciplina.getProfessor().getId())
                .orElseThrow(() -> new IdNaoEncontradoException(disciplina.getProfessor().getId(), "Professor"));


        boolean disciplinaExists = repository.existsByNomeAndProfessor(disciplina.getNome(), professor);
        if (disciplinaExists) {
            throw new InformacaoDisciplinaDuplicadaException(disciplina.getNome(), professor.getNome());
        }

        disciplina.setProfessor(professor);
        return repository.save(disciplina);

    }

    public List<Disciplina> findAll() {
        repository.findAll();
        return repository.findAll();

    }

    public Disciplina findById(Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new IdNaoEncontradoException(id, "Disciplina"));

    }

    public Disciplina update(Long id, Disciplina disciplina) {
        Disciplina disciplinaToUpdate = repository.findById(id)
                .orElseThrow(() -> new IdNaoEncontradoException(id, "Disciplina"));

        // Buscando os detalhes completos do professor a partir do ID fornecido
        Professor professor = professorRepository.findById(disciplina.getProfessor().getId())
                .orElseThrow(() -> new IdNaoEncontradoException(disciplina.getProfessor().getId(), "Professor"));


        disciplinaToUpdate.setNome(disciplina.getNome());
        disciplinaToUpdate.setProfessor(professor);


        Disciplina disciplinaAtualizado = repository.save(disciplinaToUpdate);

        return disciplinaAtualizado;

    }


    public void delete(Long id) {

        if (!repository.existsById(id)) {
            throw new IdNaoEncontradoException(id, "Disciplina");
        }
        repository.deleteById(id);

    }

}
