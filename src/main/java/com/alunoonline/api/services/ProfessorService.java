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
        validateProfessorExists(id);
        return repository.findById(id).get();

    }

    private void validateProfessorExists(Long id){

        if (!repository.existsById(id)) {
            throw new IdNaoEncontradoException(id, "Professor");
        }

    }




    public Professor update(Long id, ProfessorDTO professorDTO) {
        Professor professorAtual = repository.findById(id)
                .orElseThrow(() -> new IdNaoEncontradoException(id,"Professor"));

        boolean foiAlterado = false;

        // Verifica se o nome foi alterado e é único
        if (professorDTO.getNome() != null && !professorDTO.getNome().equals(professorAtual.getNome())) {
            validateUniqueProfessor(professorDTO.getNome(), professorAtual.getEmail(), id);
            professorAtual.setNome(professorDTO.getNome());
            foiAlterado = true;
        }

        // Verifica se o email foi alterado e é único
        if (professorDTO.getEmail() != null && !professorDTO.getEmail().equals(professorAtual.getEmail())) {
            validateUniqueProfessor(professorAtual.getNome(), professorDTO.getEmail(), id);
            professorAtual.setEmail(professorDTO.getEmail());
            foiAlterado = true;
        }

        if (!foiAlterado) {
            throw new NenhumCampoAlteradoException();
        }

        return repository.save(professorAtual);
    }


    private void validateUniqueProfessor(String nome, String email, Long id) {
        List<Professor> professoresEncontrados = repository.findByNomeAndEmail(nome, email);

        for (Professor professor : professoresEncontrados) {
            if (!professor.getId().equals(id)) {
                throw new InformacaoProfessorDuplicadaException(professor.getNome(), professor.getEmail());
            }
        }
    }


    public void delete(Long id) {

        if (!repository.existsById(id)) {
            throw new IdNaoEncontradoException(id,"Professor");
        }

        repository.deleteById(id);

    }

}
