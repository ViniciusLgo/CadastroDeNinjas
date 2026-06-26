# Guia mastigado: como implementar DTO e Mapper

Este guia e focado em implementacao.

O outro guia explica o que e DTO e Mapper. Aqui a ideia e responder:

```text
Como eu crio as classes?
Quais imports eu uso?
Quais atributos entram no DTO?
Como eu escrevo o mapper?
Como eu uso o mapper no service?
Como o controller chama tudo?
O que cada parametro significa?
```

Vou usar o mundo de Naruto como analogia, mas sempre conectando com o codigo real.

## Visao geral antes de codar

Antes de criar DTO e Mapper, pense no caminho:

```text
Controller recebe DTO
Service recebe DTO
Mapper transforma DTO em Model
Repository salva/busca Model
Mapper transforma Model em DTO
Service devolve DTO
Controller devolve DTO no JSON
```

No projeto:

```text
NinjaDTO <-> NinjaMapper <-> NinjaModel
MissoesDTO <-> MissoesMapper <-> MissoesModel
```

## Passo 1: criar o DTO

O DTO e a classe que representa o JSON que entra ou sai da API.

Exemplo real:

```java
package dev.java10x.CadastroDeNinjas.Ninjas.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NinjaDTO {

    private Long id;
    private String nome;
    private String email;
    private int idade;
    private String imgUrl;
    private String rank;
    private Long missoesId;
    private String missoesNome;
}
```

Agora vamos mastigar isso.

### package

```java
package dev.java10x.CadastroDeNinjas.Ninjas.DTO;
```

Isso diz onde a classe mora.

Como e DTO de ninja, ela fica dentro de:

```text
Ninjas/DTO
```

### imports do Lombok

```java
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
```

Esses imports deixam o Lombok gerar codigo repetitivo.

### @Data

```java
@Data
```

Gera automaticamente:

```text
getters
setters
toString
equals
hashCode
```

Para estudo, pense assim:

```text
@Data evita escrever getNome(), setNome(), getEmail(), setEmail() na mao.
```

### @AllArgsConstructor

```java
@AllArgsConstructor
```

Cria um construtor com todos os campos.

Como se fosse:

```java
public NinjaDTO(Long id, String nome, String email, int idade, String imgUrl, String rank, Long missoesId, String missoesNome) {
    this.id = id;
    this.nome = nome;
    this.email = email;
    this.idade = idade;
    this.imgUrl = imgUrl;
    this.rank = rank;
    this.missoesId = missoesId;
    this.missoesNome = missoesNome;
}
```

### @NoArgsConstructor

```java
@NoArgsConstructor
```

Cria um construtor vazio.

Isso ajuda o Spring/Jackson a montar o objeto a partir do JSON.

Quando voce manda no Postman:

```json
{
  "nome": "Naruto",
  "email": "naruto@konoha.com",
  "idade": 17,
  "rank": "Genin",
  "missoesId": 1
}
```

O Spring cria um `NinjaDTO` vazio e vai preenchendo os campos.

### atributos do DTO

```java
private Long id;
private String nome;
private String email;
private int idade;
private String imgUrl;
private String rank;
private Long missoesId;
private String missoesNome;
```

Esses campos sao os dados que a API pode receber ou devolver.

Repare:

```java
private Long missoesId;
private String missoesNome;
```

O DTO nao carrega o `MissoesModel` inteiro.

Ele carrega apenas dados simples da missao:

```text
id da missao
nome da missao
```

Isso evita expor entidade JPA diretamente no JSON.

## Passo 2: criar o Mapper

O mapper traduz DTO e Model.

Exemplo real:

```java
@Component
public class NinjaMapper {

    public NinjaModel map(NinjaDTO ninjaDTO) {
        // DTO -> Model
    }

    public NinjaDTO map(NinjaModel ninjaModel) {
        // Model -> DTO
    }
}
```

Aqui existem dois metodos com o mesmo nome: `map`.

