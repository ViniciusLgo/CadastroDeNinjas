package dev.java10x.CadastroDeNinjas.Missoes.Controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/missoes")
public class MissoesController {

    // Endpoint simples para confirmar que o controller de missoes esta respondendo.
    @GetMapping("/boasvindas")
    public String boasVindasMissoes() {
        System.out.println("Bem vindo");
        return "Bem vindo";
    }

    // POST /missoes cria uma nova missao.
    @PostMapping
    public String criarMissoes() {
        System.out.println("Criando missao");
        return "Criando missao";
    }

    // GET /missoes lista todas as missoes.
    @GetMapping
    public String mostrarTodasMissoes() {
        System.out.println("Mostrando missoes");
        return "Mostrando missoes";
    }

    // GET /missoes/{id} busca uma missao especifica.
    @GetMapping("/{id}")
    public String mostrarMissaoPorId(@PathVariable Long id) {
        System.out.println("Mostrando missao por ID: " + id);
        return "Mostrando missao por ID: " + id;
    }

    // PUT /missoes/{id} atualiza todos os dados de uma missao.
    @PutMapping("/{id}")
    public String alterarMissaoPorId(@PathVariable Long id) {
        System.out.println("Alterando missao por ID: " + id);
        return "Alterando missao por ID: " + id;
    }

    // DELETE /missoes/{id} remove uma missao especifica.
    @DeleteMapping("/{id}")
    public String deletarMissaoPorId(@PathVariable Long id) {
        System.out.println("Deletando missao por ID: " + id);
        return "Deletando missao por ID: " + id;
    }
}
