package com.alunoonline.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;


//@Entity é a entendidade Aluno, interage com o banco de dados, ou seja
@Entity
//Faz os metodos gets e sets
@Data
// Cria os construtores com parametros e contrutores sem parametros
@AllArgsConstructor
@NoArgsConstructor
public class Aluno {

    //@Id sempre é a primeira coluna da minha tabela, boa prática
    //@GeneratedValue é o valor gerado para o campo ID é a estratégia
    // Identity é a identidade de um e um
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank (message = "O nome é obrigatório.")
    private String nome;

    @NotBlank (message = "O email é obrigatório.")
    private String email;

    @NotBlank (message = "O curso é obrigatório.")
    private String curso;


}
