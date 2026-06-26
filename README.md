# Cadastro de Ninjas

API REST e interface web para cadastro de ninjas e missoes, criada como projeto de estudo em Spring Boot. O objetivo e praticar uma arquitetura em camadas usando Controller, Service, Repository, DTO, Mapper, JPA, Flyway, H2, Thymeleaf e Swagger/OpenAPI.

O projeto simula um sistema simples da vila: ninjas podem ser cadastrados, listados, atualizados, removidos e vinculados a missoes. A API REST serve para consumo por ferramentas como Postman, Swagger ou outros sistemas. A interface Thymeleaf serve para testar o fluxo visual pelo navegador.

## Status atual

O projeto ja possui:

- CRUD REST de ninjas.
- CRUD REST de missoes.
- CRUD visual de ninjas com Thymeleaf.
- DTOs para entrada e saida da API.
- Mappers para conversao entre DTO e Model.
- `ResponseEntity` nos controllers REST.
- Persistencia com Spring Data JPA.
- Banco H2 local.
- Migrations com Flyway.
- Dados de estudo via seed SQL.
- Swagger/OpenAPI para documentacao e testes da API.
- Scripts PowerShell para verificar e parar processo preso na porta `8080`.

## Tecnologias

- Java 21
- Spring Boot 4.1.0
- Spring Web MVC
- Spring Data JPA
- H2 Database
- Flyway
- Thymeleaf
- Lombok
- Springdoc OpenAPI / Swagger UI
- Maven

## Como rodar o projeto

Na raiz do projeto:

```powershell
mvn spring-boot:run
```

Depois acesse:

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`
- Interface web de ninjas: `http://localhost:8080/ninjas/ui/listar`
- H2 Console: `http://localhost:8080/h2-console`

## Banco de dados

O projeto usa H2 para desenvolvimento local.

As configuracoes principais ficam em:

```text
src/main/resources/application.properties
```

Por padrao, o banco pode ser configurado por variaveis de ambiente:

```properties
SPRING_DATASOURCE_URL
SPRING_DATASOURCE_USERNAME
SPRING_DATASOURCE_PASSWORD
SPRING_JPA_HIBERNATE_DDL_AUTO
SPRING_JPA_SHOW_SQL
SPRING_FLYWAY_ENABLED
SPRING_FLYWAY_BASELINE_ON_MIGRATE
```

As migrations ficam em:

```text
src/main/resources/db/migration
```

Arquivos importantes:

- `V1__create_initial_schema.sql`
- `V2__add_rank_to_tb_cadastro_de_ninjas.sql`
- `V3__add_recompensa_to_tb_missoes.sql`

O seed de dados de estudo fica em:

```text
src/main/resources/db/seed/seed_h2_study_data.sql
```

## Arquitetura do projeto

O projeto segue uma divisao em camadas.

```text
Controller -> Service -> Mapper -> Repository -> Model -> Banco
```

Na volta da resposta:

```text
Banco -> Model -> Mapper -> DTO -> Controller -> JSON
```

### Controller

Recebe as requisicoes HTTP e devolve respostas HTTP.

Exemplos:

- `NinjaController`
- `MissoesController`
- `NinjaControllerUI`

O controller REST trabalha com JSON e `ResponseEntity`.

O controller UI trabalha com Thymeleaf e retorna nomes de templates HTML.

### Service

Concentra a regra de negocio da aplicacao.

Exemplos:

- `NinjaService`
- `MissoesService`

O service decide como buscar, criar, atualizar e remover dados. Ele tambem chama o mapper para transformar Model em DTO e DTO em Model.

### Repository

Faz a comunicacao com o banco usando Spring Data JPA.

Exemplos:

- `NinjaRepository`
- `MissoesRepository`

Os repositories trabalham com entidades JPA, nao com DTO.

### Model

Representa as tabelas do banco.

Exemplos:

- `NinjaModel`
- `MissoesModel`

Essas classes possuem anotacoes JPA, como `@Entity`, `@Table`, `@Id`, `@Column`, `@ManyToOne` e `@OneToMany`.

### DTO

DTO significa Data Transfer Object.

No projeto, o DTO e o objeto que entra e sai da API. Ele evita que o controller exponha diretamente a entidade JPA.

Exemplos:

- `NinjaDTO`
- `MissoesDTO`

Pense assim:

- `Model` representa o banco.
- `DTO` representa o contrato da API.
- `Mapper` traduz um para o outro.

### Mapper

Mapper e a ponte entre DTO e Model.

Exemplos:

- `NinjaMapper`
- `MissoesMapper`

