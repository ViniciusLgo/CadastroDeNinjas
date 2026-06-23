package dev.java10x.CadastroDeNinjas.Missoes.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class MissoesController {

    @GetMapping("/boasvindas/missoes")
    public String boasVindasMissoes() {
        System.out.println("Bem vindo");
        return "Bem vindo";
    }
}
