package com.alunoonline.api.repository;

import com.alunoonline.api.model.Aluno;
import com.alunoonline.api.model.Disciplina;
import com.alunoonline.api.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProfessorRepository extends JpaRepository <Professor, Long> {


    boolean existsByNomeAndEmail(String nome, String email);

    List<Professor> findByNomeAndEmail(String nome, String email);



}
