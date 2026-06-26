package dev.java10x.CadastroDeNinjas.Missoes.Service;

import dev.java10x.CadastroDeNinjas.Missoes.DTO.MissoesDTO;
import dev.java10x.CadastroDeNinjas.Missoes.Mapper.MissoesMapper;
import dev.java10x.CadastroDeNinjas.Missoes.Model.MissoesModel;
import dev.java10x.CadastroDeNinjas.Missoes.Repository.MissoesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MissoesService {

    private final MissoesRepository missoesRepository;
    private final MissoesMapper missoesMapper;

    // Recebe o repository pelo construtor para o service acessar os dados de missoes.
    // Repository trabalha com Model; mapper faz a ponte entre Model e DTO.
    public MissoesService(MissoesRepository missoesRepository, MissoesMapper missoesMapper) {
        this.missoesRepository = missoesRepository;
        this.missoesMapper = missoesMapper;
    }

    // Busca no banco todas as missoes cadastradas e converte cada entidade para DTO.
    // O Model fica dentro do service/repository; para o controller sair, devolvemos List<MissoesDTO>.
    public List<MissoesDTO> listarMissoes() {
        return missoesRepository.findAll()
                .stream()
                .map(missoesMapper::map)
                .collect(Collectors.toList());
    }

    // Busca uma missao pelo ID. Se nao encontrar, retorna null para o controller transformar em 404.
    public MissoesDTO listarMissoesById(Long id) {
        Optional<MissoesModel> missoesPorId = missoesRepository.findById(id);
        return missoesPorId.map(missoesMapper::map).orElse(null);
    }

    // Converte o DTO recebido, salva a missao no banco como Model e devolve a resposta tambem em DTO.
    public MissoesDTO criarMissoes(MissoesDTO missoesDTO) {
        MissoesModel missoesModel = missoesMapper.map(missoesDTO);
        MissoesModel missoesSalva = missoesRepository.save(missoesModel);
        return missoesMapper.map(missoesSalva);
    }

    // Remove uma missao pelo ID usando o metodo pronto do JpaRepository.
    public void deletarMissoesPorId(Long id) {
        missoesRepository.deleteById(id);
    }

    // Atualiza todos os dados de uma missao existente.
    // Entrada e saida continuam DTO; Model aparece apenas na hora de salvar no repository.
    public MissoesDTO alterarMissoesPorId(Long id, MissoesDTO missoesAtualizado) {
        if (missoesRepository.existsById(id)) {
            MissoesModel missoesModel = missoesMapper.map(missoesAtualizado);
            missoesModel.setId(id);
            MissoesModel missoesSalva = missoesRepository.save(missoesModel);
            return missoesMapper.map(missoesSalva);
        }

        return null;
    }
}
