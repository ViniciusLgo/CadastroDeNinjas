package dev.java10x.CadastroDeNinjas.Ninjas.Model;

import dev.java10x.CadastroDeNinjas.Missoes.Model.MissoesModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "tb_cadastro_de_ninjas")
@NoArgsConstructor
@AllArgsConstructor
public class NinjaModel {

    // Identificador unico do ninja. O banco gera esse valor automaticamente.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // Dados principais do ninja cadastrados na tabela tb_cadastro_de_ninjas.
    @Column(name = "nome")
    private String nome;

    @Column(unique = true, name = "email")
    private String email;

    @Column(name = "idade")
    private int idade;

    @Column(name = "imgUrl")
    private String imgUrl;

    @Column(name = "rank")
    private String rank;

    // Relacionamento muitos-para-um: varios ninjas podem estar vinculados a uma unica missao.
    @ManyToOne
    @JoinColumn(name = "missoes_id")
    private MissoesModel missoes;
}
