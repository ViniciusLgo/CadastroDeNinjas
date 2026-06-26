package dev.java10x.CadastroDeNinjas.Ninjas.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO que representa os dados de entrada e saida de um ninja na API.")
public class NinjaDTO {

    // DTO usado para transportar os dados do ninja sem expor diretamente a entidade JPA.
    // Este objeto fica na borda da API: entra pelo Controller e volta como resposta em JSON.
    // O NinjaModel nao deve sair no Controller porque ele representa a tabela e os relacionamentos do banco.
    @Schema(description = "Identificador unico do ninja.", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Nome do ninja.", example = "Naruto Uzumaki")
    private String nome;

    @Schema(description = "Email de contato do ninja.", example = "naruto@konoha.com")
    private String email;

    @Schema(description = "Idade do ninja.", example = "17")
    private int idade;

    @Schema(description = "URL da imagem usada na API e na UI.", example = "https://exemplo.com/naruto.png")
    private String imgUrl;

    @Schema(description = "Rank ou nivel do ninja.", example = "Genin")
    private String rank;

    @Schema(description = "ID da missao vinculada ao ninja.", example = "1")
    private Long missoesId;

    @Schema(description = "Nome da missao vinculada. Campo usado na resposta para facilitar a leitura.", example = "Resgate do gato perdido", accessMode = Schema.AccessMode.READ_ONLY)
    private String missoesNome;
}
