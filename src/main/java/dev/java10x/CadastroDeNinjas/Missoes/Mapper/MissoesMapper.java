package dev.java10x.CadastroDeNinjas.Missoes.Mapper;

import dev.java10x.CadastroDeNinjas.Missoes.DTO.MissoesDTO;
import dev.java10x.CadastroDeNinjas.Missoes.Model.MissoesModel;

public class MissoesMapper {

    // Converte a entidade de missao para DTO, evitando expor relacionamentos internos da JPA.
    public static MissoesDTO toDTO(MissoesModel missoesModel) {
        if (missoesModel == null) {
            return null;
        }

        return new MissoesDTO(
                missoesModel.getId(),
                missoesModel.getNome(),
                missoesModel.getDificuldade(),
                missoesModel.getRecompensa()
        );
    }

    // Converte o DTO recebido pela API para a entidade usada pelo repository.
    public static MissoesModel toModel(MissoesDTO missoesDTO) {
        if (missoesDTO == null) {
            return null;
        }

        MissoesModel missoesModel = new MissoesModel();
        missoesModel.setId(missoesDTO.getId());
        missoesModel.setNome(missoesDTO.getNome());
        missoesModel.setDificuldade(missoesDTO.getDificuldade());
        missoesModel.setRecompensa(missoesDTO.getRecompensa());

        return missoesModel;
    }
}