Isso se chama sobrecarga de metodo.

O Java sabe qual usar pelo tipo do parametro:

```java
map(NinjaDTO ninjaDTO)
```

recebe DTO e devolve Model.

```java
map(NinjaModel ninjaModel)
```

recebe Model e devolve DTO.

## Passo 3: entender @Component no mapper

```java
@Component
public class NinjaMapper {
}
```

`@Component` diz para o Spring:

```text
Crie um objeto dessa classe e deixe disponivel para injetar em outros lugares.
```

Voce precisa disso porque o service recebe o mapper no construtor:

```java
public NinjaService(NinjaRepository ninjaRepository, NinjaMapper ninjaMapper) {
    this.ninjaRepository = ninjaRepository;
    this.ninjaMapper = ninjaMapper;
}
```

Sem `@Component`, o Spring nao sabe criar `NinjaMapper`.

Ai aparece:

```text
Could not autowire. No beans of 'NinjaMapper' type found.
```

## Passo 4: implementar DTO para Model

Metodo:

```java
public NinjaModel map(NinjaDTO ninjaDTO) {
    if (ninjaDTO == null) {
        return null;
    }

    NinjaModel ninjaModel = new NinjaModel();
    ninjaModel.setId(ninjaDTO.getId());
    ninjaModel.setNome(ninjaDTO.getNome());
    ninjaModel.setEmail(ninjaDTO.getEmail());
    ninjaModel.setIdade(ninjaDTO.getIdade());
    ninjaModel.setImgUrl(ninjaDTO.getImgUrl());
    ninjaModel.setRank(ninjaDTO.getRank());

    if (ninjaDTO.getMissoesId() != null) {
        MissoesModel missoesModel = new MissoesModel();
        missoesModel.setId(ninjaDTO.getMissoesId());
        ninjaModel.setMissoes(missoesModel);
    }

    return ninjaModel;
}
```

Vamos por partes.

### Assinatura

```java
public NinjaModel map(NinjaDTO ninjaDTO)
```

Leia assim:

```text
Metodo publico chamado map.
Recebe um NinjaDTO.
Devolve um NinjaModel.
```

No mundo de Naruto:

```text
Recebe uma ficha publica do ninja.
Monta o registro interno da vila.
```

### Protecao contra null

```java
if (ninjaDTO == null) {
    return null;
}
```

Se alguem passar nada, o mapper devolve nada.

Isso evita erro de tentar acessar:

```java
ninjaDTO.getNome()
```

quando `ninjaDTO` estiver `null`.

### Criar o Model

```java
NinjaModel ninjaModel = new NinjaModel();
```

Aqui voce cria a entidade que o repository entende.

Lembre:

```text
Repository salva Model, nao DTO.
```

### Copiar campo por campo

```java
ninjaModel.setNome(ninjaDTO.getNome());
```

Leia assim:

```text
Pegue o nome que veio no DTO e coloque no Model.
```

O mesmo acontece com:

```java
ninjaModel.setEmail(ninjaDTO.getEmail());
ninjaModel.setIdade(ninjaDTO.getIdade());
ninjaModel.setImgUrl(ninjaDTO.getImgUrl());
ninjaModel.setRank(ninjaDTO.getRank());
```

Mapper e basicamente isso:

```text
copiar dados do objeto A para o objeto B
```

### Tratar missao pelo id

```java
if (ninjaDTO.getMissoesId() != null) {
    MissoesModel missoesModel = new MissoesModel();
    missoesModel.setId(ninjaDTO.getMissoesId());
    ninjaModel.setMissoes(missoesModel);
}
```

Essa parte e importante.

No DTO voce manda:

```json
{
  "missoesId": 1
}
```

Mas no `NinjaModel`, o campo e:

```java
private MissoesModel missoes;
```

Ou seja:

```text
DTO tem o id da missao.
Model precisa de um objeto MissoesModel.
```

