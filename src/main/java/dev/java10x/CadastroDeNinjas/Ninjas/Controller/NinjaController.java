package dev.java10x.CadastroDeNinjas.Ninjas.Controller;

import dev.java10x.CadastroDeNinjas.Ninjas.DTO.NinjaDTO;
import dev.java10x.CadastroDeNinjas.Ninjas.Service.NinjaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ninjas")
@Tag(name = "Ninjas", description = "Endpoints REST para cadastrar, listar, atualizar e remover ninjas.")
public class NinjaController {

    private final NinjaService ninjaService;

    // Recebe o service pelo construtor para o controller poder chamar as regras de ninjas.
    public NinjaController(NinjaService ninjaService) {
        this.ninjaService = ninjaService;
    }

    // Endpoint de teste. Usa ResponseEntity para devolver o texto junto com status HTTP 200.
    @Operation(summary = "Boas-vindas de ninjas", description = "Endpoint simples para confirmar que o controller de ninjas esta respondendo.")
    @ApiResponse(responseCode = "200", description = "Mensagem de boas-vindas retornada com sucesso.")
    @GetMapping("/boasvindas")
    public ResponseEntity<String> boasVindas() {
        System.out.println("Bem vindo");
        return ResponseEntity.ok("Bem vindo");
    }

    // POST /ninjas/criar - recebe NinjaDTO, salva como NinjaModel no service e devolve NinjaDTO com status 201.
    @Operation(summary = "Criar ninja", description = "Recebe um NinjaDTO no corpo da requisicao, converte para Model no service e salva no banco.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ninja criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "JSON invalido ou campo enviado em formato incorreto.")
    })
    @PostMapping("/criar")
    public ResponseEntity<NinjaDTO> criar(@RequestBody NinjaDTO ninjaDTO) {
        NinjaDTO novoNinja = ninjaService.criarNinja(ninjaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoNinja);
    }

    // GET /ninjas/listar - devolve lista de NinjaDTO para nao expor a entidade NinjaModel no JSON.
    @Operation(summary = "Listar ninjas", description = "Busca todos os ninjas cadastrados e devolve uma lista de NinjaDTO.")
    @ApiResponse(responseCode = "200", description = "Lista de ninjas retornada com sucesso.")
    @GetMapping("/listar")
    public ResponseEntity<List<NinjaDTO>> listarTodosNinjas() {
        List<NinjaDTO> ninjas = ninjaService.listarTodosNinjas();
        return ResponseEntity.ok(ninjas);
    }

    // GET /ninjas/listar/{id} - se o service encontrar, devolve NinjaDTO com 200; se nao, devolve 404.
    @Operation(summary = "Buscar ninja por ID", description = "Busca um ninja especifico pelo ID informado na URL.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ninja encontrado."),
            @ApiResponse(responseCode = "404", description = "Nenhum ninja encontrado com o ID informado.")
    })
    @GetMapping("/listar/{id}")
    public ResponseEntity<NinjaDTO> mostrarNinjaPorId(@PathVariable Long id) {
        NinjaDTO ninja = ninjaService.mostrarNinjasPorId(id);

        if (ninja == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(ninja);
    }

    // TODO: quando virar PATCH, permitir atualizar apenas os campos enviados.
    @Operation(summary = "Atualizar ninja", description = "Atualiza todos os dados de um ninja existente. Hoje usa PUT; futuramente sera refinado para PATCH parcial.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ninja atualizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Nenhum ninja encontrado com o ID informado.")
    })
    @PutMapping("/alterar/{id}")
    public ResponseEntity<NinjaDTO> alterarNinjaPorId(@PathVariable Long id, @RequestBody NinjaDTO ninjaAtualizado) {
        NinjaDTO ninja = ninjaService.alterarNinja(id, ninjaAtualizado);

        if (ninja == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(ninja);
    }

    // DELETE /ninjas/deletar/{id} - remove o ninja e devolve 204, indicando sucesso sem corpo de resposta.
    @Operation(summary = "Remover ninja", description = "Remove um ninja pelo ID e retorna 204 quando a operacao termina sem corpo de resposta.")
    @ApiResponse(responseCode = "204", description = "Ninja removido com sucesso.")
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarNinjaPorId(@PathVariable Long id) {
        ninjaService.deleteNinjaById(id);
        return ResponseEntity.noContent().build();
    }
}
