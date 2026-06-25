package dev.java10x.CadastroDeNinjas.Missoes.Controller;

import dev.java10x.CadastroDeNinjas.Missoes.Model.MissoesModel;
import dev.java10x.CadastroDeNinjas.Missoes.Service.MissoesService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/missoes")
public class MissoesController {

    private final MissoesService missoesService;

    // Recebe o service pelo construtor para o controller poder chamar as regras de missoes.
    public MissoesController(MissoesService missoesService) {
        this.missoesService = missoesService;
    }

    // Endpoint de teste para confirmar que as rotas de missoes estao respondendo.
    @GetMapping("/boasvindas")
    public String boasVindasMissoes() {
        System.out.println("Bem vindo");
        return "Bem vindo";
    }

    // POST /missoes - prepara o endpoint responsavel por criar uma nova missao.
    @PostMapping
    public String criarMissoes() {
        System.out.println("Criando missao");
        return "Criando missao";
    }

    // GET /missoes/listar - retorna todas as missoes cadastradas no banco.
    @GetMapping("/listar")
    public List<MissoesModel> mostrarTodasMissoes() {
        return missoesService.listarMissoes();
    }

    // GET /missoes/listar/{id} - retorna uma missao especifica a partir do ID informado na URL.
    @GetMapping("/listar/{id}")
    public MissoesModel mostrarMissoesPorId(@PathVariable Long id) {
        return missoesService.listarMissoesById(id);
    }

    // PUT /missoes/alterar/{id} - atualiza todos os dados de uma missao existente.
    @PutMapping("/alterar/{id}")
    public MissoesModel alterarMissaoPorId(@PathVariable Long id, @RequestBody MissoesModel missoesAtualizado) {
        return missoesService.alterarMissoesPorId(id, missoesAtualizado);
    }

    // DELETE /missoes/deletar/{id} - remove uma missao existente pelo ID informado na URL.
    @DeleteMapping("/deletar/{id}")
    public void deletarMissaoPorId(@PathVariable Long id) {
        missoesService.deletarMissoesPorId(id);
    }
}
