# Tutorial CRUD de Usuários - Java Spring Boot + SQLite

## Sumário Executivo

Este tutorial apresenta a implementação de um sistema CRUD (Create, Read, Update, Delete) completo para gerenciamento de usuários, utilizando Java 17+, Spring Boot 3.x e SQLite. O projeto segue rigorosamente os princípios SOLID, arquitetura em camadas e padrões de projeto consolidados na indústria.

## 1. Requisitos Funcionais

### RF001 - Cadastro de Usuário

-   O sistema deve permitir o cadastro de novos usuários
-   Dados obrigatórios: nome, email, senha
-   Email deve ser único no sistema
-   Senha deve ter no mínimo 8 caracteres

### RF002 - Consulta de Usuários

-   Listar todos os usuários cadastrados
-   Buscar usuário específico por ID
-   Buscar usuário por email

### RF003 - Atualização de Usuário

-   Permitir alteração de dados do usuário
-   Validar unicidade do email na atualização
-   Não permitir alteração de senha via endpoint de atualização

### RF004 - Exclusão de Usuário

-   Remover usuário do sistema por ID
-   Implementar soft delete (exclusão lógica)

## 2. Arquitetura do Sistema

### 2.1 Visão Geral da Arquitetura

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Presentation  │    │    Business     │    │      Data       │
│     Layer       │◄──►│     Layer       │◄──►│     Layer       │
│   (Controllers) │    │   (Services)    │    │ (Repositories)  │
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

### 2.2 Camadas da Aplicação

**Presentation Layer (Controladores)**

-   Responsável pela interface REST
-   Validação de entrada
-   Mapeamento de DTOs

**Business Layer (Serviços)**

-   Lógica de negócio
-   Validações de regras
-   Orquestração de operações

**Data Layer (Repositórios)**

-   Acesso aos dados
-   Operações de persistência
-   Abstrações de banco de dados

### Verificar instalações:

```bash
java -version
mvn -version
```

## 5.6 Comandos para Executar

### Compilar e baixar dependências:

No terminal digite

```bash
cd meu-projeto
mvn clean install
```

### Executar a aplicação:

```bash
mvn spring-boot:run
```

# Parte 2: CRUD de Usuários - Arquitetura em 3 Camadas

## Java Spring Boot + SQLite + Boas Práticas

### Sumário Executivo

Este tutorial implementa um sistema CRUD completo para gerenciamento de usuários usando **arquitetura em 3 camadas** (Controller-Service-Repository), seguindo os **princípios SOLID** e **padrões de projeto** consolidados na indústria.

---

## Parte 1: Fundamentos e Padrões Aplicados

### 1.1 Arquitetura em 3 Camadas

A **arquitetura em 3 camadas** é um padrão fundamental que separa responsabilidades:

```
┌─────────────────┐
│   CONTROLLER    │ ← Camada de Apresentação (REST API)
│   (Presentation)│
└─────────────────┘
         ↓
┌─────────────────┐
│    SERVICE      │ ← Camada de Negócio (Business Logic)
│   (Business)    │
└─────────────────┘
         ↓
┌─────────────────┐
│   REPOSITORY    │ ← Camada de Dados (Data Access)
│    (Data)       │
└─────────────────┘
```

**Por que usar esta arquitetura?**

-   **Separação de responsabilidades**: Cada camada tem uma função específica
-   **Facilita manutenção**: Mudanças em uma camada não afetam as outras
-   **Testabilidade**: Cada camada pode ser testada independentemente
-   **Reutilização**: Serviços podem ser usados por diferentes controllers

### 1.2 Princípios SOLID Aplicados

#### **S - Single Responsibility Principle (SRP)**

-   **Controller**: Apenas recebe requisições HTTP e retorna respostas
-   **Service**: Apenas implementa regras de negócio
-   **Repository**: Apenas acessa dados

```java
// ✅ CORRETO - Uma responsabilidade por classe
@RestController
public class UsuarioController {
    // Só cuida de HTTP
}

@Service
public class UsuarioService {
    // Só cuida de regras de negócio
}

@Repository
public interface UsuarioRepository {
    // Só cuida de acesso a dados
}
```

#### **O - Open/Closed Principle (OCP)**

-   Classes abertas para extensão, fechadas para modificação
-   Uso de interfaces para permitir diferentes implementações

