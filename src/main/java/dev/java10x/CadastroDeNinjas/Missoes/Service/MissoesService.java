package dev.java10x.CadastroDeNinjas.Missoes.Service;

import dev.java10x.CadastroDeNinjas.Missoes.Model.MissoesModel;
import dev.java10x.CadastroDeNinjas.Missoes.Repository.MissoesRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MissoesService {

    private final MissoesRepository missoesRepository;

    // Recebe o repository pelo construtor para o service acessar os dados de missoes.
    public MissoesService(MissoesRepository missoesRepository) {
        this.missoesRepository = missoesRepository;
    }

    // Busca no banco todas as missoes cadastradas.
    public List<MissoesModel> listarMissoes() {
        return missoesRepository.findAll();
    }

    // Busca uma missao pelo ID. Se nao encontrar, retorna null por enquanto.
    public MissoesModel listarMissoesById(Long id) {
        Optional<MissoesModel> missoesPorId = missoesRepository.findById(id);
        return missoesPorId.orElse(null);
    }

    // Remove uma missao pelo ID usando o metodo pronto do JpaRepository.
    public void deletarMissoesPorId(Long id) {
        missoesRepository.deleteById(id);
    }

    // Atualiza todos os dados de uma missao existente.
    public MissoesModel alterarMissoesPorId(Long id, MissoesModel missoesAtualizado) {
        if (missoesRepository.existsById(id)) {
            missoesAtualizado.setId(id);
            return missoesRepository.save(missoesAtualizado);
        }

        return null;
    }
}
