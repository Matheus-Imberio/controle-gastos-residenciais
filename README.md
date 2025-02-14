# Sistema de Controle de Gastos Residenciais

Este é um sistema simples para controle de gastos residenciais, desenvolvido em **Java Spring Boot**. Ele permite o cadastro de pessoas e transações, além de fornecer consultas de totais de receitas, despesas e saldos.

---

## 🚀 **Funcionalidades**

### 1. Cadastro de Pessoas
- Cadastre pessoas com nome e idade.
- Listar, editar e excluir pessoas.
- Ao excluir uma pessoa, todas as suas transações são automaticamente removidas.

### 2. Cadastro de Transações
- Cadastre transações com descrição, valor, tipo (receita/despesa) e associação a uma pessoa.
- Listar transações.
- Menores de idade só podem ter transações do tipo "despesa".

### 3. Consulta de Totais
- Liste todas as pessoas com seus totais de receitas, despesas e saldo.
- Exiba o total geral de receitas, despesas e saldo líquido de todas as pessoas.

---

## 🛠️ **Tecnologias Utilizadas**

- **Back-end**:
  - Java 17
  - Spring Boot 3.x
  - Spring Data JPA
  - H2 Database (banco de dados em memória)

- **Ferramentas**:
  - Maven (gerenciamento de dependências)
  - Postman/Insomnia (testes de API)

---

## ⚙️ **Configuração**

### Pré-requisitos

- Java 17 instalado.
- Maven instalado.
- Ferramenta para testar APIs (Postman, Insomnia, etc.).
- 
## Passos para Executar o Projeto

### 1. Clone o repositório:
   ```bash
   git clone https://github.com/Matheus-Imberio/controle-gastos-residenciais.git
   ```
  ### Navegue até a pasta do projeto:

```bash
cd controle-gastos-residenciais
```
### Compile e execute o projeto:

```bash
mvn spring-boot:run
```
### Acesse o console do H2 Database em:
```bash
http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:despesasdb

User Name: sa

Password: 123456
```
## 📡 Endpoints da API

### 🧑 Pessoas

### Listar todas as pessoas  
```http
GET /pessoas
```
Buscar pessoa por ID

```http
GET /pessoas/{id}
```
Cadastrar nova pessoa
```http
POST /pessoas
Body:
{
  "nome": "João Silva",
  "idade": 25
}
```
### Excluir pessoa por ID
```http
DELETE /pessoas/{id}
```
### 💰 Transações

### Listar todas as transações

```http
GET /transacoes
```
### Buscar transação por ID

```http
GET /transacoes/{id}
```
### Cadastrar nova transação
```http
POST /transacoes
Body:

{
  "descricao": "Salário",
  "valor": 5000.00,
  "tipo": "receita",
  "pessoaId": 1
}
```
### Listar transações de uma pessoa
```http
GET /pessoas/{id}/transacoes
```
### 📊 Totais
### Consultar totais por pessoa e totais gerais
```http
GET /totais
```
### 📋 Exemplos de Respostas
```http
{
    "totaisPorPessoa": [
        {
            "pessoaId": 1,
            "nome": "João Silva",
            "totalReceitas": 5000.00,
            "totalDespesas": 1200.00,
            "saldo": 3800.00
        }
    ],
    "totalGeralReceitas": 5000.00,
    "totalGeralDespesas": 1200.00,
    "saldoLiquidoGeral": 3800.00
}
```

### 📜 Listar Pessoas
```http
[
    {
        "id": 1,
        "nome": "João Silva",
        "idade": 25,
        "transacoes": [
            {
                "id": 1,
                "descricao": "Salário",
                "valor": 5000.00,
                "tipo": "receita"
            },
            {
                "id": 2,
                "descricao": "Aluguel",
                "valor": 1200.00,
                "tipo": "despesa"
            }
        ]
    }
]
```
