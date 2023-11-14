package com.alunoonline.api.services;


import com.alunoonline.api.dto.AlunoDTO;
import com.alunoonline.api.exception.IdNaoEncontradoException;
import com.alunoonline.api.exception.InformacaoAlunoDuplicadaException;
import com.alunoonline.api.exception.NenhumCampoAlteradoException;
import com.alunoonline.api.exception.ValidacaoAlunoException;
import com.alunoonline.api.model.Aluno;
import com.alunoonline.api.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//Preciso informar para o spring que é um service - usa a anotação service
@Service
public class AlunoService {

    //Os metodos são o CRUD - Create, Read, Update e Delete
    //Tenho que buscar o aluno no pacote model

    //Injeção do repository
    @Autowired
    AlunoRepository repository;

    public Aluno create(Aluno aluno) {

        validateAluno(aluno);

        validateRegistrationDuplicationAluno(aluno);

        return repository.save(aluno);

    }

    private void validateAluno(Aluno aluno) {
        List<String> missingFields = new ArrayList<>();

        if (isNullOrEmpty(aluno.getNome())) {
            missingFields.add("nome");
        }
        if (isNullOrEmpty(aluno.getEmail())) {
            missingFields.add("email");
        }
        if (isNullOrEmpty(aluno.getCurso())) {
            missingFields.add("curso");
        }

        if (!missingFields.isEmpty()) {
            throw new ValidacaoAlunoException(String.join(", ", missingFields));
        }
    }

    private boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }


    private void validateRegistrationDuplicationAluno(Aluno aluno){

        boolean alunoExists = repository.existsByNomeAndEmailAndCurso(aluno.getNome(), aluno.getEmail(), aluno.getCurso());
        if (alunoExists) {
            throw new InformacaoAlunoDuplicadaException(aluno.getNome(), aluno.getEmail(), aluno.getCurso());
        }
    }


    public List<Aluno> findAll() {
        repository.findAll();
        return repository.findAll();

    }

    public Aluno findById(Long id) {
        validateAlunoExists(id);
        return repository.findById(id).get();
    }

    private void validateAlunoExists(Long id){

        if (!repository.existsById(id)) {
            throw new IdNaoEncontradoException(id, "Aluno");
        }

    }


    public Aluno update(Long id, AlunoDTO alunoDTO) {
        Aluno alunoAtual = searchByIdAluno(id);

        boolean foiAlterado = false;
        foiAlterado |= updateNameAluno(alunoAtual, alunoDTO);
        foiAlterado |= updateEmailAluno(alunoAtual, alunoDTO);
        foiAlterado |= updateCourseAluno(alunoAtual, alunoDTO);

        checkChangeAluno(foiAlterado);

        return repository.save(alunoAtual);
    }

    private Aluno searchByIdAluno(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IdNaoEncontradoException(id, "Aluno"));
    }

    private void checkChangeAluno(boolean foiAlterado) {
        if (!foiAlterado) {
            throw new NenhumCampoAlteradoException();
        }
    }

    private boolean updateNameAluno(Aluno aluno, AlunoDTO alunoDTO) {
        if (alunoDTO.getNome() != null && !alunoDTO.getNome().equals(aluno.getNome())) {
            validateUniqueAluno(alunoDTO.getNome(), aluno.getEmail(), aluno.getCurso(), aluno.getId());
            aluno.setNome(alunoDTO.getNome());
            return true;
        }
        return false;
    }

    private boolean updateEmailAluno(Aluno aluno, AlunoDTO alunoDTO) {
        if (alunoDTO.getEmail() != null && !alunoDTO.getEmail().equals(aluno.getEmail())) {
            validateUniqueAluno(aluno.getNome(), alunoDTO.getEmail(), aluno.getCurso(), aluno.getId());
            aluno.setEmail(alunoDTO.getEmail());
            return true;
        }
        return false;
    }

    private boolean updateCourseAluno(Aluno aluno, AlunoDTO alunoDTO) {
        if (alunoDTO.getCurso() != null && !alunoDTO.getCurso().equals(aluno.getCurso())) {
            validateUniqueAluno(aluno.getNome(), aluno.getEmail(), alunoDTO.getCurso(), aluno.getId());
            aluno.setCurso(alunoDTO.getCurso());
            return true;
        }
        return false;
    }

    private void validateUniqueAluno(String nome, String email, String curso, Long id) {
        List<Aluno> alunosEncontrados = repository.findByNomeAndEmailAndCurso(nome, email, curso);

        for (Aluno aluno : alunosEncontrados) {
            if (!aluno.getId().equals(id)) {
                throw new InformacaoAlunoDuplicadaException(aluno.getNome(), aluno.getEmail(), aluno.getCurso());
            }
        }
    }

    public void delete(Long id) {

        if (!repository.existsById(id)) {
            throw new IdNaoEncontradoException(id, "Aluno");
        }
        repository.deleteById(id);

    }



}














