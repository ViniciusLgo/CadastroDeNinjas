package dev.java10x.CadastroDeNinjas.Ninjas.Controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ninjas")
public class NinjaController {

    // Endpoint simples para confirmar que o controller de ninjas esta respondendo.
    @GetMapping("/boasvindas")
    public String boasVindas() {
        System.out.println("Bem vindo");
        return "Bem vindo";
    }

    // POST /ninjas cria um novo ninja.
    @PostMapping
    public String criar() {
        System.out.println("Criando");
        return "Criando ninja";
    }

    // GET /ninjas lista todos os ninjas.
    @GetMapping
    public String mostrarTodosOsNinjas() {
        System.out.println("Mostrando");
        return "Mostrando ninjas";
    }

    // GET /ninjas/{id} busca um ninja especifico.
    @GetMapping("/{id}")
    public String mostrarNinjaPorId(@PathVariable Long id) {
        System.out.println("Mostrando ninja por ID: " + id);
        return "Mostrando ninja por ID: " + id;
    }

    // PUT /ninjas/{id} atualiza todos os dados de um ninja.
    @PutMapping("/{id}")
    public String alterarNinjaPorId(@PathVariable Long id) {
        System.out.println("Alterando ninja por ID: " + id);
        return "Alterando ninja por ID: " + id;
    }

    // DELETE /ninjas/{id} remove um ninja especifico.
    @DeleteMapping("/{id}")
    public String deletarNinjaPorId(@PathVariable Long id) {
        System.out.println("Deletando ninja por ID: " + id);
        return "Deletando ninja por ID: " + id;
    }
}
