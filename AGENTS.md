# AGENTS.md

## Project Overview

TrustfulIP — 基于Spring Boot和Vue的个性化博客与知识管理系统，河南大学2026届本科毕设项目。

- **Author:** 李成鑫 (KitenLee), 学号 2224240861
- **GitHub:** https://github.com/roboatLee/FileManager
- **Language:** Java 8 (backend), JavaScript/TypeScript (frontend)
- **Database:** MySQL 8.0, Redis
- **Build:** Maven (backend), Vite (frontend)

## Directory Structure

```
mytest/
├── AGENTS.md              # This file
├── trustfulIP/            # Java Spring Boot multi-module backend
│   ├── pom.xml            # Parent POM (version management)
│   ├── web/               # Main entry module, controllers, security config
│   ├── auth/              # JWT authentication, user management
│   ├── blog/              # Article/blog CRUD
│   ├── letChat/           # WebSocket real-time chat
│   ├── letStudy/          # Question bank / learning module
│   ├── MyFile/            # File info entity & service
│   ├── wecom/             # WeChat Work enterprise integration
│   ├── common/            # Code generator, shared deps (OpenAPI, MapStruct)
│   ├── infrastructure/    # JSON/XML file persistence
│   ├── infrastructure-minio/ # MinIO object storage service
│   └── data/              # JSON file storage (chat-messages.json, favorites.json)
├── wecomtest/             # Vue 3 frontend
│   └── src/
│       ├── main.js        # Entry: Vue + Router + ElementPlus + Pinia
│       ├── router/        # Vue Router (JWT guard)
│       ├── store/         # Pinia stores
│       ├── api/           # Axios client + OpenAPI generated TS APIs
│       ├── auth/          # Login page, token store, UserVo
│       ├── page/MyPage/   # Main features (Chat, File, Blog, Vditor, etc.)
│       ├── letstudy/      # Learning module pages
│       ├── usercenter/    # User profile center
│       ├── todoList/      # Todo list feature
│       └── minio/         # MinIO file upload UI
├── mysql/                 # Database SQL scripts
│   ├── question.sql       # Question table
│   └── article.sql        # Article table
└── img/                   # README screenshots
```

## Tech Stack

### Backend (Spring Boot 2.7.11, Java 8)
| Technology | Version | Purpose |
|---|---|---|
| Spring Boot | 2.7.11 | Application framework |
| MyBatis-Plus | 3.5.12 | ORM / database layer |
| MySQL Connector | 8.0.33 | Database driver |
| Spring WebSocket | (via Boot) | Real-time chat |
| jjwt | 0.11.5 | JWT authentication (HS256) |
| SpringDoc OpenAPI | 1.6.15 | Swagger / API documentation |
| Minio Client | 8.5.7 | Object storage |
| MapStruct | 1.5.5 | Entity/DTO mapping |
| Lombok | 1.18.30 | Boilerplate reduction |
| Spring Data Redis | 2.7.18 | WeChat token caching |

### Frontend (Vue 3, Vite)
| Technology | Version | Purpose |
|---|---|---|
| Vue | 3.5.25 | UI framework |
| Vite | 7.2.4 | Build tool |
| Vue Router | 4.6.4 | Client-side routing |
| Pinia | 3.0.4 | State management |
| Element Plus | 2.13.3 | UI component library |
| Axios | 1.13.2 | HTTP client |
| Vditor | 3.11.2 | Markdown editor |
| jwt-decode | 4.0.0 | JWT client decoding |

## How to Run

### Prerequisites
- JDK 8
- Node.js >=20.19.0 or >=22.12.0
- MySQL 8.0 (database: `trustful_platform`, user: root, password: Lcx0618.)
- Redis (localhost:6379)
- MinIO (localhost:9000, accessKey: admin, secretKey: password)

### Backend
```bash
cd trustfulIP
mvn clean install -DskipTests
cd web
mvn spring-boot:run
# Runs on port 8080
# Swagger UI: http://localhost:8080/swagger-ui.html
# API Docs: http://localhost:8080/v3/api-docs
```

### Frontend
```bash
cd wecomtest
npm install
npm run dev
# Runs on port 5172, proxies /api -> localhost:8080
```

### Generate TypeScript API Client
```bash
cd wecomtest
npm run gen-api
# Reads from http://localhost:8080/v3/api-docs
# Outputs to src/api/generated/
```

## Architecture

### Layered Design (Backend)
- **Controller** layer: REST endpoints (`/api/*`, `/auth/*`, `/chat` WebSocket)
- **Service** layer: Business logic
- **Mapper** layer: MyBatis-Plus database interaction
- **Entity** layer: DO (Database Object, maps to table), DTO (Data Transfer Object, for API), VO (Value Object, for frontend display)
- **Convert** layer: MapStruct interfaces for object mapping (DO ↔ DTO ↔ VO)

### Authentication Flow
1. User registers via `POST /auth/register` (BCrypt password hashing)
2. Login returns `accessToken` (24h) + `refreshToken` (7 days)
3. Frontend Axios interceptor attaches `Bearer {token}` to all requests
4. On 401, interceptor attempts silent refresh via `POST /auth/refresh`
5. Backend `JwtAuthenticationFilter` validates token on each request
6. WebSocket connection includes `token` query param, validated by `JwtHandshakeInterceptor`