```java
// Interface permite extensão sem modificar código existente
public interface UsuarioService {
    Usuario salvar(Usuario usuario);
    // Novas operações podem ser adicionadas
}
```

#### **L - Liskov Substitution Principle (LSP)**

-   Implementações podem ser substituídas sem quebrar o sistema

```java
// Qualquer implementação de UsuarioService pode substituir outra
@Service
public class UsuarioServiceImpl implements UsuarioService {
    // Implementação específica
}
```

#### **I - Interface Segregation Principle (ISP)**

-   Interfaces específicas e coesas

```java
// Interface específica para operações de usuário
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
}
```

#### **D - Dependency Inversion Principle (DIP)**

-   Dependência de abstrações, não de implementações
-   Uso de injeção de dependência

```java
@Service
public class UsuarioServiceImpl {
    // Depende da abstração (interface), não da implementação
    private final UsuarioRepository repository;

    public UsuarioServiceImpl(UsuarioRepository repository) {
        this.repository = repository;
    }
}
```

### 1.3 Padrões de Projeto Utilizados

#### **Repository Pattern**

-   Encapsula a lógica de acesso aos dados
-   Fornece uma interface uniforme para diferentes fontes de dados

#### **Service Layer Pattern**

-   Centraliza a lógica de negócio
-   Coordena operações entre diferentes repositories

#### **DTO Pattern (Data Transfer Object)**

-   Transfere dados entre camadas
-   Evita exposição de entidades internas

#### **Dependency Injection Pattern**

-   Spring gerencia as dependências automaticamente
-   Facilita testes e manutenção

---

## Parte 2: Implementação Completa

### 2.1 Estrutura Final do Projeto

```
src/main/java/com/example/hexagonal/
├── HexagonalApplication.java
├── controller/
│   └── UsuarioController.java
├── service/
│   ├── UsuarioService.java
│   └── impl/
│       └── UsuarioServiceImpl.java
├── repository/
│   └── UsuarioRepository.java
├── entity/
│   └── Usuario.java
├── dto/
│   ├── UsuarioDTO.java
│   ├── CreateUsuarioDTO.java
│   └── UpdateUsuarioDTO.java
├── mapper/
│   └── UsuarioMapper.java
├── exception/
│   ├── GlobalExceptionHandler.java
│   ├── UsuarioNotFoundException.java
│   └── EmailJaExisteException.java
└── config/
    └── DatabaseConfig.java
```

### 2.2 Implementação das Entidades

Para a implementação da entidade precisaremos adicionar a dependência no arquivo `pom.xml`

Adicione esta dependência no seu bloco <dependencies>:

```java
<!-- Validação com Jakarta (necessário para @NotBlank, @Email, etc.) -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>

```

jakarta.validation= ependência necessária para validação

Próximos passos
Salve o pom.xml.

No terminal do seu projeto, execute:

```bash

./mvnw clean install
```

ou, se estiver usando o Maven instalado:

```bash
mvn clean install
```

Aguarde o download das dependências.

### Executar a aplicação:

```bash
mvn spring-boot:run
```

O erro jakarta.validation desaparecerá e suas anotações como @NotBlank, @Email e @Size funcionarão corretamente.

#### Usuario.java (Entidade JPA)

├── entity/
│ └── Usuario.java

Dentro da pasta entity atualize o arquivo Usuario.java

```java
package com.example.hexagonal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false, length = 100)
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    private String nome;

    @Column(name = "email", nullable = false, unique = true, length = 150)
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ter formato válido")
    private String email;

    @Column(name = "senha", nullable = false)
    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 8, message = "Senha deve ter no mínimo 8 caracteres")
    private String senha;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    // Construtor padrão
    public Usuario() {
        this.dataCriacao = LocalDateTime.now();
        this.ativo = true;
    }

    // Construtor com parâmetros
    public Usuario(String nome, String email, String senha) {
        this();
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    // Métodos de ciclo de vida JPA
    @PreUpdate
    public void preUpdate() {
        this.dataAtualizacao = LocalDateTime.now();
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) { this.dataAtualizacao = dataAtualizacao; }

    // Soft Delete
    public void inativar() {
        this.ativo = false;
        this.dataAtualizacao = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", ativo=" + ativo +
                ", dataCriacao=" + dataCriacao +
                '}';
    }
}
```

