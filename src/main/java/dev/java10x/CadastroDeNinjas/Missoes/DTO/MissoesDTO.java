package dev.java10x.CadastroDeNinjas.Missoes.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MissoesDTO {

    // DTO usado para transportar os dados da missao sem expor diretamente a entidade JPA.
    private Long id;
    private String nome;
    private String dificuldade;
    private String recompensa;
}
