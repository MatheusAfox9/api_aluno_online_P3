package com.alunoonline.api.model;


import com.alunoonline.api.enums.StatusMatricula;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class MatriculaAluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "disciplina_id")
    private Disciplina disciplina;
    private Double nota1;
    private Double nota2;



    //se a anotação @Enumerated para informar ao JPA como armazenar o enum no banco de dados.
    // Existem dois modos de armazenar um enum no banco de dados usando JPA: EnumType.STRING e EnumType.ORDINAL.
    // Recomendo usar EnumType.STRING, pois armazenará o nome do enum (por exemplo, "MATRICULADO")
    // como uma string no banco de dados, tornando-o mais legível.

    @Enumerated(EnumType.STRING)
    private StatusMatricula status;



}