### 2.3 DTOs (Data Transfer Objects)

DTOs são objetos usados para transferir dados entre camadas ou sistemas, geralmente entre o backend e o frontend de uma aplicação. Eles servem para:
Encapsular os dados que serão enviados ou recebidos via API.
Controlar quais campos são expostos ou aceitos, protegendo dados sensíveis (ex: não enviar a senha).
Facilitar validação e padronizar o formato da informação.
Desacoplar a estrutura interna da entidade do modelo de dados externo.

DTO é um padrão de projeto (design pattern) para estruturar objetos que transportam dados entre processos, camadas ou sistemas, especialmente em arquiteturas distribuídas.

#### UsuarioDTO.java (Resposta da API)

├── dto/
│ ├── UsuarioDTO.java
│ ├── CreateUsuarioDTO.java
│ └── UpdateUsuarioDTO.java

UsuarioDTO é usado para retornar dados da API ao cliente.
CreateUsuarioDTO contém os dados e validações para criar um usuário.
UpdateUsuarioDTO contém os dados opcionais para atualizar um usuário.

Vamos agora criar a pasta dto e dentro dela criaremos o arquivo UsuarioDTO.java

```java
package com.example.hexagonal.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class UsuarioDTO {

    private Long id;
    private String nome;
    private String email;
    private Boolean ativo;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataCriacao;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataAtualizacao;

    // Construtores
    public UsuarioDTO() {}

    public UsuarioDTO(Long id, String nome, String email, Boolean ativo,
                     LocalDateTime dataCriacao, LocalDateTime dataAtualizacao) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.ativo = ativo;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    public LocalDateTime getDataAtualizacao() { return dataAtualizacao; }
    public void setDataAtualizacao(LocalDateTime dataAtualizacao) { this.dataAtualizacao = dataAtualizacao; }
}
```

#### CreateUsuarioDTO.java (Criação)

```java
package com.example.hexagonal.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateUsuarioDTO {

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    private String nome;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ter formato válido")
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 8, message = "Senha deve ter no mínimo 8 caracteres")
    private String senha;

    // Construtores
    public CreateUsuarioDTO() {}

    public CreateUsuarioDTO(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}
```

#### UpdateUsuarioDTO.java (Atualização)

```java
package com.example.hexagonal.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class UpdateUsuarioDTO {

    @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
    private String nome;

    @Email(message = "Email deve ter formato válido")
    private String email;

    // Construtores
    public UpdateUsuarioDTO() {}

    public UpdateUsuarioDTO(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
```

### 2.4 Mapper (Conversão entre Entity e DTO)

Crie a pasta mapper e dentro dela crie o arquivo `UsuarioMapper.java`

├── mapper/
│ └── UsuarioMapper.java

O mapper serve para converter dados entre diferentes camadas da aplicação, transformando:
Entidades (modelos de domínio) em DTOs para enviar dados à API.
DTOs em entidades para persistência no banco.
Atualizar entidades com dados de DTOs de atualização.
Ou seja, ele faz a tradução entre os formatos usados internamente e os expostos externamente, facilitando o desacoplamento e organização do código.

#### UsuarioMapper.java

```java
package com.exemplo.hexagonal.mapper;

import com.example.hexagonal.dto.CreateUsuarioDTO;
import com.example.hexagonal.dto.UpdateUsuarioDTO;
import com.example.hexagonal.dto.UsuarioDTO;
import com.example.hexagonal.entity.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioMapper {

    /**
     * Converte Entity para DTO
     */
    public UsuarioDTO toDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }

        return new UsuarioDTO(
            usuario.getId(),
            usuario.getNome(),
            usuario.getEmail(),
            usuario.getAtivo(),
            usuario.getDataCriacao(),
            usuario.getDataAtualizacao()
        );
    }

    /**
     * Converte CreateDTO para Entity
     */
    public Usuario toEntity(CreateUsuarioDTO createDTO) {
        if (createDTO == null) {
            return null;
        }

        return new Usuario(
            createDTO.getNome(),
            createDTO.getEmail(),
            createDTO.getSenha()
        );
    }

    /**
     * Atualiza Entity com dados do UpdateDTO
     */
    public void updateEntity(Usuario usuario, UpdateUsuarioDTO updateDTO) {
        if (usuario == null || updateDTO == null) {
            return;
        }

        if (updateDTO.getNome() != null && !updateDTO.getNome().trim().isEmpty()) {
            usuario.setNome(updateDTO.getNome().trim());
        }

        if (updateDTO.getEmail() != null && !updateDTO.getEmail().trim().isEmpty()) {
            usuario.setEmail(updateDTO.getEmail().trim().toLowerCase());
        }
    }

    /**
     * Converte lista de Entity para lista de DTO
     */
    public List<UsuarioDTO> toDTOList(List<Usuario> usuarios) {
        return usuarios.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
```

