package com.alunoonline.api.repository;

import com.alunoonline.api.model.Aluno;
import com.alunoonline.api.model.Disciplina;
import com.alunoonline.api.model.MatriculaAluno;
import com.alunoonline.api.model.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatriculaAlunoRepository extends JpaRepository<MatriculaAluno, Long> {

    boolean existsByAlunoAndDisciplina (Aluno aluno, Disciplina disciplina);


}
