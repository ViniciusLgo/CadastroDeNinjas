package dev.java10x.CadastroDeNinjas.Missoes.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.java10x.CadastroDeNinjas.Ninjas.Model.NinjaModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "tb_missoes")
@NoArgsConstructor
@AllArgsConstructor
public class MissoesModel {

    // Identificador unico da missao. O banco gera esse valor automaticamente.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // Dados principais da missao cadastrados na tabela tb_missoes.
    @Column(name = "nome")
    private String nome;

    @Column(name = "dificuldade")
    private String dificuldade;

    @Column(name = "recompensa")
    private String recompensa;

    // Relacionamento um-para-muitos: uma missao pode possuir varios ninjas.
    // O mappedBy deve ter o mesmo nome do atributo que existe em NinjaModel.
    @OneToMany(mappedBy = "missoes")
    @JsonIgnore
    private List<NinjaModel> ninjas;

    // Construtor auxiliar usado quando a recompensa nao precisa ser informada.
    public MissoesModel(Long id, String nome, String dificuldade) {
        this.id = id;
        this.nome = nome;
        this.dificuldade = dificuldade;
    }

}
