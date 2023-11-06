package com.alunoonline.api.services;


import com.alunoonline.api.dto.AlunoDTO;
import com.alunoonline.api.exception.IdNaoEncontradoException;
import com.alunoonline.api.exception.InformacaoAlunoDuplicadaException;
import com.alunoonline.api.exception.NenhumCampoAlteradoException;
import com.alunoonline.api.model.Aluno;
import com.alunoonline.api.model.Professor;
import com.alunoonline.api.repository.AlunoRepository;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

//Preciso informar para o spring que é um service - usa a anotação service
@Service
public class AlunoService {

    //Os metodos são o CRUD - Create, Read, Update e Delete
    //Tenho que buscar o aluno no pacote model

    //Injeção do repository
    @Autowired
    AlunoRepository repository;

    public Aluno create(Aluno aluno) {

        boolean alunoExists = repository.existsByNomeAndEmailAndCurso(aluno.getNome(), aluno.getEmail(), aluno.getCurso());
        if (alunoExists) {
            throw new InformacaoAlunoDuplicadaException(aluno.getNome(), aluno.getEmail(), aluno.getCurso());
        }

        return repository.save(aluno);


    }

    public List<Aluno> findAll() {
        repository.findAll();
        return repository.findAll();

    }

    public Aluno findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IdNaoEncontradoException(id,"Aluno"));
    }


    public Aluno update(Long id, AlunoDTO alunoDTO) {
        Aluno alunoAtual = repository.findById(id)
                .orElseThrow(() -> new IdNaoEncontradoException(id, "Aluno"));

        boolean foiAlterado = false;


        if (alunoDTO.getNome() != null && !alunoDTO.getNome().equals(alunoAtual.getNome())) {
            alunoAtual.setNome(alunoDTO.getNome());
            foiAlterado = true;
        }
        if (alunoDTO.getEmail() != null && !alunoDTO.getEmail().equals(alunoAtual.getEmail())) {
            alunoAtual.setEmail(alunoDTO.getEmail());
            foiAlterado = true;
        }
        if (alunoDTO.getCurso() != null && !alunoDTO.getCurso().equals(alunoAtual.getCurso())) {
            alunoAtual.setCurso(alunoDTO.getCurso());
            foiAlterado = true;
        }

        if (!foiAlterado) {
            throw new NenhumCampoAlteradoException();
        }

        Aluno alunoAtualizado = repository.save(alunoAtual);

        return alunoAtualizado;
    }


    public void delete(Long id) {

        if (!repository.existsById(id)) {
            throw new IdNaoEncontradoException(id, "Aluno");
        }
        repository.deleteById(id);

    }



}














