package dev.java10x.CadastroDeNinjas.Missoes.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MissoesDTO {

    // DTO usado para transportar os dados da missao sem expor diretamente a entidade JPA.
    // Este objeto fica na borda da API: entra pelo Controller e volta como resposta em JSON.
    // O MissoesModel nao deve sair no Controller porque ele representa a tabela e os relacionamentos do banco.
    private Long id;
    private String nome;
    private String dificuldade;
    private String recompensa;
}