Quando a API recebe um JSON, o fluxo e:

```text
JSON -> DTO -> Mapper -> Model -> Repository -> Banco
```

Quando a API responde, o fluxo e:

```text
Banco -> Repository -> Model -> Mapper -> DTO -> JSON
```

## Endpoints REST

### Ninjas

Base path:

```text
/ninjas
```

Endpoints:

| Metodo | Rota | Descricao |
|---|---|---|
| GET | `/ninjas/boasvindas` | Testa se o controller responde |
| POST | `/ninjas/criar` | Cria um ninja |
| GET | `/ninjas/listar` | Lista todos os ninjas |
| GET | `/ninjas/listar/{id}` | Busca ninja por ID |
| PUT | `/ninjas/alterar/{id}` | Atualiza um ninja |
| DELETE | `/ninjas/deletar/{id}` | Remove um ninja |

Exemplo de JSON para criar ninja:

```json
{
  "nome": "Naruto Uzumaki",
  "email": "naruto@konoha.com",
  "idade": 17,
  "imgUrl": "https://exemplo.com/naruto.png",
  "rank": "Genin",
  "missoesId": 1
}
```

### Missoes

Base path:

```text
/missoes
```

Endpoints:

| Metodo | Rota | Descricao |
|---|---|---|
| GET | `/missoes/boasvindas` | Testa se o controller responde |
| POST | `/missoes` | Cria uma missao |
| GET | `/missoes/listar` | Lista todas as missoes |
| GET | `/missoes/listar/{id}` | Busca missao por ID |
| PUT | `/missoes/alterar/{id}` | Atualiza uma missao |
| DELETE | `/missoes/deletar/{id}` | Remove uma missao |

Exemplo de JSON para criar missao:

```json
{
  "nome": "Resgate do gato perdido",
  "dificuldade": "D",
  "recompensa": "500 ryos"
}
```

## Interface Thymeleaf

A interface web atual cobre o CRUD visual de ninjas.

Base path:

```text
/ninjas/ui
```

Telas:

| Rota | Descricao |
|---|---|
| `/ninjas/ui/listar` | Lista ninjas cadastrados |
| `/ninjas/ui/criar` | Formulario para criar ninja |
| `/ninjas/ui/detalhar/{id}` | Tela de detalhes |
| `/ninjas/ui/editar/{id}` | Formulario para editar ninja |
| `/ninjas/ui/deletar/{id}` | Acao de remocao via POST |

Arquivos:

```text
src/main/resources/templates/ninjas/listar.html
src/main/resources/templates/ninjas/formulario.html
src/main/resources/templates/ninjas/detalhar.html
src/main/resources/static/css/ninjas.css
```

O controller da UI fica escondido do Swagger com `@Hidden`, porque Swagger deve documentar a API REST, nao as paginas HTML.

## Swagger e OpenAPI

O projeto usa Springdoc OpenAPI.

Arquivos principais:

```text
src/main/java/dev/java10x/CadastroDeNinjas/Config/SwaggerConfig.java
```

Links:

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

O Swagger documenta:

- Endpoints de ninjas.
- Endpoints de missoes.
- DTOs de entrada e saida.
- Status HTTP esperados.
- Descricoes das rotas.
- Exemplos de campos.

Anotacoes usadas:

- `@Tag`
- `@Operation`
- `@ApiResponse`
- `@ApiResponses`
- `@Schema`
- `@Hidden`

## Scripts uteis

As vezes o IntelliJ para a tela, mas o processo Java continua vivo. Quando isso acontece, a proxima execucao pode falhar porque a porta `8080` ou o banco H2 continuam presos.

Para verificar:

```powershell
powershell -NoProfile -ExecutionPolicy Bypass -File .\scripts\verificar-app.ps1
```

Para parar a aplicacao presa:

```powershell
powershell -NoProfile -ExecutionPolicy Bypass -File .\scripts\parar-app.ps1
```

Esses scripts ajudam a evitar sair matando todo processo `java.exe`, porque o IntelliJ tambem usa Java internamente.

## Padroes usados no projeto

### Controllers REST

Controllers REST devem:

- Usar `@RestController`.
- Usar `ResponseEntity`.
- Receber e devolver DTOs.
- Nao devolver Model diretamente.
- Documentar endpoints com anotacoes OpenAPI.

### Services

Services devem:

- Conter a regra de negocio.
- Chamar repositories.
- Usar mappers para converter DTO e Model.
- Evitar expor entidade JPA para o controller.

### DTOs

DTOs devem:

