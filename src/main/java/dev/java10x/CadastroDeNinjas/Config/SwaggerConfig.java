package dev.java10x.CadastroDeNinjas.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    // Bean que personaliza a documentacao OpenAPI gerada automaticamente pelo springdoc.
    // Pense nele como a "capa do pergaminho": titulo, descricao, versao e servidor da API.
    @Bean
    public OpenAPI cadastroDeNinjasOpenAPI() {
        Server servidorLocal = new Server()
                .url("http://localhost:8080")
                .description("Servidor local de desenvolvimento");

        Contact contato = new Contact()
                .name("ViniciusLgo")
                .url("https://github.com/ViniciusLgo/CadastroDeNinjas");

        License licenca = new License()
                .name("Uso educacional")
                .url("https://github.com/ViniciusLgo/CadastroDeNinjas");

        Info informacoesApi = new Info()
                .title("Cadastro de Ninjas API")
                .description("Documentacao REST para cadastro de ninjas e missoes. Use esta tela para estudar e testar os endpoints sem abrir o Postman.")
                .version("1.0.0")
                .contact(contato)
                .license(licenca);

        return new OpenAPI()
                .servers(List.of(servidorLocal))
                .info(informacoesApi);
    }
}
