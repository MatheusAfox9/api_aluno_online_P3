package com.alunoonline.api.services;

import com.alunoonline.api.dto.ProfessorDTO;
import com.alunoonline.api.exception.IdNaoEncontradoException;
import com.alunoonline.api.exception.InformacaoAlunoDuplicadaException;
import com.alunoonline.api.exception.InformacaoProfessorDuplicadaException;
import com.alunoonline.api.exception.NenhumCampoAlteradoException;
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

        boolean professorExists = repository.existsByNomeAndEmail(professor.getNome(), professor.getEmail());
        if (professorExists) {
            throw new InformacaoProfessorDuplicadaException(professor.getNome(), professor.getEmail());
        }

        return repository.save(professor);
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
        Professor professorToUpdate = repository.findById(id)
                .orElseThrow(() -> new IdNaoEncontradoException(id,"Professor"));

        boolean alterado = false;



        if (professorDTO.getNome() != null && !professorDTO.getNome().equals(professorToUpdate.getNome())) {
            professorToUpdate.setNome(professorDTO.getNome());
            alterado = true;
        }

        if (professorDTO.getEmail() != null && !professorDTO.getEmail().equals(professorToUpdate.getEmail())) {
            professorToUpdate.setEmail(professorDTO.getEmail());
            alterado = true;
        }

        if (!alterado) {
            String nomeParaMensagem = professorDTO.getNome() != null ? professorDTO.getNome() : professorToUpdate.getNome();
            throw new NenhumCampoAlteradoException(nomeParaMensagem);
        }


        Professor professorAtualizado = repository.save(professorToUpdate);

        return professorAtualizado;
    }



    public void delete(Long id) {

        if (!repository.existsById(id)) {
            throw new IdNaoEncontradoException(id,"Professor");
        }

        repository.deleteById(id);

    }

}