## Tratamento de Erros

├── exception/
│ ├── GlobalExceptionHandler.java
│ ├── UsuarioNotFoundException.java
│ └── EmailJaExisteException.java

exception = Melhora o controle de erros, legibilidade do código e experiência do usuário.

### 2.5 Exceções Customizadas

Exceções customizadas (ex: UsuarioNotFoundException, EmailJaExisteException) servem para representar erros específicos do negócio de forma clara e controlada.

#### UsuarioNotFoundException.java

```java
package com.example.hexagonal.exception;

public class UsuarioNotFoundException extends RuntimeException {

    public UsuarioNotFoundException(String message) {
        super(message);
    }

    public UsuarioNotFoundException(Long id) {
        super("Usuário com ID " + id + " não encontrado");
    }

    public UsuarioNotFoundException(String field, String value) {
        super("Usuário com " + field + " '" + value + "' não encontrado");
    }
}
```

#### EmailJaExisteException.java

```java
package com.example.hexagonal.exception;

public class EmailJaExisteException extends RuntimeException {

    public EmailJaExisteException(String email) {
        super("Email '" + email + "' já está sendo usado por outro usuário");
    }
}
```

#### GlobalExceptionHandler.java

Handler global (GlobalExceptionHandler) intercepta essas exceções e outras, transformando-as em respostas HTTP padronizadas e amigáveis para o cliente, com status e mensagens apropriadas.

```java
package com.example.hexagonal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsuarioNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUsuarioNotFound(UsuarioNotFoundException ex) {
        Map<String, Object> error = createErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            "Usuário não encontrado",
            ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(EmailJaExisteException.class)
    public ResponseEntity<Map<String, Object>> handleEmailJaExiste(EmailJaExisteException ex) {
        Map<String, Object> error = createErrorResponse(
            HttpStatus.CONFLICT.value(),
            "Email já existe",
            ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        Map<String, Object> errorResponse = createErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "Dados inválidos",
            "Verifique os campos informados"
        );
        errorResponse.put("errors", errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        Map<String, Object> error = createErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Erro interno do servidor",
            "Ocorreu um erro inesperado"
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    private Map<String, Object> createErrorResponse(int status, String error, String message) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("status", status);
        errorResponse.put("error", error);
        errorResponse.put("message", message);
        return errorResponse;
    }
}
```

### 2.6 Camada de Repository

├── repository/
│ └── UsuarioRepository.java

Crie a pasta repository e dentro dela o arquivo UsuarioRepository.java

#### UsuarioRepository.java

```java
package com.example.hexagonal.repository;

import com.example.hexagonal.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca usuário por email
     */
    Optional<Usuario> findByEmail(String email);

    /**
     * Verifica se existe usuário com o email
     */
    boolean existsByEmail(String email);

    /**
     * Busca usuários ativos
     */
    List<Usuario> findByAtivoTrue();

    /**
     * Busca usuários inativos (soft delete)
     */
    List<Usuario> findByAtivoFalse();

    /**
     * Busca usuários por nome (case insensitive)
     */
    @Query("SELECT u FROM Usuario u WHERE LOWER(u.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Usuario> findByNomeContainingIgnoreCase(@Param("nome") String nome);

    /**
     * Verifica se existe outro usuário com o mesmo email (para updates)
     */
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM Usuario u WHERE u.email = :email AND u.id <> :id")
    boolean existsByEmailAndIdNot(@Param("email") String email, @Param("id") Long id);

    /**
     * Busca usuário ativo por ID
     */
    @Query("SELECT u FROM Usuario u WHERE u.id = :id AND u.ativo = true")
    Optional<Usuario> findByIdAndAtivoTrue(@Param("id") Long id);
}
```

