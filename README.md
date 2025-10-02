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
git clone https://github.com/SEU_USUARIO/hotel-api.git
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
Serviços criados (ver `docker-compose.yml`):
| Serviço | Container | Porta Host | Função |
|---------|-----------|------------|--------|
| postgres | hotel-postgres | 5432 | Banco de dados PostgreSQL |
| app | hotel-api | 8080 | API Spring Boot |

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

#### Ciclo de Desenvolvimento Rápido
O Dockerfile faz build do JAR dentro da imagem (copy + package). Qualquer alteração em código exige rebuild. Alternativas:
1. Desenvolver localmente com `mvn spring-boot:run` (mais rápido).
2. Criar um `docker-compose.dev.yml` com volume apontando para o código e usar `./mvnw spring-boot:run` dentro do container.

Exemplo (opcional) de `docker-compose.dev.yml`:
```yaml
services:
  app:
    volumes:
      - ./src:/app/src
      - ~/.m2:/root/.m2
    command: mvn spring-boot:run
```

#### Healthcheck do Banco
Verificar status:
```bash
docker inspect --format='{{json .State.Health}}' hotel-postgres | jq
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

## 🔌 Endpoints Principais (Resumo)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/quartos/disponiveis?checkin=YYYY-MM-DD&checkout=YYYY-MM-DD` | Lista quartos disponíveis no período |
| GET | `/quartos/{id}/disponivel?checkin=YYYY-MM-DD&checkout=YYYY-MM-DD` | Verifica disponibilidade de um quarto |
| POST | `/reservas` | Cria uma reserva |
| GET | `/reservas/{id}` | Detalha uma reserva |
| DELETE | `/reservas/{id}` | Cancela uma reserva |

### Exemplo de criação de reserva
```http
POST /reservas
Content-Type: application/json

{
  "quartoId": 1,
  "dataCheckin": "2025-10-10",
  "dataCheckout": "2025-10-15",
  "hospedeNome": "João Silva"
}
```
Resposta (exemplo):
```json
{
  "id": 42,
  "quartoId": 1,
  "dataCheckin": "2025-10-10",
  "dataCheckout": "2025-10-15",
  "status": "CONFIRMADA"
}
```

## 🛡️ Boas Práticas Implementadas
* Tratamento global de exceções (`GlobalExceptionHandler`)
* Validação com Bean Validation
* Uso de DTOs para entrada/saída
* Separação por camadas (controller / service / repository / mapper)
* Lombok para redução de boilerplate


## 🧭 Próximos Passos (Sugestões)
* Autenticação / Autorização (JWT)
* Controle de concorrência em reservas (lock otimista/pessimista)
* Auditoria (createdAt / updatedAt)
* Testes de integração com Testcontainers



