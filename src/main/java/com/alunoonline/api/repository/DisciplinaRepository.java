package com.alunoonline.api.repository;

import com.alunoonline.api.model.Aluno;
import com.alunoonline.api.model.Disciplina;
import com.alunoonline.api.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DisciplinaRepository extends JpaRepository<Disciplina, Long>  {

    boolean existsByNomeAndProfessor(String nome, Professor professor);

    List<Disciplina> findByProfessorId(Long professorId);


}



