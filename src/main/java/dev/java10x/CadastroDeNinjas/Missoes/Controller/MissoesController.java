package dev.java10x.CadastroDeNinjas.Missoes.Controller;

import dev.java10x.CadastroDeNinjas.Missoes.DTO.MissoesDTO;
import dev.java10x.CadastroDeNinjas.Missoes.Service.MissoesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/missoes")
@Tag(name = "Missoes", description = "Endpoints REST para cadastrar, listar, atualizar e remover missoes.")
public class MissoesController {

    private final MissoesService missoesService;

    // Recebe o service pelo construtor para o controller poder chamar as regras de missoes.
    public MissoesController(MissoesService missoesService) {
        this.missoesService = missoesService;
    }

    // Endpoint de teste. Usa ResponseEntity para devolver o texto junto com status HTTP 200.
    @Operation(summary = "Boas-vindas de missoes", description = "Endpoint simples para confirmar que o controller de missoes esta respondendo.")
    @ApiResponse(responseCode = "200", description = "Mensagem de boas-vindas retornada com sucesso.")
    @GetMapping("/boasvindas")
    public ResponseEntity<String> boasVindasMissoes() {
        System.out.println("Bem vindo");
        return ResponseEntity.ok("Bem vindo");
    }

    // POST /missoes - recebe MissoesDTO, salva como MissoesModel no service e devolve DTO com status 201.
    // DTO fica na entrada/saida da API; Model fica escondido no service, mapper e repository.
    @Operation(summary = "Criar missao", description = "Recebe uma MissoesDTO no corpo da requisicao, converte para Model no service e salva no banco.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Missao criada com sucesso."),
            @ApiResponse(responseCode = "400", description = "JSON invalido ou campo enviado em formato incorreto.")
    })
    @PostMapping
    public ResponseEntity<MissoesDTO> criarMissoes(@RequestBody MissoesDTO missoesDTO) {
        System.out.println("Criando missao");
        MissoesDTO novaMissao = missoesService.criarMissoes(missoesDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaMissao);
    }

    // GET /missoes/listar - devolve lista de MissoesDTO para nao expor a entidade MissoesModel no JSON.
    @Operation(summary = "Listar missoes", description = "Busca todas as missoes cadastradas e devolve uma lista de MissoesDTO.")
    @ApiResponse(responseCode = "200", description = "Lista de missoes retornada com sucesso.")
    @GetMapping("/listar")
    public ResponseEntity<List<MissoesDTO>> mostrarTodasMissoes() {
        List<MissoesDTO> missoes = missoesService.listarMissoes();
        return ResponseEntity.ok(missoes);
    }

    // GET /missoes/listar/{id} - se o service encontrar, devolve MissoesDTO com 200; se nao, devolve 404.
    @Operation(summary = "Buscar missao por ID", description = "Busca uma missao especifica pelo ID informado na URL.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Missao encontrada."),
            @ApiResponse(responseCode = "404", description = "Nenhuma missao encontrada com o ID informado.")
    })
    @GetMapping("/listar/{id}")
    public ResponseEntity<MissoesDTO> mostrarMissoesPorId(@PathVariable Long id) {
        MissoesDTO missao = missoesService.listarMissoesById(id);

        if (missao == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(missao);
    }

    // PUT /missoes/alterar/{id} - recebe MissoesDTO, atualiza o registro e devolve DTO atualizado com 200.
    @Operation(summary = "Atualizar missao", description = "Atualiza todos os dados de uma missao existente pelo ID informado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Missao atualizada com sucesso."),
            @ApiResponse(responseCode = "404", description = "Nenhuma missao encontrada com o ID informado.")
    })
    @PutMapping("/alterar/{id}")
    public ResponseEntity<MissoesDTO> alterarMissaoPorId(@PathVariable Long id, @RequestBody MissoesDTO missoesAtualizado) {
        MissoesDTO missao = missoesService.alterarMissoesPorId(id, missoesAtualizado);

        if (missao == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(missao);
    }

    // DELETE /missoes/deletar/{id} - remove a missao e devolve 204, indicando sucesso sem corpo de resposta.
    @Operation(summary = "Remover missao", description = "Remove uma missao pelo ID e retorna 204 quando a operacao termina sem corpo de resposta.")
    @ApiResponse(responseCode = "204", description = "Missao removida com sucesso.")
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarMissaoPorId(@PathVariable Long id) {
        missoesService.deletarMissoesPorId(id);
        return ResponseEntity.noContent().build();
    }
}