Por isso criamos uma missao com apenas id:

```java
MissoesModel missoesModel = new MissoesModel();
missoesModel.setId(ninjaDTO.getMissoesId());
```

Depois ligamos no ninja:

```java
ninjaModel.setMissoes(missoesModel);
```

No mundo de Naruto:

```text
Na ficha do ninja veio: missao 1.
No registro interno da vila, o ninja precisa apontar para a MissaoModel de id 1.
```

## Passo 5: implementar Model para DTO

Metodo:

```java
public NinjaDTO map(NinjaModel ninjaModel) {
    if (ninjaModel == null) {
        return null;
    }

    NinjaDTO ninjaDTO = new NinjaDTO();
    ninjaDTO.setId(ninjaModel.getId());
    ninjaDTO.setNome(ninjaModel.getNome());
    ninjaDTO.setEmail(ninjaModel.getEmail());
    ninjaDTO.setIdade(ninjaModel.getIdade());
    ninjaDTO.setImgUrl(ninjaModel.getImgUrl());
    ninjaDTO.setRank(ninjaModel.getRank());

    if (ninjaModel.getMissoes() != null) {
        ninjaDTO.setMissoesId(ninjaModel.getMissoes().getId());
        ninjaDTO.setMissoesNome(ninjaModel.getMissoes().getNome());
    }

    return ninjaDTO;
}
```

Leia a assinatura:

```java
public NinjaDTO map(NinjaModel ninjaModel)
```

Traducao:

```text
Recebe entidade do banco.
Devolve ficha limpa da API.
```

Aqui o caminho e inverso:

```text
NinjaModel -> NinjaDTO
```

Isso acontece quando voce buscou ou salvou algo no banco e quer devolver JSON.

## Passo 6: injetar mapper no service

No service:

```java
private final NinjaRepository ninjaRepository;
private final NinjaMapper ninjaMapper;
```

Aqui voce guarda as dependencias.

Dependencia e aquilo que a classe precisa para trabalhar.

`NinjaService` precisa de:

```text
NinjaRepository para acessar o banco
NinjaMapper para converter DTO e Model
```

Construtor:

```java
public NinjaService(NinjaRepository ninjaRepository, NinjaMapper ninjaMapper) {
    this.ninjaRepository = ninjaRepository;
    this.ninjaMapper = ninjaMapper;
}
```

Leia assim:

```text
Spring, quando criar NinjaService, me entregue um NinjaRepository e um NinjaMapper.
```

## Passo 7: usar mapper no criar

```java
public NinjaDTO criarNinja(NinjaDTO ninjaDTO) {
    NinjaModel ninja = ninjaMapper.map(ninjaDTO);
    NinjaModel ninjaSalvo = ninjaRepository.save(ninja);
    return ninjaMapper.map(ninjaSalvo);
}
```

### Parametro

```java
NinjaDTO ninjaDTO
```

Esse e o objeto que veio do controller.

Ele nasceu do JSON do Postman.

### Primeira linha

```java
NinjaModel ninja = ninjaMapper.map(ninjaDTO);
```

Voce transforma DTO em Model porque o repository salva Model.

### Segunda linha

```java
NinjaModel ninjaSalvo = ninjaRepository.save(ninja);
```

Salva no banco.

### Terceira linha

```java
return ninjaMapper.map(ninjaSalvo);
```

Transforma o Model salvo em DTO e devolve.

Resumo:

```text
DTO que veio da API
-> vira Model
-> salva no banco
-> Model salvo vira DTO
-> volta para a API
```

## Passo 8: usar mapper no listar todos

```java
public List<NinjaDTO> listarTodosNinjas() {
    return ninjaRepository.findAll()
            .stream()
            .map(ninjaMapper::map)
            .collect(Collectors.toList());
}
```

Esse metodo retorna:

```java
List<NinjaDTO>
```

Mas o repository busca:

```java
List<NinjaModel>
```

