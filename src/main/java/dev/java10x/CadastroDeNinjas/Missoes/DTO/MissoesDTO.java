package dev.java10x.CadastroDeNinjas.Missoes.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MissoesDTO {

    // DTO usado para transportar os dados da missao sem expor diretamente a entidade JPA.
    private Long id;
    private String nome;
    private String dificuldade;
    private String recompensa;
}
