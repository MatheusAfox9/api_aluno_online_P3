package com.alunoonline.api.services;


import com.alunoonline.api.dto.DisciplinaDTO;
import com.alunoonline.api.exception.IdNaoEncontradoException;
import com.alunoonline.api.exception.InformacaoDisciplinaDuplicadaException;
import com.alunoonline.api.exception.NenhumCampoAlteradoException;
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

    public Disciplina update(Long id, DisciplinaDTO disciplinaDTO) {
        Disciplina disciplinaToUpdate = repository.findById(id)
                .orElseThrow(() -> new IdNaoEncontradoException(id, "Disciplina"));

        boolean alterado = false;

        if (disciplinaDTO.getNome() != null && !disciplinaDTO.getNome().equals(disciplinaToUpdate.getNome())){
            disciplinaToUpdate.setNome(disciplinaDTO.getNome());
            alterado = true;
        }

        if (disciplinaDTO.getProfessor() != null && (disciplinaToUpdate.getProfessor() == null || !disciplinaDTO.getProfessor().getId().equals(disciplinaToUpdate.getProfessor().getId()))) {
            Professor professor = professorRepository.findById(disciplinaDTO.getProfessor().getId())
                    .orElseThrow(() -> new IdNaoEncontradoException(disciplinaDTO.getProfessor().getId(), "Professor"));
            disciplinaToUpdate.setProfessor(professor);
            alterado = true;
        }


        if (!alterado) {
            throw new NenhumCampoAlteradoException("Disciplina");
        }

        Disciplina disciplinaAtualizado = repository.save(disciplinaToUpdate);

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
