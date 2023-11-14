package com.alunoonline.api.services;

import com.alunoonline.api.dto.ProfessorDTO;
import com.alunoonline.api.exception.*;
import com.alunoonline.api.model.Professor;
import com.alunoonline.api.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        Professor professorAtual = searchByIdProfessor(id);

        boolean foiAlterado = false;
        foiAlterado |= updateNameProfessor(professorAtual, professorDTO);
        foiAlterado |= updateEmailProfessor(professorAtual, professorDTO);

        checkChangeProfessor(foiAlterado);

        return repository.save(professorAtual);
    }

    private Professor searchByIdProfessor(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IdNaoEncontradoException(id, "Professor"));
    }

    private void checkChangeProfessor(boolean foiAlterado) {
        if (!foiAlterado) {
            throw new NenhumCampoAlteradoException();
        }
    }

    private boolean updateNameProfessor(Professor professor, ProfessorDTO professorDTO) {
        if (professorDTO.getNome() != null && !professorDTO.getNome().equals(professor.getNome())) {
            validateUniqueProfessor(professorDTO.getNome(), professor.getEmail(), professor.getId());
            professor.setNome(professorDTO.getNome());
            return true;
        }
        return false;
    }

    private boolean updateEmailProfessor(Professor professor, ProfessorDTO professorDTO) {
        if (professorDTO.getEmail() != null && !professorDTO.getEmail().equals(professor.getEmail())) {
            validateUniqueProfessor(professor.getNome(), professorDTO.getEmail(), professor.getId());
            professor.setEmail(professorDTO.getEmail());
            return true;
        }
        return false;
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
