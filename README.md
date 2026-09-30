# Rent Tools API

API REST para um sistema de aluguel de ferramentas, desenvolvida com Java e Spring Boot.

O projeto foi feito para praticar desenvolvimento backend e trabalhar com cadastro de clientes, ferramentas e aluguéis.

## Tecnologias

* Java
* Spring Boot
* Spring Data JPA
* PostgreSQL
* Flyway
* Maven
* JUnit 5
* Mockito

## Funcionalidades

* Cadastro e atualização de clientes
* Cadastro e atualização de ferramentas
* Consulta de clientes e ferramentas
* Controle de disponibilidade das ferramentas
* Controle de manutenção
* Limite de aluguéis por cliente
* Criação e finalização de aluguéis
* Validações e tratamento de exceções
* Testes unitários

## Endpoints

### Clientes

#### `POST /client`

Cadastra um cliente.

```json
{
    "name": "string",
    "cpf": "00000000000",
    "category": "REGULAR"
}
```

#### `GET /client/{id}`

Busca um cliente pelo ID.

#### `PUT /client/{id}`

Atualiza um cliente.

```json
{
    "name": "string",
    "category": "REGULAR"
}
```

### Ferramentas

#### `POST /tool`

Cadastra uma ferramenta.

```json
{
    "name": "string",
    "price": 0.0,
    "category": "ELETRICA",
    "description": "string",
    "minimumRentalDays": 0,
    "condition": "NEW"
}
```

#### `GET /tool`

Lista todas as ferramentas.

#### `GET /tool/{id}`

Busca uma ferramenta pelo ID.

#### `GET /tool?name={name}`

Busca ferramentas pelo nome.

#### `PUT /tool/{id}`

Atualiza uma ferramenta.

```json
{
    "price": 0.0,
    "minimumRentalDays": 0,
    "condition": "GOOD"
}
```

#### `PATCH /tool/disable/{id}`

Deixa uma ferramenta indisponível.

#### `PATCH /tool/enable/{id}`

Deixa uma ferramenta disponível.

#### `PATCH /tool/maintenance/{id}`

Coloca uma ferramenta em manutenção.

### Aluguéis

#### `POST /rent`

Cria um aluguel.

```json
{
    "days": 0,
    "clientId": 0,
    "toolId": 0
}
```

#### `GET /rent`

Lista os aluguéis ativos.

#### `PATCH /rent/{id}/finish`

Finaliza um aluguel.

## Como executar

Clone o projeto:

```bash
git clone https://github.com/pietro2020/RentTool-Spring-API
```

Entre na pasta do projeto:

```bash
cd rent-tools-api
```

Configure o PostgreSQL e coloque os dados do banco no arquivo `application.properties`.

Depois, execute o projeto:

```bash
./mvnw spring-boot:run
```

No Windows:

```bash
./mvnw.cmd spring-boot:run
```

Para executar os testes:

```bash
./mvnw test
```

No Windows:

```bash
./mvnw.cmd test
```

## Estrutura

* `controller` — endpoints da API
* `dto` — objetos de entrada e saída
* `model` — entidades do sistema
* `repository` — acesso ao banco
* `service` — regras do sistema

## Sobre o projeto

Projeto desenvolvido para estudos de Java, Spring Boot, APIs REST, PostgreSQL e testes unitários.
