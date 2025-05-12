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
    -Inserido o padrão de do Basic Auth com Spring security(Posteriormente vou fazer modificações afim de emlhorar a autenticação);
    

Primeiros Testes com Banco de Dados junto com Postman

<img width="572" alt="image" src="https://github.com/user-attachments/assets/c532181c-5abe-4c27-82c1-e1a038c6399c" />

<img width="764" alt="image" src="https://github.com/user-attachments/assets/66ea33a7-6749-456a-8bd3-d3326f274162" />
