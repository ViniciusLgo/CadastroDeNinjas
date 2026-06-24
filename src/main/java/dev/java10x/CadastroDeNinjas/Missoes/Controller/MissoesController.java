package dev.java10x.CadastroDeNinjas.Missoes.Controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class MissoesController {

    @GetMapping("/boasvindas/missoes")
    public String boasVindasMissoes() {
        System.out.println("Bem vindo");
        return "Bem vindo";
    }



    // add missoes, alterar, procurar, mostrar e excluir ninjas endpoint
    @PostMapping("/criarMissoes")
    public String criarMissoes() {
        System.out.println("Criando");
        return "Criando2";
    }

    @GetMapping("/todasMissoes")
    public String mostrarTodasMissoes() {
        System.out.println("Mostrando");
        return "Mostrando";
    }

    //mostrar por ID
    @GetMapping("/todasMissoesID")
    public String mostrarTodasMissoesID() {
        System.out.println("mostrar Todos Os Ninjas Por ID");
        return "02 mostrar Todos Os Ninjas Por ID ";
    }

    //alterar dados (UPDATE)
    @PutMapping("/alterarMissoesID")
    public String alterarMissoesID() {
        System.out.println("Alterando");
        return "Alterando";
    }

    @DeleteMapping("/deletarMissoesID")
    public String deletarMissoesID() {
        System.out.println("Deletando");
        return "Deletando";
    }



}
