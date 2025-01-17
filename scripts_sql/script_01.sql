
-- Habilitar usuário administrador e redefinir senha (é o usuário que a aplicação está usando para se conectar)
/* Essa senha só está aqui nesse script para fins de facilidade, 
   sei que não deveria estar aqui e nem commitada no repositório. */
ALTER LOGIN sa ENABLE;
ALTER LOGIN sa WITH PASSWORD = 'sa@12345678@sa';

-- Criar base de dados para a aplicação e começar a usá-la
CREATE DATABASE tarefas
USE tarefas

-- Criar tabelas
CREATE TABLE TBL_TAREFAS (
	ID_TAREFA INT PRIMARY KEY IDENTITY,
	TITULO VARCHAR(40) NOT NULL,
	DESCRICAO VARCHAR(400) NOT NULL,
	DATA_CRIACAO DATETIME NOT NULL,
	ID_STATUS INT NOT NULL
)

CREATE TABLE TBL_TAREFAS_STATUS (
	ID_STATUS INT PRIMARY KEY IDENTITY,
	NOME VARCHAR(40) NOT NULL
)

-- Criar chave estrangeira
ALTER TABLE TBL_TAREFAS ADD FOREIGN KEY (ID_STATUS) REFERENCES TBL_TAREFAS_STATUS(ID_STATUS)









