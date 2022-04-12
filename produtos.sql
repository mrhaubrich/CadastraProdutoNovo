CREATE TABLE produto(
codigo	int	NOT NULL,
nome	varchar(60)	NULL,
descricao	varchar(255)	NULL
);

ALTER TABLE produto
ADD CONSTRAINT pk_produto PRIMARY KEY
(codigo);

INSERT INTO produto(
            codigo, nome, descricao)
    VALUES (1, 'Coca-cola', 'Garrafa PET 600ml');