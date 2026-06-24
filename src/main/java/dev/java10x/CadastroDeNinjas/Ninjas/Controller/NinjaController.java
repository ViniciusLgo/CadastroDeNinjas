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

    // PUT /ninjas/{id} - prepara o endpoint responsavel por atualizar um ninja existente.
    @PutMapping("/{id}")
    public String alterarNinjaPorId(@PathVariable Long id) {
        System.out.println("Alterando ninja por ID: " + id);
        return "Alterando ninja por ID: " + id;
    }

    // DELETE /ninjas/{id} - prepara o endpoint responsavel por remover um ninja existente.
    @DeleteMapping("/{id}")
    public String deletarNinjaPorId(@PathVariable Long id) {
        System.out.println("Deletando ninja por ID: " + id);
        return "Deletando ninja por ID: " + id;
    }
}