### 2.7 Camada de Service

├── service/
│ ├── UsuarioService.java
│ └── impl/
│ └── UsuarioServiceImpl.java

Na camada de serviço teremos que criar a pasta service e dentro da pasta serviço o arquivo UsuarioService.java.
Dentro da pasta service crie também a pasta impl e dentro dela o arquivo UsuarioServiceImpl.java

### Por que criar a camada de Service e sua estrutura?

-   **Camada Service** isola a lógica de negócio da aplicação, separando-a da persistência (repository) e da interface (controller).
-   **Interface (`UsuarioService.java`)** define contratos claros para os serviços, facilitando manutenção e testes.
-   **Implementação (`UsuarioServiceImpl.java`)** contém a lógica real, podendo ser facilmente substituída ou mockada.
-   A pasta `impl` ajuda a organizar implementações e manter o código modular e limpo.

Essa separação promove **desacoplamento, reutilização e clareza** no projeto.

```bash
UsuarioService (interface)
       ▲
       │ implementa
       │
UsuarioServiceImpl (classe)
   ├────> UsuarioRepository (acesso a dados)
   ├────> UsuarioMapper (conversão DTO ↔ Entity)
   └────> BCryptPasswordEncoder (criptografia de senha)

```

Explicação resumida:
UsuarioServiceImpl implementa a interface UsuarioService.
UsuarioServiceImpl depende do UsuarioRepository para acessar o banco.
Usa o UsuarioMapper para converter entre entidade e DTO.
Usa BCryptPasswordEncoder para criptografar senhas.
Esse esquema ajuda a entender a responsabilidade e as dependências dentro da camada de serviço

Sem DIP (acoplamento direto)

```bash
Controller
   ↓
Service (implementação concreta)
   ↓
Repository
```

Nesse caso, o Controller depende diretamente da implementação concreta do Service. Isso gera forte acoplamento — se a implementação mudar, o controller também pode ter que mudar.

Com DIP (boa prática com interfaces)

```bash
Controller
   ↓
UsuarioService (interface)  ←  UsuarioServiceImpl
                                       ↓
                                  UsuarioRepository

```

O que mudou:
O Controller não sabe quem é a implementação, apenas usa a interface UsuarioService.
A classe UsuarioServiceImpl implementa essa interface.
O Spring injeta a implementação correta via injeção de dependência, respeitando o DIP.

Benefícios:
Desacoplamento: o controller pode funcionar com qualquer implementação de UsuarioService.
Facilidade de teste: você pode injetar um mock de UsuarioService no teste do controller.
Manutenção e evolução do código com menos impacto.

A inversão está no fato de que o alto nível (Controller) depende de uma abstração, e o baixo nível (ServiceImpl) é que implementa essa abstração — e não o contrário.

Vamso agora adicioonar o código

#### UsuarioService.java (Interface)

```java
package com.example.hexagonal.service;

import com.example.hexagonal.dto.CreateUsuarioDTO;
import com.example.hexagonal.dto.UpdateUsuarioDTO;
import com.example.hexagonal.dto.UsuarioDTO;

import java.util.List;

/**
 * Interface para operações de negócio relacionadas a usuários
 */
public interface UsuarioService {

    /**
     * Criar novo usuário
     */
    UsuarioDTO criar(CreateUsuarioDTO createUsuarioDTO);

    /**
     * Buscar usuário por ID
     */
    UsuarioDTO buscarPorId(Long id);

    /**
     * Buscar usuário por email
     */
    UsuarioDTO buscarPorEmail(String email);

    /**
     * Listar todos os usuários ativos
     */
    List<UsuarioDTO> listarTodos();

    /**
     * Listar usuários por nome
     */
    List<UsuarioDTO> buscarPorNome(String nome);

    /**
     * Atualizar usuário
     */
    UsuarioDTO atualizar(Long id, UpdateUsuarioDTO updateUsuarioDTO);

    /**
     * Inativar usuário (soft delete)
     */
    void inativar(Long id);

    /**
     * Reativar usuário
     */
    UsuarioDTO reativar(Long id);

    /**
     * Verificar se email já existe
     */
    boolean emailJaExiste(String email);

    /**
     * Verificar se email já existe para outro usuário
     */
    boolean emailJaExisteParaOutroUsuario(String email, Long userId);
}
```

