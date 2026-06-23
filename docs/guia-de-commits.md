# Guia de Commits

Este projeto usa mensagens de commit no formato:

```text
tipo: descricao curta da mudanca
```

Exemplo:

```text
feat: cria entidade Ninja
```

## Tipos

| Tipo | Quando usar |
| --- | --- |
| `feat` | Commits que adicionam ou removem uma nova funcionalidade. |
| `fix` | Commits que corrigem um bug. |
| `refactor` | Commits que reescrevem ou reorganizam o codigo sem mudar o comportamento da API. |
| `perf` | Refatoracoes especiais que melhoram performance. |
| `style` | Commits que nao mudam o significado do codigo, como espacos, formatacao e ponto e virgula. |
| `test` | Commits que adicionam testes ausentes ou corrigem testes existentes. |
| `docs` | Commits que alteram apenas documentacao. |
| `build` | Commits que afetam ferramentas de build, CI, dependencias ou versao do projeto. |
| `ops` | Commits que afetam infraestrutura, deploy, backup ou recuperacao. |
| `chore` | Commits diversos, como alterar `.gitignore` ou tarefas de manutencao. |

## Escopos

O escopo adiciona contexto opcional sobre a area alterada:

```text
tipo(escopo): descricao curta da mudanca
```

Exemplos:

```text
feat(ninjas): cria controller de cadastro
build(maven): adiciona dependencia do Lombok
docs(commits): documenta padrao de mensagens
```

O escopo e opcional. Os escopos permitidos dependem do projeto.

Nao use identificadores de issue como escopo:

```text
# Evite
feat(#3): cria repository de ninjas

# Prefira
feat(ninjas): cria repository de ninjas
```

Se o commit fecha uma issue, coloque isso no corpo da mensagem:

```text
feat(ninjas): cria repository de ninjas

Closes #3
```

## Lombok em Entidades JPA

Em models JPA com relacionamentos, prefira `@Getter` e `@Setter` em vez de `@Data`.

`@Data` tambem gera `toString`, `equals` e `hashCode`. Em relacoes bidirecionais, como `NinjaModel` apontando para `MissoesModel` e `MissoesModel` contendo uma lista de ninjas, esses metodos podem percorrer os dois lados da relacao e causar recursao ou carregamentos inesperados do Hibernate.

Por isso, nos models deste projeto, o mais seguro e deixar explicito:

```java
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NinjaModel {
}
```
