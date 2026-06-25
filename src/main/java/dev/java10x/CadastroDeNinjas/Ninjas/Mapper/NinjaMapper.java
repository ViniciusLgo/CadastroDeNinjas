package dev.java10x.CadastroDeNinjas.Ninjas.Mapper;

import dev.java10x.CadastroDeNinjas.Missoes.Model.MissoesModel;
import dev.java10x.CadastroDeNinjas.Ninjas.DTO.NinjaDTO;
import dev.java10x.CadastroDeNinjas.Ninjas.Model.NinjaModel;
import org.springframework.stereotype.Component;

@Component
public class NinjaMapper {

    // Converte o DTO recebido pela API para a entidade usada pelo repository.
    public NinjaModel map(NinjaDTO ninjaDTO) {
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

    // Converte a entidade salva no banco para DTO antes de devolver a resposta da API.
    public NinjaDTO map(NinjaModel ninjaModel) {
        if (ninjaModel == null) {
            return null;
        }

        NinjaDTO ninjaDTO = new NinjaDTO();
        ninjaDTO.setId(ninjaModel.getId());
        ninjaDTO.setNome(ninjaModel.getNome());
        ninjaDTO.setEmail(ninjaModel.getEmail());
        ninjaDTO.setIdade(ninjaModel.getIdade());
        ninjaDTO.setImgUrl(ninjaModel.getImgUrl());
        ninjaDTO.setRank(ninjaModel.getRank());

        if (ninjaModel.getMissoes() != null) {
            ninjaDTO.setMissoesId(ninjaModel.getMissoes().getId());
            ninjaDTO.setMissoesNome(ninjaModel.getMissoes().getNome());
        }

        return ninjaDTO;
    }
}
