package dev.java10x.CadastroDeNinjas.Ninjas.Service;

import dev.java10x.CadastroDeNinjas.Ninjas.Model.NinjaModel;
import dev.java10x.CadastroDeNinjas.Ninjas.Repository.NinjaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NinjaService {

    private final NinjaRepository ninjaRepository;

    // Recebe o repository pelo construtor para o service acessar os dados de ninjas.
    public NinjaService(NinjaRepository ninjaRepository) {
        this.ninjaRepository = ninjaRepository;
    }

    // Busca no banco todos os ninjas cadastrados.
    public List<NinjaModel> listarTodosNinjas() {
        return ninjaRepository.findAll();
    }

    // Busca um ninja pelo ID. Se nao encontrar, retorna null por enquanto.
    public NinjaModel mostrarNinjasPorId(Long id) {
        Optional<NinjaModel> ninjaPorId = ninjaRepository.findById(id);
        return ninjaPorId.orElse(null);
    }

    // criar um ninja
    public NinjaModel criarNinja(NinjaModel ninjamodel) {
        return ninjaRepository.save(ninjamodel);
    }
}
