package dev.java10x.CadastroDeNinjas.Missoes.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO que representa os dados de entrada e saida de uma missao na API.")
public class MissoesDTO {

    // DTO usado para transportar os dados da missao sem expor diretamente a entidade JPA.
    // Este objeto fica na borda da API: entra pelo Controller e volta como resposta em JSON.
    // O MissoesModel nao deve sair no Controller porque ele representa a tabela e os relacionamentos do banco.
    @Schema(description = "Identificador unico da missao.", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Nome da missao.", example = "Resgate do gato perdido")
    private String nome;

    @Schema(description = "Nivel de dificuldade da missao.", example = "D")
    private String dificuldade;

    @Schema(description = "Recompensa prometida pela missao.", example = "500 ryos")
    private String recompensa;
}
