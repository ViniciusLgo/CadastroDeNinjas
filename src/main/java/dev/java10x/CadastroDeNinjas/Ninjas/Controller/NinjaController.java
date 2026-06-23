package dev.java10x.CadastroDeNinjas.Ninjas.Controller;

import org.springframework.web.bind.annotation.GetMapping;
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
}
