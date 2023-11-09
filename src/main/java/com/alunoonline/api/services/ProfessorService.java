package com.alunoonline.api.services;

import com.alunoonline.api.dto.ProfessorDTO;
import com.alunoonline.api.exception.*;
import com.alunoonline.api.model.Aluno;
import com.alunoonline.api.model.Professor;
import com.alunoonline.api.repository.AlunoRepository;
import com.alunoonline.api.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {

    @Autowired
    ProfessorRepository repository;


    public Professor create(Professor professor) {

        validateProfessor(professor);

        validateRegistrationDuplicationProfessor(professor);

        return repository.save(professor);
    }


    private void validateProfessor(Professor professor) {
        List<String> missingFields = new ArrayList<>();

        if (isNullOrEmpty(professor.getNome())) {
            missingFields.add("nome");
        }
        if (isNullOrEmpty(professor.getEmail())) {
            missingFields.add("email");
        }

        if (!missingFields.isEmpty()) {
            throw new ValidacaoProfessorException(String.join(", ", missingFields));
        }
    }

    private boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    private void validateRegistrationDuplicationProfessor(Professor professor){

        boolean professorExists = repository.existsByNomeAndEmail(professor.getNome(), professor.getEmail());
        if (professorExists) {
            throw new InformacaoProfessorDuplicadaException(professor.getNome(), professor.getEmail());
        }
    }


    public List<Professor> findAll() {
        repository.findAll();
        return repository.findAll();

    }

    public Professor findById(Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new IdNaoEncontradoException(id,"Professor"));

    }


    public Professor update(Long id, ProfessorDTO professorDTO) {
        Professor professorAtual = repository.findById(id)
                .orElseThrow(() -> new IdNaoEncontradoException(id,"Professor"));

        boolean foiAlterado = false;



        if (professorDTO.getNome() != null && !professorDTO.getNome().equals(professorAtual.getNome())) {
            professorAtual.setNome(professorDTO.getNome());
            foiAlterado = true;
        }

        if (professorDTO.getEmail() != null && !professorDTO.getEmail().equals(professorAtual.getEmail())) {
            professorAtual.setEmail(professorDTO.getEmail());
            foiAlterado = true;
        }

        if (!foiAlterado) {
            throw new NenhumCampoAlteradoException();
        }


        Professor professorAtualizado = repository.save(professorAtual);

        return professorAtualizado;
    }



    public void delete(Long id) {

        if (!repository.existsById(id)) {
            throw new IdNaoEncontradoException(id,"Professor");
        }

        repository.deleteById(id);

    }

}
