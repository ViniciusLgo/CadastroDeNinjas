# Guia iniciante: DTO e Mapper no CadastroDeNinjas

Este guia explica DTO e Mapper usando o projeto `CadastroDeNinjas`.

A ideia aqui nao e decorar codigo. A ideia e entender o caminho dos dados.

## O problema que DTO resolve

No projeto existem classes `Model`, como:

- `NinjaModel`
- `MissoesModel`

Essas classes representam as tabelas do banco no Java. Elas usam anotacoes do JPA:

```java
@Entity
@Table
@Id
@ManyToOne
@OneToMany
```

Isso quer dizer que o `Model` e uma classe ligada ao banco.

Mas a API nao precisa sempre mostrar ou receber exatamente a entidade do banco. Para a API, criamos uma classe mais limpa, chamada DTO.

DTO significa `Data Transfer Object`.

Traduzindo para uma ideia simples:

```text
DTO = objeto para transportar dados na API
```

## Analogia com Naruto

Pense assim:

`NinjaModel` e o ninja completo dentro da vila. Ele tem historico, vinculos, regras internas e informacoes que pertencem ao sistema da vila.

`NinjaDTO` e a ficha que a vila entrega para alguem de fora consultar.

A ficha nao precisa mostrar tudo. Ela mostra so o que faz sentido para o pedido.

Exemplo:

```text
NinjaModel = ninja completo dentro do banco da vila
NinjaDTO = ficha limpa para a API devolver no JSON
```

## O que e Mapper

Mapper e o tradutor.

Ele transforma:

```text
DTO -> Model
Model -> DTO
```

No projeto:

```text
NinjaDTO -> NinjaMapper -> NinjaModel
NinjaModel -> NinjaMapper -> NinjaDTO
```

E tambem:

```text
MissoesDTO -> MissoesMapper -> MissoesModel
MissoesModel -> MissoesMapper -> MissoesDTO
```

## Fluxo geral da API

Quando chega uma requisicao pelo Postman, o caminho ideal fica assim:

```text
Postman
  -> Controller recebe DTO
  -> Service organiza a regra
  -> Mapper transforma DTO em Model
  -> Repository salva ou busca no banco
  -> Mapper transforma Model em DTO
  -> Controller devolve DTO
```

Cada camada tem uma funcao:

```text
Controller = recebe e devolve requisicao HTTP
Service = concentra a regra e organiza o fluxo
Repository = fala com o banco
Model = representa tabela/entidade do banco
DTO = representa entrada/saida da API
Mapper = traduz DTO e Model
```

## Por que nao usar Model direto no Controller

No comeco, usar `Model` direto funciona.

Mas com o tempo isso atrapalha porque:

- a API fica presa ao formato do banco;
- voce pode expor campos que nao queria;
- relacionamentos podem gerar JSON grande demais;
- pode acontecer loop entre `NinjaModel` e `MissoesModel`;
- mudar o banco pode quebrar o contrato da API;
- fica mais dificil controlar o que entra e o que sai.

Por isso o controller deve trabalhar com DTO.

## Exemplo: criar ninja

No controller:

```java
@PostMapping("/criar")
public NinjaDTO criar(@RequestBody NinjaDTO ninjaDTO) {
    return ninjaService.criarNinja(ninjaDTO);
}
```

Leia assim:

```text
Controller recebe um NinjaDTO do JSON.
Controller manda esse DTO para o service.
Controller devolve o DTO que o service retornar.
```

No service:

```java
public NinjaDTO criarNinja(NinjaDTO ninjaDTO) {
    NinjaModel ninja = ninjaMapper.map(ninjaDTO);
    NinjaModel ninjaSalvo = ninjaRepository.save(ninja);
    return ninjaMapper.map(ninjaSalvo);
}
```

Leia linha por linha:

```java
NinjaModel ninja = ninjaMapper.map(ninjaDTO);
```

O DTO veio da API. Mas o repository salva `NinjaModel`, nao `NinjaDTO`.

Entao o mapper traduz:

```text
NinjaDTO -> NinjaModel
```

Depois:

```java
NinjaModel ninjaSalvo = ninjaRepository.save(ninja);
```

O repository salva no banco e devolve o ninja salvo.

Depois:

```java
return ninjaMapper.map(ninjaSalvo);
```

Antes de devolver para a API, o service traduz de volta:

```text
NinjaModel -> NinjaDTO
```

## Exemplo: listar todos os ninjas

No service:

