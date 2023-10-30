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
        Aluno alunoToUpdate = repository.findById(id)
                .orElseThrow(() -> new IdNaoEncontradoException(id, "Aluno"));

        boolean alterado = false;


        if (alunoDTO.getNome() != null && !alunoDTO.getNome().equals(alunoToUpdate.getNome())) {
            alunoToUpdate.setNome(alunoDTO.getNome());
            alterado = true;
        }
        if (alunoDTO.getEmail() != null && !alunoDTO.getEmail().equals(alunoToUpdate.getEmail())) {
            alunoToUpdate.setEmail(alunoDTO.getEmail());
            alterado = true;
        }
        if (alunoDTO.getCurso() != null && !alunoDTO.getCurso().equals(alunoToUpdate.getCurso())) {
            alunoToUpdate.setCurso(alunoDTO.getCurso());
            alterado = true;
        }

        if (!alterado) {
            String nomeParaMensagem = alunoDTO.getNome() != null ? alunoDTO.getNome() : alunoToUpdate.getNome();
            throw new NenhumCampoAlteradoException(nomeParaMensagem);
        }

        Aluno alunoAtualizado = repository.save(alunoToUpdate);

        return alunoAtualizado;
    }


    public void delete(Long id) {

        if (!repository.existsById(id)) {
            throw new IdNaoEncontradoException(id, "Aluno");
        }
        repository.deleteById(id);

    }



}