Por isso precisa converter cada item.

### Versao com for, mais iniciante

```java
public List<NinjaDTO> listarTodosNinjas() {
    List<NinjaModel> ninjasModel = ninjaRepository.findAll();
    List<NinjaDTO> ninjasDTO = new ArrayList<>();

    for (NinjaModel ninjaModel : ninjasModel) {
        NinjaDTO ninjaDTO = ninjaMapper.map(ninjaModel);
        ninjasDTO.add(ninjaDTO);
    }

    return ninjasDTO;
}
```

Leia assim:

```text
Busque todos os registros internos da vila.
Crie uma lista vazia de fichas publicas.
Para cada registro interno, crie uma ficha publica.
Coloque a ficha na lista.
Devolva a lista de fichas.
```

### Versao com stream

```java
return ninjaRepository.findAll()
        .stream()
        .map(ninjaMapper::map)
        .collect(Collectors.toList());
```

Faz a mesma coisa do `for`, so que mais curto.

## Passo 9: usar DTO no controller

Controller nao precisa saber de `NinjaModel`.

Ele deve falar com a API usando DTO.

Exemplo:

```java
@PostMapping("/criar")
public NinjaDTO criar(@RequestBody NinjaDTO ninjaDTO) {
    return ninjaService.criarNinja(ninjaDTO);
}
```

### @RequestBody

```java
@RequestBody NinjaDTO ninjaDTO
```

Significa:

```text
Pegue o JSON do corpo da requisicao e transforme em NinjaDTO.
```

Exemplo de JSON:

```json
{
  "nome": "Naruto",
  "email": "naruto@konoha.com",
  "idade": 17,
  "imgUrl": "naruto.png",
  "rank": "Genin",
  "missoesId": 1
}
```

O Spring transforma isso em:

```java
NinjaDTO ninjaDTO
```

Depois o controller manda para o service:

```java
ninjaService.criarNinja(ninjaDTO)
```

## Ordem para implementar sem se perder

Quando voce for criar DTO/Mapper em outra entidade, siga esta ordem:

1. Crie o DTO com os campos que a API deve receber/devolver.
2. Crie o Mapper com `@Component`.
3. Crie metodo `map(DTO dto)` para transformar DTO em Model.
4. Crie metodo `map(Model model)` para transformar Model em DTO.
5. Injete o mapper no service pelo construtor.
6. No criar/alterar, converta DTO para Model antes de salvar.
7. Depois de salvar, converta Model para DTO antes de retornar.
8. No listar, busque `List<Model>` e converta para `List<DTO>`.
9. No controller, troque entrada e retorno para DTO.
10. Compile e teste no Postman.

## Checklist rapido

Use este checklist:

```text
DTO criado?
Mapper criado?
Mapper tem @Component?
Mapper converte DTO -> Model?
Mapper converte Model -> DTO?
Service injeta o mapper?
Service recebe/devolve DTO?
Repository continua usando Model?
Controller recebe/devolve DTO?
Projeto compila?
```

## Erros comuns

### Erro: No beans of NinjaMapper found

Causa:

```text
O service pediu NinjaMapper no construtor, mas NinjaMapper nao tem @Component.
```

Solucao:

```java
@Component
public class NinjaMapper {
}
```

### Erro: tentei salvar DTO no repository

Errado:

```java
ninjaRepository.save(ninjaDTO);
```

Repository de ninja salva `NinjaModel`, nao `NinjaDTO`.

Certo:

```java
NinjaModel ninja = ninjaMapper.map(ninjaDTO);
ninjaRepository.save(ninja);
```

### Erro: controller ainda retorna Model

Evite:

```java
public NinjaModel criar(...)
```

Prefira:

```java
public NinjaDTO criar(...)
```

## Frase para gravar

```text
DTO entra e sai da API.
Model entra e sai do banco.
Mapper traduz entre os dois.
Service decide quando traduzir.
Controller so conversa em DTO.
```
