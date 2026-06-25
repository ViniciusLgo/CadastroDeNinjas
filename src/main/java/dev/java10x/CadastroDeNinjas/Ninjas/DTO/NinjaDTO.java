package dev.java10x.CadastroDeNinjas.Ninjas.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NinjaDTO {

    // DTO usado para transportar os dados do ninja sem expor diretamente a entidade JPA.
    private Long id;
    private String nome;
    private String email;
    private int idade;
    private String imgUrl;
    private String rank;
    private Long missoesId;
    private String missoesNome;
}
