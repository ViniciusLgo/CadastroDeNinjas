package dev.java10x.CadastroDeNinjas.Ninjas.Controller;

import dev.java10x.CadastroDeNinjas.Ninjas.Model.NinjaModel;
import dev.java10x.CadastroDeNinjas.Ninjas.Service.NinjaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ninjas")
public class NinjaController {

    private final NinjaService ninjaService;

    // Recebe o service pelo construtor para o controller poder chamar as regras de ninjas.
    public NinjaController(NinjaService ninjaService) {
        this.ninjaService = ninjaService;
    }

    // Endpoint de teste para confirmar que as rotas de ninjas estao respondendo.
    @GetMapping("/boasvindas")
    public String boasVindas() {
        System.out.println("Bem vindo");
        return "Bem vindo";
    }

    // POST /ninjas - prepara o endpoint responsavel por criar um novo ninja.
    @PostMapping("/criar")
    public NinjaModel criar(@RequestBody NinjaModel ninjaModel) {
        System.out.println("Criando");
        return ninjaService.criarNinja(ninjaModel);
    }

    // GET /ninjas/listar - retorna todos os ninjas cadastrados no banco.
    @GetMapping("/listar")
    public List<NinjaModel> listarTodosNinjas() {
        return ninjaService.listarTodosNinjas();
    }

    // GET /ninjas/listar/{id} - retorna um ninja especifico a partir do ID informado na URL.
    @GetMapping("/listar/{id}")
    public NinjaModel mostrarNinjaPorId(@PathVariable Long id) {
        return ninjaService.mostrarNinjasPorId(id);
    }

    // PUT /ninjas/alterar/{id} - atualiza todos os dados de um ninja existente.
    @PutMapping("/alterar/{id}")
    public NinjaModel alterarNinjaPorId(@PathVariable Long id, @RequestBody NinjaModel ninjaAtualizado) {
        return ninjaService.alterarNinja(id, ninjaAtualizado);
    }

    // DELETE /ninjas/deletar/{id} - remove um ninja existente pelo ID informado na URL.
    @DeleteMapping("/deletar/{id}")
    public void deletarNinjaPorId(@PathVariable Long id) {
        ninjaService.deleteNinjaById(id);
    }
}
