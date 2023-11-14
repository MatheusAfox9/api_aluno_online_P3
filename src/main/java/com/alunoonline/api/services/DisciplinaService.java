package com.alunoonline.api.services;


import com.alunoonline.api.dto.DisciplinaDTO;
import com.alunoonline.api.exception.*;
import com.alunoonline.api.model.Disciplina;
import com.alunoonline.api.model.Professor;
import com.alunoonline.api.repository.DisciplinaRepository;
import com.alunoonline.api.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DisciplinaService {

    @Autowired
    DisciplinaRepository disciplinaRepository;

    @Autowired
    ProfessorRepository professorRepository;

    public Disciplina create(Disciplina disciplina) {

        validateDisciplina(disciplina);

        Professor professor = validateProfessorExists(disciplina.getProfessor().getId());

        validateRegistrationDuplicationDisciplina(disciplina.getNome(), professor);

        disciplina.setProfessor(professor);
        return disciplinaRepository.save(disciplina);
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

    private Professor validateProfessorExists(Long id){

        return professorRepository.findById(id)
                .orElseThrow(() -> new IdNaoEncontradoException(id, "Professor"));
    }

    private void validateRegistrationDuplicationDisciplina (String nomeDisciplina, Professor professor){

        boolean disciplinaExists = disciplinaRepository.existsByNomeAndProfessor(nomeDisciplina, professor);
        if (disciplinaExists) {
            throw new InformacaoDisciplinaDuplicadaException(nomeDisciplina, professor.getNome());
        }
    }


    public List<Disciplina> findAll() {
        disciplinaRepository.findAll();
        return disciplinaRepository.findAll();

    }

    public Disciplina findById(Long id) {
        validateDisciplinaExists(id);
        return disciplinaRepository.findById(id).get();

    }

    private void validateDisciplinaExists(Long id){

        if (!disciplinaRepository.existsById(id)) {
            throw new IdNaoEncontradoException(id, "Disciplina");
        }

    }


    public Disciplina update(Long id, DisciplinaDTO disciplinaDTO) {
        Disciplina disciplinaAtual = buscarDisciplinaPorId(id);

        boolean foiAlterado = false;
        foiAlterado |= updateNomeDisciplina(disciplinaAtual, disciplinaDTO);
        foiAlterado |= updateProfessorDisciplina(disciplinaAtual, disciplinaDTO);

        verificarSeHouveAlteracao(foiAlterado);

        return disciplinaRepository.save(disciplinaAtual);
    }

    private Disciplina buscarDisciplinaPorId(Long id) {
        return disciplinaRepository.findById(id)
                .orElseThrow(() -> new IdNaoEncontradoException(id, "Disciplina"));
    }

    private void verificarSeHouveAlteracao(boolean foiAlterado) {
        if (!foiAlterado) {
            throw new NenhumCampoAlteradoException();
        }
    }

    private boolean updateNomeDisciplina(Disciplina disciplina, DisciplinaDTO disciplinaDTO) {
        if (disciplinaDTO.getNome() != null && !disciplinaDTO.getNome().equals(disciplina.getNome())) {
            Professor professor = disciplina.getProfessor();
            validateUniqueDisciplina(disciplinaDTO.getNome(), professor, disciplina.getId());
            disciplina.setNome(disciplinaDTO.getNome());
            return true;
        }
        return false;
    }

    private boolean updateProfessorDisciplina(Disciplina disciplina, DisciplinaDTO disciplinaDTO) {
        if (disciplinaDTO.getProfessor() != null && (disciplina.getProfessor() == null || !disciplinaDTO.getProfessor().getId().equals(disciplina.getProfessor().getId()))) {
            Professor professor = professorRepository.findById(disciplinaDTO.getProfessor().getId())
                    .orElseThrow(() -> new IdNaoEncontradoException(disciplinaDTO.getProfessor().getId(), "Professor"));
            validateUniqueDisciplina(disciplina.getNome(), professor, disciplina.getId());
            disciplina.setProfessor(professor);
            return true;
        }
        return false;
    }

    private void validateUniqueDisciplina(String nome, Professor professor, Long id) {
        List<Disciplina> disciplinasEncontradas = disciplinaRepository.findByNomeAndProfessor(nome, professor);
        for (Disciplina disciplina : disciplinasEncontradas) {
            if (!disciplina.getId().equals(id)) {
                throw new InformacaoDisciplinaDuplicadaException(disciplina.getId(), disciplina.getNome(), professor.getNome());
            }
        }
    }



    public void delete(Long id) {

        if (!disciplinaRepository.existsById(id)) {
            throw new IdNaoEncontradoException(id, "Disciplina");
        }
        disciplinaRepository.deleteById(id);

    }

    public List<Disciplina> findByProfessorId(Long professorId){
        return disciplinaRepository.findByProfessorId(professorId);



    }



}
