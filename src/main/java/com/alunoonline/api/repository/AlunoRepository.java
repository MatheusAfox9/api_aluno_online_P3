package com.alunoonline.api.repository;

import com.alunoonline.api.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository é a ponte
// Extends é feita a ponte JpaRepository
//Definir <Aluno, Long> vai fazer a ponte entre o banco de dados e o objeto aluno
@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {

}
 