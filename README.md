O Task Manager é mais um sistemas simples voltada inteiramente aos meus estudos em Java, Spring Boot, React.js e outras ferramentas necessarias na programação atualmente;

Fase 0: 

    - Criação do projeto através do "Spring Initializr" com as dependencias necessarias;
    - Configuração inicial e temporaria de containers Docker para o banco de dados em Postgres;
    - De começo duas tabelas principais:
      

```mermaid
erDiagram
    USUARIOS {
        number Id
        string Nome
        string E-mail
        string Senha
        date DataCriacao
        date DataAtualizacao
    }
    TASKS {
        number Id
        string Nome
        string Descricao
        string Status
        date DataCriacao
        date DataAtualizacao
        number UsuarioId
    }
```


Fase 1:

    -Criação das Models, Controlleres, Repository;

    -Inserido o padrão de do Basic Auth com Spring security(Posteriormente vou fazer modificações afim de melhorar a autenticação);
    

Primeiros Testes com Banco de Dados junto com Postman

Usuários:

<img width="572" alt="image" src="https://github.com/user-attachments/assets/c532181c-5abe-4c27-82c1-e1a038c6399c" />


<img width="764" alt="image" src="https://github.com/user-attachments/assets/66ea33a7-6749-456a-8bd3-d3326f274162" />

Tarefas:

<img width="580" alt="image" src="https://github.com/user-attachments/assets/1f9aa38a-6224-4e50-b134-263bde6a9823" />


<img width="793" alt="image" src="https://github.com/user-attachments/assets/78861495-7f7b-4bb1-9d81-3bc99767d84e" />

***Ocorreram várias modificações no decorrer dessa fase, afim de simplificar a possibilidade do cadastro de ambas as tabelas.


Fase 2:

    -Integração do JWT Token:
    
```mermaid

classDiagram
    class JWT {
        +header: Object
        +payload: Claims
        +signature: String
    }

class Header {
    +alg: "HS256"
    +typ: "JWT"
}

class Claims {
    +sub: "username" (String)
    +iat: 1735689600 (Number)
    +exp: 1735693200 (Number)
}

JWT --> Header
JWT --> Claims
JWT --> Signature

```

    -JWT Token é muito utilizado em verificações HTTP. Utilizado amplamente por sua segurança, que é feita por uma assinatura digital. É portável, ou seja, pode carregar informaçoes extras(ex: roles do usuário, e-mail, Id, nome e etc).

    Exemplo de fluxo com JWT:

```mermaid
    sequenceDiagram
        participant Client
        participant Server
        Client->>Server: POST /login (credenciais)
        Server->>Server: Gera JWT (generateToken())
        Server-->>Client: Retorna JWT
        Client->>Server: GET /dados (Authorization: Bearer <JWT>)
        Server->>Server: Valida JWT (isTokenValid())
        alt Token válido
            Server-->>Client: 200 OK (dados protegidos)
        else Token inválido
            Server-->>Client: 401 Unauthorized
        end
```

```mermaid
sequenceDiagram
    participant Client
    participant Server
    Client->>Server: POST /login (credenciais)
    Server->>Server: Gera JWT (generateToken())
    Server-->>Client: Retorna JWT
    Client->>Server: GET /dados (Authorization: Bearer <JWT>)
    Server->>Server: Valida JWT (isTokenValid())
    alt Token válido
        Server-->>Client: 200 OK (dados protegidos)
    else Token inválido
        Server-->>Client: 401 Unauthorized
    end
```

    Começo dos teste.

    Decidi começar com os testes do meu codigo. Foi uma parte bem complicada, mas existem algumas diferenças entre os testes:

        -Teste unitário: O teste unitário, verifica uma parte isolada do codigo, testando somente uma classe ou um metodo especifico, por exemplo o teste que fiz com a validação do Token JWT;

        -Teste de integração: O teste de integração, utiliza todo o contexto de um aplicação, passando por todas as etapas, banco de dados, validação da aplicação, ou seja testes reais;

        -Existe tambem um caso intermediário especifico, conhecido como "slice test", teste de controller com MockMvc + @MockBean, ou seja, um teste de componente com Spring. Foi o caso que utilizei no meu codigo por enquanto para o teste do AuthController.
