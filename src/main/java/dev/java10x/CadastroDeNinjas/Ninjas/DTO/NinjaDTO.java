package dev.java10x.CadastroDeNinjas.Ninjas.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