#### UsuarioServiceImpl.java (Implementação)

Para a implementação do UsuarioServiceImpl precisaremos adicionar a dependência no arquivo `pom.xml`
Adicionar a dependência do Spring Security no seu pom.xml.

Onde colocar?
No bloco <dependencies> do seu pom.xml, como abaixo:

```java
<!-- Spring Boot Starter Security -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

### ✅ Depois disso:

1. Salve o `pom.xml`.
2. Rode `mvn clean install` ou deixe o IDE atualizar as dependências.
3. O erro de importação do `org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder` será resolvido.

Aguarde o download das dependências.

### Executar a aplicação:

```bash
mvn spring-boot:run
```

Adcione o código ao arquivo UsuarioServiceImpl.java

```java
package com.example.hexagonal.service.impl;

import com.example.hexagonal.dto.CreateUsuarioDTO;
import com.example.hexagonal.dto.UpdateUsuarioDTO;
import com.example.hexagonal.dto.UsuarioDTO;
import com.example.hexagonal.entity.Usuario;
import com.example.hexagonal.exception.EmailJaExisteException;
import com.example.hexagonal.exception.UsuarioNotFoundException;
import com.example.hexagonal.mapper.UsuarioMapper;
import com.example.hexagonal.repository.UsuarioRepository;
import com.example.hexagonal.service.UsuarioService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository,
                             UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public UsuarioDTO criar(CreateUsuarioDTO createUsuarioDTO) {
        // Validar se email já existe
        if (emailJaExiste(createUsuarioDTO.getEmail())) {
            throw new EmailJaExisteException(createUsuarioDTO.getEmail());
        }

        // Converter DTO para Entity
        Usuario usuario = usuarioMapper.toEntity(createUsuarioDTO);

        // Criptografar senha
        usuario.setSenha(passwordEncoder.encode(createUsuarioDTO.getSenha()));

        // Normalizar email
        usuario.setEmail(createUsuarioDTO.getEmail().toLowerCase().trim());

        // Salvar no banco
        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        // Converter para DTO e retornar
        return usuarioMapper.toDTO(usuarioSalvo);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioDTO buscarPorId(Long id) {
        Usuario usuario = usuarioRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new UsuarioNotFoundException(id));

        return usuarioMapper.toDTO(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioDTO buscarPorEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email.toLowerCase().trim())
                .orElseThrow(() -> new UsuarioNotFoundException("email", email));

        if (!usuario.getAtivo()) {
            throw new UsuarioNotFoundException("email", email);
        }

        return usuarioMapper.toDTO(usuario);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDTO> listarTodos() {
        List<Usuario> usuarios = usuarioRepository.findByAtivoTrue();
        return usuarioMapper.toDTOList(usuarios);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDTO> buscarPorNome(String nome) {
        List<Usuario> usuarios = usuarioRepository.findByNomeContainingIgnoreCase(nome);

        // Filtrar apenas usuários ativos
        List<Usuario> usuariosAtivos = usuarios.stream()
                .filter(Usuario::getAtivo)
                .toList();

        return usuarioMapper.toDTOList(usuariosAtivos);
    }

    @Override
    public UsuarioDTO atualizar(Long id, UpdateUsuarioDTO updateUsuarioDTO) {
        // Buscar usuário existente
        Usuario usuario = usuarioRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new UsuarioNotFoundException(id));

        // Validar email se foi informado
        if (updateUsuarioDTO.getEmail() != null &&
            !updateUsuarioDTO.getEmail().trim().isEmpty()) {

            String novoEmail = updateUsuarioDTO.getEmail().toLowerCase().trim();

            if (emailJaExisteParaOutroUsuario(novoEmail, id)) {
                throw new EmailJaExisteException(novoEmail);
            }
        }

        // Atualizar dados
        usuarioMapper.updateEntity(usuario, updateUsuarioDTO);

        // Salvar alterações
        Usuario usuarioAtualizado = usuarioRepository.save(usuario);

        return usuarioMapper.toDTO(usuarioAtualizado);
    }

    @Override
    public void inativar(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException(id));

        if (!usuario.getAtivo()) {
            throw new UsuarioNotFoundException(id);
        }

        usuario.inativar();
        usuarioRepository.save(usuario);
    }

    @Override
    public UsuarioDTO reativar(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException(id));

        if (usuario.getAtivo()) {
            throw new IllegalStateException("Usuário já está ativo");
        }

        usuario.setAtivo(true);
        Usuario usuarioReativado = usuarioRepository.save(usuario);

        return usuarioMapper.toDTO(usuarioReativado);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean emailJaExiste(String email) {
        return usuarioRepository.existsByEmail(email.toLowerCase().trim());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean emailJaExisteParaOutroUsuario(String email, Long userId) {
        return usuarioRepository.existsByEmailAndIdNot(email.toLowerCase().trim(), userId);
    }
}
```

### 2.9 Configuração Adicional

#### DatabaseConfig.java

└── config/
| └── DatabaseConfig.java

Criaremos a pasta config e o arquivo DatabaseConfig.java

```java
package com.example.hexagonal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class DatabaseConfig {

    /**
     * Bean para criptografia de senhas
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

No seu caso específico:
Define um bean do tipo BCryptPasswordEncoder, que é usado para criptografar senhas.
Esse bean pode ser injetado automaticamente em outras classes (ex: seu UsuarioServiceImpl) sempre que precisar criptografar senhas, garantindo reutilização e centralização da configuração.
A anotação @Configuration indica que essa classe fornece definições de beans.
A anotação @Bean cria e expõe um objeto gerenciado pelo Spring.

Por que isso é útil?
Evita criar manualmente uma nova instância de BCryptPasswordEncoder em vários lugares.
Facilita testes e manutenção, pois a configuração está centralizada.
Permite trocar facilmente a implementação do encoder se necessário, alterando apenas essa configuração.

### 2.8 Camada de Controller

├── controller/
│ └── UsuarioController.java

Teremos agora que criar a pasta controller e o arquivo UsuarioController.java

O que é essa configuração?
O arquivo DatabaseConfig.java dentro da pasta config é uma classe de configuração do Spring, responsável por declarar beans que serão gerenciados pelo container do Spring.

#### UsuarioController.java

```java
package com.example.hexagonal.controller;

import com.example.hexagonal.dto.CreateUsuarioDTO;
import com.example.hexagonal.dto.UpdateUsuarioDTO;
import com.example.hexagonal.dto.UsuarioDTO;
import com.example.hexagonal.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Criar novo usuário
     * POST /api/usuarios
     */
    @PostMapping
    public ResponseEntity<UsuarioDTO> criar(@Valid @RequestBody CreateUsuarioDTO createUsuarioDTO) {
        UsuarioDTO usuarioCriado = usuarioService.criar(createUsuarioDTO);

        URI location = URI.create("/api/usuarios/" + usuarioCriado.getId());

        return ResponseEntity.created(location).body(usuarioCriado);
}

 @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("API está funcionando!");
    }
}
```

## Parte 3: Execução e Testes

### 3.1 Atualizando o pom.xml

Verifique se estas dependência se encontram no `pom.xml`:

```xml
<!-- Spring Boot Starter Validation -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>

