# DTO e Mapper no CadastroDeNinjas

Este arquivo fica junto do pacote DTO para servir como cola de estudo. A ideia e explicar com linguagem simples por que existem DTOs e mappers, como pensar neles e como isso se encaixa no fluxo Controller, Service, Repository e banco.

## O que e DTO

DTO significa Data Transfer Object. Em portugues simples: e um objeto feito para carregar dados de um lugar para outro.

No projeto, o DTO representa o formato de dados que entra ou sai da API. Ele nao precisa ser igual a entidade do banco.

Exemplo mental:

- `NinjaModel` e a entidade JPA, ligada ao banco.
- `NinjaDTO` e o objeto que a API pode receber ou devolver no JSON.

O DTO serve para nao expor a entidade inteira para quem chama a API. Isso ajuda porque a entidade pode ter relacionamentos, regras do JPA, campos internos e detalhes que nao precisam aparecer no Postman ou no front-end.

## O que e Mapper

Mapper e a classe que traduz um objeto para outro.

No projeto:

- `NinjaMapper` transforma `NinjaDTO` em `NinjaModel`.
- `NinjaMapper` tambem transforma `NinjaModel` em `NinjaDTO`.
- `MissoesMapper` faz a mesma ideia para missoes.

Pense no mapper como um tradutor:

```text
JSON da API -> DTO -> Mapper -> Model -> Repository -> Banco
Banco -> Repository -> Model -> Mapper -> DTO -> JSON da API
```

## Por que nao usar Model direto sempre

Usar `Model` direto funciona no comeco, mas mistura responsabilidades.

O `Model` existe para o banco. Ele tem anotacoes como `@Entity`, `@Table`, `@Id`, `@ManyToOne` e `@OneToMany`.

O `DTO` existe para a API. Ele representa o contrato de entrada e saida.

Quando o projeto cresce, separar os dois evita problemas como:

- devolver dados demais na resposta;
- receber campos que o usuario nao deveria controlar;
- causar loop em relacionamento entre ninja e missao;
- depender do formato do banco para desenhar o JSON da API;
- dificultar mudancas futuras.

## Como pensar na pratica

Use esta regra simples:

Se o dado vai para fora da aplicacao ou vem de fora da aplicacao, pense em DTO.

Se o dado vai ser salvo ou buscado no banco, pense em Model.

Se precisa passar de DTO para Model, ou de Model para DTO, use Mapper.

## O que o @Component faz no Mapper

`@Component` avisa ao Spring que aquela classe deve ser gerenciada por ele.

Quando uma classe vira componente, o Spring cria um objeto dela e consegue entregar esse objeto para outras classes pelo construtor.

Exemplo:

```java
public NinjaService(NinjaRepository ninjaRepository, NinjaMapper ninjaMapper) {
    this.ninjaRepository = ninjaRepository;
    this.ninjaMapper = ninjaMapper;
}
```

Nesse construtor, o Spring precisa encontrar dois objetos:

- um `NinjaRepository`;
- um `NinjaMapper`.

O repository ja vira bean por ser uma interface do Spring Data. O mapper so vira bean quando recebe `@Component`.

Sem `@Component`, o Spring nao sabe criar o `NinjaMapper` sozinho e aparece o erro:

```text
Could not autowire. No beans of 'NinjaMapper' type found.
```

## Macete para lembrar

Pense assim:

```text
DTO = mochila de dados da API
Model = tabela/entidade do banco no Java
Mapper = tradutor entre DTO e Model
@Component = etiqueta que diz ao Spring: pode criar e injetar esta classe
```

## Fluxo do cadastro de ninja

Quando o Postman chama `POST /ninjas/criar`:

1. O controller recebe um `NinjaDTO`.
2. O service chama o `NinjaMapper`.
3. O mapper transforma `NinjaDTO` em `NinjaModel`.
4. O repository salva o `NinjaModel`.
5. O mapper transforma o `NinjaModel` salvo em `NinjaDTO`.
6. O controller devolve o `NinjaDTO` na resposta.

Esse fluxo deixa cada parte com uma funcao clara:

- Controller recebe a requisicao.
- Service organiza a regra.
- Mapper converte os objetos.
- Repository fala com o banco.
- Model representa a tabela.
- DTO representa o JSON da API.