### Chat Architecture (WebSocket)
- Endpoint: `ws://localhost:8080/chat?token={jwt}`
- Handler: `ChatHandler` extends `TextWebSocketHandler`
- Session tracking: `ConcurrentHashMap<Long, Set<WebSocketSession>>` — one user, multiple devices
- Message types: `chat`, `join`, `leave`, `users`, `history`
- Private conversation ID: `min(userId1, userId2)_max(userId1, userId2)`
- Persistence: JSON file at `trustfulIP/data/chat-messages.json`

### Data Storage Strategy
- **MySQL**: users, articles, questions, categories, tags, file_info
- **MinIO**: user avatars, uploaded files (bucket: `blog-images`)
- **JSON files**: chat messages (`chat-messages.json`), favorites (`favorites.json`)
- **Redis**: WeChat Work access_token caching

## Key Configuration Files

| File | Contents |
|---|---|
| `trustfulIP/pom.xml` | Parent POM, all dependency versions |
| `trustfulIP/web/src/main/resources/application.yml` | Main config, imports module YAMLs |
| `trustfulIP/auth/src/main/resources/auth.yml` | Datasource, JWT secret/expiration |
| `trustfulIP/blog/src/main/resources/blog.yml` | Blog datasource |
| `trustfulIP/common/src/main/resources/common.yml` | Common datasource |
| `trustfulIP/infrastructure-minio/src/main/resources/minio.yml` | MinIO endpoint, credentials, bucket |
| `wecomtest/vite.config.js` | Vite dev server (port 5172), proxy rules |
| `wecomtest/package.json` | Frontend deps and scripts |

## Code Conventions

### Java (Backend)
- Base package: `com.lee`
- Module packages follow Maven module name
- Entity classes: `*DO` = database object, `*DTO` = transfer object, `*VO` = view object, `*BO` = business object
- Convert interfaces: `I*Convert` interface + `*ConvertImpl` (MapStruct-generated)
- Controller → Service → Mapper → Entity standard Spring MVC pattern
- Lombok: `@Data`, `@Builder`, `@AllArgsConstructor`, `@NoArgsConstructor` on entities
- Database table naming: lowercase with underscores (Alibaba standard)

### Vue (Frontend)
- Composition API with `<script setup>`
- Pinia stores for state management
- Element Plus components for UI
- Axios instance from `src/api/http.ts` for all API calls
- JWT guard in `src/router/index.js` — whitelists `/login`, `/register`

## Key Classes

| Class | Location | Role |
|---|---|---|
| `trustIP` | `web/.../com/lee/trustIP.java` | Spring Boot main entry |
| `SecurityConfig` | `web/.../com/lee/config/SecurityConfig.java` | Spring Security (CSRF off, permitAll) |
| `SwaggerConfig` | `web/.../com/lee/swagger/SwaggerConfig.java` | OpenAPI 3 config |
| `JwtUtil` | `auth/.../com/lee/security/JwtUtil.java` | Token generation/validation |
| `JwtAuthenticationFilter` | `auth/.../com/lee/security/JwtAuthenticationFilter.java` | Per-request token filter |
| `ChatHandler` | `letChat/.../com/lee/websocket/ChatHandler.java` | WebSocket message handling |
| `WebSocketConfig` | `letChat/.../com/lee/config/WebSocketConfig.java` | WebSocket endpoint config (/chat) |
| `MinioService` | `infrastructure-minio/.../com/lee/service/MinioService.java` | MinIO upload/download |
| `CodeGenerator` | `common/.../com/lee/CodeGenerator.java` | MyBatis-Plus code generator CLI |
| `JsonController` | `web/.../com/lee/controller/JsonController.java` | File system browsing API |
| `AuthController` | `auth/.../com/lee/controller/AuthController.java` | Register/login/refresh endpoints |
| `ArticleController` | `blog/.../controller/ArticleController.java` | Blog CRUD endpoints |
| `QuestionController` | `letStudy/.../controller/QuestionController.java` | Question bank endpoints |

## Testing

- No formal test suite exists (no src/test implementations found)
- Manual testing via black-box and white-box approaches (documented in thesis)
- Run backend without tests: `mvn clean install -DskipTests`
- No lint or typecheck scripts configured in `package.json`

## Database Tables

| Table | Module | Purpose |
|---|---|---|
| `user` | auth | User accounts (BCrypt password, avatar FK) |
| `file_info` | MyFile | MinIO file index (name, path, uploader) |
| `article` | blog | Blog posts (markdown + html dual content, status draft/published) |
| `question` | letStudy | Questions (types: single_choice, multiple_choice, judge, fill, math, subjective) |
| `category` | letStudy | Question categories |
| `tag` | letStudy | Tags for questions |
| `question_tag` | letStudy | Many-to-many question-tag junction |

## Notes

- The `wecom` module uses a different base package (`trustfulIP`) and its own `application.yml` (runs on port 8080 independently). It was likely an earlier iteration.
- The `web` module is the current main entry point. All other modules are dependencies of `web`.
- Chat messages are persisted as JSON files, not in MySQL — this is intentional for simplicity.
- The blog module HTML content is stored alongside Markdown for faster rendering without Markdown parser overhead.
- Question options use JSON format in a single column (de-normalized design) to reduce join complexity.
- The project has no `.env` files — all configuration is in YAML.