```java
public List<NinjaDTO> listarTodosNinjas() {
    return ninjaRepository.findAll()
            .stream()
            .map(ninjaMapper::map)
            .collect(Collectors.toList());
}
```

Esse metodo faz uma coisa muito importante:

```text
Busca uma lista de NinjaModel no banco
Transforma cada NinjaModel em NinjaDTO
Devolve uma lista de NinjaDTO
```

O repository sempre busca entidade:

```java
List<NinjaModel>
```

Mas a API deve devolver DTO:

```java
List<NinjaDTO>
```

Entao o service faz a ponte:

```text
List<NinjaModel> -> Mapper -> List<NinjaDTO>
```

## Entendendo stream, map e collect

Essa parte costuma confundir:

```java
return ninjaRepository.findAll()
        .stream()
        .map(ninjaMapper::map)
        .collect(Collectors.toList());
```

Vamos quebrar.

### findAll

```java
ninjaRepository.findAll()
```

Busca todos os ninjas no banco.

Resultado:

```text
Lista de NinjaModel
```

### stream

```java
.stream()
```

Transforma a lista em uma fila de trabalho.

Analogia Naruto:

```text
Naruto -> Sasuke -> Sakura -> Kakashi
```

Cada ninja passa um por vez.

### map

```java
.map(ninjaMapper::map)
```

Para cada ninja da fila, chama o mapper.

Essa escrita:

```java
.map(ninjaMapper::map)
```

e igual a esta ideia:

```java
.map(ninja -> ninjaMapper.map(ninja))
```

Leia assim:

```text
Para cada NinjaModel, transforme em NinjaDTO.
```

### collect

```java
.collect(Collectors.toList())
```

Depois que cada ninja virou DTO, junta tudo em uma lista de novo.

Resultado final:

```java
List<NinjaDTO>
```

## Mesma logica sem stream

Se o `stream` parecer dificil, pense nesta versao manual:

```java
public List<NinjaDTO> listarTodosNinjas() {
    List<NinjaModel> ninjas = ninjaRepository.findAll();
    List<NinjaDTO> ninjasDTO = new ArrayList<>();

    for (NinjaModel ninja : ninjas) {
        NinjaDTO ninjaDTO = ninjaMapper.map(ninja);
        ninjasDTO.add(ninjaDTO);
    }

    return ninjasDTO;
}
```

Ela faz a mesma coisa:

1. Busca todos os `NinjaModel`.
2. Cria uma lista vazia de `NinjaDTO`.
3. Passa por cada ninja.
4. Converte com o mapper.
5. Adiciona na lista nova.
6. Retorna a lista de DTO.

O `stream` so encurta esse processo.

## Por que o mapper tem @Component

O service recebe o mapper pelo construtor:

```java
public NinjaService(NinjaRepository ninjaRepository, NinjaMapper ninjaMapper) {
    this.ninjaRepository = ninjaRepository;
    this.ninjaMapper = ninjaMapper;
}
```

Quando o Spring cria o `NinjaService`, ele precisa entregar um `NinjaMapper`.

Para o Spring saber criar `NinjaMapper`, a classe precisa estar marcada:

```java
@Component
public class NinjaMapper {
}
```

`@Component` significa:

```text
Spring, gerencie essa classe. Crie um objeto dela para eu poder injetar em outros lugares.
```

Sem `@Component`, aparece erro parecido com:

```text
Could not autowire. No beans of 'NinjaMapper' type found.
```

## Como saber se esta certo

Quando voce revisar DTO e Mapper, use esta lista:

- O controller recebe DTO no `@RequestBody`?
- O controller devolve DTO?
- O service usa mapper para converter?
- O repository continua trabalhando com Model?
- O mapper tem conversao de DTO para Model?
- O mapper tem conversao de Model para DTO?
- O mapper tem `@Component` se for injetado no service?

Se a resposta for sim, o desenho esta bem encaminhado.

## Macete final

```text
DTO = ficha publica da API
Model = entidade real do banco
Mapper = tradutor entre ficha e entidade
Service = lugar onde a traducao e a regra se encontram
Controller = porta de entrada e saida da API
Repository = conversa com o banco
```

No mundo de Naruto:

```text
Model = ninja completo registrado na vila
DTO = ficha que a vila entrega para consulta
Mapper = ninja responsavel por copiar os dados certos para a ficha
Service = escritorio que organiza o processo
Controller = balcao onde o pedido chega
Repository = arquivo secreto da vila onde os registros ficam guardados
```
