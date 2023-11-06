package com.alunoonline.api.repository;

import com.alunoonline.api.model.MatriculaAluno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatriculaAlunoRepository extends JpaRepository<MatriculaAluno, Long> {

    boolean existsByAlunoAndDisciplina(String aluno, String disciplina);


}