- Representar o contrato da API.
- Ter campos claros para entrada e saida.
- Ser documentados com `@Schema`.

### Mappers

Mappers devem:

- Converter DTO em Model.
- Converter Model em DTO.
- Centralizar a logica de transformacao.

## Pontos conhecidos para melhorar

Algumas melhorias ainda estao planejadas:

- Refatorar exceptions.
- Criar testes unitarios.
- Criar testes de integracao.
- Melhorar imagens padrao da UI.
- Trocar PUT por PATCH para atualizacoes parciais.
- Migrar de H2 para MySQL.
- Dockerizar a aplicacao.
- Implementar Spring Security.

## Ordem recomendada das proximas issues

### 1. #10 - Refatorar Exceptions

Esta deve ser a proxima prioridade.

Hoje a API ainda trata alguns erros de forma simples, por exemplo retornando `null`, montando `404` manualmente ou deixando algumas excecoes do repository vazarem. Refatorar exceptions melhora a base antes de adicionar testes, banco real, Docker ou seguranca.

O objetivo seria criar:

- Exceptions proprias, como `NinjaNotFoundException` e `MissaoNotFoundException`.
- Um handler global com `@RestControllerAdvice`.
- Respostas de erro padronizadas.
- Tratamento melhor para `404`, `400` e erros de regra de negocio.

Por que fazer agora:

- Facilita testar erro depois.
- Deixa a API mais profissional.
- Evita respostas diferentes para problemas parecidos.
- Prepara o projeto para crescer.

### 2. #11 - Criar Testes Unitarios - JUnit

Depois que os erros estiverem padronizados, fica mais facil testar service, mapper e regras.

Testes unitarios devem validar partes pequenas do sistema sem subir a aplicacao inteira.

Exemplos:

- `NinjaService`
- `MissoesService`
- `NinjaMapper`
- `MissoesMapper`
- Exceptions esperadas

### 3. #12 - Criar Testes de Integracao

Depois dos testes unitarios, vale testar o fluxo HTTP real.

A ideia e validar:

- Controller REST recebendo request.
- Service sendo chamado.
- Repository usando H2 de teste.
- Status HTTP corretos.
- JSON de resposta correto.
- Swagger e rotas convivendo sem conflito.

### 4. #16 - Migrar o Banco de Dados de H2 para MySQL

O H2 e bom para estudo e teste local, mas MySQL se aproxima mais de um ambiente real.

Essa issue deve vir depois de exceptions e testes, porque mudar banco pode gerar erros novos. Com testes prontos, fica mais facil confirmar que a migracao nao quebrou o comportamento da API.

### 5. #17 - Dockerizar a Aplicacao

Docker fica mais util depois que o banco real estiver definido.

O ideal seria criar:

- `Dockerfile` da aplicacao.
- `docker-compose.yml` com app e MySQL.
- Variaveis de ambiente para configurar banco, usuario e senha.
- Instrucao de subida local com `docker compose up`.

### 6. #18 - Implementar Spring Security

Spring Security deve ficar mais para frente porque mexe em quase tudo:

- Rotas REST.
- Swagger.
- Thymeleaf.
- Testes.
- Autenticacao.
- Autorizacao.

Por isso, e melhor implementar seguranca quando a API, erros, testes e banco estiverem mais maduros.

## Estrategia geral

A melhor ordem agora e fortalecer a base antes de adicionar infraestrutura pesada.

Ordem sugerida:

1. Refatorar exceptions.
2. Criar testes unitarios.
3. Criar testes de integracao.
4. Migrar para MySQL.
5. Dockerizar.
6. Implementar Spring Security.

Essa sequencia reduz retrabalho, porque cada etapa prepara o terreno para a proxima.

## Como pensar no projeto

Uma forma simples de entender:

- Controller e a porta de entrada.
- DTO e o formulario/contrato que chega pela porta.
- Service e onde a regra acontece.
- Mapper e o tradutor.
- Model e a forma como o banco entende os dados.
- Repository e quem conversa com o banco.
- Swagger e o manual interativo da API.
- Thymeleaf e a tela visual para usar parte do sistema no navegador.

Fluxo de criacao de ninja:

```text
JSON no Swagger/Postman
        |
        v
NinjaDTO no Controller
        |
        v
NinjaService
        |
        v
NinjaMapper transforma DTO em NinjaModel
        |
        v
NinjaRepository salva no banco
        |
        v
NinjaMapper transforma NinjaModel salvo em NinjaDTO
        |
        v
Controller devolve JSON com status 201
```

Esse e o raciocinio principal do projeto.
