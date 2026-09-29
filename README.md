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
