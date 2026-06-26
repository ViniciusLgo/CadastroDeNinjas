package dev.java10x.CadastroDeNinjas.Missoes.Controller;

import dev.java10x.CadastroDeNinjas.Missoes.DTO.MissoesDTO;
import dev.java10x.CadastroDeNinjas.Missoes.Service.MissoesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    // Endpoint de teste. Usa ResponseEntity para devolver o texto junto com status HTTP 200.
    @GetMapping("/boasvindas")
    public ResponseEntity<String> boasVindasMissoes() {
        System.out.println("Bem vindo");
        return ResponseEntity.ok("Bem vindo");
    }

    // POST /missoes - recebe MissoesDTO, salva como MissoesModel no service e devolve DTO com status 201.
    // DTO fica na entrada/saida da API; Model fica escondido no service, mapper e repository.
    @PostMapping
    public ResponseEntity<MissoesDTO> criarMissoes(@RequestBody MissoesDTO missoesDTO) {
        System.out.println("Criando missao");
        MissoesDTO novaMissao = missoesService.criarMissoes(missoesDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaMissao);
    }

    // GET /missoes/listar - devolve lista de MissoesDTO para nao expor a entidade MissoesModel no JSON.
    @GetMapping("/listar")
    public ResponseEntity<List<MissoesDTO>> mostrarTodasMissoes() {
        List<MissoesDTO> missoes = missoesService.listarMissoes();
        return ResponseEntity.ok(missoes);
    }

    // GET /missoes/listar/{id} - se o service encontrar, devolve MissoesDTO com 200; se nao, devolve 404.
    @GetMapping("/listar/{id}")
    public ResponseEntity<MissoesDTO> mostrarMissoesPorId(@PathVariable Long id) {
        MissoesDTO missao = missoesService.listarMissoesById(id);

        if (missao == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(missao);
    }

    // PUT /missoes/alterar/{id} - recebe MissoesDTO, atualiza o registro e devolve DTO atualizado com 200.
    @PutMapping("/alterar/{id}")
    public ResponseEntity<MissoesDTO> alterarMissaoPorId(@PathVariable Long id, @RequestBody MissoesDTO missoesAtualizado) {
        MissoesDTO missao = missoesService.alterarMissoesPorId(id, missoesAtualizado);

        if (missao == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(missao);
    }

    // DELETE /missoes/deletar/{id} - remove a missao e devolve 204, indicando sucesso sem corpo de resposta.
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarMissaoPorId(@PathVariable Long id) {
        missoesService.deletarMissoesPorId(id);
        return ResponseEntity.noContent().build();
    }
}
