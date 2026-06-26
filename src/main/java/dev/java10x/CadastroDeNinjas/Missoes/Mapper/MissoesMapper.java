package dev.java10x.CadastroDeNinjas.Missoes.Mapper;

import dev.java10x.CadastroDeNinjas.Missoes.DTO.MissoesDTO;
import dev.java10x.CadastroDeNinjas.Missoes.Model.MissoesModel;
import org.springframework.stereotype.Component;

@Component
public class MissoesMapper {

    // Converte o DTO recebido pela API para a entidade usada pelo repository.
    // Aqui o DTO "sai" e vira Model porque o repository so sabe salvar entidades JPA.
    public MissoesModel map(MissoesDTO missoesDTO) {
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

    // Converte a entidade salva no banco para DTO antes de devolver a resposta da API.
    // Aqui o Model "sai" e vira DTO porque o controller deve devolver JSON sem expor a entidade JPA.
    public MissoesDTO map(MissoesModel missoesModel) {
        if (missoesModel == null) {
            return null;
        }

        MissoesDTO missoesDTO = new MissoesDTO();
        missoesDTO.setId(missoesModel.getId());
        missoesDTO.setNome(missoesModel.getNome());
        missoesDTO.setDificuldade(missoesModel.getDificuldade());
        missoesDTO.setRecompensa(missoesModel.getRecompensa());
        return missoesDTO;
    }
}
