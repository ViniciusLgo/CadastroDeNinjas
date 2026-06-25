package dev.java10x.CadastroDeNinjas.Ninjas.Mapper;

import dev.java10x.CadastroDeNinjas.Missoes.Model.MissoesModel;
import dev.java10x.CadastroDeNinjas.Ninjas.DTO.NinjaDTO;
import dev.java10x.CadastroDeNinjas.Ninjas.Model.NinjaModel;

public class NinjaMapper {

    // Converte a entidade de ninja para DTO, levando apenas dados simples da missao vinculada.
    public static NinjaDTO toDTO(NinjaModel ninjaModel) {
        if (ninjaModel == null) {
            return null;
        }

        MissoesModel missoesModel = ninjaModel.getMissoes();
        Long missoesId = missoesModel != null ? missoesModel.getId() : null;
        String missoesNome = missoesModel != null ? missoesModel.getNome() : null;

        return new NinjaDTO(
                ninjaModel.getId(),
                ninjaModel.getNome(),
                ninjaModel.getEmail(),
                ninjaModel.getIdade(),
                ninjaModel.getImgUrl(),
                ninjaModel.getRank(),
                missoesId,
                missoesNome
        );
    }

    // Converte o DTO recebido pela API para a entidade usada pelo repository.
    public static NinjaModel toModel(NinjaDTO ninjaDTO) {
        if (ninjaDTO == null) {
            return null;
        }

        NinjaModel ninjaModel = new NinjaModel();
        ninjaModel.setId(ninjaDTO.getId());
        ninjaModel.setNome(ninjaDTO.getNome());
        ninjaModel.setEmail(ninjaDTO.getEmail());
        ninjaModel.setIdade(ninjaDTO.getIdade());
        ninjaModel.setImgUrl(ninjaDTO.getImgUrl());
        ninjaModel.setRank(ninjaDTO.getRank());

        if (ninjaDTO.getMissoesId() != null) {
            MissoesModel missoesModel = new MissoesModel();
            missoesModel.setId(ninjaDTO.getMissoesId());
            ninjaModel.setMissoes(missoesModel);
        }

        return ninjaModel;
    }
}
