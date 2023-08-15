
-- palmas121 - senha: batman007 ; adm senha: miracema34



INSERT INTO usuario (email, login, password, nome, cpf, data_de_nascimento, data_criacao, status_usuario) VALUES
('wesley@gmail.com', 'palmas121', '2gRNvQi2vGKCQtVLp/mIYvgXiGKcng0WMV4vnb08zzikr3QO+wYWaa6xI598ERef+4CnuOFbeUjo33/C6MFZZg==', 'Wesley Dias', '12345678900', '1990-01-01', now(), 'COMPLETO'),
('marcos@gmail.com', 'adm', 'TwQipYDNLIqXcmhou3zAXjy0OJunXXwnSg3+WURG4LoX1H62Q+Jehy1+vXkdbbwh2+TXL5MRMVPXCRKKEyhAUg==', 'Marcos Dias', '05714832167', '2002-07-23', now(), 'COMPLETO');

INSERT INTO usuario_roles (usuario_id, roles) VALUES (1, 'ADMIN');
INSERT INTO usuario_roles (usuario_id, roles) VALUES (1, 'USER');
INSERT INTO usuario_roles (usuario_id, roles) VALUES (2, 'USER');
INSERT INTO usuario_roles (usuario_id, roles) VALUES (2, 'ADMIN');

insert into endereco(principal, bairro, cidade, complemento, estado, rua, numero)
VALUES (true,'St. universitário', 'Miracema', 'Perto UFT', 'TOCANTINS', 'Rua 41', '180'),
        (true,'1104 sul', 'Palmas', '', 'TOCANTINS', 'Alameda 08', 'Lote 22');
insert into usuario_endereco(usuario_id, enderecos_id) VALUES (1,1),(2,2);

INSERT INTO marca(nome) VALUES ('7hz');
INSERT INTO marca(nome) VALUES ('Letshuer');
INSERT INTO marca(nome) VALUES ('Moondrop');
INSERT INTO marca(nome) VALUES ('Kz');
INSERT INTO marca(nome) VALUES ('Truthear');


Insert Into produto(denunciado, alterado_por, marca_id, registrado_por, nome) VALUES (false, null, 1, 1, '7hz timeless' ), (false, null, 2, 2, 'D13' );

insert into inear(impendancia, id, descricaobase, driver, pino)
VALUES (32, 1, 'Planar magnetico de ultima geração', 'Planar magnetico 14.5mm', '0.78'),
(16, 2, 'Fone barato e confortavel, com foco nos graver', 'Driver dinamico 13mm', '0.78');


INSERT INTO anuncio(denunciado, data_criacao, endereco_id, produto_id, usuario_id, descricao_base, status_anuncio, titulo)
VALUES (false,now(), 1, 1, 1, 'Top', 'ATIVO', '7hz timeless'),
(false, now(), 2, 2, 2, 'D13','ATIVO', 'Fone Letshuer d13');


INSERT INTO variacao(descricao, peso, quantidade_estoque, usado, valor_liquido,valor_bruto, cor ) VALUES
('Timeless 4.4 balanceado', 0.5, 10, false, 1000.00, 900.00, 'Preto' ),
('Timeless 3.5', 0.5, 5, false, 1000.00, 900.00, 'Preto'),
('Timeless AE', 0.7, 5, false, 1260.00, 900.00, 'Azul');


INSERT INTO variacao(descricao, peso, quantidade_estoque, usado, valor_liquido,valor_bruto, cor ) VALUES
('D13 PRO', 0.5, 10, false, 500.00, 550.00, 'Preto' ),
('D13', 0.5, 5, false, 400.00, 440.00, 'Azul');

 INSERT INTO anuncio_variacao(anuncio_id, variacao_id) VALUES
 (1, 1),
 (1, 2),
 (1, 3),
 (2, 4),
 (2, 5);