<!-- Spring Security (para BCrypt) -->
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-crypto</artifactId>
</dependency>
```

### 3.2 Executando a Aplicação

```bash
# Compilar e executar
mvn clean install
mvn spring-boot:run

# Ou com Maven Wrapper
./mvnw spring-boot:run
```

### 3.3 Testando as APIs

#### 1. Health Check

```bash
curl -X GET http://localhost:8080/api/usuarios/health
```

#### 2. Criar Usuário

```bash
curl -X POST http://localhost:8080/api/usuarios \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João Silva",
    "email": "joao@email.com",
    "senha": "12345678"
  }'
```

#### 3. Listar Usuários

```bash
curl -X GET http://localhost:8080/api/usuarios
```

#### 4. Buscar por ID

```bash
curl -X GET http://localhost:8080/api/usuarios/1
```

#### 5. Buscar por Email

```bash
curl -X GET http://localhost:8080/api/usuarios/email/joao@email.com
```

#### 6. Buscar por Nome

```bash
curl -X GET "http://localhost:8080/api/usuarios/buscar?nome=João"
```

#### 7. Atualizar Usuário

```bash
curl -X PUT http://localhost:8080/api/usuarios/1 \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João Santos",
    "email": "joao.santos@email.com"
  }'
```

#### 8. Inativar Usuário

```bash
curl -X DELETE http://localhost:8080/api/usuarios/1
```

#### 9. Reativar Usuário

```bash
curl -X PATCH http://localhost:8080/api/usuarios/1/reativar
```

#### 10. Verificar se Email Existe

```bash
curl -X GET http://localhost:8080/api/usuarios/email-existe/joao@email.com
```

### 3.4 Respostas Esperadas

#### Sucesso - Criar Usuário (201 Created)

```json
{
    "id": 1,
    "nome": "João Silva",
    "email": "joao@email.com",
    "ativo": true,
    "dataCriacao": "2024-01-15T10:30:00",
    "dataAtualizacao": null
}
```

#### Erro - Email já existe (409 Conflict)

```json
{
    "timestamp": "2024-01-15T10:35:00",
    "status": 409,
    "error": "Email já existe",
    "message": "Email 'joao@email.com' já está sendo usado por outro usuário"
}
```

#### Erro - Usuário não encontrado (404 Not Found)

```json
{
    "timestamp": "2024-01-15T10:35:00",
    "status": 404,
    "error": "Usuário não encontrado",
    "message": "Usuário com ID 999 não encontrado"
}
```

#### Erro - Validação (400 Bad Request)

```json
{
    "timestamp": "2024-01-15T10:35:00",
    "status": 400,
    "error": "Dados inválidos",
    "message": "Verifique os campos informados",
    "errors": {
        "nome": "Nome é obrigatório",
        "email": "Email deve ter formato válido",
        "senha": "Senha deve ter no mínimo 8 caracteres"
    }
}
```

---

## Parte 4: Vantagens da Arquitetura em 3 Camadas

### 4.1 Benefícios Alcançados

#### ✅ **Separação de Responsabilidades**

-   **Controller**: Apenas lida com HTTP
-   **Service**: Apenas implementa regras de negócio
-   **Repository**: Apenas acessa dados

#### ✅ **Facilidade de Manutenção**

-   Mudanças em uma camada não afetam as outras
-   Código mais organizado e legível

#### ✅ **Testabilidade**

-   Cada camada pode ser testada independentemente
-   Fácil criação de mocks

#### ✅ **Reutilização**

-   Services podem ser usados por diferentes controllers
-   Repositories podem ser usados por diferentes services

#### ✅ **Aplicação dos Princípios SOLID**

-   **S**: Cada classe tem uma responsabilidade
-   **O**: Interfaces permitem extensão
-   **L**: Implementações são substituíveis
-   **I**: Interfaces específicas e coesas
-   **D**: Dependência de abstrações

### 4.2 Fluxo de Execução

```
Cliente HTTP → Controller → Service → Repository → Database
                  ↓           ↓          ↓
               Valida     Aplica      Persiste
              Request     Regras      Dados
                  ↓           ↓          ↓
              Response  ← DTO      ← Entity
```

### 4.3 Padrões Implementados

-   **Repository Pattern**: Abstração do acesso aos dados
-   **Service Layer Pattern**: Centralização da lógica de negócio
-   **DTO Pattern**: Transferência segura de dados
-   **Dependency Injection**: Inversão de controle
-   **Exception Handling**: Tratamento centralizado de erros

---

## Conclusão do Tutorial 1

Implementamos com sucesso um **sistema CRUD completo** seguindo:

-   ✅ **Arquitetura em 3 camadas** bem definida
-   ✅ **Princípios SOLID** aplicados corretamente
-   ✅ **Padrões de projeto** consolidados
-   ✅ **Tratamento de exceções** robusto
-   ✅ **Validações** adequadas
-   ✅ **APIs REST** bem estruturadas

### Próximos Passos

No **Tutorial 2**, refatoraremos este código para usar **Arquitetura Hexagonal**, que oferece:

-   🔄 **Maior flexibilidade** para mudanças futuras
-   🔄 **Desacoplamento total** entre camadas
-   🔄 **Facilidade para testes** unitários
-   🔄 **Independência de frameworks**
-   🔄 **Melhor organização** do código
