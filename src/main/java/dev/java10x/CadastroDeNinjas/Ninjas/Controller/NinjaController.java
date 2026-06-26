package dev.java10x.CadastroDeNinjas.Ninjas.Controller;

import dev.java10x.CadastroDeNinjas.Ninjas.DTO.NinjaDTO;
import dev.java10x.CadastroDeNinjas.Ninjas.Service.NinjaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    // Endpoint de teste. Usa ResponseEntity para devolver o texto junto com status HTTP 200.
    @GetMapping("/boasvindas")
    public ResponseEntity<String> boasVindas() {
        System.out.println("Bem vindo");
        return ResponseEntity.ok("Bem vindo");
    }

    // POST /ninjas/criar - recebe NinjaDTO, salva como NinjaModel no service e devolve NinjaDTO com status 201.
    @PostMapping("/criar")
    public ResponseEntity<NinjaDTO> criar(@RequestBody NinjaDTO ninjaDTO) {
        NinjaDTO novoNinja = ninjaService.criarNinja(ninjaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoNinja);
    }

    // GET /ninjas/listar - devolve lista de NinjaDTO para nao expor a entidade NinjaModel no JSON.
    @GetMapping("/listar")
    public ResponseEntity<List<NinjaDTO>> listarTodosNinjas() {
        List<NinjaDTO> ninjas = ninjaService.listarTodosNinjas();
        return ResponseEntity.ok(ninjas);
    }

    // GET /ninjas/listar/{id} - se o service encontrar, devolve NinjaDTO com 200; se nao, devolve 404.
    @GetMapping("/listar/{id}")
    public ResponseEntity<NinjaDTO> mostrarNinjaPorId(@PathVariable Long id) {
        NinjaDTO ninja = ninjaService.mostrarNinjasPorId(id);

        if (ninja == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(ninja);
    }

    // TODO: quando virar PATCH, permitir atualizar apenas os campos enviados.
    @PutMapping("/alterar/{id}")
    public ResponseEntity<NinjaDTO> alterarNinjaPorId(@PathVariable Long id, @RequestBody NinjaDTO ninjaAtualizado) {
        NinjaDTO ninja = ninjaService.alterarNinja(id, ninjaAtualizado);

        if (ninja == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(ninja);
    }

    // DELETE /ninjas/deletar/{id} - remove o ninja e devolve 204, indicando sucesso sem corpo de resposta.
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarNinjaPorId(@PathVariable Long id) {
        ninjaService.deleteNinjaById(id);
        return ResponseEntity.noContent().build();
    }
}
