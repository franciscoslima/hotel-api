<div align="center">

# Hotel API 🏨

API REST em Spring Boot para gerenciamento de propriedades, quartos e reservas de hotel, incluindo verificação de disponibilidade por período.

![Java](https://img.shields.io/badge/Java-17+-red?logo=openjdk) ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.x-brightgreen?logo=springboot) ![Build](https://img.shields.io/badge/Build-Maven-blue?logo=apachemaven) ![Database](https://img.shields.io/badge/DB-PostgreSQL-316192?logo=postgresql)

</div>

## 📌 Principais Funcionalidades

* Cadastro e consulta de propriedades e quartos
* Criação e gerenciamento de reservas (check-in / check-out)
* Verificação de disponibilidade de quartos por intervalo de datas
* Paginação de resultados
* Documentação automática via Swagger / OpenAPI
* Execução local ou em containers (Docker / Docker Compose)

## 🏗️ Stack Tecnológica

| Camada | Tecnologias |
|--------|-------------|
| Linguagem | Java 17 |
| Framework | Spring Boot 3.5.6 (Web, Validation, Data JPA) |
| Persistência | JPA / Hibernate |
| Banco | PostgreSQL |
| Documentação | springdoc-openapi (Swagger UI) |
| Build | Maven |
| Conveniências | Lombok, DevTools |

## 🚀 Como Executar

### 1. Pré-requisitos
* Java 17+
* Maven 3.8+
* Docker (opcional, para execução containerizada)

### 2. Clonar o repositório
```bash
git clone https://github.com/franciscoslima/hotel-api/
cd hotel-api/hotelapi
```

### 3. Executar com Docker Compose (recomendado)
Dentro da pasta `hotelapi`:
```bash
# (Primeira vez ou após mudanças no código / dependências)
docker compose up --build

# Ou em background
docker compose up -d --build
```

Logs em tempo real (API):
```bash
docker compose logs -f app
```
Logs do banco:
```bash
docker compose logs -f postgres
```
Acessar psql dentro do container:
```bash
docker compose exec postgres psql -U admin -d hotel
```
Parar (mantendo volume de dados):
```bash
docker compose down
```
Parar e remover volume (reset TOTAL de dados):
```bash
docker compose down -v
```
Rebuild forçando cache limpo da imagem da API:
```bash
docker compose build --no-cache app
```

#### Otimização de Build
O Dockerfile usa `mvn dependency:go-offline` para cache de dependências. Ao mudar apenas código em `src/`, camadas de dependência permanecem cacheadas acelerando rebuild.


### 4. Build do JAR
```bash
mvn clean package -DskipTests
java -jar target/hotelapi-0.0.1-SNAPSHOT.jar
```

## 📚 Documentação / Swagger
Após subir a aplicação:
* OpenAPI JSON: `GET /api-docs`
* Swagger UI: `http://localhost:8080/swagger-ui.html`

## 🗄️ Modelo de Dados (Resumo)

Entidades principais (simplificado):
* `Propriedade`
* `Quarto` (inclui status / disponibilidade)
* `Reserva` (datas de check-in / check-out)
* `Endereco`

Diagrama (referência):
<img width="643" height="409" alt="ERD" src="https://github.com/user-attachments/assets/026e27d7-2d52-47da-8d8f-53f3165f7f9c" />

## 📚 Documentação da API (Swagger UI)

Após subir a aplicação, a documentação interativa e a especificação OpenAPI estarão acessíveis em:

* **Swagger UI**: `http://localhost:8080/swagger-ui.html`
* **OpenAPI JSON**: `GET /api-docs`

-----

## 🔌 Endpoints e Exemplos

### **Propriedades (`/api/propriedades`)**

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/api/propriedades` | **Cria** nova propriedade. |
| GET | `/api/propriedades` | **Lista** todas as propriedades (suporte a paginação). |
| GET | `/api/propriedades/{id}` | **Busca** propriedade por ID. |
| PUT | `/api/propriedades/{id}` | **Atualiza** propriedade. |
| DELETE | `/api/propriedades/{id}` | **Deleta** propriedade. |

**Exemplo de Payload (POST):**

```json
{
  "nome": "Hotel Copacabana",
  "descricao": "Hotel 5 estrelas à beira-mar",
  "tipo": "HOTEL",
  "endereco": {
    "rua": "Avenida Atlântica",
    "bairro": "Copacabana",
    "cidade": "Rio de Janeiro",
    "estado": "RJ"
  }
}
```

### **Quartos (`/api/quartos`)**

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/api/quartos` | **Cria** novo quarto. |
| GET | `/api/quartos` | **Lista** todos os quartos (suporte a paginação). |
| GET | `/api/quartos/{id}` | **Busca** quarto por ID. |
| PUT | `/api/quartos/{id}` | **Atualiza** quarto. |
| DELETE | `/api/quartos/{id}` | **Deleta** quarto. |
| GET | `/api/quartos/disponiveis?checkIn=...&checkOut=...` | **Lista** quartos disponíveis no período. |
| GET | `/api/quartos/{id}/disponivel?checkIn=...&checkOut=...` | **Verifica** a disponibilidade de um quarto específico. |

**Exemplo de Payload (POST):**

```json
{
  "numeracao": 101,
  "descricao": "Quarto com vista para o mar",
  "valorDiaria": 500.0,
  "propriedadeId": 1,
  "status": "DISPONIVEL"
}
```

### **Reservas (`/api/reservas`)**

| Método | Endpoint | Descrição |
|---|---|---|
| POST | `/api/reservas` | **Cria** nova reserva. |
| GET | `/api/reservas` | **Lista** todas as reservas (suporte a paginação). |
| GET | `/api/reservas/{id}` | **Busca** reserva por ID. |
| PUT | `/api/reservas/{id}` | **Atualiza** reserva. |
| DELETE | `/api/reservas/{id}` | **Cancela** reserva. |

**Exemplo de Payload (POST):**

```json
{
  "userId": 1,
  "quartoId": 1,
  "checkIn": "2025-10-10",
  "checkOut": "2025-10-15"
}
```

-----

## 🗄️ Organização e Arquitetura

O projeto segue a arquitetura em camadas padrão do Spring Boot, facilitando a separação de responsabilidades e a manutenção:

```
hotelapi/
├── src/main/java/com/capgemini/hotelapi/
│   ├── controller/        # Gerencia requisições HTTP (endpoints)
│   ├── service/           # Camada de Lógica de Negócios (Business Logic)
│   ├── repository/        # Acesso ao banco de dados (Spring Data JPA)
│   ├── model/             # Entidades de persistência
│   ├── dtos/              # Objetos de Transferência de Dados (Input/Output)
│   ├── mapper/            # Conversores entre DTOs e Model
│   └── ...
└── ...
```

### 🛡️ Boas Práticas e Qualidade de Código

* **Tratamento de Exceções**: Uso de `GlobalExceptionHandler` para respostas padronizadas.
* **Validação**: Implementação de Bean Validation (JSR-380) nas DTOs.
* **Padrão DTO**: Uso de DTOs para isolar a camada de *model* das requisições.
* **Separação de Camadas**: Controle estrito da separação entre *Controller*, *Service* e *Repository*.

-----

## 🧭 Próximos Passos (Sugestões para Evolução)

* **Segurança**: Implementação de Autenticação e Autorização via JWT.
* **Concorrência**: Adição de controle de concorrência (ex.: *lock* otimista/pessimista) para evitar problemas de concorrência em reservas.
* **Auditoria**: Inclusão de campos de auditoria (`createdAt` / `updatedAt`) nas entidades.
* **Testes**: Expansão da suíte de testes de integração com **Testcontainers**.

<!-- end list -->
