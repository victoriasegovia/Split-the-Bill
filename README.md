![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=springboot)
![Architecture](https://img.shields.io/badge/Architecture-Hexagonal-blue?style=for-the-badge)
![Angular](https://img.shields.io/badge/Angular-DD0031?style=for-the-badge&logo=angular)

# Split-the-Bill

This project is a simplified version of an expense-sharing application, developed as an academic exercise. The primary focus is to demonstrate a **Pure Hexagonal Architecture** (Ports and Adapters) and clean code principles.
<img width="3840" height="1134" alt="Screenshot 2026-04-12 094943" src="https://github.com/user-attachments/assets/98b3ba55-f53c-4533-ae45-1ff91ed954e9" />

## Project Overview
The goal is to apply modern software design patterns in a controlled environment:
* **Pure Hexagonal Architecture:** Strict separation between business logic and technical implementation.
* **Basic DDD:** Implementation of Domain Entities and Repositories (intentionally omitting Value Objects, UUIDs, and Aggregate Roots for simplicity).
* **SOLID & OOP:** Strong adherence to Single Responsibility and Dependency Inversion.
* **KISS & DRY:** Focused on keeping the solution simple and avoiding unnecessary duplication.
* **Functional Programming:** Extensive use of Java Streams and functional paradigms for data mapping and transformations.
* **Decoupling:** Implementation of **Application DTOs** to ensure the Domain remains agnostic of the external layers.

## Tech Stack
* **Java 21+**
* **Spring Boot 3.x**
* **Maven & Gradle:** (Roadmap: Modularizing the project into independent modules for each layer).
* **H2 Database:** For development and testing.
* **Testing:** JUnit 5 and Mockito for Unit and Integration tests with Docker DB.
* **Angular:** Basic frontend with Angular.

---
![SplitTheBill-GoogleChrome2026-04-1209-54-35-ezgif com-video-to-gif-converter (2)](https://github.com/user-attachments/assets/8520b1fb-7bf8-4856-af10-1b37f86dcb33)
---

## Project Structure
The project follows a standard Hexagonal modular organization:

```text
src/main/java/com/yourpackage/splitthebill/
├── application/
│   ├── dto/                # Application Data Transfer Objects (Decoupling)
│   ├── mapper/             # Domain <-> DTO Mapping logic
│   └── usecases/           # Application logic implementation
├── domain/
│   ├── ports/
│   │   ├── inbound/        # Use Case interfaces (Input)
│   │   └── outbound/       # Repository interfaces (Output)
│   └── model/              # Pure Domain Entities (User, Group, Expense)
└── infrastructure/
    ├── adapters/
    │   ├── inbound/        # REST Controllers (Web Adapters)
    │   └── outbound/       # Persistence Adapters (JPA/Hibernate)
    ├── mappers/            # Request/Response to DTO Mappers
    ├── exception/          # GlobalExceptionHandler
    └── config/             # Framework-specific configuration
```

---

## Run the proyect
Prerrequisitos:
- Java 21+
- Node.js v18+ y npm
- Angular CLI

```bash
# Clone the repository
git clone https://github.com/victoriasegovia/STEMFounding.git
cd split-the-bill-backend
# Build the project (skipping tests for a faster first run)
./mvnw clean install -DskipTests
# Run the application
./mvnw spring-boot:run
# Run the front end
cd split-the-bill-frontend
ng serve
```

---

## Estrategia de Testing
El proyecto aplica una pirámide de pruebas enfocada en el aislamiento de capas:
- Unit Tests (Domain): Pruebas de lógica pura sin dependencias de frameworks.
- Integration Tests (Infrastructure): Validación de adaptadores de persistencia con H2.
- API Tests: Validación de contratos de entrada/salida mediante MockMvc.

Ejecutar tests: ./mvnw test

---

## UML
```mermaid
classDiagram
    %% Capa de Dominio
    class Group {
        +Long id
        +String name
        +List<User> members
    }
    
    class User {
        +Long id
        +String name
    }
    
    class GroupRepository {
        +Optional<Group> save(Group group)
        +Optional<Group> findByName(String name)
        +List<Group> findAll()
        +List<User> findUsersByGroupId(Long groupId)
        +void addUserToGroup(Long groupId, Long userId)
        +void removeUserFromGroup(Long groupId, Long userId)
    }
    
    class UserRepository {
        +Optional<User> save(User user)
        +Optional<User> findById(Long id)
        +List<User> findAllById(List<Long> ids)
    }
    
    %% Capa de Aplicación
    class GroupDTO {
        +Long id
        +String name
        +List<Long> membersIds
    }
    
    class UserDTO {
        +Long id
        +String name
    }
    
    class GroupService {
        +GroupDTO createGroupUseCase(GroupDTO groupDTO)
        +List<GroupDTO> listGroupsUseCase()
        +void addUserToGroupUseCase(Long groupId, Long userId)
        +void removeUserFromGroupUseCase(Long groupId, Long userId)
    }
    
    class GroupUseCases {
        -GroupRepository groupRepository
        -UserRepository userRepository
    }
    
    class GroupDTOMapper {
        +GroupDTO domainToDTO(Group group)
        +Group dtoToDomain(GroupDTO dto, List<User> members)
    }
    
    %% Capa de Infraestructura
    class GroupController {
        -GroupService groupService
        -UserRepository userRepository
        +ResponseEntity<GroupResponse> create(GroupRequest request)
        +List<GroupResponse> list()
    }
    
    class GroupPersistanceAdapter {
        -JpaGroupRepository jpaGroupRepository
    }
    
    class UserPersistanceAdapter {
        -JpaUserRepository jpaUserRepository
    }
    
    %% Relaciones
    GroupUseCases ..|> GroupService : implements
    GroupPersistanceAdapter ..|> GroupRepository : implements
    UserPersistanceAdapter ..|> UserRepository : implements
    
    GroupController --> GroupService : uses
    GroupUseCases --> GroupRepository : uses
    GroupUseCases --> UserRepository : uses
    GroupController --> UserRepository : uses
    
    GroupUseCases --> GroupDTOMapper : uses
    GroupController --> GroupDTOMapper : uses (via RequestResponseMapper)
    
    Group --> User : contains
    GroupDTO --> UserDTO : references ids
    
    GroupRepository --> Group : manages
    UserRepository --> User : manages
    GroupService --> GroupDTO : uses
```

## Database Diagrama
```mermaid
erDiagram
    User {
        BIGINT id PK
        VARCHAR name
    }

    Group {
        BIGINT id PK
        VARCHAR name
    }

    GroupMember {
        BIGINT id PK
        BIGINT group_id FK
        BIGINT user_id FK
    }

    Expense {
        BIGINT id PK
        VARCHAR description
        DECIMAL amount

        BIGINT group_id FK
        BIGINT paid_by FK
    }

    User ||--o{ GroupMember : "belongs to"
    Group ||--o{ GroupMember : "has members"
    Group ||--o{ Expense : "contains"
    User ||--o{ Expense : "pays"
```

```mermaid
sequenceDiagram
    participant Client as Cliente (HTTP)
    participant Controller as GroupController (Infra)
    participant Mapper as RequestResponseMapper (Infra)
    participant Service as GroupService (App)
    participant UseCase as GroupUseCases (App)
    participant DTOMapper as GroupDTOMapper (App)
    participant UserRepo as UserRepository (Domain)
    participant GroupRepo as GroupRepository (Domain)
    participant UserAdapter as UserPersistanceAdapter (Infra)
    participant GroupAdapter as GroupPersistanceAdapter (Infra)
    participant DB as Base de Datos

    Client->>Controller: POST /api/groups (JSON)
    Controller->>Mapper: requestToDomainDTO(request)
    Mapper-->>Controller: GroupDTO
    Controller->>Service: createGroupUseCase(GroupDTO)
    Service->>UseCase: createGroupUseCase(GroupDTO)
    UseCase->>DTOMapper: dtoToDomain(GroupDTO, members)
    DTOMapper-->>UseCase: Group
    UseCase->>UserRepo: findAllById(membersIds)
    UserRepo->>UserAdapter: findAllById(membersIds)
    UserAdapter->>DB: SELECT users
    DB-->>UserAdapter: List<User>
    UserAdapter-->>UserRepo: List<User>
    UserRepo-->>UseCase: List<User>
    UseCase->>GroupRepo: save(Group)
    GroupRepo->>GroupAdapter: save(Group)
    GroupAdapter->>DB: INSERT group
    DB-->>GroupAdapter: Group (saved)
    GroupAdapter-->>GroupRepo: Group
    GroupRepo-->>UseCase: Group
    UseCase->>DTOMapper: domainToDTO(Group)
    DTOMapper-->>UseCase: GroupDTO
    UseCase-->>Service: GroupDTO
    Service-->>Controller: GroupDTO
    Controller->>Mapper: domainDTOToResponse(GroupDTO, memberNames)
    Mapper-->>Controller: GroupResponse
    Controller-->>Client: HTTP 201 (JSON GroupResponse)
```
