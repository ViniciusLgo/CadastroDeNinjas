package dev.java10x.CadastroDeNinjas.Ninjas.Controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class NinjaController {

    @GetMapping("/boasvindas/ninja")
    public String boasVindas() {
        System.out.println("Bem vindo");
        return "Bem vindo";
    }

    // add ninjas, alterar, procurar, mostrar e excluir ninjas endpoint
    @PostMapping("/criar")
    public String criar() {
        System.out.println("Criando");
        return "Criando2";
    }

    @GetMapping("/todos")
    public String mostrarTodosOsNinjas() {
        System.out.println("Mostrando");
        return "Mostrando";
    }

    //mostrar por ID
    @GetMapping("/todosID")
    public String mostrarTodosOsNinjasPorID() {
        System.out.println("mostrar Todos Os Ninjas Por ID");
        return "02 mostrar Todos Os Ninjas Por ID ";
    }

    //alterar dados (UPDATE)
    @PutMapping("/alterarID")
    public String alterarID() {
        System.out.println("Alterando");
        return "Alterando";
    }

    @DeleteMapping("/deletarID")
    public String deletarNinjaPorID() {
        System.out.println("Deletando");
        return "Deletando";
    }
}
