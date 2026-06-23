-- Script manual para limpar e povoar o H2 durante os estudos.
-- Rode no H2 Console quando quiser voltar para uma base previsivel.

SET REFERENTIAL_INTEGRITY FALSE;

DELETE FROM tb_cadastro_de_ninjas;
DELETE FROM tb_missoes;

ALTER TABLE tb_cadastro_de_ninjas ALTER COLUMN id RESTART WITH 1;
ALTER TABLE tb_missoes ALTER COLUMN id RESTART WITH 1;

SET REFERENTIAL_INTEGRITY TRUE;

INSERT INTO tb_missoes (dificuldade, nome, recompensa) VALUES
('Facil', 'Resgate do gato perdido', '100 ryos'),
('Medio', 'Escolta do comerciante', '500 ryos'),
('Dificil', 'Capturar ninja fugitivo', '2000 ryos'),
('Rank S', 'Proteger a vila de uma invasao', '10000 ryos');

INSERT INTO tb_cadastro_de_ninjas (email, idade, img_url, nome, rank, missoes_id) VALUES
('naruto@konoha.com', 17, 'https://example.com/naruto.png', 'Naruto Uzumaki', 'Genin', 1),
('sasuke@konoha.com', 17, 'https://example.com/sasuke.png', 'Sasuke Uchiha', 'Genin', 2),
('sakura@konoha.com', 17, 'https://example.com/sakura.png', 'Sakura Haruno', 'Chunin', 2),
('kakashi@konoha.com', 31, 'https://example.com/kakashi.png', 'Kakashi Hatake', 'Jonin', 4);

SELECT * FROM tb_missoes;
SELECT * FROM tb_cadastro_de_ninjas;
