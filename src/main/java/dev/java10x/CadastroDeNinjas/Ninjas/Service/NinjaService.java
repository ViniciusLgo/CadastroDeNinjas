package dev.java10x.CadastroDeNinjas.Ninjas.Service;

import dev.java10x.CadastroDeNinjas.Ninjas.DTO.NinjaDTO;
import dev.java10x.CadastroDeNinjas.Ninjas.Mapper.NinjaMapper;
import dev.java10x.CadastroDeNinjas.Ninjas.Model.NinjaModel;
import dev.java10x.CadastroDeNinjas.Ninjas.Repository.NinjaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NinjaService {

    private final NinjaRepository ninjaRepository;
    private final NinjaMapper ninjaMapper;

    public NinjaService(NinjaRepository ninjaRepository, NinjaMapper ninjaMapper) {
        this.ninjaRepository = ninjaRepository;
        this.ninjaMapper = ninjaMapper;
    }

    // Busca no banco todos os ninjas cadastrados e converte cada entidade para DTO.
    public List<NinjaDTO> listarTodosNinjas() {
        return ninjaRepository.findAll()
                .stream()
                .map(ninjaMapper::map)
                .collect(Collectors.toList());
    }

    // Busca um ninja pelo ID. Se nao encontrar, retorna null por enquanto.
    public NinjaDTO mostrarNinjasPorId(Long id) {
        Optional<NinjaModel> ninjaPorId = ninjaRepository.findById(id);
        return ninjaPorId.map(ninjaMapper::map).orElse(null);
    }

    // Converte o DTO recebido, salva o ninja no banco e devolve a resposta tambem em DTO.
    public NinjaDTO criarNinja(NinjaDTO ninjaDTO) {
        NinjaModel ninja = ninjaMapper.map(ninjaDTO);
        NinjaModel ninjaSalvo = ninjaRepository.save(ninja);
        return ninjaMapper.map(ninjaSalvo);
    }

    // Remove um ninja pelo ID usando o metodo pronto do JpaRepository.
    public void deleteNinjaById(Long id) {
        ninjaRepository.deleteById(id);
    }

    // Atualiza todos os dados de um ninja existente.
    // TODO: refinar este metodo para permitir alteracoes parciais no PUT,
    // mantendo os valores atuais quando algum campo nao vier no JSON.
    public NinjaDTO alterarNinja(Long id, NinjaDTO ninjaAtualizado) {
        if (ninjaRepository.existsById(id)) {
            NinjaModel ninjaModel = ninjaMapper.map(ninjaAtualizado);
            ninjaModel.setId(id);
            NinjaModel ninjaSalvo = ninjaRepository.save(ninjaModel);
            return ninjaMapper.map(ninjaSalvo);
        }
        return null;
    }
}
