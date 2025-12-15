# 🚀 E-commerce API

## Sobre o Projeto
> Este é o microsserviço principal responsável pela lógica de e-commerce, 
> incluindo catálogo de produtos, gerenciamento de pedidos e integração 
> assíncrona com serviços de pagamento e estoque via Apache Kafka.

## Funcionalidades

Liste os principais recursos que seu projeto oferece. Use caixas de seleção (`- [ ]` ou `- [x]`) se for uma lista de tarefas ou um *roadmap*.

* Funcionalidade 1: Exemplo - Autenticação de usuários via OAuth2.
* Funcionalidade 2: Exemplo - Geração de relatórios em PDF.
* Funcionalidade 3: Exemplo - Integração com API externa X.

## 🛠️ Tecnologias Utilizadas

| Categoria          | Tecnologia  | Versão |
|:-------------------|:------------|:-------|
| **Linguagem**      | Java        | 21.0.9 |
| **Framework**      | Spring Boot | 3.2.0  |
| **Banco de Dados** | H2 Database | -      |
| **Mensageria**     | Kafka       | -      |

## Pré-requisitos

* JDK 21
* Apache Maven (Ou use o wrapper mvnw).
* Docker ou Instalação Local do Kafka/Zookeeper.

## Como Iniciar o Projeto

Instruções passo a passo para configurar e rodar o projeto localmente.

### 1. Clonar o Repositório

```bash
git clone [https://github.com/seu-usuario/nome-do-projeto.git](https://github.com/seu-usuario/nome-do-projeto.git)
cd nome-do-projeto
```

### 2. Iniciar Kafka

Este projeto depende de um Broker Kafka rodando em *localhost:9092*.

* Se estiver usando Docker: Execute o comando que inicia o seu stack de mensageria.
* Se estiver usando instalação local: Inicie o Zookeeper e o Kafka Broker em terminais separados, conforme as instruções de instalação do Kafka.

### 3. Compilar e executar

```bash
mvn clean install -U
mvn spring-boot:run
```

## Endpoints

| Função   | Método | Caminho   | Descrição |
|:---------|:-------|:----------|-----------|
| Produtos | Get    | /products | Retorna o catálogo de produtos.      |
| Pedidos  | Post   | /payments | Processa um novo pedido e envia evento Kafka.      |

## Testes

Instruções passo a passo para configurar e rodar o projeto localmente.

### 1. Testes unitários e de integração

Execute todos os testes com:

```bash
mvn test
```